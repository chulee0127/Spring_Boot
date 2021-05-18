package com.example.demo.controller;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.service.BoardService;
import com.google.gson.JsonObject;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@Autowired
	BoardService boardService;
	
	@GetMapping(value = "/list")
	public String boardList() {
		logger.info("[ ::: board/list ::: ]");
		return "board/boardList";
	}
	
	@PostMapping(value="/list/get")
	public @ResponseBody String boardListGet(@RequestBody HashMap<String, Object> params) {
		
		JsonObject jsonObj =new JsonObject();
		int resultCode = 0;

		try {
			
			jsonObj.addProperty("boardList", boardService.selectBoardList().toString());
			
		} catch (Exception ex) {
			resultCode = 99;
			ex.printStackTrace();
		} finally {
			jsonObj.addProperty("resultCode", resultCode);
		}

		return jsonObj.toString();
	}
	
}
