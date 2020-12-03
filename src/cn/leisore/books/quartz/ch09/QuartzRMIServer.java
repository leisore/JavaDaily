package cn.leisore.books.quartz.ch09;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzRMIServer {
	public void run() throws Exception {
		Log log = LogFactory.getLog(QuartzRMIServer.class);

		// Use this properties file instead of quartz.properties
		System.setProperty("org.quartz.properties", "server.properties");

		// RMI with Quartz requires a special security manager
		// Get a reference to the Scheduler
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

		/*
		 * Due to the server.properties file, our Scheduler will be exported to
		 * RMI Registry automatically.
		 */
		scheduler.start();

		log.info("Quartz RMI Server started at " + new Date());
		log.info("RMI Clients may now access it. ");

		System.out.println("\n");
		System.out.println("The scheduler will run until you type \"exit\"");

		BufferedReader rdr = new BufferedReader(new InputStreamReader(System.in));

		while (true) {
			System.out.print("Type 'exit' to shutdown server: ");
			if ("exit".equals(rdr.readLine())) {
				break;
			}
		}

		log.info("Scheduler is shutting down...");
		scheduler.shutdown(true);
		log.info("Scheduler has been stopped.");
	}

	public static void main(String[] args) throws Exception {

		QuartzRMIServer example = new QuartzRMIServer();
		example.run();
	}
}
