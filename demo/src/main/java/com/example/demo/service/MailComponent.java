package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.autoconfigure.mail.MailProperties;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.stereotype.Component;

@Component
public class MailComponent {

	@Autowired
	MailProperties mailProperties;

	@Autowired
	private JavaMailSender emailSender;

	public void sendSimpleMessage(String to, String subject, String text) throws Exception {
		
		System.out.println("---------- application.properties 값 가져오기 ----------");
		System.out.println("host :: "+mailProperties.getHost());
		System.out.println("port :: "+mailProperties.getPort());
		System.out.println("userName :: "+mailProperties.getUsername());
		System.out.println("password :: "+mailProperties.getPassword());
		
		SimpleMailMessage message = new SimpleMailMessage();
		//message.setFrom("who");
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);
		emailSender.send(message);
	}

}
