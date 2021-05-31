package com.example.demo.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Base64;
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
		/* ����ȭ �׽�Ʈ */
		byte[] serializedMember = null;
        String serializedMemberStr = "";
        
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
                oos.writeObject(userDetailsVO);
                serializedMember = baos.toByteArray();
                serializedMemberStr = Base64.getEncoder().encodeToString(serializedMember);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("����ȭ ���ڿ� :: "+serializedMemberStr);
        /* ����ȭ �׽�Ʈ �� */
        /* ������ȭ �׽�Ʈ */
        byte[] deSerializedMember = Base64.getDecoder().decode("rO0ABXNyACFjb20uZXhhbXBsZS5kZW1vLnZvLlVzZXJEZXRhaWxzVk8AAAAAAAAAAQIABEwABHRlc3R0ABJMamF2YS9sYW5nL1N0cmluZztMAAl1c2VyX2F1dGhxAH4AAUwAB3VzZXJfaWRxAH4AAUwADXVzZXJfcGFzc3dvcmRxAH4AAXhwcHQAFVJPTEVfYWRtaW4sUk9MRV90ZXN0MXQABXRlc3QxdAAGdGVzdDFe");
        try (ByteArrayInputStream bais = new ByteArrayInputStream(deSerializedMember)) {
            try (ObjectInputStream ois = new ObjectInputStream(bais)) {
                Object o = ois.readObject();
                UserDetailsVO o1 = (UserDetailsVO) o;
                System.out.println("������ȭ ���ڿ� :: "+o1+o1.getUsername());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        /* ������ȭ �׽�Ʈ ��*/
        
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
