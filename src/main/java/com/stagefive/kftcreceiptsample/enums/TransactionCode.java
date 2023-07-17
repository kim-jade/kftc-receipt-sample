package com.stagefive.kftcreceiptsample.enums;

import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * CMS 전문 헤더 공통부의 거래구분코드
 */
@Getter
@AllArgsConstructor
public enum TransactionCode {

  CENTER_SEND("R"),
  CENTER_RECEIVE("S");

  private final String code;

  private static final Map<String, TransactionCode> BY_CODE_MAP = new HashMap<>();

  static {
    for (TransactionCode taskType : TransactionCode.values()) {
      BY_CODE_MAP.put(taskType.getCode(), taskType);
    }
  }

  public static TransactionCode getByCode(String name) {
    return BY_CODE_MAP.get(name);
  }
}
