package cn.leisore._20171219;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

public class TestGuavaBus {

	public static void main(String[] args) {

		EventBus bus = new EventBus("test");
		bus.register(new Object() {
			
			@Subscribe
			public void handleString(String s) {
				System.out.println(s);
			}
			
			@Subscribe
			public void handleInt(Integer i) {
				System.out.println(i);
			}
		});
		
		bus.post("123");
		bus.post(456);
	}
}
