package tcc.cliente.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import net.miginfocom.swing.MigLayout;
import tcc.balcao.interfaces.Acoes;
import tcc.balcao.model.entity.Pedidos;
import tcc.cliente.controller.action.TelasActions;
import tcc.cliente.view.listener.TelaListaPedidosListener;

public class TelaListaPedidos extends JFrame {
	private static final long serialVersionUID = 6566785817526599747L;
	private JPanel panelPrincipal, panelBotoes, geralPanelBG;
	private JLabel valorLabel;
	private JButton pedirButton, fecharButton, removerButton;
	private JTable tabelaProdutos;
	private String[] cabecalhoCarrinho = new String[]{"Qtd.", "Valor", "Produto" };
	private String[] cabecalhoPedidos = new String[]{"Qtd.", "Valor", "Produto", "Status" };
	int acao;
	private DefaultTableModel tableModel;
	private ArrayList<TelaListaPedidosListener> telaCarrinhoListeners = new ArrayList<TelaListaPedidosListener>();
	private ArrayList<Pedidos> pedidos = new ArrayList<Pedidos>();

	public TelaListaPedidos(int acao, ArrayList<Pedidos> pedidos) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		this.acao = acao;
		this.pedidos = pedidos;
		
		iniciarComponentes();
		if(acao == Acoes.PEDIDOS_REALIZADOS){
			criarTabelaPedidos();
		}
		else if(acao == Acoes.LISTA_PEDIDOS){
			criarTabelaCarrinho();
		}
		getGeralPanelBG().add(criarBotoes(), "dock south, growy");
		
