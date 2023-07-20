package com.stagefive.kftcreceiptsample.dto.cms;

import com.stagefive.kftcreceiptsample.dto.cms.common.CommonHeader;
import com.stagefive.kftcreceiptsample.util.ParserUtil;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 0620 전문
 * 결번 확인 요구
 */
@NoArgsConstructor
@Setter
public class MissingCheckRequestDTO extends CommonHeader {
  // block의 순차적 번호
  private int blockNo;
  // 최종 시퀀스 번호
  private int finalSequenceNo;

  public MissingCheckRequestDTO(byte[] data) {
    super(data);
    this.blockNo = ParserUtil.getIntFromByteArray(data, 32, 36);
    this.finalSequenceNo = ParserUtil.getIntFromByteArray(data, 36, 39);
  }

  public byte[] getByte() {
    byte[] result = new byte[super.getByte().length + 7];
    byte[] header = super.getByte();
    int offset = 0;

    offset = ParserUtil.copyBytes(result, header, offset, header.length);
    offset = ParserUtil.copyBytes(String.valueOf(blockNo).getBytes(), result, offset, 4);
    ParserUtil.copyBytes(String.valueOf(finalSequenceNo).getBytes(), result, offset, 3);

    return result;
  }
}
