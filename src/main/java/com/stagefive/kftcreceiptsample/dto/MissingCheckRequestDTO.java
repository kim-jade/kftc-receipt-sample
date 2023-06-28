package com.stagefive.kftcreceiptsample.dto;

import com.stagefive.kftcreceiptsample.dto.common.CommonLayout;

/**
 * 0620 전문
 * 결번 확인 요구
 */
public class MissingCheckRequestDTO extends CommonLayout {
  byte[] blockNo; // block의 순차적 번호
  byte[] finalSequenceNo; // 최종 시퀀스 번호
}
