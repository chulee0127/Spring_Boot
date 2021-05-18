package com.example.demo.jobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class Job1 implements Job {
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println("[ 스케줄 작업 ]");
	}
	
}
