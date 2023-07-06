package com.stagefive.kftcreceiptsample;

import com.stagefive.kftcreceiptsample.util.ParserUtil;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

public class ByteParsingTest {

  @Test
  public void test() {
    byte[] data2 = "ABC".getBytes();
    byte[] data3 = "123".getBytes();
    System.out.println(new String(ParserUtil.formatData(data2, 5, false)));
    System.out.println(new String(ParserUtil.formatData(data3, 5, true)));
    System.out.println(Arrays.toString(ParserUtil.formatData(data3, 5, true)));
  }
}
