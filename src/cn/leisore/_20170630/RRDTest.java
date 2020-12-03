package cn.leisore._20170630;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class RRDTest {
	public static void main(String[] args) {
		Map<String, Integer> map = workPath(".");
		for(Entry<String,Integer> entry: map.entrySet()){
			System.out.println("ext:" + entry.getKey() + "\t count:" + entry.getValue());
		}
	}
	public static Map<String, Integer> workPath(String pathname){
		Path path = Paths.get(pathname);
		Map<String, Integer> map = new HashMap<String,Integer>();
		try {
			Files.walkFileTree(path, new SimpleFileVisitor<Path>(){
				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attr) throws IOException{
					String ext = makeFileExtName(file.getFileName().toString());
					if(map.containsKey(ext)){
						map.put(ext, map.get(ext) + 1);
					}else{
						map.put(ext, 1);
					}
					return FileVisitResult.CONTINUE;
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}
	public static String makeFileExtName(String filename){
		String[] split = filename.split("\\.");
		return split[split.length -1];
	}
}

