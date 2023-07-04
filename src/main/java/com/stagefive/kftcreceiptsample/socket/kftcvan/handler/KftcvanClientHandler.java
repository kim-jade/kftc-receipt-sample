package com.stagefive.kftcreceiptsample.socket.kftcvan.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class KftcvanClientHandler extends SimpleChannelInboundHandler<String> {

  String response;

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, String msg) {
    // 파일 송신 (센터 -> 기관) 플로우
    response = msg;
    switch (msg) {
      // 해당 요청일때는 별다른 응답 안함
      case "0610", "0310", "0320" -> {
      }

      // 파일정보확인[지시]
      case "0630" -> {
        ctx.writeAndFlush("0640"); // 파일정보확인[보고]
      }

      // 결번확인[지시]
      case "0620" -> {
        ctx.writeAndFlush("0300"); // 결번확인[보고]
      }

      // 파일전송완료[지시], 업무종료[지시]
      case "0600,0" -> {
        ctx.writeAndFlush("0610,0"); // 파일전송완료[보고]
      }

      // 파일전송완료[지시], 업무종료[지시]
      case "0600,1" -> {
        ctx.writeAndFlush("0610,1"); // 파일전송완료[보고], 업무종료[보고]
        ctx.close();
      }

      default -> {
        response = msg;
      }
    }

    if (response != null) ctx.close();

    log.info("Received data: {}", msg);
  }

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    // 클라이언트가 서버에 연결되면 데이터 전송
//    byte[] data = "업무 개시 전문";
//    ctx.writeAndFlush(data);

    log.info("Channel Activated");
  }

  @Override
  public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    System.out.println(response);
    log.info("Channel Inactivated");
  }
}
