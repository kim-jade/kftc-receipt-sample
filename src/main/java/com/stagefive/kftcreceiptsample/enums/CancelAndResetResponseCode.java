package com.stagefive.kftcreceiptsample.enums;

import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 0500/0510 전문에서 사용하는 취소/초기화 결과코드
 */
@Getter
@AllArgsConstructor
public enum CancelAndResetResponseCode {

  // 취소/초기화 성공
  SUCCESS("000"),
  // 파일 처리중(센터에서 파일 검증 처리중. 잠시 후 취소/초기화 시도 바람)
  PRECESSING("500"),
  // 취소/초기화 대상파일 존재하지 않음(수신된 적 없는 파일)
  NOT_EXIST("511"),
  // 기 취소/초기화된 파일
  ALREADY_DONE("512"),
  // 재전송이 불가한 기 취소/초기화 파일
  RESEND_NOT_POSSIBLE("513"),
  // 취소/초기화 불가한 파일종류
  // 취소/초기화 가능한 파일 : EI13, EI16, EB12, EB13, EB21, EC21, EB31
  INVALID_FILE_TYPE("514"),
  // 취소/초기화 요청 마감시한 경과
  REQUEST_OVERDUE("515"),
  // 연계파일 존재
  // EI13 파일은 연계된 EB13 파일을 먼저 취소/초기화한 후에 취소/초기화 가능
  LINKED_FILE_EXIST("516"),
  // 기타 사유로 인한 취소/초기화 실패
  ETC_ERROR("599");

  final String code;

  private static final Map<String, CancelAndResetResponseCode> BY_CODE_MAP = new HashMap<>();

  static {
    for (CancelAndResetResponseCode responseCode : CancelAndResetResponseCode.values()) {
      BY_CODE_MAP.put(responseCode.getCode(), responseCode);
    }
  }

  public static CancelAndResetResponseCode getByCode(String name) {
    return BY_CODE_MAP.get(name);
  }
}
