package tcc.cliente.view.listener;

import tcc.balcao.model.entity.Pedidos;

public interface TelaListaPedidosListener {
	public void enviarListaPedidos();
	public void fecharAction();
	public void removerDaLista(Pedidos pedido);
}