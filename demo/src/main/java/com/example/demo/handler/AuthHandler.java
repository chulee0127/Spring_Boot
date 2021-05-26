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

	// 로그인 성공
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		HttpSession session = request.getSession();
		session.setAttribute("user_id", authentication.getName());

		System.out.println("로그인 성공 Handler :: " + session.getAttribute("user_id"));
		System.out.println("로그인 성공 Handler :: " + authentication.getAuthorities());

		response.sendRedirect("/");
	}

	// 로그인 실패
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		System.out.println("로그인 실패 Handler :: " + username);
		System.out.println("로그인 실패 Handler :: " + password);

		response.sendRedirect("/loginFail");
	}

}