package cn.leisore._20190624;

import java.io.File;
import java.net.URL;

import org.springframework.boot.loader.LaunchedURLClassLoader;
import org.springframework.boot.loader.jar.JarFile;

public class ByteArrayFastJson {

	public static void main(String[] args) throws Exception {
		JarFile jf = new JarFile(new File("F:/study/java/JavaDaily/lib/mysql-connector-java-8.0.15.jar"));
		System.out.println(jf.getUrl());
		LaunchedURLClassLoader cl = new LaunchedURLClassLoader(new URL[]{jf.getUrl()}, ByteArrayFastJson.class.getClassLoader());
		
		Class<?> loadClass = cl.loadClass("com.mysql.cj.protocol.a.TextResultsetReader");
		System.out.println(loadClass.getClassLoader());
		System.out.println(loadClass.getProtectionDomain().getCodeSource().getLocation());

		/*System.out.println(JSON.toJSONString(new A(),true));
		System.out.println(Base64.getEncoder().encodeToString(new byte[]{'A','B','C'}));*/
	}

}

class A {
	private byte[] arr = new byte[]{'A','B','C'};

	public byte[] getArr() {
		return arr;
	}

	public void setArr(byte[] arr) {
		this.arr = arr;
	}
}
