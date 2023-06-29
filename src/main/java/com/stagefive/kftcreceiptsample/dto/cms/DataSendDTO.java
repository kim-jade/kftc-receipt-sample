package com.stagefive.kftcreceiptsample.dto.cms;

import com.stagefive.kftcreceiptsample.dto.cms.common.CommonLayout;

/**
 * 0320 / 0310 전문
 * Data 전송, 결번 Data 전송
 */
public class DataSendDTO extends CommonLayout {
  byte[] blockNo; // block의 순차적 번호
  byte[] sequenceNo; // 전문의 순차적 번호
  byte[] dataByte; // 파일내역 중에서 실제 Data Byte
  byte[] data; // 실제 Data
}
