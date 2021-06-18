package com.example.demo.vo;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsVO implements UserDetails, Serializable {
	
	private static final long serialVersionUID = 1L;

	private String user_id;
	
	private String user_password;
	
	private String user_auth;
	
	public UserDetailsVO(String user_id, String user_password){
		this.user_id = user_id;
		this.user_password = user_password;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		//System.out.println("컬럼 :: "+this.user_auth);
		
		String [] authorityArray = this.user_auth.split(",");
		
		ArrayList<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for(int i=0; i<authorityArray.length; i++) {
			authorities.add(new SimpleGrantedAuthority(authorityArray[i]));
		}
        
		return authorities;
	}

	// 비밀번호
	@Override
	public String getPassword() {
		return this.user_password;
	}

	// Primary key
	@Override
	public String getUsername() {
		return this.user_id;
	}

	/* ********** 컬럼으로 만들어서 관리 *********** */
	// 계정 만료 여부
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	// 계정 잠김 여부
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	// 비밀번호 완료 여부
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	// 사용자 활성화 여부
	@Override
	public boolean isEnabled() {
		return true;
	}

}
