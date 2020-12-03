package cn.leisore._20170620;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarFile;

public class PrintMF extends HH {
	
	static {
		System.out.println("PrintMF"+PrintMF.class.getClassLoader());
	}

	public static void main(String[] args) throws IOException {
		final Set<String> jdk = new HashSet<>();
		Files.list(Paths.get("E:/production/webgate/bes.cars")).forEach(p -> {
			System.out.println(p);
			try {
				JarFile jf = new JarFile(p.toFile());
				String value = jf.getManifest().getMainAttributes().getValue("Build-Jdk");
				jdk.add(value.trim());
				System.out.println(value);
				jf.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		jdk.stream().forEach(System.out::println);
	}
	
	@Override
	protected void finalize() throws Throwable {
		System.out.println("I'm die:" + PrintMF.class.getClassLoader());
		super.finalize();
	}

}
