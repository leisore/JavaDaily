package cn.leisore.books.pro_java_nio2.ch06;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;


public class WatchTest {

	public static void main(String[] args) throws IOException, InterruptedException {
		WatchService ws = FileSystems.getDefault().newWatchService();
		Paths.get(".").register(ws, StandardWatchEventKinds.ENTRY_CREATE,StandardWatchEventKinds.ENTRY_DELETE,StandardWatchEventKinds.ENTRY_MODIFY,StandardWatchEventKinds.OVERFLOW);
		for (;;) {
			
			WatchKey take = ws.take();
			if (take.isValid()) {
				take.pollEvents().forEach(s -> {
					System.out.println("==");
					System.out.println(s.kind());
					System.out.println(s.context().getClass());
					System.out.println(s.context());
				});
			}
		}
	}
}
