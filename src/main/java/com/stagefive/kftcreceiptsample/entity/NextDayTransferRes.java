package com.stagefive.kftcreceiptsample.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.Date;
import lombok.Getter;

@Entity
@Getter
public class NextDayTransferRes {
  @Id
  Long id;
  Date reqDt;
  String memberId;
  String bankCd;
  String bankAccNo;
  String memberBirthDt;
  String memberCmpnRegNo;
  String currencyType;
  String withdrawalType;
  String cashReceiptVerificInfo;
  String resCd;
  String errCd;
  Date rgstDt;
}
