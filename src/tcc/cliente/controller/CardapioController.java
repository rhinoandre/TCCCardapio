package tcc.cliente.controller;

import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.UnsupportedLookAndFeelException;

import tcc.balcao.interfaces.Acoes;
import tcc.balcao.interfaces.StatusMesas;
import tcc.balcao.interfaces.StatusPedidos;
import tcc.balcao.model.entity.Conta;
import tcc.balcao.model.entity.Item;
import tcc.balcao.model.entity.Mesa;
import tcc.balcao.model.entity.Pedidos;
import tcc.balcao.model.entity.Statuspedido;
import tcc.cliente.controller.threads.ThreadCardapio;
import tcc.cliente.view.ProdutoEspecificacaoForm;
import tcc.cliente.view.TelaCardapioPrincipal;
import tcc.cliente.view.TelaDesbloqueio;
import tcc.cliente.view.TelaError;
import tcc.cliente.view.TelaEscolhaMesa;
import tcc.cliente.view.TelaListaPedidos;
import tcc.cliente.view.listener.ControllerListener;
import tcc.cliente.view.listener.TelaCardapioAdapter;
import tcc.cliente.view.listener.TelaDesbloqueioListener;
import tcc.cliente.view.listener.TelaErrorAdapter;
import tcc.cliente.view.listener.TelaEscolhaMesaListener;
import tcc.cliente.view.listener.TelaListaPedidosListener;
import tcc.cliente.view.listener.TelaProdutoEspecificacoesListener;

public class CardapioController {
	private Mesa mesaAtual;
//	TELAS
	private TelaCardapioPrincipal telaCardapio;
	private TelaEscolhaMesa telaEscolhaMesa;
	private TelaDesbloqueio telaDesbloqueio;
	private TelaListaPedidos telaCarrinho, telaListaPedido;
	private TelaError telaError;

	private ThreadCardapio enviarSolicitacoes;
	
	@SuppressWarnings("unused")
	private JFrame telaAtual;
	
	private ProdutoEspecificacaoForm produtoEspecificacoes;
	
	private ArrayList<Pedidos> carrinho = new ArrayList<Pedidos>();
	private HashMap<String, ArrayList<?>> hashTipos = null;
	
