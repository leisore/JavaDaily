package cn.leisore.books.pro_java_nio2.ch02;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.DosFileAttributes;
import java.nio.file.attribute.UserPrincipal;

public class MetaTest {

	public static void main(String[] args) {
		
		FileSystems.getDefault().supportedFileAttributeViews().forEach(System.out::println);
		
		Path path = Paths.get("rrd");
		try {
			BasicFileAttributes readAttributes = Files.readAttributes(path, BasicFileAttributes.class);
			System.out.println(readAttributes.creationTime().toMillis());
			System.out.println(readAttributes.fileKey());
			
			
			DosFileAttributes readAttributes2 = Files.readAttributes(path, DosFileAttributes.class, LinkOption.NOFOLLOW_LINKS);
			System.out.println(readAttributes2.isHidden());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			UserPrincipal lookupPrincipalByName = FileSystems.getDefault().getUserPrincipalLookupService().lookupPrincipalByName("guest");
			System.out.println(lookupPrincipalByName);
			
			System.out.println(Files.getOwner(path));
			
			//Files.getFileAttributeView(path, FileOwnerAttributeView.class).setOwner(lookupPrincipalByName);
			
			//PosixFileAttributes readAttributes = Files.readAttributes(path, PosixFileAttributes.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		FileSystems.getDefault().getFileStores().forEach(System.out::println);
	}
	
	
}
