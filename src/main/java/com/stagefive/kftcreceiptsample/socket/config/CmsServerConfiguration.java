package com.stagefive.kftcreceiptsample.socket.config;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import java.net.InetSocketAddress;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class CmsServerConfiguration {

  @Value("${banking.server.host}")
  private String host;
  @Value("${banking.server.port}")
  private int port;
  @Value("${banking.server.boss-count}")
  private int bossCount;
  @Value("${banking.server.worker-count}")
  private int workerCount;
  @Value("${banking.server.backlog}")
  private int backlog;

  @Bean
  public ServerBootstrap serverBootstrap(CmsServerInitializer cmsServerInitializer) {
    ServerBootstrap serverBootstrap = new ServerBootstrap();
    serverBootstrap.group(bossGroup(), workerGroup()).channel(NioServerSocketChannel.class)
        .handler(new LoggingHandler(LogLevel.DEBUG)).childHandler(cmsServerInitializer)
        .option(ChannelOption.SO_BACKLOG, backlog);

    return serverBootstrap;
  }

  // boss: incoming connection을 수락하고, 수락한 connection을 worker에게 등록(register)
  @Bean(destroyMethod = "shutdownGracefully")
  public NioEventLoopGroup bossGroup() {
    return new NioEventLoopGroup(bossCount);
  }

  // worker: boss가 수락한 연결의 트래픽 관리
  @Bean(destroyMethod = "shutdownGracefully")
  public NioEventLoopGroup workerGroup() {
    return new NioEventLoopGroup(workerCount);
  }

  @Bean
  public InetSocketAddress inetSocketAddress() {
    return new InetSocketAddress(host, port);
  }
}
