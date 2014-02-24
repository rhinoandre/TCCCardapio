package tcc.cliente.view.listener;

import java.util.ArrayList;
import java.util.HashMap;

import tcc.balcao.model.entity.Conta;
import tcc.balcao.model.entity.Mesa;
import tcc.balcao.model.entity.Pedidos;

public interface ControllerListener {
	public void mesaConectada(Mesa mesa);
	public void contaCriada(Conta conta);
	public void receberItens(HashMap<String, ArrayList<?>> itens);
	public void itensConta(ArrayList<Pedidos> pedidos);
	public void apresentarErro(String erro);
	public void setValorTotal(Double valor);
	public void bloquearTela(Mesa mesa);
	public void respostaCancelar(Pedidos pedido);
}
