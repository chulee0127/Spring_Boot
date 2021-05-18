package com.example.demo.common;

import org.quartz.CronScheduleBuilder;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;

public class QuartzConfig {
	
	public static Scheduler scheduler;

	public static Trigger oneSecTrigger = TriggerBuilder.newTrigger().withIdentity(new TriggerKey("job1Key"))
			.withSchedule(CronScheduleBuilder.cronSchedule("0/9 * * * * ?")).build();
	
}
