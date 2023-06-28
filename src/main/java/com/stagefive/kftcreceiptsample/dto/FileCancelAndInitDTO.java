package com.stagefive.kftcreceiptsample.dto;

import com.stagefive.kftcreceiptsample.dto.common.CommonLayout;

/**
 * 0500 / 0510 전문
 * 파일 취소/초기화 요구(0500)/응답(0510)
 */
public class FileCancelAndInitDTO extends CommonLayout {
  byte[] fileProcessedDate; // 파일 처리일자
  byte[] fileName; // 취소/초기화 대상 파일명
  byte[] responseCode; // 취소/초기화 결과코드
}
