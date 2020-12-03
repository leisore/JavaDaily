package cn.leisore._20190925;

import java.util.Base64;
import java.util.Date;

public class Main {

	public static void main(String[] args) {

		{
			short AppID = 12;
			byte[] IP = { (byte) 124, (byte) 125, (byte) 126, (byte) 127 };
			short Port = 8080;
			long hidv4 = (((long)AppID << 1) & 0xFFFE) << 48
	                   | (long)IP[0] << 40
	                   | (long)IP[1] << 32
	                   | (long)IP[2] << 24
	                   | (long)IP[3] << 16
	                   | Port;
	                  
	                     
			System.out.println(hidv4);
			System.out.println(hidv4 >> 48 >> 1);
			System.out.println((hidv4 >> 40) & 0xFF);
			System.out.println((hidv4 >> 32) & 0xFF);
			System.out.println((hidv4 >> 24) & 0xFF);
			System.out.println((hidv4 >> 16) & 0xFF);
			System.out.println(hidv4& 0xFFFF);
			
			System.out.println();
			
			long Time = System.currentTimeMillis();
			long ConnId= 200;
			
			long M = (Time & 0xFFFFFFFFFFFFl) << 16 | (ConnId & 0xFFFF);
			System.out.println(M);
			System.out.println(Time);
			System.out.println((M>>16) & 0xFFFFFFFFFFFFl);
			System.out.println(M& 0xFFFF);
			
			System.out.println();
			byte SessionId = (byte)22;
			byte ProducerId = (byte)33;
			long MsgId = 123456789L;
			long L = (long)(SessionId & 0xFF) << 56 |
					(long)(ProducerId & 0xFF) << 48 |
					(MsgId & 0xFFFFFFFFFFFFl);
			
			System.out.println(L);
			System.out.println((L>>56)&0xFF);
			System.out.println((L>>48)&0xFF);
			System.out.println(L&0xFFFFFFFFFFFFl);
		}
		
		
		
		System.out.println("=========IPv6===========");
		{
			short AppID = 12;
			short[] IP = { (short) 10000, (short) 10001, (short) 10002, (short) 10003,(short) 10004, (short) 10005, (short) 10006, (short) 10007 };
			short Port = 8080;
			int H1 = (((int)AppID << 1) | 0x01) << 16
	                   | (int)IP[0]& 0xFFFFFFF;
	                     
			System.out.println(H1);
			System.out.println(H1 >> 16 >> 1);
			System.out.println(H1& 0xFFFF);
			
			long H2 = ((long)IP[1] & 0xFFFF) << 48 |
					((long)IP[2] & 0xFFFF) << 32 |
					((long)IP[3]&  0xFFFF) << 16 |
					((long)IP[4]&  0xFFFF);
			System.out.println(H2);
			System.out.println((H2 >> 48)&0xFFFF);
			System.out.println((H2 >> 32)&0xFFFF);
			System.out.println((H2 >> 16)&0xFFFF);
			System.out.println((H2 )&0xFFFF);
			
			long H3 = ((long)IP[5]&  0xFFFF) << 48 |
					((long)IP[6]&  0xFFFF) << 32 |
					((long)IP[7]&  0xFFFF) << 16 |
					Port & 0xFFFF;
			System.out.println(H3);
			System.out.println((H3 >> 48)&0xFFFF);
			System.out.println((H3 >> 32)&0xFFFF);
			System.out.println((H3 >> 16)&0xFFFF);
			System.out.println((H3 )&0xFFFF);
					
		}
		
	}

}
