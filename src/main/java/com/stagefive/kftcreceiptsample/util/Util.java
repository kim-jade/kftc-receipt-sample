package com.stagefive.kftcreceiptsample.util;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

public class Util {
  public static String getNowDateToString(String format) {
    SimpleDateFormat sdf = new SimpleDateFormat(format);
    return sdf.format(new Date());
  }

  public static int getNowDateToInt(String format) {
    SimpleDateFormat sdf = new SimpleDateFormat(format);
    return Integer.parseInt(sdf.format(new Date()));
  }

  public static String createSpaceString(int length) {
    return " ".repeat(length);
  }

  public static String createRepeatString(String data, int length) {
    return data.repeat(length);
  }

  public static byte[] concat(byte[]... arrays) {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    if (arrays != null) {
      Arrays.stream(arrays).filter(Objects::nonNull)
          .forEach(array -> out.write(array, 0, array.length));
    }
    return out.toByteArray();
  }
}
