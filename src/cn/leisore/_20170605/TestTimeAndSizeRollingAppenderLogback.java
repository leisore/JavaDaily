package cn.leisore._20170605;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestTimeAndSizeRollingAppenderLogback {

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
		System.setProperty("log4j.configurationFile", "log4j2.xml");
		Logger logger = LoggerFactory.getLogger("test");
		for (int i = 0; i < 10000 * 100; i++) {
			Thread.sleep((int)(Math.random() * 10) + 1);
			logger.info( i + "#" + UUID.randomUUID().toString() );
		}

	}
}
