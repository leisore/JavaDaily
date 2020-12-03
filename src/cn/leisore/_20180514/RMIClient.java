package cn.leisore._20180514;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class RMIClient {
	public static void main(String[] args) throws Exception {
		ObjectInputStream oos = new ObjectInputStream(new FileInputStream("stub.bat"));
		NodeAdmin admin = (NodeAdmin) oos.readObject();
		oos.close();

		for (;;) {
			admin.shutdown();
			Thread.sleep(5000L);
		}
	}
}
