package cn.leisore._20181222;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Unicodes {

	public static void main(String[] args) {

		System.out.println(Character.forDigit(0x520001, 16));
		System.out.println(Character.charCount(0x520001));
		System.out.println(Character.toChars(0x4e25)[0]);
		
		byte[] bytes = String.valueOf(Character.toChars(0x4e25)).getBytes(StandardCharsets.UTF_8);
		System.out.println(Integer.toBinaryString(0x4e25&0xffff));
		System.out.println(Integer.toBinaryString(bytes[0]&0xff));
		System.out.println(Integer.toBinaryString(bytes[1]&0xff));
		System.out.println(Integer.toBinaryString(bytes[2]&0xff));
		
		System.out.println(Character.toChars(0x4e00)[0]);
		System.out.println(Character.toChars(0x9fa5)[0]);
		System.out.println(Character.toChars(0x9fff)[0]);
		
	}

}
