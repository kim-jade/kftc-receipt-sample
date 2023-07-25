package com.stagefive.kftcreceiptsample.service.cms;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CmsEI13FileServiceTest {
  @Autowired
  CmsEI13FileService cmsEI13FileService;

  @Test
  void test() {
    cmsEI13FileService.generateEI13File();
  }
}
