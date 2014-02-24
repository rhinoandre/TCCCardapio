package tcc.cliente.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;

import net.miginfocom.swing.MigLayout;
import tcc.balcao.model.entity.Item;
import tcc.cliente.view.listener.TelaProdutoEspecificacoesListener;

@SuppressWarnings("serial")
public class ProdutoEspecificacaoForm extends JFrame {
	private JPanel imagemPanel, direitaPanel, detalhesPanel, quantidadePanel,  botoesPanel, pedidosPanel;
	private JButton cancelarButton, pedirButton, addPedidosListaButton, maisButton, menosButton;
	private JPanelBackgroundImage panelBG;
	private JLabel label, imagemLabel, quantidadeLabel;
	private JTextArea detalhesLabel;
	private ImageIcon produtoImageIcon;
	private ArrayList<TelaProdutoEspecificacoesListener> especificacaoListeners = new ArrayList<TelaProdutoEspecificacoesListener>();
	private Item item;
	
	public ProdutoEspecificacaoForm(Item item) {
		try {
			this.item = item;
			iniciar();
			criarTitulo();
			getPanelBG().add(criarImagemPanel(), "split");

			criarDireitaPanel().add(criarDetalhesPanel(), "growx, width 100%, height 100%, wrap");
			criarQuantidade();
			criarPedidosPanel().add(criarQuantidadePanel(), "align right");
			criarPedidosPanel().add(criarBotoesPanel(), "align right, height 64");
			
			criarDireitaPanel().add(criarPedidosPanel(), "dock south, growx, right");

			getPanelBG().add(criarDireitaPanel());
			
			add(getPanelBG(), "width 100%, height 100%");
			
			
			pack();
			setLocationRelativeTo(null);
			setVisible(true);
		} catch (UnsupportedLookAndFeelException err) {
			System.out.println("Erro ao setar o L&F!");
		} catch (Exception err) {
			err.printStackTrace();
		}
	}

