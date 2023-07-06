package com.stagefive.kftcreceiptsample.dto.cms.common;


import com.stagefive.kftcreceiptsample.enums.TaskType;
import com.stagefive.kftcreceiptsample.util.ParserUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@ToString
@Getter
@Setter
public class CommonHeader {
  // 전문 BYTE 수
  private int byteCount;
  // 업무 구분 코드
  private String businessTypeCode;
  // 기관 코드
  private String institutionCode;
  // 전문 종별 코드
  private TaskType typeCode;
  // 거래 구분 코드
  private String transactionCode;
  // 송수신 FLAG
  private String sendReceiveFlag;
  // 파일명
  private String fileName;
  // 응답코드
  private String responseCode;

  public CommonHeader(byte[] data) {
    this.byteCount = ParserUtil.getIntFromByteArray(data, 0, 4);
    this.businessTypeCode = ParserUtil.getStringFromByteArray(data, 4, 7);
    this.institutionCode = ParserUtil.getStringFromByteArray(data, 7, 15);
    this.typeCode = TaskType.getByCode(ParserUtil.getStringFromByteArray(data, 15, 19));
    this.transactionCode = ParserUtil.getStringFromByteArray(data, 19, 20);
    this.sendReceiveFlag = ParserUtil.getStringFromByteArray(data, 20, 21);
    this.fileName = ParserUtil.getStringFromByteArray(data, 21, 29);
    this.responseCode = ParserUtil.getStringFromByteArray(data, 29, 32);
  }

  public byte[] getByte() {
    byte[] result = new byte[32];
    int offset = 0;

    offset = ParserUtil.copyBytes(String.valueOf(this.byteCount).getBytes(), result, offset, 4);
    offset = ParserUtil.copyBytes(this.businessTypeCode.getBytes(), result, offset, 3);
    offset = ParserUtil.copyBytes(this.institutionCode.getBytes(), result, offset, 8);
    offset = ParserUtil.copyBytes(this.typeCode.getCode().getBytes(), result, offset, 4);
    offset = ParserUtil.copyBytes(this.transactionCode.getBytes(), result, offset, 1);
    offset = ParserUtil.copyBytes(this.sendReceiveFlag.getBytes(), result, offset, 1);
    offset = ParserUtil.copyBytes(this.fileName.getBytes(), result, offset, 8);
    ParserUtil.copyBytes(this.responseCode.getBytes(), result, offset, 3);

    return result;
  }
}
