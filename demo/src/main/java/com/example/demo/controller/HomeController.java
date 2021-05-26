package com.example.demo.controller;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.service.HomeService;

import com.google.gson.JsonObject;

@Controller
public class HomeController {

	@Autowired
	HomeService homeService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView indexPage(Authentication authentication, HttpSession session) {
		
		if(authentication != null) {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			System.out.println("[ HomeController ] :: "+userDetails.getUsername()+" :: "+userDetails.getAuthorities());
		}
		
		ModelAndView mav = new ModelAndView("index");
		int resultCode = 0;

		try {
			
			mav.addObject("userList", homeService.selectUser());

		} catch (Exception ex) {
			resultCode = 99;
			ex.printStackTrace();
		} finally {
			mav.addObject("resultCode", resultCode);
		}

		return mav;
	}
	
	// 로그인 페이지
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "login";
	}
	
	// 로그인 실패 페이지
	@RequestMapping(value = "/loginFail", method = RequestMethod.GET)
	public String loginFail() {
		return "loginFail";
	}
	
	/* ********** Ajax 로그인 ********** */
	@RequestMapping(value = "/signIn", method = RequestMethod.POST)
	public @ResponseBody String signIn(@RequestBody HashMap<String, Object> params, HttpSession session) {
		
		JsonObject jsonObj =new JsonObject();
		int resultCode = 0;

		try {
			
			HashMap<String, Object> userMap = homeService.signIn(params);
			if(userMap == null || userMap.isEmpty()) {
				resultCode = 88;
			} else {
				session.setAttribute("userMap", userMap);
			}
			
		} catch (Exception ex) {
			resultCode = 99;
			ex.printStackTrace();
		} finally {
			jsonObj.addProperty("resultCode", resultCode);
		}

		return jsonObj.toString();
	}
	
	@RequestMapping(value = "/signOut", method = RequestMethod.POST)
	public @ResponseBody String signOut(@RequestBody HashMap<String, Object> params, HttpSession session) {
		
		JsonObject jsonObj =new JsonObject();
		int resultCode = 0;

		try {
			
			session.removeAttribute("userMap");
			
		} catch (Exception ex) {
			resultCode = 99;
			ex.printStackTrace();
		} finally {
			jsonObj.addProperty("resultCode", resultCode);
		}

		return jsonObj.toString();
	}
	/* ********** Ajax 로그인 끝 ********** */
}
