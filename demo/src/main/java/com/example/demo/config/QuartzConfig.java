package com.example.demo.config;

import org.quartz.CronScheduleBuilder;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;

public class QuartzConfig {
	
	public static Scheduler quartzScheduler;

	public static Trigger oneSecTrigger = TriggerBuilder.newTrigger().withIdentity(new TriggerKey("job1Key"))
			.withSchedule(CronScheduleBuilder.cronSchedule("0/11 * * * * ?")).build();
	
}
