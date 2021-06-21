package com.example.demo.component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.stereotype.Component;

import com.example.demo.netty.NettyServer;

@Component
public class NettyServerComponent {

	// netty Server
	//@PostConstruct
	public void nettyServerStart() {

		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					new NettyServer(8999).nettyServerRun();
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