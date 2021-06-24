package com.example.demo.handler;

import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

@Sharable
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

//	private int DATA_LENGTH = 5; // TestDecoder 와 동일
//	private ByteBuf buff;

	// 핸들러가 생성될 때 호출되는 메소드
	@Override
	public void handlerAdded(ChannelHandlerContext ctx) {

		System.out.println("---------- netty Handler가 생성됩니다. ---------- ");

//		buff = ctx.alloc().buffer(DATA_LENGTH); // ByteBuf 동적 할당
	}
	
	// 핸들러가 제거될 때 호출되는 메소드
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
    	
    	System.out.println("---------- netty Handler가 제거됩니다. ----------");
    	
//        buff = null;
    }

	// 클라이언트와 연결되어 트래픽을 생성할 준비가 되었을 때 호출되는 메소드
	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		String remoteAddress = ctx.channel().remoteAddress().toString();
		System.out.println("---------- Remote Address ::: " + remoteAddress);
	}
	
	// 현재 바이트 크기만큼 데이터를 수신해야 실행되게끔 설정
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		/*
		ByteBuf mBuf = (ByteBuf) msg;
		// String readMessage = ((ByteBuf) msg).toString(Charset.forName("euc-kr"));
		String readMessage = mBuf.toString(Charset.forName("euc-kr"));
//		ctx.write(msg); // 이건 뭘까?
		System.out.println("---------- message from received ::: " + readMessage);
		
		System.out.println("* buff.refCnt() ::: "+buff.refCnt()+" mBuf.refCnt() ::: "+mBuf.refCnt()); // 1이면 참조중
		buff.writeBytes(mBuf); // 클라이언트에서 보내는 데이터가 축적됨
		mBuf.release(); // 참조 해제(netty에서 참조 카운팅 지원)
		// 참조 카운팅은 다른 객체에서 더이상 참조 하지 않는 객체가 보유한 리소스를 해제해 메모리 사용량과 성능을 최적화 하는 기법.
		System.out.println("* 참조 해제 후 --- buff.refCnt() ::: "+buff.refCnt()+" mBuf.refCnt() ::: "+mBuf.refCnt()); // 0이면 메모리 비움
		
		final ChannelFuture f = ctx.writeAndFlush(buff);
		//2.writeAndFlush()은 내부적으로 기록과 전송의 두가지 메서드 호출 write(). flush()
	    //2.1 write() : 채널에 데이터를 기록
	    //2.2 flush() : 채널에 기록된 데이터를 서버로 전송
		f.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if(future.isSuccess()) {
                    System.out.println("서버에서 전송 성공");
                
                }
                else {
                    System.out.println("서버에서 전송  실패");
                }
            }
        });
		//f.addListener(ChannelFutureListener.CLOSE);
		*/
		ByteBuf mBuf = (ByteBuf) msg;
		String readMessage = mBuf.toString(Charset.forName("euc-kr"));
		System.out.println("---------- message from received ::: " + readMessage);
		readMessage += "abcd";
		
		ByteBuf messageBuffer = Unpooled.buffer(); 
		messageBuffer.writeBytes(readMessage.getBytes());
		ctx.writeAndFlush(messageBuffer);
//		ctx.write(messageBuffer); // flush를 안하면 응답을 보내지않고, 연결상태가 계속 유지
	}
	
	// 데이터를 수신할 때마다 실행하는 함수
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) {
		System.out.println("---------- 수신된 데이터를 모두 읽었습니다. ---------- ");
		
//		ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
//		ctx.close();// 서버와 연결된 채널을 닫음, 데이터 송수신 채널은 닫히게 되고 클라이언트 프로그램은 종료됨
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		// Close the connection when an exception is raised.
		ctx.close();
		cause.printStackTrace();
	}

}