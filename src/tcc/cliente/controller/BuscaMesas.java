package tcc.cliente.controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import tcc.balcao.interfaces.Acoes;
import tcc.balcao.model.TransferObject;
import tcc.balcao.model.entity.Mesa;


public class BuscaMesas {
	private static BuscaMesas instance = null;
	private Socket socket;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private InetAddress endServidor;
	
	private BuscaMesas() throws UnknownHostException, IOException {
		endServidor = DescobrirServidor.getInstance().descobrirServidor();
		socket = new Socket(endServidor, 1234);
		ois = new ObjectInputStream(socket.getInputStream());
		oos = new ObjectOutputStream(socket.getOutputStream());
	}
	
	public static BuscaMesas getInstance() throws UnknownHostException, IOException{
		if(instance == null){
			instance = new BuscaMesas();
		}
		return instance;
	}
	
	public ArrayList<Mesa> buscarMesas() throws IOException, ClassNotFoundException{
		
		TransferObject to = new TransferObject(Acoes.BUSCAR_MESAS, null);
		oos.writeObject(to);
		Object obj = ois.readObject();
		ArrayList<Mesa> mesas = null;
		
		if (obj instanceof TransferObject) {
			to = (TransferObject) obj;
			mesas = (ArrayList<Mesa>) to.getObj();
		}
		
		return mesas;
	}
	
}