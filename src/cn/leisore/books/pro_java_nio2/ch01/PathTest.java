package cn.leisore.books.pro_java_nio2.ch01;

import java.nio.file.Path;
import java.nio.file.Paths;

public class PathTest {
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Path path = Paths.get("./..\\..\\JavaDaily\\log4j.xml");

		System.out.println(path);
		System.out.println(path.getFileName());
		System.out.println(path.getNameCount());
		System.out.println(path.getRoot());
		
		for (Path p : path) {
			System.out.println(p);
		}
		
		path.forEach(System.out::println);
	}
}