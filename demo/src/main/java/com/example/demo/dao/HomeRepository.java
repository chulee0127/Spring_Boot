package com.example.demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.mapper.HomeMapper;
import com.example.demo.vo.UserDetailsVO;

@Repository
public class HomeRepository {
	
	@Autowired
	HomeMapper homeMapper;
	
	public UserDetailsVO findById(String user_id){
		return homeMapper.findById(user_id);
	}
	
}
