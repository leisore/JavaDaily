package cn.leisore._20170406;

import org.apache.thrift.TException;

public class VarInt {

	public static void main(String[] args) throws TException {
writeVarint64(longToZigzag(-1L));
	}

	static int intToZigZag(int n) {
		return (n << 1) ^ (n >> 31);
	}

	static long longToZigzag(long l) {
		return (l << 1) ^ (l >> 63);
	}

	static byte[] varint64out = new byte[10];

	static void writeVarint64(long n) throws TException {
		int idx = 0;
		while (true) {
			if ((n & ~0x7FL) == 0) {
				varint64out[idx++] = (byte) n;
				break;
			} else {
				varint64out[idx++] = ((byte) ((n & 0x7F) | 0x80));
				n >>>= 7;
			}
		}
		System.out.println(idx);
	}
}
