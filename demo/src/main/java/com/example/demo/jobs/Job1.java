package com.example.demo.jobs;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Job1 implements Job {
	
	Logger logger = LoggerFactory.getLogger(Job1.class);
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("[ 이것은 Quartz ] 현재 시간 : {}", new Date());
	}
	
}
