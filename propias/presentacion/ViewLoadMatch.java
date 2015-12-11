package propias.presentacion;

import java.util.List;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
	JPanel buttons;
	JButton buttonReturn;
	
	private loadMatch l;
	
	protected interface loadMatch{
		public void getBack();
		public void loadMatch(String match);
	}
	public ViewLoadMatch(List<String> id, Object container){
		super();
		setTitle("Carregar Partida");
		this.id = id;
		this.l = (loadMatch) container;
		startGUI();
	}
	
	public void startGUI() {
		configureGUI();
		if (id.size() >0) {
			addToGUI();
			//pack();
		}
		setVisible(true);
	}
	/**
	 * Configura la vista
	 */
	private void configureGUI() {
		//setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//dispose();
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
		add(panelButtons, BorderLayout.CENTER);
		buttonReturn = new JButton("Tornar");
		buttonReturn.setToolTipText("Tornar al Menu Principal");
		buttons = new JPanel();
		buttons.add(buttonReturn);
		buttons.add(Box.createVerticalStrut(80), BorderLayout.SOUTH);
		buttons.setBackground(Color.white);
		add(buttons, BorderLayout.SOUTH);
		
	}
	/**
	 * 
	 * @param ma Comproba si el boto s'ha premut
	 * 
	 */
	public void listener(MouseAdapter ma){
		buttonReturn.addMouseListener(ma);
	}
	/**
	 * 
	 * @param ma Comproba si el boto button s'ha premut
	 * @param button
	 */
	public void listeners(MouseAdapter ma, JButton button){
		button.addMouseListener(ma);
	}
	public class MouseManage extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			JButton button = (JButton)e.getSource();
			if (button.getText() == "Tornar") {
				l.getBack();
				
			}
			else {
				l.loadMatch(button.getText());
				}
		}
							
	}
	/**
	 * Deixa la vista com a no visible
	 */
	public void disableView(){
		setVisible(false);
	}
	
}
