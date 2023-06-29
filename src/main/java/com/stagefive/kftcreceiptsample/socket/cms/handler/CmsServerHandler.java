package com.stagefive.kftcreceiptsample.socket.cms.handler;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.stereotype.Component;

@Component
public class CmsServerHandler extends SimpleChannelInboundHandler<byte[]> {

  int callCount = 0;

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, byte[] msg) throws InterruptedException {
    // 받은 데이터 처리

    // 파일 송신 (센터 -> 기관) 플로우
    String workType = new String(msg);
    switch (workType) {

      // 업무개시[요구] 요청이 왔을 때
      case "0600" -> {
        ctx.writeAndFlush("0610".getBytes());// 업무개사[통보]
        Thread.sleep(1000);
        ctx.writeAndFlush("0630".getBytes()); // 파일정보확인[지시]
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
        System.out.println("업무 종료됨");
      }
    }

    System.out.println("Received data: " + new String(msg));
  }

  @Override
  public void channelReadComplete(ChannelHandlerContext ctx) {
    ctx.flush();
  }

}
