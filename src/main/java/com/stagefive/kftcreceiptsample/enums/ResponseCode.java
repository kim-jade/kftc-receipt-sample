package com.stagefive.kftcreceiptsample.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * CMS에서 사용하는 응답 코드.
 */
@Getter
@AllArgsConstructor
public enum ResponseCode {

  SUCCESS("000"), // 정상
  SYSTEM_ERROR("090"), // 시스템 장애 (비밀번호 오류횟수 초과인 경우도 해당)
  SENDER_ERROR("310"), // 송신자명 오류
  SENDER_PASSWORD_ERROR("320"), // 송신자 암호 오류
  SEND_SUCCESS("630"), // 기 전송완료
  UNREGISTERED_BUSINESS("631"), // 미등록 업무
  INVALID_FILENAME("632"), // 비정상 파일명
  INVALID_BYTE_COUNT("633"), // 비정상 전문 Byte 수
  FILE_SUCCESS("634"), // 파일송신 가능시간/일자 완료
  VERIFICATION_SUCCESS("635"), // EI13파일 검증완료 전 EB13파일 수신
  FORMAT_ERROR("800"); // Format 오류

  final String code;

  private static final Map<String, String> fieldMap;

  static {
    fieldMap = Arrays.stream(ResponseCode.values())
        .collect(Collectors.toMap(ResponseCode::name, ResponseCode::getCode));
  }

  public static String findByCode(String code) {
    return fieldMap.getOrDefault(code, null);
  }
}
