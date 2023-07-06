package com.stagefive.kftcreceiptsample.dto.cms;

import com.stagefive.kftcreceiptsample.dto.cms.common.CommonHeader;
import com.stagefive.kftcreceiptsample.enums.TaskManagementInfo;
import com.stagefive.kftcreceiptsample.util.ParserUtil;
import lombok.Getter;

/**
 * 0600 / 0610 전문 처리를 담당하는 DTO
 * 업무개시 요구(0060) / 응답(0610)
 * 업무종료 요구(0600) / 응답(0610)
 * 파일전송완료 요구(0600) / 응답(0610)
 */
@Getter
public class TaskDTO extends CommonHeader {
  // 전문 전송 일시
  private String timestamp;
  // 업무 관리 정보
  private TaskManagementInfo taskManagementInfo;
  // 송신자명
  private String senderName;
  // 송신자 암호
  private String senderPassword;

  public TaskDTO(byte[] data) {
    super(data);
    this.timestamp = ParserUtil.getStringFromByteArray(data, 34, 44);
    this.taskManagementInfo = TaskManagementInfo.getByCode(ParserUtil.getStringFromByteArray(data, 44, 47));
    this.senderName = ParserUtil.getStringFromByteArray(data, 47, 67);
    this.senderPassword = ParserUtil.getStringFromByteArray(data, 67, 83);
  }

  public byte[] getByte() {
    byte[] header = super.getByte();
    byte[] result = new byte[header.length + 49];
    int offset = 0;

    offset = ParserUtil.copyBytes(result, header, offset, header.length);
    offset = ParserUtil.copyBytes(timestamp.getBytes(), result, offset, 10);
    offset = ParserUtil.copyBytes(taskManagementInfo.getCode().getBytes(), result, offset, 3);
    offset = ParserUtil.copyBytes(senderName.getBytes(), result, offset, 20);
    ParserUtil.copyBytes(this.senderPassword.getBytes(), result, offset, 16);

    return result;
  }
}
