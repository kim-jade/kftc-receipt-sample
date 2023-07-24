package com.stagefive.kftcreceiptsample.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CmsConstants {

  public static String ENTERPRISE_ID;

  public static String BUSINESS_TYPE_CODE = "FTE";

  public static String SENDER_NAME = "TEST_SENDER_NAME";

  @Value("${cms.id}")
  public void setEnterpriseId(String enterpriseId) {
    ENTERPRISE_ID = enterpriseId;
  }
}
