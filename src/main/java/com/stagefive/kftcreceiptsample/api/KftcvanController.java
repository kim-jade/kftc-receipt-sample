package com.stagefive.kftcreceiptsample.api;


import com.stagefive.kftcreceiptsample.service.kftc.KftcvanService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/kftc")
@RequiredArgsConstructor
public class KftcvanController {

  private final KftcvanService kftcvanService;

  @PostMapping()
  public void run() {
    kftcvanService.run();
  }

  @GetMapping("/check/card")
  public String checkInvalidCard() {
    return kftcvanService.checkInvalidCard();
  }
}