		add(geralPanelBG);
		pack();
		setVisible(true);
		setLocationRelativeTo(null);

	}


	private void iniciarComponentes() throws ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			UnsupportedLookAndFeelException {
		
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new MigLayout());
		setAlwaysOnTop(true);
		setResizable(false);
		setUndecorated(true);
		setBackground(new Color(73, 33, 33, 255));
	}

	private void criarTabelaCarrinho() {
		if(panelPrincipal != null){
			panelPrincipal.removeAll();
			panelPrincipal = null;
		}
		if (panelPrincipal == null) {
			panelPrincipal = new JPanel(new MigLayout());
			tabelaProdutos = new JTable();
			tabelaProdutos.setDragEnabled(false);
			JScrollPane scrollPane = new JScrollPane();
			tabelaProdutos.setModel(getTableModel());
			tabelaProdutos.getColumnModel().setColumnSelectionAllowed(false);
			tabelaProdutos.getColumnModel().getColumn(2).setPreferredWidth(240);
			tabelaProdutos.getColumnModel().getColumn(1).setPreferredWidth(20);
			tabelaProdutos.getColumnModel().getColumn(0).setPreferredWidth(10);
			tabelaProdutos.setRowHeight(30);
			tabelaProdutos.setFont(new Font("Calibri", 2, 20));
			tabelaProdutos.setForeground(Color.white);
			tabelaProdutos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tabelaProdutos.setGridColor(new Color(52, 17, 17, 255));
			tabelaProdutos.setBackground(new Color(73, 33, 33, 255));
			tabelaProdutos.setSelectionBackground(new Color(151, 119, 119, 255));
			tabelaProdutos.setPreferredScrollableViewportSize(new Dimension(527,497));
			tabelaProdutos.setMinimumSize(new Dimension(527,497));
			JTableHeader cabecalho = tabelaProdutos.getTableHeader();  
			cabecalho.setFont(new Font("Calibri", 0, 15));
			cabecalho.setForeground(Color.white);
			cabecalho.setBackground(new Color(99, 64, 64, 255));
			cabecalho.setEnabled(false);
			cabecalho.setOpaque(false);
			setDados();
			scrollPane.setViewportView(tabelaProdutos);
			scrollPane.getViewport().setBackground(new Color(73, 33, 33, 255));
			scrollPane.setPreferredSize(tabelaProdutos.getPreferredScrollableViewportSize());
			scrollPane.setMinimumSize(tabelaProdutos.getMinimumSize());
			scrollPane.setBorder(BorderFactory.createLineBorder(new Color(52, 17, 17, 255), 1));
			
			panelPrincipal.setBackground(Color.black);
			panelPrincipal.add(scrollPane, "dock north");
		}
		getGeralPanelBG().add(panelPrincipal);
		
	}

	public void criarTabelaPedidos() {
		if(panelPrincipal != null){
			panelPrincipal.removeAll();
			panelPrincipal = null;
		}
		if (panelPrincipal == null) {
			panelPrincipal = new JPanel(new MigLayout());
			tabelaProdutos = new JTable();
			tabelaProdutos.setDragEnabled(false);
			JScrollPane scrollPane = new JScrollPane();
			tabelaProdutos.setModel(getTableModel());
			tabelaProdutos.getColumnModel().setColumnSelectionAllowed(false);
			tabelaProdutos.getColumnModel().getColumn(3).setPreferredWidth(25);
			tabelaProdutos.getColumnModel().getColumn(2).setPreferredWidth(220);
			tabelaProdutos.getColumnModel().getColumn(1).setPreferredWidth(20);
			tabelaProdutos.getColumnModel().getColumn(0).setPreferredWidth(5);
			tabelaProdutos.setRowHeight(30);
			tabelaProdutos.setFont(new Font("Calibri", 2, 20));
			tabelaProdutos.setForeground(Color.white);
			tabelaProdutos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tabelaProdutos.setGridColor(new Color(52, 17, 17, 255));
			tabelaProdutos.setBackground(new Color(73, 33, 33, 255));
			tabelaProdutos.setSelectionBackground(new Color(151, 119, 119, 255));
			tabelaProdutos.setPreferredScrollableViewportSize(new Dimension(527,497));
			tabelaProdutos.setMinimumSize(new Dimension(527,497));
			JTableHeader cabecalho = tabelaProdutos.getTableHeader();  
			cabecalho.setFont(new Font("Calibri", 0, 15));
			cabecalho.setForeground(Color.white);
			cabecalho.setBackground(new Color(99, 64, 64, 255));
			cabecalho.setEnabled(false);
			cabecalho.setOpaque(false);
			setDados();
			scrollPane.setViewportView(tabelaProdutos);
			scrollPane.getViewport().setBackground(new Color(73, 33, 33, 255));
			scrollPane.setPreferredSize(tabelaProdutos.getPreferredScrollableViewportSize());
			scrollPane.setMinimumSize(tabelaProdutos.getMinimumSize());
			scrollPane.setBorder(BorderFactory.createLineBorder(new Color(52, 17, 17, 255), 1));
			
			panelPrincipal.setBackground(Color.black);
			panelPrincipal.add(scrollPane, "dock north");
		}
		getGeralPanelBG().add(panelPrincipal);
	}


	public DefaultTableModel getTableModel() {
		if (tableModel == null) {
			if(acao == Acoes.LISTA_PEDIDOS){
				tableModel = new DefaultTableModel(null, cabecalhoCarrinho) {
			

				private static final long serialVersionUID = 1L;

				public boolean isCellEditable(int row, int col) {
					return false;
				}
				};
			}
			else if(acao == Acoes.PEDIDOS_REALIZADOS){
				tableModel = new DefaultTableModel(null, cabecalhoPedidos) {
			

				private static final long serialVersionUID = 1L;

				public boolean isCellEditable(int row, int col) {
					return false;
				}
				};
			}
		}
		return tableModel;
	}

	public void setDados() {
		for (Pedidos pedido : pedidos) {
			Vector<String> linha = new Vector<String>();
			linha.add(Integer.toString(pedido.getQuantidade()));
			linha.add("R$ " + String.format("%.2f", pedido.getItem().getValor()));
			linha.add(pedido.getItem().getNome());
			if(acao == Acoes.PEDIDOS_REALIZADOS){
				linha.add(pedido.getStatuspedido().getStatus());
			}
			getTableModel().addRow(linha);
		}
	}
	

	private JPanel criarBotoes() {
		if (panelBotoes == null) {
			panelBotoes = new JPanel(new MigLayout("", "[]push[][][]"));
			pedirButton = new JButton(new ImageIcon("imgs\\detalhesProduto\\imgPedir.png"));
			pedirButton.setPressedIcon(new ImageIcon("imgs\\detalhesProduto\\imgPedirPressed.png"));
			configurarBotoes(pedirButton);
			pedirButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					for (TelaListaPedidosListener listener : telaCarrinhoListeners) {
						listener.enviarListaPedidos();
					}
				}
			});
			
			fecharButton = new JButton(new ImageIcon("imgs\\listaPedidos\\imgFechar.png"));
			fecharButton.setPressedIcon(new ImageIcon("imgs\\listaPedidos\\imgFecharPressed.png"));
			configurarBotoes(fecharButton);
			fecharButton.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent arg0) {
					for (TelaListaPedidosListener listener : telaCarrinhoListeners) {
						listener.fecharAction();
					}
				}
			});
			if(acao == Acoes.LISTA_PEDIDOS){
				removerButton = new JButton(new ImageIcon("imgs\\listaPedidos\\imgRemover.png"));
				removerButton.setPressedIcon(new ImageIcon("imgs\\listaPedidos\\imgRemoverPressed.png"));	
			}
			else if(acao == Acoes.PEDIDOS_REALIZADOS){
				removerButton = new JButton(new ImageIcon("imgs\\listaPedidos\\imgCancelarPedido.png"));
				removerButton.setPressedIcon(new ImageIcon("imgs\\listaPedidos\\imgCancelarPedidoPressed.png"));	
			}
			configurarBotoes(removerButton);
			removerButton.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent arg0) {
					for (TelaListaPedidosListener listener : telaCarrinhoListeners) {
						listener.removerDaLista(pedidos.get(tabelaProdutos.getSelectedRow()));
						if(acao == Acoes.LISTA_PEDIDOS){
							getTableModel().removeRow(tabelaProdutos.getSelectedRow());
						}
					}
				}
			});
			
			panelBotoes.setOpaque(false);
			panelBotoes.add(getValorLabel());
			if(acao == Acoes.LISTA_PEDIDOS){
				panelBotoes.add(pedirButton);
			}
			panelBotoes.add(removerButton);
			panelBotoes.add(fecharButton);
			
		}
		return panelBotoes;
	}

	public JLabel getValorLabel(){
		if (valorLabel == null) {
			valorLabel = new JLabel("VALOR TOTAL: R$ "+ String.format("%.2f", TelasActions.getInstance().calcularValorTotal(pedidos)));
			valorLabel.setForeground(Color.white);
			valorLabel.setFont(new Font("Calibri", 1, 15));
		}
		return valorLabel;
	}
	
	private void configurarBotoes(JButton btn) {
		btn.setContentAreaFilled(false);
		btn.setFocusPainted(false);
		btn.setMargin(new Insets(0, 0, 0, 0));
	}
	
	private JPanel getGeralPanelBG() {
		if(geralPanelBG == null){
			geralPanelBG = new JPanel(new MigLayout());
			geralPanelBG.setBackground(new Color(73,33,33,255));
		}
		return geralPanelBG;
	}
	
	public void alterarStatus(Pedidos pedido){
		int i = 0;
		for (Pedidos pedido1 : pedidos) {
			if(pedido1.getIdpedido().equals(pedido.getIdpedido())){
				break;
			}
			i++;
		}
		tabelaProdutos.setValueAt(pedido.getStatuspedido().getStatus(), i, 3);
	}

	public void addListener(TelaListaPedidosListener listener){
		this.telaCarrinhoListeners.add(listener);
	}
}