	private JPanel criarBotoesPanel() {
		if(botoesPanel == null){
			botoesPanel = new JPanel(new MigLayout("insets 0", "push[][]"));
			botoesPanel.add(getQuantidadeLabel());
			botoesPanel.setOpaque(false);
			botoesPanel.setPreferredSize(new Dimension(440, 66));
			cancelarButton = new JButton(new ImageIcon("imgs\\detalhesProduto\\imgCancelar.png"));
			cancelarButton.setPressedIcon(new ImageIcon("imgs\\detalhesProduto\\imgCancelarPressed.png"));
			cancelarButton.setPreferredSize(new Dimension(138, 52));
			configurarBotoes(cancelarButton);
			cancelarButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					for (TelaProdutoEspecificacoesListener listener : especificacaoListeners) {
						listener.cancelarAction();
					}
				}
			});

			pedirButton = new JButton(new ImageIcon("imgs\\detalhesProduto\\imgPedir.png"));
			pedirButton.setPressedIcon(new ImageIcon("imgs\\detalhesProduto\\imgPedirPressed.png"));
			configurarBotoes(pedirButton);
			pedirButton.setPreferredSize(new Dimension(78, 52));
			pedirButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					for (TelaProdutoEspecificacoesListener listener : especificacaoListeners) {
						listener.realizarPedido(item);
					}
				}
			});
			
			addPedidosListaButton = new JButton(new ImageIcon("imgs\\detalhesProduto\\imgAddCarrinho.png"));
			addPedidosListaButton.setPressedIcon(new ImageIcon("imgs\\detalhesProduto\\imgAddCarrinhoPressed.png"));
			configurarBotoes(addPedidosListaButton);
			addPedidosListaButton.setPreferredSize(new Dimension(138, 52));
			addPedidosListaButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					for (TelaProdutoEspecificacoesListener listener : especificacaoListeners) {
						listener.addPedidosLista(item);
					}
				}
			});
			
			botoesPanel.add(pedirButton);
			botoesPanel.add(addPedidosListaButton);
			botoesPanel.add(cancelarButton);
		}
		return botoesPanel;
	}

	private void configurarBotoes(JButton btn) {
		btn.setContentAreaFilled(false);
		btn.setFocusPainted(false);
		btn.setMargin(new Insets(-5, -5, -5, -5));
	}

	private JPanel criarQuantidadePanel() {
		if(quantidadePanel == null){
			quantidadePanel = new JPanel(new MigLayout("insets 0"));
			quantidadePanel.setOpaque(false);
		}
		return quantidadePanel;
	}
	private void criarQuantidade(){
		if(maisButton == null){
			
			maisButton = new JButton(new ImageIcon("imgs\\detalhesProduto\\imgMais.png"));
			maisButton.setPressedIcon(new ImageIcon("imgs\\detalhesProduto\\imgMaisPressed.png"));
			configurarBotoes(maisButton);
			maisButton.setPreferredSize(new Dimension(38,34));
			maisButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					int i = Integer.parseInt(getQuantidadeLabel().getText());
					i++;
					getQuantidadeLabel().setText(""+i);
				}
			});
			criarQuantidadePanel().add(maisButton, "wrap");
		}
		if(menosButton == null){
			menosButton = new JButton(new ImageIcon("imgs\\detalhesProduto\\imgMenos.png"));
			menosButton.setPressedIcon(new ImageIcon("imgs\\detalhesProduto\\imgMenosPressed.png"));
			configurarBotoes(menosButton);
			menosButton.setPreferredSize(new Dimension(38,34));
			menosButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int i = Integer.parseInt(getQuantidadeLabel().getText());
					if(i != 1){
						i--;
						getQuantidadeLabel().setText(""+i);
					}
				}
			});
			criarQuantidadePanel().add(menosButton);
		}
		
	}
	
	private JPanel criarPedidosPanel() {
		if(pedidosPanel == null){
			pedidosPanel = new JPanel(new MigLayout("insets 0"));
			pedidosPanel.setOpaque(false);
			pedidosPanel.setPreferredSize(new Dimension(440, 64));
		}
		return pedidosPanel;
	}

	private JPanel criarDetalhesPanel() {
		if(detalhesPanel == null){
			detalhesPanel = new JPanel(new MigLayout("insets 0"));
			detalhesPanel.setOpaque(false);
			detalhesLabel = new JTextArea(item.getDescricao());
			detalhesLabel.setEditable(false);
			detalhesLabel.setOpaque(false);
			detalhesLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			detalhesLabel.setLineWrap(true);
			detalhesLabel.setFont(new Font("Calibri", 0, 15));
			detalhesLabel.setForeground(new Color(44,8,8,200));
			Border detalhesBorder = BorderFactory.createBevelBorder(0, Color.LIGHT_GRAY, Color.lightGray);
			
			detalhesPanel.setBorder(BorderFactory.createTitledBorder(detalhesBorder, "Detalhes", 0, 0, new Font("Calibri", 0, 15), Color.WHITE));
			detalhesPanel.setPreferredSize(new Dimension(440, 315));
			detalhesPanel.setMaximumSize(new Dimension(440, 315));
			detalhesPanel.add(detalhesLabel, "height 100%, width 100%");
		}
		return detalhesPanel;
	}

	private JPanel criarDireitaPanel() {
		if(direitaPanel == null){
			direitaPanel = new JPanel(new MigLayout("insets 0"));
			direitaPanel.setOpaque(false);
			direitaPanel.setPreferredSize(new Dimension(440, 350));
		}
		return direitaPanel;
	}

	private JPanel criarImagemPanel() {
		if(imagemPanel == null){
			imagemPanel = new JPanel(new MigLayout());
			imagemPanel.setOpaque(false);
			Image imgProduto = new ImageIcon(item.getImgs()).getImage();
			Image imgProdutoCerto = imgProduto.getScaledInstance(500, 349, Image.SCALE_DEFAULT);
			produtoImageIcon = new ImageIcon(imgProdutoCerto);
			imagemLabel = new JLabel(produtoImageIcon);
			imagemPanel.setPreferredSize(new Dimension(500, 349));
			imagemPanel.add(imagemLabel);
		}
		return imagemPanel;
	}

	private void criarTitulo() {
		label = new JLabel(item.getNome() + " - R$ " + String.format("%.2f", item.getValor()));
		label.setFont(new Font("Calibri", 2, 30));
		label.setForeground(Color.WHITE);
		JPanel labelPanel = new JPanel(new MigLayout());
		labelPanel.setOpaque(false);
		labelPanel.add(label, "align center");
		getPanelBG().add(labelPanel, "align center, wrap");
	}

	private void iniciar() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		setLayout(new MigLayout("insets 0"));
		setPreferredSize(new Dimension(971, 432));
		setAlwaysOnTop(true);
		setUndecorated(true);
	}
	
	private JPanelBackgroundImage getPanelBG() {
		if(panelBG == null){
			panelBG = new JPanelBackgroundImage(new ImageIcon("imgs\\detalhesProduto\\fundoProdutoEspeci.jpg").getImage(), new MigLayout(), 0, 0);
//			panelBG.setBorder(new DropShadowBorder(Color.BLACK, 10, 1, 10, true, true, true, true));
		}
		return panelBG;
	}
	
	public Integer getQuantidade(){
		return Integer.parseInt(getQuantidadeLabel().getText());
	}
	private JLabel getQuantidadeLabel() {
		if(quantidadeLabel == null){
			quantidadeLabel = new JLabel("1");
			quantidadeLabel.setFont(new Font("Calibri", 2, 40));
			quantidadeLabel.setForeground(Color.white);
			criarQuantidadePanel().add(quantidadeLabel);
		}
		return quantidadeLabel;
	}

	//	LISTENERS
	public void addListener(TelaProdutoEspecificacoesListener listener){
		especificacaoListeners.add(listener);
	}
}
