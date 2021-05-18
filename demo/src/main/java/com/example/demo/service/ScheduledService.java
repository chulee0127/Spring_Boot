package com.example.demo.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledService {
	
	private static Logger logger = LoggerFactory.getLogger(ScheduledService.class);

	@Scheduled(cron = "0/14 * * * * *")
	public void alert() {
		logger.info("현재 시간 : {}", new Date());
	}
	
}
