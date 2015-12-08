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
	public JButton button1;
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
		configureGUI();
		addToGUI();
		//pack();
		setVisible(true);
	}
	/**
	 * Instancia els elements de la vista
	 */
	private void instanceGUI() {
		button1 = new JButton("Iniciar Sessio");
		button2 = new JButton("Iniciar Convidat");	
		button3 = new JButton("Registrar Usuari");
		button4 = new JButton("Sortir");
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
		panelButtons.add(button1);
		panelButtons.add(button2);
		panelButtons.add(button3);
		panelButtons.add(button4);
		panelButtons.setBackground(Color.WHITE);
		panelButtons.setLayout(new GridLayout(4,0));
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
	/**
	 * Deixa la vista com a no visible
	 */
	public void disableView(){
		setVisible(false);
	}
}
