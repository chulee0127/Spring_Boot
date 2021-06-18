package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.BoardMapper;

@Service
public class BoardService {
	
	@Autowired
	BoardMapper boardMapper;
	
	public ArrayList<HashMap<String, Object>> selectBoardList(){
		
		ArrayList<HashMap<String, Object>> resultList = boardMapper.selectBoardList();
		
//		for(int i=0; i<resultList.size(); i++) {
//			HashMap<String, Object> getData = resultList.get(i);
//			System.out.println("게시판 목록 :: "+getData);
//		}
		
		return resultList;
	}
	
}
