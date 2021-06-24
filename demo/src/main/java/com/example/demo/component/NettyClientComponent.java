package com.example.demo.component;

import org.springframework.stereotype.Component;

import com.example.demo.netty.NettyClient;

@Component
public class NettyClientComponent {

	// netty Client
	public void nettyClientStart() {
		
		NettyClient nettyClientObj = new NettyClient("localhost", 8999, "123456");
		
		nettyClientObj.nettyClientRun();
	}

}