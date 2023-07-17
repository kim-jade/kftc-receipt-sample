package com.stagefive.kftcreceiptsample.socket.cms.handler;

import com.stagefive.kftcreceiptsample.service.cms.CmsParserService;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
@Sharable
public class CmsServerHandler extends SimpleChannelInboundHandler<byte[]> {

  private final CmsParserService cmsParserService;

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, byte[] msg) throws InterruptedException {
    // 받은 데이터 처리

    byte[] responseData = cmsParserService.serverProcessData(msg);
    if (responseData != null) {
      ctx.writeAndFlush(responseData);
    } else {
      ctx.close();
    }

    log.info("Received data: {}", new String(msg));
  }

  @Override
  public void channelReadComplete(ChannelHandlerContext ctx) {
    ctx.flush();
  }

}
