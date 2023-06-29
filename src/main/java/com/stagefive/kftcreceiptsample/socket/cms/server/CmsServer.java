package com.stagefive.kftcreceiptsample.socket.cms.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import jakarta.annotation.PreDestroy;
import java.net.InetSocketAddress;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class CmsServer {
  private final ServerBootstrap serverBootstrap;
  private final InetSocketAddress tcpPort;
  private Channel serverChannel;

  public void start() {
    try {
      ChannelFuture serverChannelFuture = serverBootstrap.bind(tcpPort).sync();

      serverChannel = serverChannelFuture.channel().closeFuture().sync().channel();
    }
    catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  // Bean을 제거하기 전에 해야할 작업이 있을 때 설정
  @PreDestroy
  public void stop() {
    if (serverChannel != null) {
      serverChannel.close();
      serverChannel.parent().closeFuture();
    }
  }
}
