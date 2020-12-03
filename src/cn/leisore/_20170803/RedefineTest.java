package cn.leisore._20170803;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Paths;

import cn.leisore._20170620.PrintMF;

public class RedefineTest {

	public static void main(String[] args) throws Exception {
		//Class.forName("cn.leisore._20170620.PrintMF");
		Method md = ClassLoader.class.getDeclaredMethod("defineClass", String.class, byte[].class, int.class,
				int.class);
		md.setAccessible(true);

		{
			REClassLoader rcl = new REClassLoader();
			
			byte[] bs = Files.readAllBytes(Paths.get("HH.class"));
			Object invoke = md.invoke(rcl, "cn.leisore._20170620.HH", bs, 0, bs.length);
			
			 bs = Files.readAllBytes(Paths.get("PrintMF.class"));
			 invoke = md.invoke(rcl, "cn.leisore._20170620.PrintMF", bs, 0, bs.length);
			((Class) invoke).newInstance();
			System.out.println(invoke);
		}
/*		{
			REClassLoader rcl = new REClassLoader();
			byte[] bs = Files.readAllBytes(Paths.get("PrintMF.class"));
			Object invoke = md.invoke(rcl, "cn.leisore._20170620.PrintMF", bs, 0, bs.length);
			((Class) invoke).newInstance();
			System.out.println(invoke);
		}
		{
			REClassLoader rcl = new REClassLoader();
			byte[] bs = Files.readAllBytes(Paths.get("PrintMF.class"));
			Object invoke = md.invoke(rcl, "cn.leisore._20170620.PrintMF", bs, 0, bs.length);
			((Class) invoke).newInstance();
			System.out.println(invoke);
		}
		{
			REClassLoader rcl = new REClassLoader();
			byte[] bs = Files.readAllBytes(Paths.get("PrintMF.class"));
			Object invoke = md.invoke(rcl, "cn.leisore._20170620.PrintMF", bs, 0, bs.length);
			((Class) invoke).newInstance();
			System.out.println(invoke);
		}*/
		for (;;) {
			byte[] b = new byte[64 * 1024 * 1024];
			Thread.sleep(100);
		}
	}
}
