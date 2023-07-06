package com.stagefive.kftcreceiptsample.dto.org_cms.cms.common;


import com.stagefive.kftcreceiptsample.util.ParserUtil;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@ToString
public class CommonLayout {

  private byte[] byteCount = new byte[4]; // 전문 BYTE 수
  private byte[] businessTypeCode = new byte[3]; // 업무 구분 코드
  private byte[] institutionCode = new byte[8]; // 기관 코드
  private byte[] typeCode = new byte[4]; // 전문 종별 코드
  private byte[] transactionCode = new byte[1]; // 거래 구분 코드
  private byte[] sendReceiveFlag = new byte[1]; // 송수신 FLAG
  private byte[] fileName = new byte[8]; // 파일명
  private byte[] responseCode = new byte[3]; // 응답코드

  public CommonLayout(byte[] data) {
    int offset = 0;

    offset = ParserUtil.copyBytes(data, byteCount, offset);
    offset = ParserUtil.copyBytes(data, businessTypeCode, offset);
    offset = ParserUtil.copyBytes(data, institutionCode, offset);
    offset = ParserUtil.copyBytes(data, typeCode, offset);
    offset = ParserUtil.copyBytes(data, transactionCode, offset);
    offset = ParserUtil.copyBytes(data, sendReceiveFlag, offset);
    offset = ParserUtil.copyBytes(data, fileName, offset);

    ParserUtil.copyBytes(data, responseCode, offset);
  }

  public byte[] getByteData() {
    int totalSize = 32;
    int offset = 0;
    byte[] result = new byte[totalSize];

    offset = ParserUtil.copyBytes(byteCount, result, offset);
    offset = ParserUtil.copyBytes(businessTypeCode, result, offset);
    offset = ParserUtil.copyBytes(institutionCode, result, offset);
    offset = ParserUtil.copyBytes(typeCode, result, offset);
    offset = ParserUtil.copyBytes(transactionCode, result, offset);
    offset = ParserUtil.copyBytes(sendReceiveFlag, result, offset);
    offset = ParserUtil.copyBytes(fileName, result, offset);

    ParserUtil.copyBytes(responseCode, result, offset);

    return result;
  }

  public int getByteCount() {
    return Integer.parseInt(ParserUtil.byteToString(byteCount));
  }

  public void setByteCount(int byteCount) {
    this.byteCount = ParserUtil.formatData(Integer.toString(byteCount).getBytes(), this.byteCount.length, true);
  }

  public String getBusinessTypeCode() {
    return ParserUtil.byteToString(businessTypeCode);
  }

  public void setBusinessTypeCode(String businessTypeCode) {
    this.businessTypeCode = ParserUtil.formatData(businessTypeCode.getBytes(), this.businessTypeCode.length, false);
  }

  public String getInstitutionCode() {
    return ParserUtil.byteToString(institutionCode);
  }

  public void setInstitutionCode(String institutionCode) {
    this.institutionCode = ParserUtil.formatData(institutionCode.getBytes(), this.institutionCode.length, false);
  }

  public String getTypeCode() {
    return ParserUtil.byteToString(typeCode);
  }

  public void setTypeCode(String typeCode) {
    this.typeCode = ParserUtil.formatData(typeCode.getBytes(), this.typeCode.length, false);
  }

  public String getTransactionCode() {
    return ParserUtil.byteToString(transactionCode);
  }

  public void setTransactionCode(String transactionCode) {
    this.transactionCode = ParserUtil.formatData(transactionCode.getBytes(), this.transactionCode.length, false);
  }

  public String getSendReceiveFlag() {
    return ParserUtil.byteToString(sendReceiveFlag);
  }

  public void setSendReceiveFlag(String sendReceiveFlag) {
    this.sendReceiveFlag = ParserUtil.formatData(sendReceiveFlag.getBytes(), this.sendReceiveFlag.length, false);
  }

  public String getFileName() {
    return ParserUtil.byteToString(fileName);
  }

  public void setFileName(String fileName) {
    this.fileName = ParserUtil.formatData(fileName.getBytes(), this.fileName.length, false);
  }

  public String getResponseCode() {
    return ParserUtil.byteToString(responseCode);
  }

  public void setResponseCode(String responseCode) {
    this.responseCode = ParserUtil.formatData(responseCode.getBytes(), this.responseCode.length, false);
  }

}
