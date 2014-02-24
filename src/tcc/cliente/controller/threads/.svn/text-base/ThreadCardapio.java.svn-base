package tcc.cliente.controller.threads;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import tcc.balcao.interfaces.Acoes;
import tcc.balcao.model.TransferObject;
import tcc.balcao.model.entity.Conta;
import tcc.balcao.model.entity.Mesa;
import tcc.balcao.model.entity.Pedidos;
import tcc.cliente.controller.DescobrirServidor;
import tcc.cliente.view.listener.ControllerListener;

@SuppressWarnings("unchecked")
public class ThreadCardapio implements Runnable{
	private Socket socket;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private InetAddress endServidor;
	private Boolean parar = false;
	private TransferObject to;

	private ControllerListener listener;
	
	public ThreadCardapio(ControllerListener listener) throws IOException {
		this.listener = listener;
		endServidor = DescobrirServidor.getInstance().descobrirServidor();
		socket = new Socket(endServidor, 1236);
		ois = new ObjectInputStream(socket.getInputStream());
		oos = new ObjectOutputStream(socket.getOutputStream());
	}

	@Override
	public void run() {

		while (!parar) {
			Object object;
			try {
				object = ois.readObject();

				if (object instanceof TransferObject) {
					to = (TransferObject) object;
					verificarOpcoes(to.getOpcao());
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	private void verificarOpcoes(int opcao) {
		switch (opcao) {
		case Acoes.INICIAR_CONEXAO:
			if (to.getObj() instanceof Mesa) {
				listener.mesaConectada((Mesa) to.getObj());
			}
			break;
		case Acoes.TOTAL_CONTA:
			if (to.getObj() instanceof Double) {
				Double valor = (Double) to.getObj();
				listener.setValorTotal(valor);
			}
			break;
		case Acoes.DESBLOQUEAR_MESA:
			if (to.getObj() instanceof Conta) {
				Conta conta = (Conta) to.getObj();
				listener.contaCriada(conta);
			}
			break;
		case Acoes.BUSCAR_TIPO_ITENS:
			HashMap<String, ArrayList<?>> itens = null;

			if (to.getObj() instanceof HashMap<?, ?>) {
				itens = (HashMap<String, ArrayList<?>>) to.getObj();
				listener.receberItens(itens);
			} else {
				new Exception("Objeto recebido não é instência de TransferObject");
			}
			break;
		case Acoes.PEDIDOS_CONTA:
			if (to.getObj() instanceof ArrayList<?>) {
				ArrayList<Pedidos> pedidos = (ArrayList<Pedidos>) to.getObj();
				listener.itensConta(pedidos);
			}
			break;
		case Acoes.BLOQUEAR_TELA:
			if (to.getObj() instanceof Mesa) {
				Mesa mesa = (Mesa) to.getObj();
				listener.bloquearTela(mesa);
			}
			break;
		case Acoes.CANCELAR_PEDIDO:
			if (to.getObj() instanceof Pedidos) {
				Pedidos pedido = (Pedidos) to.getObj();
				listener.respostaCancelar(pedido);
			}
		case Acoes.FECHAR_CONEXAO:
			if (to.getObj() instanceof Boolean) {
				parar = true;
			}
			break;
		case Acoes.ERRO:
			if (to.getObj() instanceof String) {
				String erro = (String) to.getObj();
				listener.apresentarErro(erro);
			}
			break;
		}
	}

	private void enviarObject(Object obj) throws IOException {
		oos.writeObject(obj);
	}

	@SuppressWarnings({ "unused", "unchecked" })
	public void iniciarConexao(Mesa mesa) throws IOException,
			ClassNotFoundException {

		TransferObject obj = new TransferObject(Acoes.INICIAR_CONEXAO, mesa);
		enviarObject(obj);
	}

	public void criarConta() throws IOException, ClassNotFoundException {
		enviarObject(new TransferObject(Acoes.DESBLOQUEAR_MESA, null));
	}

	public void getItens() throws IOException{

		TransferObject to = new TransferObject(Acoes.BUSCAR_TIPO_ITENS, null);
		this.enviarObject(to);
	}

	public void buscarPedidos(Conta conta) throws IOException,
			ClassNotFoundException {
		TransferObject obj = new TransferObject(Acoes.BUSCAR_PEDIDOS_CONTA,	conta);
		enviarObject(obj);

	}

	public void enviarPedido(ArrayList<Pedidos> pedido) throws IOException {
		TransferObject to = new TransferObject(Acoes.NOVO_PEDIDO, pedido);
		this.enviarObject(to);
	}

	public void fecharConexao(Mesa mesa) throws IOException,
			ClassNotFoundException {
		this.enviarObject(new TransferObject(Acoes.FECHAR_CONEXAO, mesa));
	}


	public void fecharConta() throws IOException {
		enviarObject(new TransferObject(Acoes.FECHAR_CONTA, null));
	}

	public void cancelarPedido(Pedidos pedido) throws IOException {
		enviarObject(new TransferObject(Acoes.CANCELAR_PEDIDO,pedido));
	}
}