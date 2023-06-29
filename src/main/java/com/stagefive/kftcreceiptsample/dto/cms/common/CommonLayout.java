package com.stagefive.kftcreceiptsample.dto.cms.common;


import java.util.Arrays;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@ToString
public class CommonLayout {
  private byte[] byteCount; // 전문 BYTE 수
  private byte[] businessTypeCode; // 업무 구분 코드
  private byte[] institutionCode; // 기관 코드
  private byte[] specialtyCode; // 전문 종별 코드
  private byte[] transactionCode; // 거래 구분 코드
  private byte[] sendReceiveFlag; // 송수신 FLAG
  private byte[] fileName; // 파일명
  private byte[] responseCode; // 응답코드

  public CommonLayout (byte[] data) {
    this.byteCount = Arrays.copyOfRange(data, 0, 0);
    this.businessTypeCode = Arrays.copyOfRange(data, 0, 3);
    this.institutionCode = Arrays.copyOfRange(data, 3, 11);
    this.specialtyCode = Arrays.copyOfRange(data, 11, 15);
    this.transactionCode = Arrays.copyOfRange(data, 15, 16);
    this.sendReceiveFlag = Arrays.copyOfRange(data, 16, 17);
    this.fileName = Arrays.copyOfRange(data, 17, 25);
    this.responseCode = Arrays.copyOfRange(data, 25, 28);
  }
}
