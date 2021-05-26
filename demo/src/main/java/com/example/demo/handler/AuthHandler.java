package com.example.demo.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
public class AuthHandler implements AuthenticationSuccessHandler, AuthenticationFailureHandler {

	// �α��� ����
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		HttpSession session = request.getSession();
		session.setAttribute("user_id", authentication.getName());

		System.out.println("�α��� ���� Handler :: " + session.getAttribute("user_id"));
		System.out.println("�α��� ���� Handler :: " + authentication.getAuthorities());

		response.sendRedirect("/");
	}

	// �α��� ����
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		System.out.println("�α��� ���� Handler :: " + username);
		System.out.println("�α��� ���� Handler :: " + password);

		response.sendRedirect("/loginFail");
	}

}