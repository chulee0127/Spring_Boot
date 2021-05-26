package com.example.demo.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.vo.UserDetailsVO;

@Mapper
public interface HomeMapper {
	
	// Spring Security << findById >>
	public UserDetailsVO findById(String user_id);
	
	// ����� ���
	public ArrayList<HashMap<String, Object>> selectUser();
	
	// �α���
	public HashMap<String, Object> signIn(HashMap<String, Object> params);
	
}