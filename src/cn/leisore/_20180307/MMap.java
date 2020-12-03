package cn.leisore._20180307;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.util.ArrayList;
import java.util.List;

public class MMap {

	public static void main(String[] args) throws Exception {
		
		int i = 0;
		List l = new ArrayList<>();
		while (true) {
			FileChannel fileChannel = new RandomAccessFile("D:/t.dat" + i++, "rw").getChannel();
			MappedByteBuffer mappedByteBuffer = fileChannel.map(MapMode.READ_WRITE, 0, 1 * 1024 * 1024 * 1024L);
			l.add(fileChannel);
			l.add(mappedByteBuffer);
			
			ByteBuffer bf = mappedByteBuffer.slice();
			bf.flip();
			System.out.println(bf.limit());
			System.out.println(bf.capacity());
			
			bf.limit(1 * 1024 * 1024 * 1024 - 1);
			bf.position(1 * 1024 * 1024 * 1024 - 1 - 8);
			bf.putLong(123L);
			//mappedByteBuffer.force();
			
			Thread.sleep(100);
			System.out.println(i);
		}
		
	}
}
