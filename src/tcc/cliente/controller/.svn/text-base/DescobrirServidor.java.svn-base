package tcc.cliente.controller;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class DescobrirServidor {
	private static DescobrirServidor instance = null;
	private DescobrirServidor() {}
	
	public static DescobrirServidor getInstance(){
		if (instance == null) {
			instance = new DescobrirServidor();
		}
		return instance;
	}
	
	public InetAddress descobrirServidor() throws IOException {
		DatagramSocket dtSocket = new DatagramSocket();
		byte[] buf = new byte[0];
		DatagramPacket dtPacket = new DatagramPacket(buf, buf.length, InetAddress.getByName("234.0.0.95"), 1231);
		dtSocket.send(dtPacket);
		
		buf = new byte[0];
		dtPacket = new DatagramPacket(buf, buf.length);
		dtSocket.receive(dtPacket);
		
		return dtPacket.getAddress();
	}
}
