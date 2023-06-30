package com.stagefive.kftcreceiptsample.socket.kftcvan.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KftcvanServerHandler extends SimpleChannelInboundHandler<String> {

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, String msg) throws InterruptedException {
    // 받은 데이터 처리

    // 파일 송신 (센터 -> 기관) 플로우
    String workType = msg;
    switch (workType) {

      // 업무개시[요구] 요청이 왔을 때
      case "0600" -> {
        ctx.writeAndFlush("0610");// 업무개사[통보]
        Thread.sleep(1000);
        ctx.writeAndFlush("0630"); // 파일정보확인[지시]
      }

      // 파일정보확인[보고] 요청이 왔을 때
      case "0640" -> {
        ctx.writeAndFlush("0320".getBytes()); // DATA전송
        Thread.sleep(1000);
        ctx.writeAndFlush("0620".getBytes()); // 결번확인[지시]
      }

      // 결번확인[보고] 요청이 왔을 때
      case "0300" -> {
        ctx.writeAndFlush("0310".getBytes()); // 결번 DATA 전송
        Thread.sleep(1000);
        ctx.writeAndFlush("0600,0".getBytes()); // 파일전송완료 [지시]
      }

      // 파일전송완료[보고] 요청이 왔을 때
      case "0610,0" -> {
        ctx.writeAndFlush("0600,1".getBytes()); // 업무 종료 [지시]
      }

      // 업무종료[보고] 요청이 왔을 때
      case "0610,1" -> {
        log.info("업무 종료됨");
      }
    }

    log.info("Received data: {}", new String(msg));
  }

  @Override
  public void channelReadComplete(ChannelHandlerContext ctx) {
    ctx.flush();
  }

}
