package com.stagefive.kftcreceiptsample.socket.cms.config;

import com.stagefive.kftcreceiptsample.socket.cms.decoder.CmsClientDecoder;
import com.stagefive.kftcreceiptsample.socket.cms.encoder.CmsClientEncoder;
import com.stagefive.kftcreceiptsample.socket.cms.handler.CmsClientHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CmsClientInitializer extends ChannelInitializer<SocketChannel> {

  @Override
  public void initChannel(SocketChannel ch) {
    ChannelPipeline pipeline = ch.pipeline();

    pipeline.addLast(new CmsClientDecoder());
    pipeline.addLast(new CmsClientEncoder());
    pipeline.addLast(new CmsClientHandler());
  }
}
