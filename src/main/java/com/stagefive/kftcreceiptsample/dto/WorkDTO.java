package com.stagefive.kftcreceiptsample.dto;

import com.stagefive.kftcreceiptsample.dto.common.CommonLayout;

/**
 * 0600 / 0610 전문 처리를 담당하는 DTO
 * 업무개시 요구(0060) / 응답(0610)
 * 업무종료 요구(0600) / 응답(0610)
 * 파일전송완료 요구(0600) / 응답(0610)
 */
public class WorkDTO extends CommonLayout {
  byte[] timestamp; // 전문 전송 일시
  byte[] taskInfo; // 업무 관리 정보
  byte[] senderName; // 송신자명
  byte[] senderPassword;  // 송신자 암호
}
