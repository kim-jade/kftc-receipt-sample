package com.stagefive.kftcreceiptsample.service.cms;

import com.stagefive.kftcreceiptsample.dto.cms.TaskDTO;
import com.stagefive.kftcreceiptsample.dto.cms.common.CommonHeader;
import com.stagefive.kftcreceiptsample.enums.TaskType;
import org.springframework.stereotype.Service;

@Service
@Deprecated
public class CmsParserService {

  public byte[] serverProcessData(byte[] data) throws InterruptedException {
    // 파일 송신 (센터 -> 기관) 플로우
    String workType = new String(data);
    byte[] result = null;
    switch (workType) {

      // 업무개시[요구] 요청이 왔을 때
      case "0600" -> {
        Thread.sleep(500);
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setRequestHeader();
        taskDTO.setByteCount(1000);
        result = taskDTO.getByte(); // 파일정보확인[지시]
      }

      // 파일정보확인[보고] 요청이 왔을 때
      case "0640" -> {
        result = "0320".getBytes(); // DATA전송
        Thread.sleep(500);
        result = "0620".getBytes(); // 결번확인[지시]
      }

      // 결번확인[보고] 요청이 왔을 때
      case "0300" -> {
        result = "0310".getBytes(); // 결번 DATA 전송
        Thread.sleep(500);
        result = "0600,0".getBytes(); // 파일전송완료 [지시]
      }

      // 파일전송완료[보고] 요청이 왔을 때
      case "0610,0" -> {
        result = "0600,1".getBytes(); // 업무 종료 [지시]
      }

      // 업무종료[보고] 요청이 왔을 때
      case "0610,1" -> {
      }
    }

    return result;
  }

  public byte[] processData(byte[] data) {
    CommonHeader header = new CommonHeader(data);
    TaskType typeCode = header.getTypeCode();
    byte[] result = null;

    switch (typeCode) {
      // 0610 업무개시[통보] 응답
      case TASK_MANAGEMENT_RESPONSE -> {
        // 파일 송신 (센터 -> 기관)
        // 파일 수신 (기관 -> 센터)
        // 파일 조회
        // 파일 취소/초기화
      }

      // 0630 업무개시[통보] 응답
      case FILE_INFO_MANAGEMENT -> {
      }

    }

    return result;
  }
}
