package com.example.demo.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
	
	@Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        System.out.println("---------- 가입되지 않은 사용자 접근 ----------");
        response.sendRedirect("/login.do");
    }
	
}