package com.stagefive.kftcreceiptsample.service.cms;

import com.stagefive.kftcreceiptsample.dto.cms.TaskDTO;
import com.stagefive.kftcreceiptsample.enums.SendReceiveFlag;
import com.stagefive.kftcreceiptsample.enums.TaskType;
import com.stagefive.kftcreceiptsample.enums.TransactionCode;
import com.stagefive.kftcreceiptsample.socket.cms.client.CmsClient;
import com.stagefive.kftcreceiptsample.socket.cms.handler.CmsAutoPaymentAgreementHandler;
import io.netty.channel.Channel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CmsBatchService {

  private final CmsClient cmsClient;
  private final CmsAutoPaymentAgreementHandler cmsAutoPaymentAgreementHandler;

  //  @Scheduled(cron = "0/10 * * * * ?")
  public void run() {
    Channel channel = cmsClient.connectCmsServer();
    channel.pipeline().addLast();

    // 소켓 처음 연결 후, 업무 개시 요구 전문 전송 (0600)
    try {
      TaskDTO taskDTO = new TaskDTO();
      taskDTO.setRequestHeader();
      taskDTO.setStartTaskRequestData();
      taskDTO.setTypeCode(TaskType.TASK_MANAGEMENT);
      channel.writeAndFlush(taskDTO.getByte()).sync();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 자동 납부 동의자료 송신 업무 개시
   */
  public void startAutoPaymentAgreementSendTask() {
    // cms 서버 연결
    Channel channel = cmsClient.connectCmsServer();
    // 자동 납부 동의자료 핸들러 추가
    channel.pipeline().addLast(cmsAutoPaymentAgreementHandler);

    // 소켓 처음 연결 후, 업무 개시 요구 전문 전송 (0600)
    try {
      channel.writeAndFlush(getStartAutoPaymentAgreementFile()).sync();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  private byte[] getStartAutoPaymentAgreementFile() {
    TaskDTO taskDTO = new TaskDTO();
    taskDTO.setRequestHeader();
    taskDTO.setTypeCode(TaskType.TASK_MANAGEMENT);
    taskDTO.setByteCount(1000);
    taskDTO.setTransactionCode(TransactionCode.CENTER_RECEIVE);
    taskDTO.setSendReceiveFlag(SendReceiveFlag.ENTERPRISE_OCCUR);
    return taskDTO.getByte();
  }
}
