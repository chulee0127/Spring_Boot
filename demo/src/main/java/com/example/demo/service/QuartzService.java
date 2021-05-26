package com.example.demo.service;

import javax.annotation.PostConstruct;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import org.springframework.stereotype.Service;

import com.example.demo.config.QuartzConfig;
import com.example.demo.jobs.Job1;

@Service
public class QuartzService {
	
	private SchedulerFactory schedulerFactory;
	
	@PostConstruct
	public void start() throws SchedulerException {

		schedulerFactory = new StdSchedulerFactory();
		QuartzConfig.quartzScheduler = schedulerFactory.getScheduler();
		QuartzConfig.quartzScheduler.start();

		JobDetail job1 = JobBuilder.newJob(Job1.class).build();

		//QuartzConfig.quartzScheduler.scheduleJob(job1, QuartzConfig.oneSecTrigger);
	}
	
}
