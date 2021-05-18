package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.HomeMapper;

@Service
public class HomeService {
	
	@Autowired
	HomeMapper homeMapper;
	
	public ArrayList<HashMap<String, Object>> selectUser(){
		return homeMapper.selectUser();
	}
	
	public HashMap<String, Object> signIn(HashMap<String, Object> params){
		return homeMapper.signIn(params);
	}
	
}
