package com.stagefive.kftcreceiptsample.dto;

import com.stagefive.kftcreceiptsample.dto.common.CommonLayout;

/**
 * 0630 / 0640 전문 처리를 담당하는 DTO
 * 파일 정보 확인 요구(0630) / 응답(0640)
 */
public class FileDTO extends CommonLayout {
  byte[] fileName; // 파일명
  byte[] fileSize; // 파일 사이즈
  byte[] byteCount; // 전문 Byte 수
}
