package com.stagefive.kftcreceiptsample.service.cms;

import com.stagefive.kftcreceiptsample.socket.cms.config.CmsClientInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CmsService {

  @Value("${cms.server.host}")
  private String HOST;
  @Value("${cms.server.port}")
  private int PORT;

//  @Scheduled(cron = "0/10 * * * * ?")
  public void run() {
    Bootstrap bootstrap = new Bootstrap();
    bootstrap.group(new NioEventLoopGroup())
        .channel(NioSocketChannel.class)
        .handler(new CmsClientInitializer());

    Channel channel;

    try {
      channel = bootstrap.connect(HOST, PORT).sync().channel();

      // 소켓 처음 연결 후, 업무 개시 요구 전문 전송 (0600)
      channel.writeAndFlush("0600".getBytes()).sync();

      // 연결 종료
      channel.closeFuture().sync();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  public byte[] processData(byte[] data) throws InterruptedException {
    // 파일 송신 (센터 -> 기관) 플로우
    String workType = new String(data);
    byte[] result = null;
    switch (workType) {

      // 업무개시[요구] 요청이 왔을 때
      case "0600" -> {
        result = "0610".getBytes();// 업무개사[통보]
        Thread.sleep(500);
        result = "0630".getBytes(); // 파일정보확인[지시]
      }

      // 파일정보확인[보고] 요청이 왔을 때
      case "0640" -> {
        result = "0320".getBytes(); // DATA전송
        Thread.sleep(500);
        result = "0620".getBytes(); // 결번확인[지시]
      }

      // 결번확인[보고] 요청이 왔을 때
      case "0300" -> {
        result = "0310".getBytes(); // 결번 DATA 전송
        Thread.sleep(500);
        result = "0600,0".getBytes(); // 파일전송완료 [지시]
      }

      // 파일전송완료[보고] 요청이 왔을 때
      case "0610,0" -> {
        result = "0600,1".getBytes(); // 업무 종료 [지시]
      }

      // 업무종료[보고] 요청이 왔을 때
      case "0610,1" -> {
      }
    }

    return result;
  }
}
