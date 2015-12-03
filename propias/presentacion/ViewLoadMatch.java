package propias.presentacion;

import java.util.List;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ViewLoadMatch extends SetView{
	List<String> id;
	List<JButton> buttonList = new ArrayList<JButton>();
	public ViewLoadMatch(List<String> id){
		super();
		setTitle("Carregar Partida");
		this.id = id;
		startGUI();
	}
	
	public void startGUI() {
		instanceGUI();
		configureGUI();
		addToGUI();
		pack();
		setVisible(true);
	}
	private void instanceGUI() {
	}
	/**
	 * Configura la vista
	 */
	private void configureGUI() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		dispose();
	}
	/**
	 * inclou a la vista els components
	 */
	private void addToGUI() {
		Box vertical = Box.createVerticalBox();
		vertical.add(Box.createVerticalGlue());
		JPanel panelButtons = new JPanel();
		for(int i=0; i<id.size(); ++i) {
			JButton b = new JButton(id.get(i));
			buttonList.add(b);
			panelButtons.add(b);
		}
		panelButtons.setBackground(Color.WHITE);
		panelButtons.setLayout(new GridLayout(id.size(),0));
		getContentPane().add(panelButtons, BorderLayout.CENTER);
	}
	/**
	 * 
	 * @param ma Comproba si el boto s'ha premut
	 * @param button Boto a premer
	 */
	public void listeners(MouseAdapter ma, JButton button){
		button.addMouseListener(ma);
	}
	/**
	 * Deixa la vista com a no visible
	 */
	public void disableView(){
		setVisible(false);
	}
	
}
