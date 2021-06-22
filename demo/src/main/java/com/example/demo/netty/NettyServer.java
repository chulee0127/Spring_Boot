package com.example.demo.netty;

import com.example.demo.handler.NettyServerHandler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class NettyServer {
	
	private int port;
	
	//private Channel serverChannel;
	
    public NettyServer(int port) {
        this.port = port;
    }

    public void nettyServerRun() {

		// 다중 스레드 모델
    	// boss: incoming connection을 수락하고, 수락한 connection을 worker에게 등록(register)
		EventLoopGroup bossGroup = new NioEventLoopGroup(1); // bossGroup에는 스레드가 하나뿐
		// worker: boss가 수락한 연결의 트래픽 관리
		EventLoopGroup workerGroup = new NioEventLoopGroup(); // workerGroup 스레드 풀에 스레드 수를 지정하지 않았으므로 기본값은 CPU 코어 수에 2를 곱한 것

		ServerBootstrap b = new ServerBootstrap();

		b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class) // NioServerSocketChannel: incoming connections를 수락하기 위해 새로운 Channel을 객체화할 때 사용
				.handler(new LoggingHandler(LogLevel.DEBUG))
				.childHandler(new ChannelInitializer<SocketChannel>() { // ChannelInitializer: 새로운 Channel을 구성할 때 사용되는 특별한 handler. 주로 ChannelPipeline으로 구성
					
					@Override
					public void initChannel(SocketChannel ch) throws Exception {

						ChannelPipeline pipeline = ch.pipeline();

						// decoder는 @Sharable이 안됨. Bean 객체 주입이 안되고, 매번 새로운 객체를 생성해야 함
						TestDecoder testDecoder = new TestDecoder();

						// 뒤이어 처리할 디코더 및 핸들러 추가
						pipeline.addLast(testDecoder);
						pipeline.addLast(new NettyServerHandler());
					}
				});
		
		// SO_KEEPALIVE, TCP_NODELAY 등 옵션 제공
		b.option(ChannelOption.SO_BACKLOG, 2) // SO_BACKLOG: 동시에 수용 가능한 최대 incoming connections 개수
		.childOption(ChannelOption.SO_KEEPALIVE, true);

		try {
			// 지정한 host, port로 소켓을 바인딩하고 incoming connections을 받도록 준비함
			ChannelFuture serverChannelFuture = b.bind(port).sync(); // ChannelFuture: I/O operation의 결과나 상태를 제공하는 객체

			// 서버 소켓이 닫힐 때까지 기다림
			//serverChannelFuture.channel().closeFuture().sync();
			//serverChannel = serverChannelFuture.channel().closeFuture().sync().channel();
			serverChannelFuture.channel().closeFuture().sync().channel();
			
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
	}

//	// Bean을 제거하기 전에 해야할 작업이 있을 때 설정, 잘 모르겠
//	@PreDestroy
//	private void nettyServerStop() {
//		System.out.println("---------- [ PreDestroy annotation ] ----------");
//
//// 		if (serverChannel != null) {
//// 			serverChannel.close();
//// 			serverChannel.parent().closeFuture();
//// 		}
//	}

}
