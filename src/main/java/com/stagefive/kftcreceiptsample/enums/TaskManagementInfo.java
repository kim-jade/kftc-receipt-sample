package com.stagefive.kftcreceiptsample.enums;

import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * CMS 0600 / 0610 전문의 업무관리정보 값
 */
@Getter
@AllArgsConstructor
public enum TaskManagementInfo {

  START_TASK("001"), // 업무 개시
  FILE_EXIST_SAR_COMPLETE("002"), // 파일 송수신 완료( 송신할 파일 존재 )
  FILE_NOT_EXIST_SAR_COMPLETE("003"), // 파일 송수신 완료( 송신할 파일 없음 )
  END_TASK("004"); // 업무 종료

  private final String code;

  private static final Map<String, TaskManagementInfo> BY_CODE_MAP = new HashMap<>();

  static {
    for (TaskManagementInfo taskManagementInfo : TaskManagementInfo.values()) {
      BY_CODE_MAP.put(taskManagementInfo.getCode(), taskManagementInfo);
    }
  }

  public static TaskManagementInfo getByCode(String name) {
    return BY_CODE_MAP.get(name);
  }
}
