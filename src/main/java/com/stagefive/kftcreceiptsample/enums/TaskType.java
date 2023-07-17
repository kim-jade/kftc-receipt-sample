package com.stagefive.kftcreceiptsample.enums;

import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * CMS 전문 Layout 공통부의 전문종별코드에서 사용
 */
@Getter
@AllArgsConstructor
public enum TaskType {

  // 업무 관리
  TASK_MANAGEMENT("0600"),
  // 업무 관리 응답
  TASK_MANAGEMENT_RESPONSE("0610"),
  // 결번 전문 확인
  MISS_CHECK("0620"),
  // 파일 정보 관리
  FILE_INFO_MANAGEMENT("0630"),
  // 파일 정보 관리 응답
  FILE_INFO_MANAGEMENT_RESPONSE("0640"),
  // 조회
  READ("0400"),
  // 조회 응답
  READ_RESPONSE("0410"),
  // 취소/초기화
  CANCEL_AND_INIT("0500"),
  // 취소/초기화 응답
  CANCEL_AND_INIT_RESPONSE("0510"),
  // DATA 전문
  DATA("0320"),
  // 결번자료 내역 전송
  MISS_DATA_RESPONSE("0300"),
  // 결번 DATA 전송
  MISS_DATA_SEND("0310");

  private final String code;

  private static final Map<String, TaskType> BY_CODE_MAP = new HashMap<>();

  static {
    for (TaskType taskType : TaskType.values()) {
      BY_CODE_MAP.put(taskType.getCode(), taskType);
    }
  }

  public static TaskType getByCode(String name) {
    return BY_CODE_MAP.get(name);
  }
}
