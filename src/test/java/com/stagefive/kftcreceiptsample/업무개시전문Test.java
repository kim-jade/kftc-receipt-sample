package com.stagefive.kftcreceiptsample;

import com.stagefive.kftcreceiptsample.dto.cms.TaskDTO;
import java.lang.reflect.Field;
import org.junit.jupiter.api.Test;

public class 업무개시전문Test {
    // header 정보
    String 전문바이트수 = "0012";
    String 업무구분코드 = "FTE";
    String 기관코드 = "ABCDEFGH";
    String 전문종별코드 = "0600";
    String 거래구분코드 = "R";
    String 송수신FLAG = "C";
    String 파일명 = "        ";
    String 응답코드 = "000";

    // 0600 / 0610 전문 개별부
    String 전문전송일시 = "0705235959";
    String 업무관리정보 = "001";  // 업무개시
    String 송신자명 = "manager             ";
    String 송신자암호 = "password        ";

  @Test
  public void 업무개시전문테스트() {
    String data = combineFields();

    TaskDTO taskInfo = new TaskDTO(data.getBytes());
    System.out.println(taskInfo);
  }

  public String combineFields() {
    StringBuilder sb = new StringBuilder();

    // 현재 클래스의 모든 필드 가져오기
    Field[] fields = this.getClass().getDeclaredFields();

    for (Field field : fields) {
      field.setAccessible(true); // 접근 가능하도록 설정

      try {
        Object value = field.get(this); // 필드의 값을 가져옴
        sb.append(value.toString()); // 문자열로 변환하여 추가
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      }
    }

    return sb.toString();
  }
}
