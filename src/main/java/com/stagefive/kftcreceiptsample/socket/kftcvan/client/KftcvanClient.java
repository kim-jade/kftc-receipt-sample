package com.stagefive.kftcreceiptsample.socket.kftcvan.client;

import com.stagefive.kftcreceiptsample.socket.kftcvan.config.KftcvanClientInitializer;
import com.stagefive.kftcreceiptsample.socket.kftcvan.handler.KftcvanClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class KftcvanClient {

  static final String HOST = "127.0.0.1";
  static final int PORT = 8889;

  public static void main(String[] args) {
    Bootstrap bootstrap = new Bootstrap();
    bootstrap.group(new NioEventLoopGroup())
        .channel(NioSocketChannel.class)
        .handler(new KftcvanClientInitializer());

    Channel ch;

    try {
      ch = bootstrap.connect(HOST, PORT).sync().channel();
//    KftcvanClientHandler handler = bootstrap.connect(HOST, PORT).sync().channel().pipeline().get(KftcvanClientHandler.class);
//    String response = handler.getResponse();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }

    // 소켓 처음 연결 후, 업무 개시 요구 전문 전송 (0600)
    ch.writeAndFlush("0600");
  }
}
