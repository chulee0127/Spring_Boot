package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dao.HomeRepository;
import com.example.demo.mapper.HomeMapper;
import com.example.demo.vo.UserDetailsVO;

@Service
public class HomeService implements UserDetailsService {
	
	@Autowired
	HomeRepository homeRepository;
	
	@Autowired
	HomeMapper homeMapper;

	/**
	 * Spring Security �ʼ� �޼ҵ� ����
	 *
	 * @param ID
	 * @return UserDetails
	 * @throws UsernameNotFoundException ������ ���� �� ���� �߻�
	 */
	@Override // �⺻���� ��ȯ Ÿ���� UserDetails, UserDetails�� ��ӹ��� UserInfo�� ��ȯ Ÿ�� ���� (�ڵ����� �ٿ� ĳ���õ�)
	public UserDetails loadUserByUsername(String user_id) throws UsernameNotFoundException { // ��ť��Ƽ���� ������ �����̱� ������ �� �޼ҵ带 �ʼ��� ����
		
		UserDetailsVO userDetailsVO = homeRepository.findById(user_id); // ID Ȯ��
		
		String userName = "";
		String encryptPassword = "";
		ArrayList<GrantedAuthority> authorities = new ArrayList<>();
		
		if(userDetailsVO != null) {
			userName = userDetailsVO.getUsername();
			
			BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
			encryptPassword = bcryptPasswordEncoder.encode(userDetailsVO.getPassword());
			System.out.println("��й�ȣ ��ȣȭ :: "+userDetailsVO.getPassword()+" :: "+encryptPassword);
			
			authorities = (ArrayList<GrantedAuthority>) userDetailsVO.getAuthorities();
			System.out.println("���� ::: "+userDetailsVO.getAuthorities());
		}
		
		//System.out.println("���⼭ �����ؼ� ���� ���İ�.. "+userDetailsVO.getUsername()+" :: "+userDetailsVO.getAuthorities());
		// UserDetails implements �� VO ��
		return new User(userName, encryptPassword, authorities);
	}

	public ArrayList<HashMap<String, Object>> selectUser() {
		return homeMapper.selectUser();
	}

	public HashMap<String, Object> signIn(HashMap<String, Object> params) {
		return homeMapper.signIn(params);
	}
	
}
