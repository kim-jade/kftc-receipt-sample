package com.stagefive.kftcreceiptsample.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {
  public static String getNowDateToString(String format) {
    SimpleDateFormat sdf = new SimpleDateFormat(format);
    return sdf.format(new Date());
  }

  public static String createSpaceString(int length) {
    return " ".repeat(length);
  }
}
