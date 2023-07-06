package com.stagefive.kftcreceiptsample.enums;

import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * CMS 전문 0400/0410 전문에서 조회 종류
 */
@Getter
@AllArgsConstructor
public enum ReadType {

  // 파일 상태 조회
  FILE_STATUS_READ("001");

  private final String code;

  private static final Map<String, ReadType> BY_CODE_MAP = new HashMap<>();

  static {
    for (ReadType taskType : ReadType.values()) {
      BY_CODE_MAP.put(taskType.getCode(), taskType);
    }
  }

  public static ReadType getByCode(String name) {
    return BY_CODE_MAP.get(name);
  }
}
