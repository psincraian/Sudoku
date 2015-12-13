package propias.presentacion;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

import javax.swing.*;
/**
 * 
 * @author Brian Martinez Alvarez
 *
 */
public class ViewStart extends SetView {
	public JButton[] button;
	String[] nom = {"Iniciar Sessio", "Iniciar Convidat", "Registrar Usuari", "Sortir"};
	JButton button2;
	JButton button3;
	JButton button4;
	/**
	 * Creadora
	 */
	public ViewStart(){
		super();
		setTitle("Inici");
		startGUI();
	}
	/**
	 * instancia tots els metodes
	 */
	public void startGUI() {
		instanceGUI();
		addToGUI();
	}
	/**
	 * Instancia els elements de la vista
	 */
	private void instanceGUI() {
		button = new JButton[4];
		for(int i = 0; i < 4; ++i){
			button[i] = new JButton(nom[i]);
			button[i].setPreferredSize(new Dimension(125,50));
			button[i].setMinimumSize(new Dimension(125,50));
			button[i].setMaximumSize(new Dimension(125,50));
		}
	}

	/**
	 * inclou a la vista els components
	 */
	private void addToGUI() {
		Box panelButtons = Box.createVerticalBox();
		panelButtons.add(Box.createGlue());
		panelButtons.add(button[0]);
		panelButtons.add(button[1]);
		panelButtons.add(button[2]);
		panelButtons.add(button[3]);
		panelButtons.add(Box.createGlue());
		panelButtons.setBackground(Color.WHITE);
		add(panelButtons, BorderLayout.CENTER);
	}
	/**
	 * 
	 * @param ma Comproba si el boto s'ha premut
	 * @param button Boto a premer
	 */
	public void listeners(MouseAdapter ma, JButton button){
		button.addMouseListener(ma);
	}
}
