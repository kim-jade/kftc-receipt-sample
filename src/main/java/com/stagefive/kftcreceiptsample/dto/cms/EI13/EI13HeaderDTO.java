package com.stagefive.kftcreceiptsample.dto.cms.EI13;

import com.stagefive.kftcreceiptsample.constants.CmsConstants;
import com.stagefive.kftcreceiptsample.util.ParserUtil;
import com.stagefive.kftcreceiptsample.util.Util;
import lombok.Setter;
import lombok.ToString;

@Setter
@ToString
public class EI13HeaderDTO {
  private String taskTypeCode;
  private int recordType;
  private int serialNumber;
  private int reqDate;
  private String enterpriseId;
  private int totalDataCount;
  private String filler;

  public EI13HeaderDTO() {
    this.taskTypeCode = "AE1112";
    this.recordType = 11;
    this.serialNumber = 0000000;
    this.reqDate = Integer.parseInt(Util.getNowDateToString("yyyyMMDD"));
    this.enterpriseId = CmsConstants.ENTERPRISE_ID;
  }

  public byte[] getByte() {
    byte[] result = new byte[1024];
    int offset = 0;

    offset = ParserUtil.copyBytes(taskTypeCode, result, offset, 6);
    offset = ParserUtil.copyBytes(recordType, result, offset, 2);
    offset = ParserUtil.copyBytes(serialNumber, result, offset, 7);
    offset = ParserUtil.copyBytes(reqDate, result, offset, 8);
    offset = ParserUtil.copyBytes(enterpriseId, result, offset, 20);
    offset = ParserUtil.copyBytes(totalDataCount, result, offset, 7);
    ParserUtil.copyBytes(filler, result, offset, 974);

    return result;
  }
}
