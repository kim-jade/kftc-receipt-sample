package com.stagefive.kftcreceiptsample.dto.cms;

import com.stagefive.kftcreceiptsample.dto.cms.common.CommonLayout;

/**
 * 0300 전문
 * 결번 확인 응답
 */
public class MissingCheckResponseDTO extends CommonLayout {
  byte[] blockNo; // block의 순차적 번호
  byte[] finalSequenceNo; // 최종 시퀀스 번호
  byte[] missingCount; // 결번 개수
  byte[] missingConfirm; // 결번 확인
}
