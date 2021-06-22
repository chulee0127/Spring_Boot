package com.example.demo.handler;

import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NettyClientHandler extends ChannelInboundHandlerAdapter {

	private String msg;

	public NettyClientHandler(String msg) {
		this.msg = msg;
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		
		for(int i=0; i<5; i++) {
			ByteBuf messageBuffer = Unpooled.buffer();
			messageBuffer.writeBytes(msg.getBytes());
			
			System.out.println("---------- send message ::: " + msg);
			//ctx.write(messageBuffer); // 채널에 데이터를 기록
			ctx.writeAndFlush(messageBuffer);
			
			Thread.sleep(4000); // 4초 대기
		}
		//ctx.flush(); // 서버에 전송
		
		// 한 번에 보낼 때
//		ByteBuf messageBuffer = Unpooled.buffer();
//		messageBuffer.writeBytes(msg.getBytes());
		
//		ctx.writeAndFlush(messageBuffer);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("---------- receive message ::: " + ((ByteBuf) msg).toString(Charset.defaultCharset()));
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.close();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		System.out.println(cause);
		ctx.close();
	}

}