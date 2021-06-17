package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.demo.handler.AuthHandler;
import com.example.demo.service.HomeService;

//@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	AuthHandler authHandler;
	
	@Autowired
	HomeService homeService;

	@Override
	public void configure(WebSecurity web) { // ���� ����
		web.ignoring().antMatchers("/css/**", "/js/**", "/img/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/", "/login", "/loginFail", "/crawling.do", "/sendEmail.do").permitAll() // ������ ���� ���
				// ������ "ROLE_"�� ���۵��� �ʱ� ������ hasRole()�� ������� �ʽ��ϴ�. �������ִ� ��� (��: "ROLE_USER") hasRole("USER")�� hasAuthority("ROLE_USER")�� ����
				.antMatchers("/board/**").hasAnyRole("test1", "else") // .hasRole("test1") .hasAuthority("ROLE_test1")
				.anyRequest().authenticated() // ������ ��û���� ������ ������ ��� ���� ������ �־�� ���� ����
				.and()
				.formLogin()
				.loginPage("/login") // �α��� ������ ��ũ
				.usernameParameter("user_id") // �⺻�� username
				.passwordParameter("user_password") // �⺻�� password
				.successHandler(authHandler)
				.failureHandler(authHandler)
				//..defaultSuccessUrl("/") // �α��� ���� �� �����̷�Ʈ �ּ�
				.and()
				.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/") // �α׾ƿ� ������ �����̷�Ʈ �ּ�
				.invalidateHttpSession(true) // ���� ������
		;
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(homeService).passwordEncoder(new BCryptPasswordEncoder());
	}
	
}
