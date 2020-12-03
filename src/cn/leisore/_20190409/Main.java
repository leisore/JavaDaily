package cn.leisore._20190409;

public class Main {

	public static void main(String[] args) {

		int availableProcessors = Runtime.getRuntime().availableProcessors();
		long start = System.currentTimeMillis();
		for (int i = 0; i < availableProcessors; i++) {
			new Thread(() -> {
				long x = 1;
				while ((System.currentTimeMillis() - start) < 60000) {
					x = x * x;
				}
			}).start();
		}

	}

}
