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

  private final CmsClientInitializer cmsClientInitializer;

  public Channel connectCmsServer() {
    EventLoopGroup group = new NioEventLoopGroup();
    Channel channel;

    try {
      Bootstrap bootstrap = new Bootstrap();
      bootstrap.group(group)
          .channel(NioSocketChannel.class)
          .handler(cmsClientInitializer);

      ChannelFuture future = bootstrap.connect(HOST, PORT).sync();
      channel = future.channel();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }

    return channel;
  }
}
