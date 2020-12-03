package cn.leisore.books.quartz.ch09;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.alibaba.fastjson.JSON;

public class HelloJob implements Job {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			Object who = context.getJobDetail().getJobDataMap().get("who");
			System.out.println(new Date() + "--: hello " + who);
			System.out.println(JSON.toJSONString(context, true));
			System.out.println();	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
