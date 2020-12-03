package cn.leisore._20170907;

import java.net.MalformedURLException;
import java.net.URL;

public class JdbcUrlParser {

	/**
	 * @return the database name from the connect string, which is typically the
	 *         'path' component, or null if we can't.
	 */
	public static String getDatabaseName(String connectString) {
		try {
			String sanitizedString = null;
			int schemeEndOffset = connectString.indexOf("://");
			if (-1 == schemeEndOffset) {
				// couldn't find one? try our best here.
				sanitizedString = "http://" + connectString;
			} else {
				sanitizedString = "http" + connectString.substring(schemeEndOffset);
			}

			URL connectUrl = new URL(sanitizedString);
			String databaseName = connectUrl.getPath();
			if (null == databaseName) {
				return null;
			}

			// This is taken from a 'path' part of a URL, which may have leading
			// '/'
			// characters; trim them off.
			while (databaseName.startsWith("/")) {
				databaseName = databaseName.substring(1);
			}

			return databaseName;
		} catch (MalformedURLException mue) {
			mue.printStackTrace();
			return null;
		}
	}

	/**
	 * @return the hostname from the connect string, or null if we can't.
	 */
	public static String getHostName(String connectString) {
		try {
			String sanitizedString = null;
			int schemeEndOffset = connectString.indexOf("://");
			if (-1 == schemeEndOffset) {
				// Couldn't find one? ok, then there's no problem, it should
				// work as a
				// URL.
				sanitizedString = connectString;
			} else {
				sanitizedString = "http" + connectString.substring(schemeEndOffset);
			}

			URL connectUrl = new URL(sanitizedString);
			return connectUrl.getHost();
		} catch (MalformedURLException mue) {
			mue.printStackTrace();
			return null;
		}
	}

	/**
	 * @return the port from the connect string, or -1 if we can't.
	 */
	public static int getPort(String connectString) {
		try {
			String sanitizedString = null;
			int schemeEndOffset = connectString.indexOf("://");
			if (-1 == schemeEndOffset) {
				// Couldn't find one? ok, then there's no problem, it should
				// work as a
				// URL.
				sanitizedString = connectString;
			} else {
				sanitizedString = "http" + connectString.substring(schemeEndOffset);
			}

			URL connectUrl = new URL(sanitizedString);
			return connectUrl.getPort();
		} catch (MalformedURLException mue) {
			mue.printStackTrace();
			return -1;
		}
	}

	   public static void main(String[] args) throws MalformedURLException {
			String url = "jdbc:mysql:loadbalance://10.22.33.44:3306,10.22.33.55:3306/MySQL?characterEncoding=UTF-8";
			System.out.println(getHostName(url));
			System.out.println(getPort(url));
		}
}
