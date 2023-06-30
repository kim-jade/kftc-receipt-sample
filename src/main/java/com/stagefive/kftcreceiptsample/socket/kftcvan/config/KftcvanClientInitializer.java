package com.stagefive.kftcreceiptsample.socket.kftcvan.config;

import com.stagefive.kftcreceiptsample.socket.kftcvan.decoder.KftcvanClientDecoder;
import com.stagefive.kftcreceiptsample.socket.kftcvan.encoder.KftcvanClientEncoder;
import com.stagefive.kftcreceiptsample.socket.kftcvan.handler.KftcvanClientHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KftcvanClientInitializer extends ChannelInitializer<SocketChannel> {

  @Override
  public void initChannel(SocketChannel ch) {
    ChannelPipeline pipeline = ch.pipeline();

    pipeline.addLast(new StringDecoder());
    pipeline.addLast(new StringEncoder());
    pipeline.addLast(new KftcvanClientHandler());
  }
}
