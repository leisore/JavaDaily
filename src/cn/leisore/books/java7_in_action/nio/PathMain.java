package cn.leisore.books.java7_in_action.nio;

import java.io.IOException;
import java.lang.reflect.Proxy;
import java.nio.file.FileSystems;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PathMain {

	public static void main(String[] args) throws IOException {
		Path path1 = Paths.get("logs/test.bat");
		Path path1Proxy = (Path)Proxy.newProxyInstance(PathMain.class.getClassLoader(), new Class[]{Path.class}, new InvokeLogger<Path>(path1));
		
		path1Proxy.endsWith(Paths.get("test.bat"));
		path1Proxy.endsWith(Paths.get("bat"));
		
		path1Proxy.endsWith("test.bat");
		path1Proxy.endsWith("bat");
		
		path1Proxy.endsWith(path1);
		
		path1Proxy.getFileName();
		path1Proxy.getFileSystem();
		path1Proxy.getName(0);
		path1Proxy.getNameCount();
		path1Proxy.getParent();
		path1Proxy.getRoot();
		path1Proxy.isAbsolute();
		path1Proxy.iterator();
		path1Proxy.normalize();
		
		path1Proxy.relativize(Paths.get("/"));
		path1Proxy.relativize(Paths.get("logs"));
		
		path1Proxy.resolve("123");
		path1Proxy.resolve("test.bat");
		
		path1Proxy.resolveSibling("123");
		path1Proxy.resolveSibling("test.bat");

		path1Proxy.startsWith("logs");
		path1Proxy.startsWith("logs/test.bat");
		
		path1Proxy.subpath(0,1);
		path1Proxy.subpath(2,3);

		path1Proxy.toAbsolutePath();
		path1Proxy.toFile();
		
		path1Proxy.toRealPath(LinkOption.NOFOLLOW_LINKS);
		
		((Path)Proxy.newProxyInstance(PathMain.class.getClassLoader(), new Class[]{Path.class}, new InvokeLogger<Path>(path1Proxy.toAbsolutePath()))).getRoot();
		
		FileSystems.getDefault().getRootDirectories().forEach(System.out::println);
	}
}
