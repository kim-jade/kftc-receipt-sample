package com.stagefive.kftcreceiptsample;

import static org.assertj.core.api.Assertions.*;

import com.stagefive.kftcreceiptsample.util.EncryptionUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EncryptionUtilTest {

  @Test
  public void getMacValidationValueTest() {
    String data = "23121023617800000000051204512110389840000000110120";
    String result = EncryptionUtil.getMacValidationValue(data);
    System.out.println(result);
    assertThat("7370670538").isEqualTo(result);
  }

  @Test
  public void generateSenderPasswordTest() {
    String result = EncryptionUtil.generateSenderPassword();
    System.out.println(result);
    assertThat("T1W129SVBPPJ8BFE").isEqualTo(result);
  }
}
