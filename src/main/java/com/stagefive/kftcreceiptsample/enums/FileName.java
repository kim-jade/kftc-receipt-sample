package com.stagefive.kftcreceiptsample.enums;

import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * CMS 전문 파일명
 */
@Getter
@AllArgsConstructor
public enum FileName {

  // 출금이체신청내역
  TRANSFER_REGISTRATION_REQUEST("EB13"),
  // 출금이체 신청결과내역
  TRANSFER_REGISTRATION_RESPONSE("EB14"),
  // 자동납부동의자료내역
  AUTO_PAYMENT_AGREEMENT_REQUEST("EI13"),
  // 출금의뢰내역(익일출금)
  NEXT_DAY_TRANSFER_REQUEST("EB21"),
  // 출금결과내역(익일출금)
  NEXT_DAY_TRANSFER_RESPONSE("EB22");

  private final String code;

  private static final Map<String, FileName> BY_CODE_MAP = new HashMap<>();

  static {
    for (FileName taskType : FileName.values()) {
      BY_CODE_MAP.put(taskType.getCode(), taskType);
    }
  }

  public static FileName getByCode(String name) {
    return BY_CODE_MAP.get(name);
  }
}
