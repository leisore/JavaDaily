package cn.leisore._20170605;

import java.util.UUID;

import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.spi.LoggingEvent;

public class TestTimeAndSizeRollingAppenderLog4j_ext {

	public static void main(String[] args) {

		Logger logger = Logger.getLogger(TestRollingFileAppender.class);
		
		SizeBasedDailyRollingFileAppender rfa = new SizeBasedDailyRollingFileAppender();
		rfa.setAppend(true);
		rfa.setFile("test.bat");
		rfa.setMaxBackupIndex(5);
		rfa.setMaxFileSize("5MB");
		rfa.setLayout(new PatternLayout("%m%n"));
		rfa.activateOptions();
		rfa.setDatePattern("");
		
		for (int i = 0; i < 10000 * 100; i++) {
			LoggingEvent le = new LoggingEvent(null,logger, null, i + "#"+ UUID.randomUUID().toString() + "\n", null);
			rfa.append(le);
		}
		rfa.close();
	}

}
