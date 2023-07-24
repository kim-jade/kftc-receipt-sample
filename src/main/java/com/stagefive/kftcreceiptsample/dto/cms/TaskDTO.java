package com.stagefive.kftcreceiptsample.dto.cms;

import com.stagefive.kftcreceiptsample.constants.CmsConstants;
import com.stagefive.kftcreceiptsample.dto.cms.common.CommonHeader;
import com.stagefive.kftcreceiptsample.enums.TaskManagementInfo;
import com.stagefive.kftcreceiptsample.util.EncryptionUtil;
import com.stagefive.kftcreceiptsample.util.ParserUtil;
import com.stagefive.kftcreceiptsample.util.Util;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 0600 / 0610 전문 처리를 담당하는 DTO
 * 업무개시 요구(0600) / 응답(0610)
 * 업무종료 요구(0600) / 응답(0610)
 * 파일전송완료 요구(0600) / 응답(0610)
 */
@Getter
@Setter
@ToString(callSuper = true)
public class TaskDTO extends CommonHeader {
  // 전문 전송 일시
  private String timestamp;
  // 업무 관리 정보
  private TaskManagementInfo taskManagementInfo;
  // 송신자명
  private String senderName;
  // 송신자 암호
  private String senderPassword;

  public TaskDTO() {
    this.timestamp = Util.getNowDateToString("MMddHHmmss");
    this.senderName = CmsConstants.SENDER_NAME;
    this.senderPassword = EncryptionUtil.generateSenderPassword();
  }

  public TaskDTO(byte[] data) {
    super(data);
    this.timestamp = ParserUtil.getStringFromByteArray(data, 32, 42);
    this.taskManagementInfo = TaskManagementInfo.getByCode(ParserUtil.getStringFromByteArray(data, 42, 45));
    this.senderName = ParserUtil.getStringFromByteArray(data, 45, 65);
    this.senderPassword = ParserUtil.getStringFromByteArray(data, 65, 81);
  }

  public byte[] getByte() {
    byte[] header = super.getByte();
    byte[] result = new byte[header.length + 49];
    int offset = 0;

    offset = ParserUtil.copyBytes(header, result, offset, header.length);
    offset = ParserUtil.copyBytes(timestamp, result, offset, 10);
    offset = ParserUtil.copyBytes(taskManagementInfo.getCode(), result, offset, 3);
    offset = ParserUtil.copyBytes(senderName, result, offset, 20);
    ParserUtil.copyBytes(this.senderPassword, result, offset, 16);

    return result;
  }

  public void setStartTaskRequestData() {
    this.timestamp = Util.getNowDateToString("MMddHHmmss");
    this.taskManagementInfo = TaskManagementInfo.START_TASK;
    this.senderName = "jade";
    this.senderPassword = "jadePassword";
  }
}
