package cn.leisore._20170810;

import java.io.File;
import java.util.Iterator;
import java.util.ServiceLoader;
import java.util.jar.JarFile;

public class Runner extends Thread {

	@Override
	public void run() {
		try {
			System.out.println(Class.forName("cn.leisore._20170810.Inf1").getClassLoader());
		}catch (Exception e) {
			e.printStackTrace();
		}
		try {
			System.out.println("task start");
			Thread.sleep(5 * 1000);
			String lib = System.getProperty("lib");
			File f = new File("lib");
			File[] listFiles = f.listFiles();
			for (File jar : listFiles) {
				JarFile jf = new JarFile(jar);
				System.out.println("add jar " + jar.getAbsolutePath());
				Holder.inst.appendToBootstrapClassLoaderSearch(jf);
			}
			Iterator<Inf> it = ServiceLoader.load(Inf.class, null).iterator();
			while (it.hasNext()) {
				System.out.println(it.next());
			}
			System.out.println("task end");
			
			System.out.println(Class.forName("cn.leisore._20170810.Inf1").getClassLoader());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
