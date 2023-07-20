package com.stagefive.kftcreceiptsample.dto.cms;

import com.stagefive.kftcreceiptsample.dto.cms.common.CommonHeader;
import com.stagefive.kftcreceiptsample.util.ParserUtil;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 0320 / 0310 전문
 * Data 전송, 결번 Data 전송
 */
@NoArgsConstructor
@Setter
public class DataSendDTO extends CommonHeader {

  // block의 순차적 번호
  private int blockNo;
  // 전문의 순차적 번호
  private int sequenceNo;
  // 파일내역 중에서 실제 Data Byte
  private int dataByte;
  // 실제 Data
  private String fileData;

  public DataSendDTO(byte[] data) {
    super(data);
    this.blockNo = ParserUtil.getIntFromByteArray(data, 32, 36);
    this.sequenceNo = ParserUtil.getIntFromByteArray(data, 36, 39);
    this.dataByte = ParserUtil.getIntFromByteArray(data, 39, 43);
    this.fileData = ParserUtil.getStringFromByteArray(data, 43, 5000);
  }

  public byte[] getByte() {
    byte[] result = new byte[super.getByte().length + 5000];
    byte[] header = super.getByte();
    int offset = 0;

    offset = ParserUtil.copyBytes(result, header, offset, header.length);
    offset = ParserUtil.copyBytes(String.valueOf(blockNo).getBytes(), result, offset, 4);
    offset = ParserUtil.copyBytes(String.valueOf(sequenceNo).getBytes(), result, offset, 3);
    offset = ParserUtil.copyBytes(String.valueOf(dataByte).getBytes(), result, offset, 4);
    ParserUtil.copyBytes(fileData.getBytes(), result, offset, 4000);

    return result;
  }
}
