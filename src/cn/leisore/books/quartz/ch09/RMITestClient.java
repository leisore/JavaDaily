package cn.leisore.books.quartz.ch09;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.TriggerBuilder;
import org.quartz.core.jmx.JobDetailSupport;
import org.quartz.impl.StdSchedulerFactory;

public class RMITestClient {

	public void run() throws Exception {

		Log log = LogFactory.getLog(RMITestClient.class);

		// Use this properties file instead of quartz.properties
		System.setProperty("org.quartz.properties", "client.properties");

		// Get a reference to the remote scheduler
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		
		//scheduler.pauseJob(JobKey.jobKey("remotelyAddedJob", "default"));
		//scheduler.resumeJob(JobKey.jobKey("remotelyAddedJob", "default"));
		//scheduler.deleteJob(JobKey.jobKey("remotelyAddedJob", "default"));
		//System.exit(0);

		// Define the job to add

		JobDataMap map = new JobDataMap();
		map.put("who", "lee");
		JobDetail job = JobBuilder.newJob(HelloJob.class).usingJobData(map).withIdentity("remotelyAddedJob", "default")
				.build();

		CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity("remotelyAddedTrigger", "default")
				.withSchedule(CronScheduleBuilder.cronSchedule("/5 * * ? * *")).build();

		// schedule the remote job
		scheduler.scheduleJob(job, trigger);

		log.info("Remote job scheduled.");
	}

	public static void main(String[] args) throws Exception {
		RMITestClient example = new RMITestClient();
		example.run();
	}
}