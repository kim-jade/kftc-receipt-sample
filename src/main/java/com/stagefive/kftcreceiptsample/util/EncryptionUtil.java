package com.stagefive.kftcreceiptsample.util;

import java.util.ArrayList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EncryptionUtil {
  private static String verificationKey1;

  @Value("${cms.verification-key-1}")
  public void setVerificationKey1(String value) {
    verificationKey1 = value;
  }

  private static String verificationKey2;

  @Value("${cms.verification-key-2}")
  public void setVerificationKey2(String value) {
    verificationKey2 = value;
  }

  /**
   * 맥 검증값 반환
   * @param content MAC 검증값 반환에 필요한 데이터
   * @return 맥 검증값
   */
  public static String getMacValidationValue(String content) {

    ArrayList<String> dataList = splitData(content);

    String O1 = verificationKey1;
    String O2 = verificationKey2;

    for (int i = 0; i < dataList.size(); i++) {
      // 첫 번째 데이터 D1에 송수신키(검증키1, 검증키2)를 가산하여 결과값을 임시저장소(T1, T2)에 저장
      String data = dataList.get(i);
      String T1 = formatToString(Long.parseLong(data) + Long.parseLong(O1));
      String T2 = formatToString(Long.parseLong(data) + Long.parseLong(O2));

      // 위 결과값(T1, T2)에, 상대편 검증키의 첫 번째 자리 값 만큼 CRS시킨 결과값을 가산
      String CRS_T1 = shiftString(T1, verificationKey2.charAt(i) - '0');
      String CRS_T2 = shiftString(T2, verificationKey1.charAt(i) - '0');
      O1 = formatToString(Long.parseLong(T1) + Long.parseLong(CRS_T1));
      O2 = formatToString(Long.parseLong(T2) + Long.parseLong(CRS_T2));

      log.info("T1: {}", T1);
      log.info("T2: {}", T2);
      log.info("O1: {}", O1);
      log.info("O2: {}", O2);
      log.info("============");
    }

    return String.format("%010d", Long.parseLong(O1) + Long.parseLong(O2));
  }


  // 데이터 길이를 10Bytes 단위로 쪼개준다. 마지막 데이터가 10Bytes 이내일 경우, 0으로 채운다.
  private static ArrayList<String> splitData(String input) {
    int length = (int) Math.ceil((double) input.length() / 10);
    ArrayList<String> result = new ArrayList<>(length);

    for (int i = 0; i < length; i++) {
      int start = i * 10;
      int end = Math.min(start + 10, input.length());
      result.add(input.substring(start, end));
    }

    String lastData = result.get(length - 1);
    if (lastData.length() < 10) {
      result.set(length - 1, rightPad(lastData));
    }

    return result;
  }

  // 들어오는 데이터 길이가 10Bytes 미만일 경우, 오른쪽을 0으로 채워준다.
  private static String rightPad(String data) {
    return data + "0".repeat(Math.max(0, 10 - data.length()));
  }

  // 입력받은 문자열을 오른쪽으로 shitf 만큼 이동한 문자열로 반환
  private static String shiftString(String str, int shift) {
    int length = str.length();
    shift %= length;

    return str.substring(length - shift) + str.substring(0, length - shift);
  }

  // 10Bytes가 안되면 0을 채워주고, 10Bytes를 초과할 경우 앞에 자리를 제거
  private static String formatToString(Long data) {
    String result = String.format("%010d", data);
    return result.substring(result.length() - 10);
  }
}
