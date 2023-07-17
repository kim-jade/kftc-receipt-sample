package com.stagefive.kftcreceiptsample.repository;

import com.stagefive.kftcreceiptsample.entity.NextDayTransferRes;
import com.stagefive.kftcreceiptsample.util.Util;
import jakarta.persistence.EntityManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class TransferRequestReqRepository {

  private final EntityManager em;

  public List<NextDayTransferRes> findAll() {
    String tableName = "tb_next_day_transfer_res";
    String dateFormat = Util.getNowDateToString("yyyyMM");
    String fullTableName = String.format("%s_%s", tableName, dateFormat);
    String selectQuery = String.format("select * from %s", fullTableName);
    return em.createNativeQuery(selectQuery, NextDayTransferRes.class).getResultList();
  }

  @Transactional
  public void createTableByDate() {
    String tableName = "tb_next_day_transfer_res";
    String dateFormat = Util.getNowDateToString("yyyyMM");
    String fullTableName = String.format("%s_%s", tableName, dateFormat);
    String createQuery = String.format("CREATE TABLE %s ("
        + "id int not null auto_increment primary key,"
        + "req_dt datetime,"
        + "member_id varchar(30),"
        + "bank_cd varchar(30),"
        + "bank_acc_no varchar(30),"
        + "bill_amt varchar(30),"
        + "member_birth_dt varchar(30),"
        + "member_cmpn_reg_no varchar(30),"
        + "currency_type varchar(30),"
        + "withdrawal_type varchar(30),"
        + "cash_receipt_verific_info varchar(30),"
        + "res_cd varchar(30),"
        + "err_cd varchar(30),"
        + "rgst_dt datetime default current_timestamp"
        + ")", fullTableName);
    em.createNativeQuery(createQuery).executeUpdate();
  }
}
