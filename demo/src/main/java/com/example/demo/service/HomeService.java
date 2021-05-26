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
	 * Spring Security 필수 메소드 구현
	 *
	 * @param ID
	 * @return UserDetails
	 * @throws UsernameNotFoundException 유저가 없을 때 예외 발생
	 */
	@Override // 기본적인 반환 타입은 UserDetails, UserDetails를 상속받은 UserInfo로 반환 타입 지정 (자동으로 다운 캐스팅됨)
	public UserDetails loadUserByUsername(String user_id) throws UsernameNotFoundException { // 시큐리티에서 지정한 서비스이기 때문에 이 메소드를 필수로 구현
		
		UserDetailsVO userDetailsVO = homeRepository.findById(user_id); // ID 확인
		
		String userName = "";
		String encryptPassword = "";
		ArrayList<GrantedAuthority> authorities = new ArrayList<>();
		
		if(userDetailsVO != null) {
			userName = userDetailsVO.getUsername();
			
			BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
			encryptPassword = bcryptPasswordEncoder.encode(userDetailsVO.getPassword());
			System.out.println("비밀번호 암호화 :: "+userDetailsVO.getPassword()+" :: "+encryptPassword);
			
			authorities = (ArrayList<GrantedAuthority>) userDetailsVO.getAuthorities();
			System.out.println("권한 ::: "+userDetailsVO.getAuthorities());
		}
		
		//System.out.println("여기서 리턴해서 어디로 가냐고.. "+userDetailsVO.getUsername()+" :: "+userDetailsVO.getAuthorities());
		// UserDetails implements 한 VO 로
		return new User(userName, encryptPassword, authorities);
	}

	public ArrayList<HashMap<String, Object>> selectUser() {
		return homeMapper.selectUser();
	}

	public HashMap<String, Object> signIn(HashMap<String, Object> params) {
		return homeMapper.signIn(params);
	}
	
}
