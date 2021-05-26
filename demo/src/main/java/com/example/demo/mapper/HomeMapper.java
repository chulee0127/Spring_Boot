package com.example.demo.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.vo.UserDetailsVO;

@Mapper
public interface HomeMapper {
	
	// Spring Security << findById >>
	public UserDetailsVO findById(String user_id);
	
	// 사용자 목록
	public ArrayList<HashMap<String, Object>> selectUser();
	
	// 로그인
	public HashMap<String, Object> signIn(HashMap<String, Object> params);
	
}