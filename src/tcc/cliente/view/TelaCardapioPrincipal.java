package tcc.cliente.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.Border;

import net.miginfocom.swing.MigLayout;
import tcc.balcao.model.entity.Item;
import tcc.balcao.model.entity.Tipoitem;
import tcc.cliente.view.listener.TelaCardapioListener;

public class TelaCardapioPrincipal extends JFrame{
	private static final long serialVersionUID = -5903097583393140905L;
	
	private JPanelBackgroundImage menuPanel, rodapePanel, geralPanel;
	private JPanel produtosPanel;
	private JLabel logoCardapioEletronico, totalConta, nomeProduto, precoProduto;
	private Image imgProduto, imgProdutoMenor;
	private JButton pedidosRealizadosButton, fecharContaButton, listaPedidosButton,tipoProdutoButton;
	private HashMap<String, ArrayList<?>> hashTipos;
	private ArrayList<TelaCardapioListener> cardapioListeners = new ArrayList<TelaCardapioListener>();
	private Border buttonBorder = BorderFactory.createMatteBorder(0, 0, 0, 2, new Color(44,8,8,200));
	private Border firstButtonBorder = BorderFactory.createMatteBorder(0, 2, 0, 2, new Color(44,8,8,200));
	

	public TelaCardapioPrincipal(HashMap<String, ArrayList<?>> hashTipos) throws Exception {
			this.hashTipos = hashTipos;
			
			geralPanel = new JPanelBackgroundImage(new ImageIcon("imgs\\geralBG.jpg").getImage(), new MigLayout("insets 0"), 0, 0);
			geralPanel.setMaximumSize(new Dimension(1980, 1280));
			iniciarComponentes();
			
			criarMenu();
			
			geralPanel.add(criarProdutoPane(), "dock north, width 100%, growx, height 90%, growy, wrap");
			
			criarRodape();
			
			add(geralPanel, "height 100%, width 100%");
			pack();
			setVisible(true);
			
			
			
	}

	public void iniciarComponentes() throws Exception{
		
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
//		setAlwaysOnTop(true);
		
//		Método para fechar a conexão quando fechar a tela
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				for (TelaCardapioListener listener : cardapioListeners) {
					listener.fecharTela(event);
				}
			}
		});
		
//		Desabilita a exibição da Barra de titulo do windows
		setUndecorated(true); 
		
//		Não permite que altere o tamanho da tela
		setResizable(false);
		setLayout(new MigLayout("insets 0"));
	}
	
	public void criarMenu() throws Exception{
		
		@SuppressWarnings("unchecked")
		ArrayList<Tipoitem> tipos = (ArrayList<Tipoitem>) hashTipos.get("itens");
		
		menuPanel = new JPanelBackgroundImage(new ImageIcon("imgs\\menuBG.jpg").getImage(), new MigLayout("insets 0"), 0, 0);
		logoCardapioEletronico = new JLabel(new ImageIcon("imgs\\logoCardapioEletronico.png"));
		menuPanel.add(logoCardapioEletronico);
	
		for (Tipoitem tipo : tipos) {
			
			tipoProdutoButton = new JButton(tipo.getTipo());
			if(tipos.indexOf(tipo) == 0){
				setButtonConfiguracao(tipoProdutoButton, firstButtonBorder);
			}
			else{
				setButtonConfiguracao(tipoProdutoButton, buttonBorder);
			}
			menuPanel.add(tipoProdutoButton);
			
			tipoProdutoButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					for (TelaCardapioListener atualizar : cardapioListeners) {
						atualizar.atualizarListener(event);
					}
				}
			});
		}
		
		geralPanel.add(menuPanel, "dock north, width 100%, growx, height 50, growy, wrap");
	}

	
	public void criarRodape(){
		rodapePanel = new JPanelBackgroundImage(new ImageIcon("imgs\\menuBG.jpg").getImage(), new MigLayout("insets 0", "[]push[][][]"), 0, 0);

		pedidosRealizadosButton = new JButton("Pedidos Realizados");
		setButtonConfiguracao(pedidosRealizadosButton, buttonBorder);
		pedidosRealizadosButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for (TelaCardapioListener listener : cardapioListeners) {
					listener.pedidosRealizados();
				}
			}
		});
		fecharContaButton = new JButton("Fechar Conta");
		setButtonConfiguracao(fecharContaButton, buttonBorder);
		fecharContaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for (TelaCardapioListener listener : cardapioListeners) {
					listener.fecharConta();
				}
			}
		});
		rodapePanel.add(getTotalConta(), "gap 10 0 10 0");
//		//*************************************************************************
		rodapePanel.add(criarListaPedidosButton()); //TODO: Passar a quantidade
