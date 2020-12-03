package cn.leisore._20170630;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;

public class Start {
	public static void main(String[] args) {
		ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(1);
		ThreadA a = new ThreadA(queue);
		ThreadB b = new ThreadB(queue,a);
		a.start();
		b.start();
	}
}
