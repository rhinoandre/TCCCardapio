package tcc.cliente.controller.action;

import java.util.ArrayList;

import tcc.balcao.model.entity.Pedidos;

public class TelasActions {
	private static TelasActions instance = new TelasActions();
	
	private TelasActions() {}
	
	public static TelasActions getInstance(){
		return instance;
	}
	
	public float calcularValorTotal(ArrayList<Pedidos> pedidos){
		float valorTotal = 0;
		for (Pedidos pedido : pedidos) {
			valorTotal += (float) (pedido.getQuantidade() * pedido.getItem().getValor());
		}
		
		return valorTotal;
	}
}