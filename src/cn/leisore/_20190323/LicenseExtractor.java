package cn.leisore._20190323;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

public class LicenseExtractor {

	public static void main(String[] args) throws IOException {

		final String rootPath = "F:/study/opensource/kettle/pdi-ce-7.1.0.0-12/data-integration/lib";
		
		final Map<String, List<String>> licMap = new HashMap<>();
		Files.walkFileTree(Paths.get(rootPath), new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				System.out.println("visit:" + file);
				if (attrs.isRegularFile() && file.toString().endsWith(".jar")) {
					JarFile jar = new JarFile(file.toFile());
					ZipEntry entry = jar.getEntry("META-INF/LICENSE.txt");
					String lic = "unknown";
					if (entry != null) {
						byte[] bytes = new byte[200];
						jar.getInputStream(entry).read(bytes);
						lic = new String(bytes,0,200);
					}
					List<String> jars = licMap.get(lic);
					if (jars == null) {
						jars = new ArrayList<>();
						licMap.put(lic, jars);
					}
					jars.add(file.toString());
					jar.close();
				}
				return FileVisitResult.CONTINUE;
			}
		});
		
		System.out.println();
		System.out.println();
		System.out.println("Result:");
		licMap.forEach((k,v) -> {
			System.out.println("\t" + k);
			v.forEach(j -> {
				System.out.println("\t\t" + j);	
			});
		});
		
	}

}
