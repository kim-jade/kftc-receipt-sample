package com.stagefive.kftcreceiptsample;

import com.stagefive.kftcreceiptsample.dto.cms.TaskDTO;
import com.stagefive.kftcreceiptsample.enums.TaskManagementInfo;
import com.stagefive.kftcreceiptsample.enums.TaskType;
import com.stagefive.kftcreceiptsample.enums.TransactionCode;
import com.stagefive.kftcreceiptsample.util.ParserUtil;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ByteParsingTest {

  @Test
  public void test() {
    byte[] data2 = "ABC".getBytes();
    byte[] data3 = "123".getBytes();
    System.out.println(new String(ParserUtil.formatData(data2, 5, false)));
    System.out.println(new String(ParserUtil.formatData(data3, 5, true)));
    System.out.println(Arrays.toString(ParserUtil.formatData(data3, 5, true)));
  }

  @Test
  public void taskDtoTest() {
    TaskDTO taskDTO = new TaskDTO();
    taskDTO.setRequestHeader();
    taskDTO.setTypeCode(TaskType.TASK_MANAGEMENT);
    // 기관 -> 센터로 자동납부 동의자료를 전송하기 때문에 센터가 수신자
    taskDTO.setTransactionCode(TransactionCode.CENTER_RECEIVE);

    // 파일 전송 완료 지시
    taskDTO.setTaskManagementInfo(TaskManagementInfo.FILE_EXIST_SAR_COMPLETE);

    System.out.println(taskDTO);
    System.out.println(Arrays.toString(taskDTO.getByte()));
    System.out.println(new TaskDTO(taskDTO.getByte()));
  }

}
