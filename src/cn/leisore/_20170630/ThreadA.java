package cn.leisore._20170630;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class ThreadA extends Thread{
	private BlockingQueue<Integer> queue;
	
	public ThreadA(BlockingQueue<Integer> queue){
		this.queue = queue;
	}
	
	@Override
	public void run(){
		Random rd = new Random(6);
		while(true){
			try {
				this.queue.put(rd.nextInt(6) + 1);
			} catch (InterruptedException e) {
				return;
			}
		}
	}
}
