package cn.leisore._20170605;

import java.util.UUID;

import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;
import org.apache.log4j.spi.LoggingEvent;

public class TestRollingFileAppender {

	public static void main(String[] args) throws InterruptedException {

		
		RollingFileAppender rfa = new RollingFileAppender();
		rfa.setAppend(true);
		rfa.setFile("logs/test.bat");
		rfa.setMaxBackupIndex(1);
		rfa.setMaxFileSize("1MB");
		rfa.setLayout(new PatternLayout("%m%n"));
		rfa.activateOptions();
		
		for (int i = 0; i < 10000 * 100; i++) {
			LoggingEvent le = new LoggingEvent(null,new NopLogger(), null, i + "#"+ UUID.randomUUID().toString() + "\n", null);
			rfa.append(le);
		}
		rfa.close();
	}
	
	static class NopLogger extends Logger {

		protected NopLogger() {
			super(NopLogger.class.getName());
		}
		
	}
}
