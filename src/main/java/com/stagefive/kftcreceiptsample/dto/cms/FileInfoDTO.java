package com.stagefive.kftcreceiptsample.dto.cms;

import com.stagefive.kftcreceiptsample.dto.cms.common.CommonHeader;
import com.stagefive.kftcreceiptsample.util.ParserUtil;

/**
 * 0630 / 0640 전문 처리를 담당하는 DTO
 * 파일 정보 확인 요구(0630) / 응답(0640)
 */

public class FileInfoDTO extends CommonHeader {

  // 파일명
  private String fileName;
  // 파일 사이즈
  private int fileSize;
  // 전문 Byte 수
  private int fileByteCount;

  public FileInfoDTO(byte[] data) {
    super(data);
    this.fileName = ParserUtil.getStringFromByteArray(data, 32, 40);
    this.fileSize = ParserUtil.getIntFromByteArray(data, 40, 52);
    this.fileByteCount = ParserUtil.getIntFromByteArray(data, 52, 56);
  }

  public byte[] getByte() {
    byte[] result = new byte[super.getByte().length + 24];
    byte[] header = super.getByte();
    int offset = 0;

    offset = ParserUtil.copyBytes(result, header, offset, header.length);
    offset = ParserUtil.copyBytes(fileName.getBytes(), result, offset, 8);
    offset = ParserUtil.copyBytes(String.valueOf(fileSize).getBytes(), result, offset, 12);
    ParserUtil.copyBytes(String.valueOf(fileByteCount).getBytes(), result, offset, 4);

    return result;
  }
}
