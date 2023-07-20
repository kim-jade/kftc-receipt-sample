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

  private static String senderName;

  @Value("${cms.sender-name}")
  public void setSenderName(String value) {
    senderName = value;
  }

  private static String senderPassword;

  @Value("${cms.sender-password}")
  public void setSenderPassword(String value) {
    senderPassword = value;
  }

  private static String id;

  @Value("${cms.id}")
  public void setId(String value) {
    id = value;
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

  public static String generateSenderPassword() {
    String plainPassword = repeatString(senderPassword, 16);
    String encKey = String.format("%c%c", id.charAt(2), id.charAt(9))  // 기관코드 2자리(3, 10번째 값)
//        + Util.getNowDateToString("yyMMdd") // 전송일 6자리(YYMMDD)
        + "200602" // 전송일 6자리(YYMMDD)
        + senderName.toUpperCase().substring(0, 8); // 전송자명 앞 8자리(대문자)

    int[] result = new int[16];
    for (int i = 0; i < result.length; i++) {
      char p = plainPassword.charAt(i);
      char k = encKey.charAt(i);

      // 10미만일 경우 숫자. 이상일 경우 문자.
      // 숫자 0∼9 : 9의 보수로 치환, 알파벳 A∼Z : 문자 ASCII값에 대한 100의 보수로 치환
      int intP = Character.isDigit(p) ? 9 - (p - '0') : 100 - p;
      int intK = Character.isDigit(k) ? 9 - (k - '0') : 100 - k;

      // 36으로 나눈 나머지 값 연산
      result[i] = (intP + intK) % 36;
    }

    StringBuilder resultString = new StringBuilder();
    for (int data: result) {
      // 10미만일 경우 숫자. 이상일 경우 문자.
      // 숫자 0∼9 : 9의 보수로 치환, 알파벳 A∼Z : 문자 ASCII값에 대한 100의 보수로 치환
      String resultChar = data < 10 ? String.valueOf(9 - data) : String.valueOf((char) (100 - data));
      resultString.append(resultChar);
    }

    return resultString.toString();
  }

  private static String repeatString(String str, int length) {
    StringBuilder stringBuilder = new StringBuilder();
    int strLength = str.length();

    for (int i = 0; i < length; i++) {
      stringBuilder.append(str.charAt(i % strLength));
    }
    return stringBuilder.toString();
  }
}
