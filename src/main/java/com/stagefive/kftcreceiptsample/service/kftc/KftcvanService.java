package com.stagefive.kftcreceiptsample.service.kftc;

import com.stagefive.kftcreceiptsample.socket.kftcvan.config.KftcvanClientInitializer;
import com.stagefive.kftcreceiptsample.socket.kftcvan.handler.KftcvanClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class KftcvanService {

  @Value("${kftcvan.server.host}")
  private String HOST;
  @Value("${kftcvan.server.port}")
  private int PORT;

  //  @Scheduled(cron = "0/10 * * * * ?")
  public void run() {
    Bootstrap bootstrap = new Bootstrap();
    bootstrap.group(new NioEventLoopGroup())
        .channel(NioSocketChannel.class)
        .handler(new KftcvanClientInitializer());

    Channel channel;

    try {
      channel = bootstrap.connect(HOST, PORT).sync().channel();

      // 소켓 처음 연결 후, 업무 개시 요구 전문 전송 (0600)
      channel.writeAndFlush("0600").sync();

      // 연결 종료
      channel.closeFuture().sync();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  public String checkInvalidCard() {
    Bootstrap bootstrap = new Bootstrap();
    bootstrap.group(new NioEventLoopGroup())
        .channel(NioSocketChannel.class)
        .handler(new KftcvanClientInitializer());

    Channel channel;
    String response;

    try {
      channel = bootstrap.connect(HOST, PORT).sync().channel();

      // 소켓 처음 연결 후, 유효한 카드인지 확인하는 전문 발송
      channel.writeAndFlush("check_card").sync();

      while (true) {
        KftcvanClientHandler handler = channel.pipeline().get(KftcvanClientHandler.class);
        String receivedMessage = handler.getResponse();
        if (receivedMessage != null) {
          response = receivedMessage;
          break;
        }
      }

      // 연결 종료
      channel.closeFuture().sync().channel().closeFuture();

    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }

    return response;
  }
}
