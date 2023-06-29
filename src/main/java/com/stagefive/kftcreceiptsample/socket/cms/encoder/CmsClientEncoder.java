package com.stagefive.kftcreceiptsample.socket.cms.encoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class CmsClientEncoder extends MessageToByteEncoder<byte[]> {

  @Override
  protected void encode(ChannelHandlerContext ctx, byte[] msg, ByteBuf out) {
    out.writeBytes(msg);
  }

}
