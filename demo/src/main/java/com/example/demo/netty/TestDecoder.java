package com.example.demo.netty;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class TestDecoder extends ByteToMessageDecoder { // netty에서 제공하는 추상 클래스

//	private int DATA_LENGTH = 5; // 데이터의 크기가 5Byte

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		
		System.out.println("들어오는 ByteBuf ::: "+in);
		System.out.println("들어오는 ByteBuf ::: "+in.readableBytes());
		
		// 정해놓은 바이트보다 작으면 List에 add하지 않고 return
//		if (in.readableBytes() < DATA_LENGTH) {
//			return;
//		}

//		out.add(in.readBytes(DATA_LENGTH)); // 이걸 실행해야 channelRead로 넘어감
		out.add(in.readBytes(in.readableBytes()));
	}

}