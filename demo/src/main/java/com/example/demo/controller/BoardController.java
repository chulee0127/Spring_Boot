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
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.service.BoardService;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@Autowired
	BoardService boardService;
	
	@GetMapping(value = "/list")
	public String boardList() {
		return "/board/boardList";
	}
	
	@PostMapping(value="/list/get")
	public ModelAndView boardListGet(@RequestBody HashMap<String, Object> params) {
		logger.info("[ ::: board/list/get ::: ]");
		
		ModelAndView mav = new ModelAndView("jsonView");
		
		int resultCode = 0;

		try {
			
			mav.addObject("boardList", boardService.selectBoardList());
			
		} catch (Exception ex) {
			resultCode = 99;
			ex.printStackTrace();
		} finally {
			mav.addObject("resultCode", resultCode);
		}

		return mav;
	}
	
}
