package com.stagefive.kftcreceiptsample.socket.kftcvan.config;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import java.net.InetSocketAddress;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class KftcvanServerConfiguration {

  @Value("${kftcvan.server.host}")
  private String host;
  @Value("${kftcvan.server.port}")
  private int port;
  @Value("${kftcvan.server.boss-count}")
  private int bossCount;
  @Value("${kftcvan.server.worker-count}")
  private int workerCount;
  @Value("${kftcvan.server.backlog}")
  private int backlog;

  @Bean
  public ServerBootstrap kftcvnServerBootstrap(KftcvanServerInitializer kftcvanServerInitializer) {
    ServerBootstrap serverBootstrap = new ServerBootstrap();
    serverBootstrap.group(kftcvanBossGroup(), kftcvanWorkerGroup()).channel(NioServerSocketChannel.class)
        .handler(new LoggingHandler(LogLevel.DEBUG)).childHandler(kftcvanServerInitializer)
        .option(ChannelOption.SO_BACKLOG, backlog);

    return serverBootstrap;
  }

  // boss: incoming connection을 수락하고, 수락한 connection을 worker에게 등록(register)
  @Bean(destroyMethod = "shutdownGracefully")
  public NioEventLoopGroup kftcvanBossGroup() {
    return new NioEventLoopGroup(bossCount);
  }

  // worker: boss가 수락한 연결의 트래픽 관리
  @Bean(destroyMethod = "shutdownGracefully")
  public NioEventLoopGroup kftcvanWorkerGroup() {
    return new NioEventLoopGroup(workerCount);
  }

  @Bean
  public InetSocketAddress kftcvanInetSocketAddress() {
    return new InetSocketAddress(host, port);
  }
}
