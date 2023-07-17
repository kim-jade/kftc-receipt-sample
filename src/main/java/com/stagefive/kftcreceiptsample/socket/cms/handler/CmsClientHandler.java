package com.stagefive.kftcreceiptsample.socket.cms.handler;

import com.stagefive.kftcreceiptsample.service.cms.CmsParserService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CmsClientHandler extends SimpleChannelInboundHandler<byte[]> {

  private final CmsParserService cmsParserService;

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, byte[] msg) {
    // 파일 송신 (센터 -> 기관) 플로우
    String workType = new String(msg);
    byte[] response = cmsParserService.processData(msg);
    switch (workType) {
      // 해당 요청일때는 별다른 응답 안함
      case "0610", "0310", "0320" -> {
      }

      // 파일정보확인[지시]
      case "0630" -> {
        ctx.writeAndFlush("0640".getBytes()); // 파일정보확인[보고]
      }

      // 결번확인[지시]
      case "0620" -> {
        ctx.writeAndFlush("0300".getBytes()); // 결번확인[보고]
      }

      // 파일전송완료[지시], 업무종료[지시]
      case "0600,0" -> {
        ctx.writeAndFlush("0610,0".getBytes()); // 파일전송완료[보고]
      }

      // 파일전송완료[지시], 업무종료[지시]
      case "0600,1" -> {
        ctx.writeAndFlush("0610,1".getBytes()); // 파일전송완료[보고], 업무종료[보고]
        ctx.close();
      }
    }

    response = null;
    log.info("Received data: {}", new String(msg));
  }

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    log.info("Cms Channel Activated");
  }

  @Override
  public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    log.info("Cms Channel Inactivated");
  }
}
