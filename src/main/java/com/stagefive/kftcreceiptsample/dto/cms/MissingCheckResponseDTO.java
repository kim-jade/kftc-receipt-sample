package com.stagefive.kftcreceiptsample.dto.cms;

import com.stagefive.kftcreceiptsample.dto.cms.common.CommonHeader;
import com.stagefive.kftcreceiptsample.util.ParserUtil;
import lombok.Getter;

/**
 * 0300 전문
 * 결번 확인 응답
 */
@Getter
public class MissingCheckResponseDTO extends CommonHeader {
  // block의 순차적 번호
  private int blockNo;
  // 최종 시퀀스 번호
  private int finalSequenceNo;
  // 결번 개수
  private int missingCount;
  // 결번 확인
  private String missingConfirm;

  public MissingCheckResponseDTO(byte[] data) {
    super(data);
    this.blockNo = ParserUtil.getIntFromByteArray(data, 32, 36);
    this.finalSequenceNo = ParserUtil.getIntFromByteArray(data, 36, 39);
    this.missingCount = ParserUtil.getIntFromByteArray(data, 39, 42);
    this.missingConfirm = ParserUtil.getStringFromByteArray(data, 42, 1041); // 최대 999개
  }

  public byte[] getByte() {
    byte[] result = new byte[super.getByte().length + 1041];
    byte[] header = super.getByte();
    int offset = 0;

    offset = ParserUtil.copyBytes(result, header, offset, header.length);
    offset = ParserUtil.copyBytes(String.valueOf(blockNo).getBytes(), result, offset, 4);
    offset = ParserUtil.copyBytes(String.valueOf(finalSequenceNo).getBytes(), result, offset, 3);
    offset = ParserUtil.copyBytes(String.valueOf(missingCount).getBytes(), result, offset, 3);
    ParserUtil.copyBytes(String.valueOf(missingConfirm).getBytes(), result, offset, 100);

    return result;
  }
}
