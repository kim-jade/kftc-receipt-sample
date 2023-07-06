package com.stagefive.kftcreceiptsample.enums;

import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 0400/0410 전문에서 사용하는 조회 결과 코드
 */
@Getter
@AllArgsConstructor
public enum ReadResponseCode {

  // 정상
  SUCCESS("000"),
  // 처리중
  PRECESSING("400"),
  // 송신중단
  SENDING_STP("411"),
  // MAC 검증값 오류
  MAC_ERROR("412"),
  // 데이터 오류(FORMAT 오류 등)
  DATA_ERROR("413"),
  // 데이터 변형 오류
  DATA_TRANSFORM_ERROR("414"),
  // 조회대상파일 존재하지 않음
  NOT_EXIST("415");

  final String code;

  private static final Map<String, ReadResponseCode> BY_CODE_MAP = new HashMap<>();

  static {
    for (ReadResponseCode responseCode : ReadResponseCode.values()) {
      BY_CODE_MAP.put(responseCode.getCode(), responseCode);
    }
  }

  public static ReadResponseCode getByCode(String name) {
    return BY_CODE_MAP.get(name);
  }
}
