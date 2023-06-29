package com.stagefive.kftcreceiptsample.dto.cms;

import com.stagefive.kftcreceiptsample.dto.cms.common.CommonLayout;

/**
 * 0400 / 0410 전문
 * 파일조회 요구(0400) / 응답(0410)
 */
public class FileReadDTO extends CommonLayout {
  byte[] readType; // 조회 종류
  byte[] fileTimestamp; // 조회 대상 일자
  byte[] fileName; // 조회 대상 파일명
  byte[] resultCode; // 조회 결과 코드
}
