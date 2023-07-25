package com.stagefive.kftcreceiptsample.dto.cms.EI13;

import com.stagefive.kftcreceiptsample.util.ParserUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Setter;
import lombok.ToString;

@Setter
@ToString
@Builder
@AllArgsConstructor
public class EI13TrailerDTO {
  // Trailer record
  private String taskTypeCode;
  private int recordType;
  private int serialNumber;
  private String enterpriseCode;
  private int totalDataRecordCount;
  private int totalDataBlockCount;
  private String filler;

  public byte[] getByte() {
    byte[] result = new byte[1024];
    int offset = 0;

    offset = ParserUtil.copyBytes(taskTypeCode, result, offset, 6);
    offset = ParserUtil.copyBytes(recordType, result, offset, 2);
    offset = ParserUtil.copyBytes(serialNumber, result, offset, 7);
    offset = ParserUtil.copyBytes(enterpriseCode, result, offset, 20);
    offset = ParserUtil.copyBytes(totalDataRecordCount, result, offset, 7);
    offset = ParserUtil.copyBytes(totalDataBlockCount, result, offset, 10);
    ParserUtil.copyBytes(filler, result, offset, 972);

    return result;
  }
}
