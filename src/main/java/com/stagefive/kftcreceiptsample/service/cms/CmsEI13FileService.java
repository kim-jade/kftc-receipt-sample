package com.stagefive.kftcreceiptsample.service.cms;

import com.stagefive.kftcreceiptsample.constants.CmsConstants;
import com.stagefive.kftcreceiptsample.dto.cms.EI13.EI13DataDTO;
import com.stagefive.kftcreceiptsample.dto.cms.EI13.EI13FileDTO;
import com.stagefive.kftcreceiptsample.dto.cms.EI13.EI13HeaderDTO;
import com.stagefive.kftcreceiptsample.dto.cms.EI13.EI13TrailerDTO;
import com.stagefive.kftcreceiptsample.util.Util;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CmsEI13FileService {

  /**
   * 자동납부동의자료 파일 생성
   * @return 자동납부동의자료 파일 DTO
   */
  public EI13FileDTO generateEI13File() {
    byte[] file1 = Util.createRepeatString("D",2970).getBytes();
    byte[] file2 = Util.createRepeatString("A",1929).getBytes();
    List<byte[]> files = new ArrayList<>();
    files.add(file1);
    files.add(file2);

    int totalDataCount = files.size();

    EI13HeaderDTO headerDTO = createEI13Header(totalDataCount);
    List<EI13DataDTO> dataDTOList = createEI13Data(files);

    int totalBlockCount = calculateTotalDataBlock(files);
    EI13TrailerDTO trailerDTO = createEI13Trailer(totalDataCount, totalBlockCount);

    EI13FileDTO fileDTO = EI13FileDTO.builder()
        .header(headerDTO)
        .EI13DataDTOList(dataDTOList)
        .trailer(trailerDTO)
        .build();

    try {
      File file = new File("EI13");
      FileOutputStream fileOutputStream = new FileOutputStream(file);
      fileOutputStream.write(fileDTO.getByte());
      fileOutputStream.close();
    } catch (Throwable e) {
      e.printStackTrace(System.out);
    }

    return fileDTO;
  }

  private EI13TrailerDTO createEI13Trailer(int totalDataCount, int totalDataBlockCount) {
    return EI13TrailerDTO.builder()
        .taskTypeCode("AE1112")
        .recordType(33)
        .serialNumber(9999999)
        .enterpriseCode(CmsConstants.ENTERPRISE_ID)
        .totalDataRecordCount(totalDataCount)
        .totalDataBlockCount(totalDataBlockCount)
        .filler(Util.createSpaceString(972))
        .build();
  }

  private List<EI13DataDTO> createEI13Data(List<byte[]> files) {
    List<EI13DataDTO> ei13DataDTOList = new ArrayList<>();
    for (int i = 0; i < files.size(); i++) {
      byte[] fileData = files.get(i);
      int fillerSize = (1024 - (119 + fileData.length) % 1024) % 1024;

      EI13DataDTO dataDTO = EI13DataDTO.builder()
          .taskTypeCode("AE1112")
          .enterpriseId(CmsConstants.ENTERPRISE_ID)
          .recordType(22)
          .serialNumber(i + 1)
          .fillerOne(Util.createSpaceString(10))
          .memberId("납부자번호" + (i + 1))
          .bankCode(030)
          .bankAccountNumber("계좌번호" + (i + 1))
          .reqDate(Util.getNowDateToInt("yyyyMMdd"))
          .dataType("7")
          .dataExtension("jpg")
          .dataLength(fileData.length)
          .data(fileData)
          .fillerTwo(Util.createSpaceString(fillerSize))
          .build();

      ei13DataDTOList.add(dataDTO);
    }

    return ei13DataDTOList;
  }

  private EI13HeaderDTO createEI13Header(int totalDataCount) {
    EI13HeaderDTO headerDTO = new EI13HeaderDTO();
    headerDTO.setRecordType(11);
    headerDTO.setSerialNumber(0000000);
    headerDTO.setReqDate(Integer.parseInt(Util.getNowDateToString("yyyyMMdd")));
    headerDTO.setEnterpriseId(CmsConstants.ENTERPRISE_ID);
    headerDTO.setTotalDataCount(totalDataCount);
    headerDTO.setFiller(Util.createSpaceString(974));
    return headerDTO;
  }

  private int calculateTotalDataBlock(List<byte[]> files) {
    int totalDataBlocks = 2;
    for (byte[] fileData : files) {
      int fillerSize = (1024 - (119 + fileData.length) % 1024) % 1024;
      int blockSize = (int) Math.ceil((119 + fileData.length + fillerSize) / 1024.0);
      totalDataBlocks += blockSize;
    }
    return totalDataBlocks;
  }
}
