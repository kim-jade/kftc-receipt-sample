package com.stagefive.kftcreceiptsample.api;


import com.stagefive.kftcreceiptsample.service.cms.CmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("/api/cms")
@RequiredArgsConstructor
public class CmsController {

  private final CmsService cmsService;
  private final WebClient webClient;

  @PostMapping()
  public void run() {
    cmsService.run();
  }

  @GetMapping()
  public Object getMember() {
    return webClient.get()
        .uri("https://shop-services.dev.stagefive.io/management/banners")
        .retrieve()
        .bodyToMono(Object.class);
  }
}
