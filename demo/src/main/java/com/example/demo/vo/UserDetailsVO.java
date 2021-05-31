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
		//System.out.println("�÷� :: "+this.user_auth);
		
		String [] authorityArray = this.user_auth.split(",");
		
		ArrayList<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for(int i=0; i<authorityArray.length; i++) {
			authorities.add(new SimpleGrantedAuthority(authorityArray[i]));
		}
        
		return authorities;
	}
	
	// ��й�ȣ
	@Override
	public String getPassword() {
		return this.user_password;
	}
	
	// Primary key
	@Override
	public String getUsername() {
		return this.user_id;
	}
	
	/* ********** �÷����� ���� ���� *********** */
	// ���� ���� ����
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	// ���� ��� ����
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	// ��й�ȣ �Ϸ� ����
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	// ����� Ȱ��ȭ ����
	@Override
	public boolean isEnabled() {
		return true;
	}

}
