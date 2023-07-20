package com.stagefive.kftcreceiptsample.dto.cms;

import com.stagefive.kftcreceiptsample.dto.cms.common.CommonHeader;
import com.stagefive.kftcreceiptsample.util.ParserUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 0630 / 0640 전문 처리를 담당하는 DTO
 * 파일 정보 확인 요구(0630) / 응답(0640)
 */

@Getter
@Setter
@NoArgsConstructor
public class FileInfoDTO extends CommonHeader {

  // 파일명
  private String fileInfoName;
  // 파일 사이즈
  private int fileInfoSize;
  // 전문 Byte 수
  private int fileInfoByteCount;

  public FileInfoDTO(byte[] data) {
    super(data);
    this.fileInfoName = ParserUtil.getStringFromByteArray(data, 32, 40);
    this.fileInfoSize = ParserUtil.getIntFromByteArray(data, 40, 52);
    this.fileInfoByteCount = ParserUtil.getIntFromByteArray(data, 52, 56);
  }

  public byte[] getByte() {
    byte[] result = new byte[super.getByte().length + 24];
    byte[] header = super.getByte();
    int offset = 0;

    offset = ParserUtil.copyBytes(result, header, offset, header.length);
    offset = ParserUtil.copyBytes(fileInfoName.getBytes(), result, offset, 8);
    offset = ParserUtil.copyBytes(String.valueOf(fileInfoSize).getBytes(), result, offset, 12);
    ParserUtil.copyBytes(String.valueOf(fileInfoByteCount).getBytes(), result, offset, 4);

    return result;
  }
}
