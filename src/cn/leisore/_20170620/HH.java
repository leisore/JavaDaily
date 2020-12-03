package cn.leisore._20170620;

import com.sun.xml.internal.ws.Closeable;

public class HH {
	static {
		System.out.println("HH:" + HH.class.getClassLoader());
	}

	private static void testMethod() {
		System.out.println("testMethod");
	}

	public static void main(String[] args) {
		((HH) null).testMethod();
		main2();
	}

	public static void main2() {
		int t;
		// i代表最高位上的数字
		for (int i = 1; i <= 9; i++) {
			// j代表最低位上的数字
			for (int j = 0; j <= 9; j++) {
				//if (i != j) // 题目要求，两位不相等
				{
					// i、j组成的四位数
					t = i * 1000 + i * 100 + j * 10 + j;
					// k的取值根据四位数字开平方得到的大概范围
					for (int k = 30; k < 100; k++) {
						if (k * k == t) // 判断是否是平方数
						{
							System.out.println("车牌号码：" + k * k + " " + k);
						}
					}
				}
			}

		}
	}

	static enum Lee implements Cloneable {

	}

	static interface LEE extends Cloneable, Closeable {
	}

	static abstract class EEL {
	}
}
