package tcc.cliente.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import net.miginfocom.swing.MigLayout;
import tcc.balcao.model.entity.Mesa;
import tcc.cliente.view.listener.TelaEscolhaMesaListener;

public class TelaEscolhaMesa extends JFrame{

	private static final long serialVersionUID = 8393179391469208364L;
	
	private JButton okButton;
	private JComboBox mesaComboBox;
	private JPanel panelPrincipal;
	private ArrayList<TelaEscolhaMesaListener> escolhaMesaListeners = new ArrayList<TelaEscolhaMesaListener>();
	private ArrayList<Mesa> mesas;
	
	public TelaEscolhaMesa(ArrayList<Mesa> mesas) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		this.mesas = mesas;
		iniciar();
		add(criarComponentes());
		
		pack();
		setVisible(true);
	}

	private void iniciar() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {

		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setAlwaysOnTop(true);
		setTitle("Escolha Mesa");
		setResizable(false);
		setLayout(new MigLayout("insets 0"));
		setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
		setUndecorated(true);
	}
	
	private JPanel criarComponentes(){
		if(panelPrincipal == null){
			panelPrincipal = new JPanel(new MigLayout("align center"));
			panelPrincipal.add(getMesaComboBox());
			panelPrincipal.add(getOkButton());
			panelPrincipal.setBackground(Color.BLACK);
			panelPrincipal.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());

		}
		return panelPrincipal;
	}
	
	public JButton getOkButton() {
		if(okButton == null){
			okButton = new JButton("OK");
			okButton.setPreferredSize(new Dimension(50,50));
			okButton.setFont(new Font("Arial", 2, 20));
			okButton.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent arg0) {
					for (TelaEscolhaMesaListener listener : escolhaMesaListeners) {
						listener.okButtonAction();
					}
				}
			});
		}
		return okButton;
	}

	private JComboBox getMesaComboBox() {

		if(mesaComboBox == null){
			String[] stringMesas = new String[mesas.size()];
			for (int i = 0; i < mesas.size(); i++) {
				stringMesas[i] = mesas.get(i).getMesa();
			}
			mesaComboBox = new JComboBox(stringMesas);
			mesaComboBox.setPreferredSize(new Dimension(100, 50));
			mesaComboBox.setFont(new Font("Arial", 2, 20));
		}
		return mesaComboBox;
	}

	public Mesa pegarMesaComboBox(){
		for (Mesa mesa : mesas) {
			if(mesaComboBox.getSelectedItem() == mesa.getMesa()){
				return mesa;	
			}
		}
		System.out.println("Não encontrou a mesa");
		return null;
	}
	
	public void addListener(TelaEscolhaMesaListener listener){
		this.escolhaMesaListeners.add(listener);
	}
}