package cn.leisore._20170920;

import java.net.Socket;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMITest {

	public static void main(String[] args) throws Exception {
		
		Registry createRegistry = LocateRegistry.createRegistry(1099);
		Thread.sleep(30000L);
		
		Socket s = new Socket("localhost", 1099);
		s.getOutputStream().write((byte)'b');
		while (true) {
			System.out.println(s.getInputStream().read());
		}
	}

}
