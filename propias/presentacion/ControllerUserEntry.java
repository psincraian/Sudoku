package propias.presentacion;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.KeyStroke;

import propias.presentacion.ControllerViewBoard.MouseManage;

/**
 * Controla la clase UserEntry. Configura els listeners sobre els botons i obre la vista.
 * 
 * @author Daniel Sanchez Martinez
 * 
 */
public class ControllerUserEntry {
	
	UserEntry eu;
	ControllerPresentation cp;
	/**
	 * 
	 * Constructor de ControllerUserEntry tal de gestionar la vista seleccionada.
	 * 
	 * @param cp: Instancia de controlador de presentació per tal de poder comunicar-se
	 * i tracta les dades inserides per l'usuari.
	 * @param typeUser: Si typeUser(1) es generarà la vista Login, en el cas contrari, es generarà 
	 * la vista de creació d'un nou usuari. 
	 * 
	 */
	public ControllerUserEntry(ControllerPresentation cp, boolean typeUser, JFrame frame){
		if(typeUser)
			eu = new ViewLogin();
		else 
			eu = new ViewCreateUser();
		frame.getContentPane().add(eu);
		this.cp = cp;
		eu.buttonListener(new MouseManage(), this.eu.accept);
		eu.buttonListener(new MouseManage(), this.eu.cancel);
		addKeyBinding();
	}
	/**
	 * 
	 * Genera un missatge per tal de ser mostrat a l'usuari.
	 * 
	 * @param message El missatge en questió.
	 * 
	 */
	public void sendMessage(String message){
		eu.sendMessage(message);
	}
	
	/**
	 * 
	 * Gestiona els clicks per part del ratolí. Hi han dos butons amb listeners, 
	 * 'Accept' i 'Cancel'.
	 * 
	 */
	public class MouseManage extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			JButton b = (JButton)e.getSource();
			if(b.getText() == "Aceptar"){
				if(cp.checkInfoUser(eu.getInfoUser())){
					eu.disableView();
					cp.getBack();
				}
				else{
					eu.disableView();
					cp.start();
				}				
			}
			else{
				eu.disableView();
				cp.start();
			}
		}
	}
	/**
	 * 
	 * S'encarrega que quan l'usuari pitji la tecla 'INTRO', es comprovaran les dades
	 * ficades per l'usuari.
	 * 
	 */
	public void addKeyBinding(){
		eu.panelButtons.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), "ENTER pressed");
	    eu.panelButtons.getActionMap().put("ENTER pressed", new AbstractAction() {
	        public void actionPerformed(ActionEvent ae) {
	        	if(cp.checkInfoUser(eu.getInfoUser())){
					eu.disableView();
					cp.getBack();
				}
	        }
	    });
	}
}
