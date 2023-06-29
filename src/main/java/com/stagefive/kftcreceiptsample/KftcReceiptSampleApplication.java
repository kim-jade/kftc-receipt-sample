package com.stagefive.kftcreceiptsample;

import com.stagefive.kftcreceiptsample.socket.cms.server.CmsServer;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@RequiredArgsConstructor
@EnableScheduling
public class KftcReceiptSampleApplication implements CommandLineRunner {

  private final CmsServer cmsServer;

  public static void main(String[] args) {
    SpringApplication.run(KftcReceiptSampleApplication.class, args);
  }

  @Override
  public void run(String... args) {
    cmsServer.start();
  }
}
