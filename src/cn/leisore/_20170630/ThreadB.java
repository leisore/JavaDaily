package cn.leisore._20170630;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class ThreadB extends Thread{
	private BlockingQueue<Integer> queue;
	private ThreadA ta;
	
	private List<Integer> list = new ArrayList<Integer>();
	
	public ThreadB(BlockingQueue<Integer> queue, ThreadA ta){
		this.ta = ta;
		this.queue = queue;
	}
	
	@Override
	public void run(){
		while(true){
			try {
				Integer take = this.queue.take();
				if(list.size() >= 3){
					list.remove(0);
				}
				list.add(take);
				
				System.out.println(take);
				if(list.size() == 3){
					if(list.get(0) == 6 && list.get(1) == 6 && list.get(2) == 6 ){
						ta.interrupt();
						return ;
					}
				}
			} catch (InterruptedException e) {
				
			}
		}
	}
}
