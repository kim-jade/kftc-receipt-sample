package com.stagefive.kftcreceiptsample.socket.cms.config;

import com.stagefive.kftcreceiptsample.socket.cms.decoder.CmsServerDecoder;
import com.stagefive.kftcreceiptsample.socket.cms.encoder.CmsServerEncoder;
import com.stagefive.kftcreceiptsample.socket.cms.handler.CmsServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CmsServerInitializer extends ChannelInitializer<SocketChannel> {
  @Override
  protected void initChannel(SocketChannel ch) {
    ChannelPipeline pipeline = ch.pipeline();

    pipeline.addLast(new CmsServerDecoder());
    pipeline.addLast(new CmsServerEncoder());
    pipeline.addLast(new CmsServerHandler());
  }
}
