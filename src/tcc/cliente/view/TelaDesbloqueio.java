package tcc.cliente.view;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

import net.miginfocom.swing.MigLayout;
import tcc.cliente.view.listener.TelaDesbloqueioListener;

public class TelaDesbloqueio extends JFrame {
	
	private static final long serialVersionUID = 665907306952502140L;

	private JPanel panelPrincipal;
	private JLabel botaoLiberar;
	private ImageIcon imagemBotaoLiberar;
	private ArrayList<TelaDesbloqueioListener> desbloquearListener = new ArrayList<TelaDesbloqueioListener>(); 
	
	public TelaDesbloqueio() throws Exception {
		iniciarComponentes();
		
		add(criarPanelPrincipal(), "growx, width 100%, growy, height 100%");
		
		pack();
		setVisible(true);
	}

	private JPanel criarPanelPrincipal() {
		if(panelPrincipal == null){
			panelPrincipal = new JPanel(new MigLayout("align center"));
			imagemBotaoLiberar = new ImageIcon("imgs\\liberarButton.png");
			botaoLiberar = new JLabel(imagemBotaoLiberar);
			botaoLiberar.addMouseListener(new MouseAdapter() {
				public void mouseReleased(MouseEvent event) {
					for (TelaDesbloqueioListener listener : desbloquearListener) {
						listener.liberarCardapio();
					}
				}	
			});
			
			panelPrincipal.add(botaoLiberar);
			panelPrincipal.setBackground(Color.BLACK);
		}
		return panelPrincipal;
	}

	public void iniciarComponentes() throws Exception{
		
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
		
//		Método para fechar a conexão quando fechar a tela
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				for (TelaDesbloqueioListener listener : desbloquearListener) {
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
	
	public void addListener(TelaDesbloqueioListener listener){
		desbloquearListener.add(listener);
	}
}