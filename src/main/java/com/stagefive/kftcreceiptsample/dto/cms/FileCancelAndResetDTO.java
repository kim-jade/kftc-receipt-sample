package com.stagefive.kftcreceiptsample.dto.cms;

import com.stagefive.kftcreceiptsample.dto.cms.common.CommonHeader;
import com.stagefive.kftcreceiptsample.enums.CancelAndResetResponseCode;
import com.stagefive.kftcreceiptsample.enums.ReadResponseCode;
import com.stagefive.kftcreceiptsample.enums.ReadType;
import com.stagefive.kftcreceiptsample.util.ParserUtil;

/**
 * 0500 / 0510 전문
 * 파일 취소/초기화 요구(0500)/응답(0510)
 */
public class FileCancelAndResetDTO extends CommonHeader {
  // 파일 처리일자
  private int fileProcessedDate;
  // 취소/초기화 대상 파일명
  private String fileName;
  // 취소/초기화 결과코드
  private CancelAndResetResponseCode responseCode;

  public FileCancelAndResetDTO(byte[] data) {
    super(data);
    this.fileProcessedDate = ParserUtil.getIntFromByteArray(data, 32, 40);
    this.fileName = ParserUtil.getStringFromByteArray(data, 40, 48);
    this.responseCode = CancelAndResetResponseCode.getByCode(ParserUtil.getStringFromByteArray(data, 48, 51));
  }

  public byte[] getByte() {
    byte[] header = super.getByte();
    byte[] result = new byte[header.length + 19];
    int offset = 0;

    offset = ParserUtil.copyBytes(result, header, offset, header.length);
    offset = ParserUtil.copyBytes(String.valueOf(responseCode).getBytes(), result, offset, 8);
    offset = ParserUtil.copyBytes(fileName.getBytes(), result, offset, 8);
    ParserUtil.copyBytes(responseCode.getCode().getBytes(), result, offset, 3);

    return result;
  }
}
