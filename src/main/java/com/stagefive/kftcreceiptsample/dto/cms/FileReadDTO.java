package com.stagefive.kftcreceiptsample.dto.cms;

import com.stagefive.kftcreceiptsample.dto.cms.common.CommonHeader;
import com.stagefive.kftcreceiptsample.enums.ReadResponseCode;
import com.stagefive.kftcreceiptsample.enums.ReadType;
import com.stagefive.kftcreceiptsample.util.ParserUtil;

/**
 * 0400 / 0410 전문
 * 파일조회 요구(0400) / 응답(0410)
 */
public class FileReadDTO extends CommonHeader {
  private ReadType readType; // 조회 종류
  private int fileTimestamp; // 조회 대상 일자
  private String fileName; // 조회 대상 파일명
  private ReadResponseCode resultCode; // 조회 결과 코드

  public FileReadDTO(byte[] data) {
    super(data);
    this.readType = ReadType.getByCode(ParserUtil.getStringFromByteArray(data, 32, 35));
    this.fileTimestamp = ParserUtil.getIntFromByteArray(data, 35, 43);
    this.fileName = ParserUtil.getStringFromByteArray(data, 43, 51);
    this.resultCode = ReadResponseCode.getByCode(ParserUtil.getStringFromByteArray(data, 51, 54));
  }

  public byte[] getByte() {
    byte[] header = super.getByte();
    byte[] result = new byte[header.length + 22];
    int offset = 0;

    offset = ParserUtil.copyBytes(result, header, offset, header.length);
    offset = ParserUtil.copyBytes(readType.getCode().getBytes(), result, offset, 3);
    offset = ParserUtil.copyBytes(String.valueOf(fileTimestamp).getBytes(), result, offset, 8);
    offset = ParserUtil.copyBytes(fileName.getBytes(), result, offset, 8);
    ParserUtil.copyBytes(resultCode.getCode().getBytes(), result, offset, 3);

    return result;
  }
}
