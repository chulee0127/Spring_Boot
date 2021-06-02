package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.service.CrawlingService;

@Controller
public class CrawlingController {
	
	@Autowired
	CrawlingService crawlingService;
	
	@RequestMapping(value = "/crawling.do", method = RequestMethod.POST)
	public ModelAndView indexPage() {
		
		ModelAndView mav = new ModelAndView("jsonView");
		int resultCode = 0;

		try {
			
			String[] htmlArray = crawlingService.gogo();
			mav.addObject("htmlArray", htmlArray);
			
		} catch (Exception ex) {
			resultCode = 99;
			ex.printStackTrace();
		} finally {
			mav.addObject("resultCode", resultCode);
		}

		return mav;
	}
	
}
