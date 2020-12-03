package cn.leisore._201180516;

import java.nio.ByteBuffer;

public class BFTest {

	public static void main(String[] args) {

		ByteBuffer bb = ByteBuffer.allocate(1024);
		
		bb.slice().putInt(1).putInt(2).putInt(3);
		
		System.out.println("bb:");
		System.out.println("\tcapacity:" + bb.capacity());
		System.out.println("\tlimit:" + bb.limit());
		System.out.println("\tposition:" + bb.position());
		
		ByteBuffer bb1 = bb.slice();
		bb1.position(0);
		bb1.limit(8);
		System.out.println("bb1:");
		System.out.println("\tcapacity:" + bb1.capacity());
		System.out.println("\tlimit:" + bb1.limit());
		System.out.println("\tposition:" + bb1.position());
		
		while (bb1.hasRemaining()) {
			System.out.println(bb1.getInt());
		}
	}

}
