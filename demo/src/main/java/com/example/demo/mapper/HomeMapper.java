package com.example.demo.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HomeMapper {
	
	// ����� ���
	public ArrayList<HashMap<String, Object>> selectUser();
	
	// �α���
	public HashMap<String, Object> signIn(HashMap<String, Object> params);
	
}