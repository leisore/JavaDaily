package cn.leisore._20190115;

public class LambdaTest {

	public static void main(String[] args) {

		int n = 10;
		MyFunc<Integer> func = () -> {
			
			int t = n;
			n++;
			return t;
		};
		
	}

}

interface MyFunc<T> {
	T func();
}
