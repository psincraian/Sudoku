package propias.presentacion;
import java.awt.*;
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
		JPanel panel = new JPanel();
		Box Buttons = Box.createVerticalBox();
		Buttons.add(Box.createVerticalGlue());
		Buttons.add(button[0]);
		Buttons.add(button[1]);
		Buttons.add(button[2]);
		Buttons.add(button[3]);
		Buttons.add(Box.createVerticalGlue());
		Buttons.setBackground(Color.WHITE);
		panel.setBackground(Color.WHITE);
		panel.add(Buttons);
		add(panel, BorderLayout.CENTER);
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
