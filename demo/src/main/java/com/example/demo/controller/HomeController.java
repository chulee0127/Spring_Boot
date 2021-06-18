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
import com.example.demo.service.MailComponent;

import com.google.gson.JsonObject;

@Controller
public class HomeController {

	@Autowired
	HomeService homeService;
	
	@Autowired
	MailComponent mailComponent;
	
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
	
	// 이메일 전송
	@RequestMapping(value = "/sendEmail.do", method = RequestMethod.POST)
	public ModelAndView sendEmail(@RequestBody HashMap<String, Object> params) {
		
		System.out.println("RequestBody 테스트 :: "+params);
		
		ModelAndView mav = new ModelAndView("jsonView");

		boolean resultBoolean = false;

		try {
			
			String toUser = "";
			
			String htmlContent = "<h3 style=\"margin-bottom: 10px;\">제목</h3>";
			htmlContent += "<p style=\"color: green; margin-bottom: 10px;\">내용</p>";
			htmlContent += "<img src=\"cid:image1\" width=\"20px\" height=\"20px\">";
			
//			mailComponent.sendSimpleMessage(toUser, "테스트 제목", "<p style=\"color: green;\">테스트 내용</p>");
//			mailComponent.sendMessageWithAttachment(toUser, "테스트 제목", "<p style=\"color: green;\">테스트 내용</p>", "C:/Users/A/Desktop/Tool/google_otp_capture.jpeg");
			mailComponent.sendEmailToHtmlForm(toUser, "테스트 제목", htmlContent, "C:/Users/A/Desktop/Tool/google_otp_capture.jpeg");
			resultBoolean = true;
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			mav.addObject("resultBoolean", resultBoolean);
		}

		return mav;
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
