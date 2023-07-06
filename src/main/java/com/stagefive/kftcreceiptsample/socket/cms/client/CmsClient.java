package com.stagefive.kftcreceiptsample.socket.cms.client;

import com.stagefive.kftcreceiptsample.socket.cms.config.CmsClientInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public final class CmsClient {

  @Value("${cms.server.host}")
  private String HOST;
  @Value("${cms.server.port}")
  private int PORT;

  public  void main(String[] args) {
    Bootstrap bootstrap = new Bootstrap();
    bootstrap.group(new NioEventLoopGroup())
        .channel(NioSocketChannel.class)
        .handler(new CmsClientInitializer());

    Channel ch;

    try {
      ch = bootstrap.connect(HOST, PORT).sync().channel();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }

    // 소켓 처음 연결 후, 업무 개시 요구 전문 전송 (0600)
    ch.writeAndFlush("0600".getBytes());
  }

  public Channel connectCmsServer() {
    EventLoopGroup group = new NioEventLoopGroup();
    Channel channel;

    try {
      Bootstrap bootstrap = new Bootstrap();
      bootstrap.group(group)
          .channel(NioSocketChannel.class)
          .handler(new CmsClientInitializer());

      ChannelFuture future = bootstrap.connect(HOST, PORT).sync();
      channel = future.channel();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }

    return channel;
  }
}
