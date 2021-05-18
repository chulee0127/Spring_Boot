package com.example.demo.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@SuppressWarnings("deprecation")
public class LoggerInterceptor extends HandlerInterceptorAdapter{
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		// null�� �ƴϰ� ajax ��û�� �ƴ� ��
		if(request.getSession().getAttribute("userMap") != null && !"XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
			System.out.println("[ �α�����..! ] "+request.getRequestURI());
		}
		
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception { }
	
}
