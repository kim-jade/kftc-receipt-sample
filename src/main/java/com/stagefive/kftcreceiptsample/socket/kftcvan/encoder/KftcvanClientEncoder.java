package com.stagefive.kftcreceiptsample.socket.kftcvan.encoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class KftcvanClientEncoder extends MessageToByteEncoder<byte[]> {

  @Override
  protected void encode(ChannelHandlerContext ctx, byte[] msg, ByteBuf out) {
    out.writeBytes(msg);
  }

}
