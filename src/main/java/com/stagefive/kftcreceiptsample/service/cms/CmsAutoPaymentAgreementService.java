package com.stagefive.kftcreceiptsample.service.cms;

import com.stagefive.kftcreceiptsample.dto.cms.FileInfoDTO;
import com.stagefive.kftcreceiptsample.dto.cms.MissingCheckResponseDTO;
import com.stagefive.kftcreceiptsample.dto.cms.TaskDTO;
import com.stagefive.kftcreceiptsample.dto.cms.common.CommonHeader;
import com.stagefive.kftcreceiptsample.enums.ResponseCode;
import com.stagefive.kftcreceiptsample.enums.TaskManagementInfo;
import com.stagefive.kftcreceiptsample.enums.TaskType;
import io.netty.channel.ChannelHandlerContext;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.springframework.stereotype.Service;

@Service
public class CmsAutoPaymentAgreementService {

  /*
  기관에서 센터로 송신할 파일이 있을 경우 다음과 같은 순서로 업무개시 처리를 한다.
  1) 센터로 ｢업무개시요구(0600/001)｣전문을 송신한다.
  2) 센터로부터 ｢업무개시통보(0610/001)｣전문이 정상으로 수신되면 송신처리를 시작한다.
  3) ｢업무개시요구(0600/001)｣전문을 송신한 후 센터로부터 응답이 없거나, TIMEOVER 된 경우에는 ｢2. 4. 2 장애종류별 처리｣의 절차를 따른다.
   */
  public BlockingQueue<byte[]> processAutoPaymentAgreement(byte[] data, ChannelHandlerContext ctx) {
    // 헤더정보에서 어떤 업무 전문인지 확인
    CommonHeader header = new CommonHeader(data);
    TaskType typeCode = header.getTypeCode();

    BlockingQueue<byte[]> response = new LinkedBlockingQueue<>();

    switch (typeCode) {
      // 업무개시
      case TASK_MANAGEMENT_RESPONSE -> {
        TaskDTO taskDTO = new TaskDTO(data);
        TaskManagementInfo taskManagementInfo = taskDTO.getTaskManagementInfo();

        // 센터로부터 ｢업무개시통보(0610/001)｣전문이 정상으로 수신되면 송신처리를 시작한다.
        if (taskManagementInfo == TaskManagementInfo.START_TASK) {
          // 기관에서는 송신할 파일의｢파일정보확인요구(0630)｣전문을 송신한다.
          response.add(generateFileInfoReq());
        }
      }

      /*
      센터에서는 기관으로부터 ｢파일정보확인요구(0630)｣ 전문을 수신하면 ｢파일정보확인통보(0640)｣ 전문을 응답으로 송신한다.
       */
      case FILE_INFO_MANAGEMENT_RESPONSE -> {
        FileInfoDTO fileInfoDTO = new FileInfoDTO(data);
        ResponseCode responseCode = fileInfoDTO.getResponseCode();

        /*
        기관에서는 센터로부터｢파일정보확인통보(0640)｣전문을 수신한 다음, DATA의 송신처리를 시작한다.
        이 때, 해당 파일SIZE를 비교하여 기 송신 완료된 파일인 경우, 파일전송완료 처리를한다.
         */
        if (responseCode == ResponseCode.ALREADY_SEND) {
          // - 해당파일 기 수신완료 시 : 응답코드 "630"
          response.add(generateFileSendComplete());
        } else if (responseCode == ResponseCode.SUCCESS && fileInfoDTO.getFileSize() == 0) {
          // - 최초 시 : 응답코드 "000"
          response.add(generateSendData());
          // 기관에서는 ｢DATA (0320)｣전문을 BLOCK단위로 송신하고 한 BLOCK 전송이 끝난 후 센터로｢결번확인요구(0620)｣전문을 송신한다.
          response.add(generateMissingNumbersReq());
        } else if (responseCode == ResponseCode.SUCCESS && fileInfoDTO.getFileSize() > 0) {
          // - 미완료 시 : 응답코드 "000"
          response.add(generateSendData(fileInfoDTO.getFileSize()));
          // 기관에서는 ｢DATA (0320)｣전문을 BLOCK단위로 송신하고 한 BLOCK 전송이 끝난 후 센터로｢결번확인요구(0620)｣전문을 송신한다.
          response.add(generateMissingNumbersReq());
        }
      }

      /*
      결번이 있는 경우 ｢결번확인통보(0300)｣전문의 해당되는 결번개수를 SET하고 결번대상FIELD에 "0"을 SET하여 기관으로 송신하고,
      결번이 없는 경우에는 ｢결번확인통보(0300)｣전문의결번개수를 "0"으로 SET하고 결번확인 FIELD를 모두 "1" 로 SET하여 기관으로 송신한다.
       */
      case MISS_DATA_RESPONSE -> {
        MissingCheckResponseDTO missingCheckResponseDTO = new MissingCheckResponseDTO(data);

        /*
        ｢결번DATA전송(0310)｣전문으로 재작성하여 결번처리를 반복하고 결번전문이없을 경우에는 해당 파일에 대하여 추가 송신할 DATA BLOCK이 있는 경우에는 다음 DATA
        BLOCK부터 송신처리를 반복하고, 추가 DATA BLOCK이 없는 경우에는 파일전송완료처리를한다.
         */
        if (missingCheckResponseDTO.getMissingCount() == 0) {
          response.add(generateFileSendComplete());
        } else {
          response.add(generateMissingNumbersDataSend());
        }
      }

      case TASK_MANAGEMENT -> {
        TaskDTO taskDTO = new TaskDTO(data);
        TaskManagementInfo taskManagementInfo = taskDTO.getTaskManagementInfo();

        if (taskManagementInfo == TaskManagementInfo.START_TASK) {
          return null;
        } else if (taskManagementInfo == TaskManagementInfo.END_TASK) {
          ctx.close();
        }
      }
    }

    return response;
  }

  private byte[] generateMissingNumbersDataSend() {
    return new byte[0];
  }

  private byte[] generateMissingNumbersReq() {
    return new byte[0];
  }

  private byte[] generateSendData(int fileSize) {
    return new byte[0];
  }

  private byte[] generateSendData() {
    return new byte[0];
  }

  private byte[] generateFileSendComplete() {
    return new byte[0];
  }

  private byte[] generateFileInfoReq() {
    return new byte[0];
  }
}
