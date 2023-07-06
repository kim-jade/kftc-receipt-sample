package com.stagefive.kftcreceiptsample.util;

import java.nio.charset.Charset;
import java.util.Arrays;

public class ParserUtil {

  public static final byte ZERO = 0x30;
  public static final byte SPACE = 0x20;

  /**
   * 전문 전송시, 데이터 타입에 따라 포맷을 변경해준다.
   * @param data 데이터
   * @param size 데이터 byte size
   * @param isNumeric 데이터가 숫자인지 아닌지에 대한 값
   * @return 포맷된 byte array 데이터
   */
  public static byte[] formatData(byte[] data, int size, boolean isNumeric) {

    // 전문 byte size 만큼 byte 생성
    byte[] result = new byte[size];

    // 숫자인 경우 우측 정렬하고 앞부분은 0으로 채움
    // 숫자가 아닐 경우 좌측 정렬하고 뒷부분은 공백으로 채움
    if (isNumeric) {
      Arrays.fill(result, ZERO);
      System.arraycopy(data, 0, result, size - data.length, data.length);
    } else {
      Arrays.fill(result, SPACE);
      System.arraycopy(data, 0, result, 0, data.length);
    }

    return result;
  }

  /**
   * 전문을 파싱하고 파싱한 위치를 반환한다.
   * @param source 값을 파싱할 원본 byte array
   * @param destination 파싱한 값을 저장할 byte array
   * @param offset 원본 byte array 에서 파싱을 시작할 offset
   * @return 다음에 파싱할 byte array의 offset
   */
  public static int copyBytes(byte[] source, byte[] destination, int offset) {
    System.arraycopy(source, offset, destination, 0, destination.length);
    return offset + destination.length;
  }

  /**
   * 전문을 파싱하고 파싱한 위치를 반환한다.
   * @param source 값을 파싱할 원본 byte array
   * @param destination 파싱한 값을 저장할 byte array
   * @param offset 원본 byte array 에서 파싱을 시작할 offset
   * @return 다음에 파싱할 byte array의 offset
   */
  public static int copyBytes(byte[] source, byte[] destination, int offset, int length) {
    System.arraycopy(source, 0, destination, offset, length);
    return offset + length;
  }

  public static String byteToString(byte[] data) {
    return new String(data, Charset.forName("EUC-KR"));
  }

  public static int getIntFromByteArray(byte[] data, int startIndex, int endIndex) {
    return Integer.parseInt(new String(Arrays.copyOfRange(data, startIndex, endIndex)));
  }

  public static String getStringFromByteArray(byte[] data, int startIndex, int endIndex) {
    return new String(Arrays.copyOfRange(data, startIndex, endIndex));
  }
}
