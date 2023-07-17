package com.stagefive.kftcreceiptsample.enums;

import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 전문 헤더 공통부의 송수신FLAG enum
 */
@Getter
@AllArgsConstructor
public enum SendReceiveFlag {

  CENTER_OCCUR("C"),
  ENTERPRISE_OCCUR("E");

  private final String code;

  private static final Map<String, SendReceiveFlag> BY_CODE_MAP = new HashMap<>();

  static {
    for (SendReceiveFlag taskType : SendReceiveFlag.values()) {
      BY_CODE_MAP.put(taskType.getCode(), taskType);
    }
  }

  public static SendReceiveFlag getByCode(String name) {
    return BY_CODE_MAP.get(name);
  }
}