//		//*************************************************************************
		rodapePanel.add(pedidosRealizadosButton);
		rodapePanel.add(fecharContaButton, "gap 0 10 0 0");
		
		geralPanel.add(rodapePanel, "growx, height 50, growy");
	}

	public void atualizaLista(String tipo) throws Exception{
		
		if(criarProdutoPane() != null){
			criarProdutoPane().removeAll();
			criarProdutoPane().repaint();
		}
		
		@SuppressWarnings("unchecked")
		ArrayList<Item> produtos = (ArrayList<Item>) hashTipos.get(tipo);
		int i = 1;
		for ( final Item produto : produtos){
			
			final JPanel panelAuxiliar = new JPanel(new MigLayout());
			imgProduto = new ImageIcon(produto.getImgs()).getImage();
			imgProdutoMenor = imgProduto.getScaledInstance(200, 140, Image.SCALE_DEFAULT);
			nomeProduto = new JLabel(produto.getNome());
			nomeProduto.setFont(new Font("Calibri", 0, 15));
			nomeProduto.setForeground(Color.white);
			precoProduto = new JLabel("R$ " + String.format("%.2f", produto.getValor()));
			precoProduto.setFont(new Font("Calibri", 1, 18));
			precoProduto.setForeground(Color.white);
			panelAuxiliar.setBackground(new Color(0,0,0, 150));
			panelAuxiliar.addMouseListener( new MouseAdapter() {
				
				public void mouseReleased(MouseEvent event) {
					for (TelaCardapioListener listener : cardapioListeners) {
						listener.mostrarEspecificacoes(produto);
					}
				}
			});
			criarProdutoPane().revalidate();
			panelAuxiliar.add(nomeProduto, "align center, wrap");
			panelAuxiliar.add(new JLabel(new ImageIcon(imgProdutoMenor)), "align center, wrap");
			panelAuxiliar.add(precoProduto, "align center");
			if(i % 4 == 0){
				criarProdutoPane().add(panelAuxiliar, "width 200, height 150, wrap");
				i++;
			}
			else{
			criarProdutoPane().add(panelAuxiliar, "width 200, height 150");
			i++;
			}
		}
	}
	
	private JButton criarListaPedidosButton() {
		if(listaPedidosButton == null){
			listaPedidosButton = new JButton("<html>Carrinho:&nbsp&nbsp&nbsp<i>0</i></html>");
			setButtonConfiguracao(listaPedidosButton, firstButtonBorder);
			listaPedidosButton.setForeground(Color.WHITE);
			listaPedidosButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					for (TelaCardapioListener listener : cardapioListeners) {
						listener.abrirListaPedidos();
					}
				}
			});
		}
		return listaPedidosButton;
	}

	public void alterarQtdCarrinho(Integer qtd){
		if(qtd <= 0) 
			qtd = 0;
		
		criarListaPedidosButton().setText("<html>Carrinho:&nbsp&nbsp&nbsp<i>" + qtd + "</i></html>");
	}
	
	public JPanel criarProdutoPane(){
		if(produtosPanel == null){
			produtosPanel = new JPanel(new MigLayout());
			produtosPanel.setOpaque(false);
			produtosPanel.setMaximumSize(new Dimension(1980, 1280));
			JLabel bemvindo = new JLabel("Bem vindo!");
			bemvindo.setForeground(new Color(72,33,33,200));
			bemvindo.setFont(new Font("Calibri", 2, 100));
			JLabel bemvindo2 = new JLabel("Escolha um tipo para que os produtos apareçam!");
			bemvindo2.setForeground(new Color(72,33,33,200));
			bemvindo2.setFont(new Font("Calibri", 2, 30));
			produtosPanel.add(bemvindo, "gap 0 0 50 0");
			produtosPanel.add(bemvindo2);
			
		}
		return produtosPanel;
	}
	
	public JLabel getTotalConta() {
		if(totalConta == null){
			totalConta = new JLabel("Total: R$ 0,00");
			totalConta.setForeground(Color.white);
			totalConta.setFont(new Font("Calibri", 1, 20));
		}
		return totalConta;
	}
	
	public void setTotalConta(Double valor){
		getTotalConta().setText("Total: R$ " + String.format("%.2f", valor));
	}

	public void setButtonConfiguracao(JButton btn, Border border){
		btn.setContentAreaFilled(false);
		btn.setMargin(new Insets(0,0,0,0));
		btn.setFocusPainted(false);
		btn.setBorder(border);
		btn.setPreferredSize(new Dimension(185, 50));
		btn.setFont(new Font("Calibri", 0, 18));
		btn.setForeground(Color.WHITE);
	}
	
	public void addListener(TelaCardapioListener listener){
		this.cardapioListeners.add(listener);
	}
}