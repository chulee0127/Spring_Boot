package com.example.demo.service;

import java.io.File;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.mail.MailProperties;

import org.springframework.core.io.FileSystemResource;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import org.springframework.stereotype.Component;

@Component
public class MailComponent {
	
	@Value("${value.test.str}")
	String testStr;
	
	@Autowired
	MailProperties mailProperties;

	@Autowired
	private JavaMailSender emailSender;

	// Text 전송
	public void sendSimpleMessage(String to, String subject, String text) throws Exception {

		System.out.println("---------- application.properties 값 가져오기 ----------");
		System.out.println("testStr :: " + testStr);
		System.out.println("host :: " + mailProperties.getHost());
		System.out.println("port :: " + mailProperties.getPort());
		System.out.println("userName :: " + mailProperties.getUsername());
		System.out.println("password :: " + mailProperties.getPassword());

		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom(mailProperties.getUsername()); // gmail은 굳이 안써도 됨...
			message.setTo(to);
			message.setSubject(subject);
			message.setText(text);

			emailSender.send(message);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// 파일 첨부
	public void sendMessageWithAttachment(String to, String subject, String text, String pathToAttachment) throws Exception {

		MimeMessage message = emailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(message, true);

//		helper.setFrom(new InternetAddress(mailProperties.getUsername(), "fromName", "UTF-8"));
		helper.setFrom(mailProperties.getUsername(), "누구로부터");
		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(text);

		FileSystemResource file = new FileSystemResource(new File(pathToAttachment));
		helper.addAttachment(file.getFilename(), file);

		emailSender.send(message);
	}

	// HTML 양식
	public void sendEmailToHtmlForm(String to, String subject, String text, String pathToAttachment) throws Exception {

		MimeMessagePreparator preparator = new MimeMessagePreparator() {

			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {

				final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");

				message.setTo(to);
				message.setFrom(mailProperties.getUsername(), "누구로부터");
				message.setSubject(subject);
				message.setText(text, true); // true : html 형식 사용
				
				// HTML tag 안에 img
				FileSystemResource htmlImgFile1 = new FileSystemResource(new File("C:/Users/A/Desktop/내 문서/개발환경.png"));
				message.addInline("image1", htmlImgFile1);
				
				// 파일 첨부
				FileSystemResource file1 = new FileSystemResource(new File(pathToAttachment));
				message.addAttachment(file1.getFilename(), file1);
				FileSystemResource file2 = new FileSystemResource(new File("C:/Users/A/Desktop/승인 화면 소스코드 분석 - 20210616.pptx"));
				message.addAttachment(file2.getFilename(), file2);
			}
		};

		emailSender.send(preparator);
	}

}
