package com.stagefive.kftcreceiptsample.dto.cms.EI13;

import com.stagefive.kftcreceiptsample.util.ParserUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Setter;
import lombok.ToString;

@Setter
@ToString
@AllArgsConstructor
@Builder
public class EI13DataDTO {
  private String taskTypeCode;
  private int recordType;
  private int serialNumber;
  private String fillerOne;
  private String enterpriseId;
  private String memberId;
  private int bankCode;
  private String bankAccountNumber;
  private int reqDate;
  private String dataType;
  private String dataExtension;
  private int dataLength;
  private byte[] data;
  private String fillerTwo;

  public byte[] getByte() {
    int blockSize = (int) Math.ceil((119 + data.length + fillerTwo.length()) / 1024.0);
    byte[] result = new byte[1024 * blockSize];
    int offset = 0;

    offset = ParserUtil.copyBytes(taskTypeCode, result, offset, 6);
    offset = ParserUtil.copyBytes(recordType, result, offset, 2);
    offset = ParserUtil.copyBytes(serialNumber, result, offset, 7);
    offset = ParserUtil.copyBytes(fillerOne, result, offset, 10);
    offset = ParserUtil.copyBytes(enterpriseId, result, offset, 20);
    offset = ParserUtil.copyBytes(memberId, result, offset, 30);
    offset = ParserUtil.copyBytes(bankCode, result, offset, 3);
    offset = ParserUtil.copyBytes(bankAccountNumber, result, offset, 20);
    offset = ParserUtil.copyBytes(reqDate, result, offset, 8);
    offset = ParserUtil.copyBytes(dataType, result, offset, 1);
    offset = ParserUtil.copyBytes(dataExtension, result, offset, 5);
    offset = ParserUtil.copyBytes(dataLength, result, offset, 7);
    offset = ParserUtil.copyBytes(data, result, offset, data.length);
    ParserUtil.copyBytes(fillerTwo, result, offset, fillerTwo.length());

    return result;
  }
}
