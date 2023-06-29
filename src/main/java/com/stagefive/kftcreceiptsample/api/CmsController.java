package com.stagefive.kftcreceiptsample.api;


import com.stagefive.kftcreceiptsample.service.cms.CmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cms")
@RequiredArgsConstructor
public class CmsController {

  private final CmsService cmsService;

  @PostMapping("/")
  public void run() {
    cmsService.run();
  }
}
