package cn.leisore._20170803;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class  REClassLoader extends URLClassLoader {
	public REClassLoader() throws MalformedURLException {
		super(new URL[] { new File("lib/junit-4.11.jar").toURL() });
	}
	@Override
	protected void finalize() throws Throwable {
		System.out.println("== die:" + this);
		super.finalize();
	}
}