	public CardapioController() {
		telaAtual = new JFrame();
		
		try {
			solicitacoes();
			escolherMesa();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}
		private void solicitacoes() throws IOException {
		enviarSolicitacoes = new ThreadCardapio(new ControllerListener(){
			
			@Override
			public void mesaConectada(Mesa mesa) {
				mesaAtual = mesa;
				if (mesaAtual.getStatusmesa().getStatus().equals(StatusMesas.LIVRE.getStatus())) {
					telaEscolhaMesa.dispose();
					iniciarCliente();
				} else if (mesaAtual.getStatusmesa().getStatus().equals(StatusMesas.OCUPADA.getStatus())){
					telaEscolhaMesa.dispose();
					telaCardapio();
				} else {
					apresentarErro("Conexão recusada com o servidor");
				}
			}
			@Override
			public void setValorTotal(Double valor) {
				telaCardapio.setTotalConta(valor);
			}
			
			@Override
			public void contaCriada(Conta conta) {
				mesaAtual.setConta(conta);
				telaDesbloqueio.dispose();
				telaCardapio();
			}
			
			@Override
			public void receberItens(HashMap<String, ArrayList<?>> itens) {
				hashTipos = itens; 
			}
			
			@Override
			public void itensConta(ArrayList<Pedidos> pedidos) {
				try {
					telaListaPedido = new TelaListaPedidos(Acoes.PEDIDOS_REALIZADOS, pedidos);
					telaListaPedido.addListener(new TelaListaPedidosListener(){

						@Override
						public void enviarListaPedidos() {
							// TODO Fazer
						}

						@Override
						public void fecharAction() {
							telaListaPedido.dispose();
						}

						@Override
						public void removerDaLista(Pedidos pedido) {
							
								try {
									if (!pedido.getStatuspedido().getStatus().equals(StatusPedidos.ABERTO.getStatus())) {
										apresentarTelaErro("Impossível cancelar pedidos, ele está sendo execução ou já foi cancelado.");
									} else {
										pedido.setStatuspedido(StatusPedidos.CANCELADO);
										enviarSolicitacoes.cancelarPedido(pedido);
									}
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
						}
					});
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (UnsupportedLookAndFeelException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void apresentarErro(String erro) {
				apresentarTelaErro(erro);
			}
			@Override
			public void bloquearTela(Mesa mesa) {
				telaCardapio.dispose();
				iniciarCliente();
			}
			@Override
			public void respostaCancelar(Pedidos pedido) {
				apresentarErro("Produto cancelado com sucesso!");
				telaListaPedido.alterarStatus(pedido);
			}
		});
		
		new Thread(enviarSolicitacoes).start();
		
//Efetua o carregamento antes de qualquer ação do cliente, 
//para que não haja demora no carregamento do cardápio
		enviarSolicitacoes.getItens();
	}

	private void escolherMesa() throws UnknownHostException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, IOException {
			telaEscolhaMesa = new TelaEscolhaMesa(BuscaMesas.getInstance().buscarMesas());
			telaEscolhaMesa.addListener(new TelaEscolhaMesaListener() {
				public void okButtonAction() {
						mesaAtual = telaEscolhaMesa.pegarMesaComboBox();
						if (mesaAtual.getStatusmesa().getStatus().equals(StatusMesas.ERRO.getStatus())) {
							try {
								telaError = new TelaError(Acoes.WARNING, "A " + mesaAtual.getMesa() + ", já possui uma conta vinculada. Deseja utilizar está mesa?");
								telaError.addListener(new TelaErrorAdapter(){
									
									@Override
									public void naoAction() {
										telaError.dispose();
									}
									
									@Override
									public void simAction() {
										try {
											mesaAtual.setStatusmesa(StatusMesas.OCUPADA);
											enviarSolicitacoes.iniciarConexao(mesaAtual);
											telaError.dispose();
										} catch (IOException e) {
											e.printStackTrace();
										} catch (ClassNotFoundException e) {
											e.printStackTrace();
										} catch (Exception e) {
											e.printStackTrace();
										}
									}
								});
							} catch (ClassNotFoundException e1) {
								e1.printStackTrace();
							} catch (InstantiationException e1) {
								e1.printStackTrace();
							} catch (IllegalAccessException e1) {
								e1.printStackTrace();
							} catch (UnsupportedLookAndFeelException e1) {
								e1.printStackTrace();
							}
						}else{
							try {
								enviarSolicitacoes.iniciarConexao(mesaAtual);
							} catch (IOException e) {
								e.printStackTrace();
							} catch (ClassNotFoundException e) {
								e.printStackTrace();
							}
						}
				}
			});
		System.out.println(StatusMesas.AGUARDANDO);
		System.out.println(StatusMesas.ERRO);
		System.out.println(StatusMesas.LIVRE);
		System.out.println(StatusMesas.OCUPADA);
		System.out.println(StatusMesas.OFFILINE);
	}

	private void iniciarCliente() {
		try {
			telaDesbloqueio = new TelaDesbloqueio();
			telaDesbloqueio.addListener(new TelaDesbloqueioListener() {
				
				public void liberarCardapio() {
					try {
						enviarSolicitacoes.criarConta();
					} catch (IOException e) {
						System.out.println("Erro ao criar a conta");
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						System.out.println("Erro ao receber a conta do servidor");
						e.printStackTrace();
					} 					
				}

				@Override
				public void fecharTela(WindowEvent event) {
					fecharConexao();
				}
			});
			
//Alterar qual tela está em exibição
			alterarTelaAtual(telaDesbloqueio);
			
		} catch (Exception e) {
			System.out.println("Erro no método iniciarCliente() quando tentei criar a tela de desbloqueio");
			e.printStackTrace();
		}
	}

	private void telaCardapio() {
		try {
			telaCardapio = new TelaCardapioPrincipal(hashTipos);
			telaCardapio.addListener(new TelaCardapioAdapter() {
				
				@SuppressWarnings("deprecation")
				public void atualizarListener(ActionEvent event) {
					try {
						telaCardapio.atualizaLista(((JButton) event.getSource()).getLabel());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				public void mostrarEspecificacoes(Item produto) {
					telaCardapio.setEnabled(false);
					produtoEspecificacoes = new ProdutoEspecificacaoForm(produto);
					produtoEspecificacoes.addListener(new TelaProdutoEspecificacoesListener() {
						public void realizarPedido(Item produto) {
							Pedidos pedido = new Pedidos();
							pedido.setItem(produto);
							pedido.setConta(mesaAtual.getConta());
							pedido.setMesa(mesaAtual);
							pedido.setQuantidade(produtoEspecificacoes.getQuantidade());
							ArrayList<Pedidos> pedidos = new ArrayList<Pedidos>();
							pedidos.add(pedido);
							try {
								enviarSolicitacoes.enviarPedido(pedidos);
							} catch (IOException e) {
								e.printStackTrace();
							}
							telaCardapio.setEnabled(true);
							produtoEspecificacoes.dispose();
						}
						
						public void cancelarAction() {
							telaCardapio.setEnabled(true);
							produtoEspecificacoes.dispose();
						}

						public void addPedidosLista(Item produto) {
							addPedidoLista(produto, produtoEspecificacoes.getQuantidade());
							telaCardapio.alterarQtdCarrinho(carrinho.size());
						}
					});
				}

				public void abrirListaPedidos() {
					try {
						if(carrinho.size() > 0){
							telaCarrinho = new TelaListaPedidos(Acoes.LISTA_PEDIDOS, carrinho);
							telaCarrinho.addListener(new TelaListaPedidosListener() {
								public void enviarListaPedidos() {
									try {
										enviarSolicitacoes.enviarPedido(carrinho);
										telaCarrinho.dispose();
										carrinho = new ArrayList<Pedidos>();
										telaCardapio.alterarQtdCarrinho(carrinho.size());
									} catch (IOException e) {
										apresentarTelaErro("Pau na hora de enviar o a lista de pedidos ao servidor");
										e.printStackTrace();
									}
								}

								@Override
								public void fecharAction() {
									telaCarrinho.dispose();
								}

								public void removerDaLista(Pedidos pedido) {
									carrinho.remove(carrinho.indexOf(pedido));
									telaCarrinho.repaint();
									telaCardapio.alterarQtdCarrinho(carrinho.size());
								}
							});
						alterarTelaAtual(telaCarrinho);
						}
					} catch (ClassNotFoundException e) {
						apresentarTelaErro("Erro: Classe não encontrada. Classe: CardapioController, no listener do abrirListaPedidos()");
						e.printStackTrace();
					} catch (InstantiationException e) {
						apresentarTelaErro("Erro: InstantiationException. Classe: CardapioController, no listener do abrirListaPedidos()");
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						apresentarTelaErro("Erro: Acesso Ilegal. Classe: CardapioController, no listener do abrirListaPedidos()");
						e.printStackTrace();
					} catch (UnsupportedLookAndFeelException e) {
						apresentarTelaErro("Erro: Lock and feel não suportada. Classe: CardapioController, no listener do abrirListaPedidos()");
						e.printStackTrace();
					}
				}

				public void pedidosRealizados() {
					try {
						enviarSolicitacoes.buscarPedidos(mesaAtual.getConta());
					} catch (IOException e) {
						e.printStackTrace();
						System.out.println("Erro de entrada e saída");
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
						System.out.println("Classe não encontrada");
					};
				}

				public void fecharConta(){
					try {
						telaError = new TelaError(Acoes.WARNING, "Deseja realmente fechar a conta?"); 
						telaError.addListener(new TelaErrorAdapter(){

							public void naoAction() {
								telaError.dispose();
							}

							@Override
							public void simAction() {
								try {
									enviarSolicitacoes.fecharConta();
									telaError.dispose();
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						});
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (InstantiationException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (UnsupportedLookAndFeelException e) {
						e.printStackTrace();
					}
				}
				
				@Override
				public void fecharTela(WindowEvent event) {
					fecharConexao();
				}

			});
			
//			Alterar qual tela está em exibição
			alterarTelaAtual(telaCardapio);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	public void addPedidoLista(Item item, Integer qtd){
		Pedidos pedido = new Pedidos();
		pedido.setConta(mesaAtual.getConta());
		pedido.setMesa(mesaAtual);
		pedido.setQuantidade(qtd);
		pedido.setItem(item);
		
		carrinho.add(pedido);
	}
	
	public void alterarTelaAtual(JFrame tela){
		this.telaAtual = tela;
	}
	
	public void criaPedido(ArrayList<Pedidos> pedido) throws IOException {
		enviarSolicitacoes.enviarPedido(pedido);
	}
	
	private void apresentarTelaErro(String erro) {
		try {
			telaError = new TelaError(Acoes.ERRO, erro);
			telaError.addListener(new TelaErrorAdapter(){

				@Override
				public void okAction() {
					telaError.dispose();
				}
			});
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}
	protected void fecharConexao() {
		try {
			enviarSolicitacoes.fecharConexao(mesaAtual);
		} catch (IOException e) {
			System.out.println("Erro ao fechar a conexão. Método: fecharConexao()");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("Erro ao fechar a conexão. Método: fecharConexao()");
			e.printStackTrace();
		}
	}

}