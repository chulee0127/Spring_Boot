package com.example.demo.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledService {
	
	Logger logger = LoggerFactory.getLogger(ScheduledService.class);

	//@Scheduled(cron = "0/9 * * * * *")
	public void scheduledFunction() {
		logger.info("[ 이것은 Scheduled ] 현재시간 : {}", new Date());
	}
	
}
