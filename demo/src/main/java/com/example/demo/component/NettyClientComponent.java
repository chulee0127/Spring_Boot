package com.example.demo.component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.stereotype.Component;

import com.example.demo.netty.NettyClient;

@Component
public class NettyClientComponent {

	// netty Client
	//@PostConstruct
	public void nettyClientStart() {

		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					new NettyClient("localhost", 8999, "[ netty socket 통신 ]").nettyClientRun();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}).start();
	}

	//@PreDestroy
	private void destory() {
		System.out.println("---------- [ PreDestroy annotation ] ----------");
	}
	
}