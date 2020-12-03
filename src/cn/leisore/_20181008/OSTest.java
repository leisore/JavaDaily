package cn.leisore._20181008;

public class OSTest {
	public static void main(String[] args) {
		f3();
		System.out.println(System.getProperties());
	}
	
	static void f1() {
		throw new RuntimeException("f1");
	}
	
	static void f2() {
		try {
			f1();
		} catch (Exception e) {
			throw new RuntimeException("f2" ,e);
		}
	}
	
	static void f3() {
		try {
			f2();
		} catch (Exception e) {
			RuntimeException runtimeException = new RuntimeException("f3");
			runtimeException.setStackTrace(e.getStackTrace());
			throw runtimeException;
		}
	}
}
