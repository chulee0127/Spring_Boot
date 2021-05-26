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
	public void configure(WebSecurity web) { // 인증 무시
		web.ignoring().antMatchers("/css/**", "/js/**", "/img/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/", "/login", "/loginFail").permitAll() // 누구나 접근 허용
				// 권한이 "ROLE_"로 시작되지 않기 때문에 hasRole()이 적용되지 않습니다. 권한이있는 경우 (예: "ROLE_USER") hasRole("USER")는 hasAuthority("ROLE_USER")와 동일
				.antMatchers("/board/**").hasAnyRole("test1", "else") // .hasRole("test1") .hasAuthority("ROLE_test1")
				.anyRequest().authenticated() // 나머지 요청들은 권한의 종류에 상관 없이 권한이 있어야 접근 가능
				.and()
				.formLogin()
				.loginPage("/login") // 로그인 페이지 링크
				.successHandler(authHandler)
				.failureHandler(authHandler)
				//..defaultSuccessUrl("/") // 로그인 성공 후 리다이렉트 주소
				.and()
				.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/") // 로그아웃 성공시 리다이렉트 주소
				.invalidateHttpSession(true) // 세션 날리기
		;
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(homeService).passwordEncoder(new BCryptPasswordEncoder());
	}
	
}
