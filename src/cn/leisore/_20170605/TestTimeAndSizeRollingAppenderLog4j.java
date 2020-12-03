package cn.leisore._20170605;

import java.util.Random;
import java.util.UUID;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.RollingFileAppender;
import org.apache.logging.log4j.core.appender.rolling.CompositeTriggeringPolicy;
import org.apache.logging.log4j.core.appender.rolling.FileCountLimitRolloverStrategy;
import org.apache.logging.log4j.core.appender.rolling.OnStartupTriggeringPolicy;
import org.apache.logging.log4j.core.appender.rolling.SizeBasedTriggeringPolicy;
import org.apache.logging.log4j.core.appender.rolling.TimeBasedTriggeringPolicy;
import org.apache.logging.log4j.core.appender.rolling.action.Action;
import org.apache.logging.log4j.core.config.DefaultConfiguration;
import org.apache.logging.log4j.core.impl.DefaultLogEventFactory;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.SimpleMessageFactory;

public class TestTimeAndSizeRollingAppenderLog4j {

	public static void main(String[] args) throws InterruptedException {
		/*
		Logger logger = Logger.getLogger(TestRollingFileAppender.class);

		TimeAndSizeRollingAppender appender = new TimeAndSizeRollingAppender();
		
		
		rfa.setAppend(true);
		rfa.setFile("test.bat");
		rfa.setMaxBackupIndex(5);
		rfa.setMaxFileSize("5MB");
		rfa.setLayout(new Layout() {

			@Override
			public void activateOptions() {

			}

			@Override
			public boolean ignoresThrowable() {
				return true;
			}

			@Override
			public String format(LoggingEvent arg0) {
				return arg0.getRenderedMessage();
			}
		});
		rfa.activateOptions();

		for (int i = 0; i < 10000 * 100; i++) {
			LoggingEvent le = new LoggingEvent(null, logger, null, i + "#" + UUID.randomUUID().toString() + "\n", null);
			rfa.append(le);
		}
		rfa.close();
		*/
		
		
		
		//System.setProperty("log4j.configurationFile", "log4j2.xml");
		
		PatternLayout patternLayout = PatternLayout.newBuilder().withPattern("%m%n").build();

		OnStartupTriggeringPolicy onStartupTriggeringPolicy = OnStartupTriggeringPolicy.createPolicy(1);
		TimeBasedTriggeringPolicy timeBasedTriggeringPolicy = TimeBasedTriggeringPolicy.createPolicy("5", "false");
		SizeBasedTriggeringPolicy sizeBasedTriggeringPolicy = SizeBasedTriggeringPolicy.createPolicy("5 MB");
		CompositeTriggeringPolicy compositeTriggeringPolicy = CompositeTriggeringPolicy
				.createPolicy(onStartupTriggeringPolicy, timeBasedTriggeringPolicy, sizeBasedTriggeringPolicy);

		FileCountLimitRolloverStrategy fileCountLimitRolloverStrategy = FileCountLimitRolloverStrategy
				.createStrategy("5", "1", null, null, (Action[]) null, false, new DefaultConfiguration(), "app-.*\\.log");

		RollingFileAppender fileAppender = RollingFileAppender.newBuilder().withName("RollingFileAppender").withFileName("logs/app.log")
				.withFilePattern("logs/app-%d{yyyy-MM-dd-HH-ss}.log").withAppend(true).withLayout(patternLayout)
				.withPolicy(compositeTriggeringPolicy).withStrategy(fileCountLimitRolloverStrategy).build();

		//Logger logger = LogManager.getLogger("test");
		
		
		Random rand = new Random();
		for (int i = 0; i < 10000 * 100; i++) {
			int millis = 1 + Math.abs(rand.nextInt(5));
			Thread.sleep(millis);
			//logger.info( i + "#" + UUID.randomUUID().toString() );
			
			Message msg = SimpleMessageFactory.INSTANCE.newMessage(i + "#" + UUID.randomUUID().toString());
			LogEvent logEvent = DefaultLogEventFactory.getInstance().createEvent("test", null, null, Level.INFO, msg, null, null);
			fileAppender.append(logEvent);
		}

	}
}
