package tcc.cliente.view.listener;

import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;

import tcc.balcao.model.entity.Item;

public interface TelaCardapioListener {
//Método para atualizar o número de itens na lista de pedido
	public void atualizarListener(ActionEvent event);  
	
//Ações para pedidos
	public void pedidosRealizados();
	public void fecharConta();
	
//Ações para telas
	public void liberarCardapio();
	public void mostrarEspecificacoes(Item produto);
	public void abrirListaPedidos();
	public void fecharTela(WindowEvent event);
}