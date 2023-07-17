package com.stagefive.kftcreceiptsample;

import com.stagefive.kftcreceiptsample.util.EncryptionUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EncryptionUtilTest {

  @Test
  public void test() {
    String data = "23121023617800000000051204512110389840000000110120";
    System.out.println("맥 검증값: " + EncryptionUtil.getMacValidationValue(data));
  }
}
