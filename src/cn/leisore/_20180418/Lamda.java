package cn.leisore._20180418;

public class Lamda {

	public static void main(String[] args) {
		Lamda l = new Lamda();
		l.apply(l::test);
		System.out.println("done");
	}
	
	void apply(A a) {
		if (a.isOK()) {
			System.out.println(124);
		} else {
			System.out.println(456);
		}
	}
	
	boolean test() {
		return true;
	}
	
	int test2() {
		return 1;
	}

}

interface A {
	boolean isOK();
}
