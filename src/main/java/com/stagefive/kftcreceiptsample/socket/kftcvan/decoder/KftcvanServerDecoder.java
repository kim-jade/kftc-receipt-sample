package com.stagefive.kftcreceiptsample.socket.kftcvan.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class KftcvanServerDecoder extends ByteToMessageDecoder {
  @Override
  protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
    byte[] bytes = new byte[in.readableBytes()];
    in.readBytes(bytes);
    out.add(bytes);
  }

}
