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
		// i�������λ�ϵ�����
		for (int i = 1; i <= 9; i++) {
			// j�������λ�ϵ�����
			for (int j = 0; j <= 9; j++) {
				//if (i != j) // ��ĿҪ����λ�����
				{
					// i��j��ɵ���λ��
					t = i * 1000 + i * 100 + j * 10 + j;
					// k��ȡֵ������λ���ֿ�ƽ���õ��Ĵ�ŷ�Χ
					for (int k = 30; k < 100; k++) {
						if (k * k == t) // �ж��Ƿ���ƽ����
						{
							System.out.println("���ƺ��룺" + k * k + " " + k);
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
