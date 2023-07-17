package com.stagefive.kftcreceiptsample.socket.cms.handler;

import com.stagefive.kftcreceiptsample.service.cms.CmsAutoPaymentAgreementService;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.util.concurrent.BlockingQueue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@Sharable
public class CmsAutoPaymentAgreementHandler extends SimpleChannelInboundHandler<byte[]> {

  private final CmsAutoPaymentAgreementService cmsAutoPaymentAgreementService;

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, byte[] msg) {
    log.info("Received data: {}", new String(msg));
    BlockingQueue<byte[]> responseQueue = cmsAutoPaymentAgreementService.processAutoPaymentAgreement(msg, ctx);
    if (!responseQueue.isEmpty()) {
      byte[] message = responseQueue.poll();
      ctx.writeAndFlush(message);
      log.info("Send data: {}", new String(message));
    }
  }

  @Override
  public void channelActive(ChannelHandlerContext ctx) {
    log.info("Channel Activated");
  }

  @Override
  public void channelInactive(ChannelHandlerContext ctx) {
    log.info("Channel Inactivated");
  }
}
