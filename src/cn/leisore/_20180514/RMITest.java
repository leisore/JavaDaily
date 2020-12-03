package cn.leisore._20180514;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;

public class RMITest {
	public static void main(String[] args) throws Exception {
		Object stub = UnicastRemoteObject.exportObject(new NodeAdminImpl(), 0, new TRMIClientSocketFactory(),
				new TRMIServerSocketFactory());
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("stub.bat"));
		oos.writeObject(stub);
		oos.close();
		Thread.sleep(100000000000L);
	}
}

interface NodeAdmin extends Remote {
	void shutdown() throws RemoteException;
}

class TRMIClientSocketFactory implements RMIClientSocketFactory, Serializable {
	@Override
	public Socket createSocket(String host, int port) throws IOException {
		return new Socket(InetAddress.getLoopbackAddress(), port);
	}
}

class TRMIServerSocketFactory implements RMIServerSocketFactory, Serializable {
	@Override
	public ServerSocket createServerSocket(int port) throws IOException {
		System.out.println(InetAddress.getLoopbackAddress());
		ServerSocket ss = new ServerSocket(port, 1, InetAddress.getLoopbackAddress());
		return ss;
	}
}

class NodeAdminImpl implements NodeAdmin {

	@Override
	public void shutdown() {
		System.out.println("shutdown");
	}

}
