package com.example.demo.service;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

@Service
public class CrawlingService {

	public String[] gogo() {
		
		String[] htmlArray = new String[2];
		
		try {

			Connection.Response response = Jsoup.connect("https://www.cubici.co.kr").method(Connection.Method.GET).execute();
			Document document = response.parse();
			htmlArray[0] = document.html();
			htmlArray[1] = document.text();
			
			Element loadingBar = document.select(".loadingSpinner").first();
			System.out.println("로딩바 HTML :: "+loadingBar.html());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return htmlArray;
	}

}
