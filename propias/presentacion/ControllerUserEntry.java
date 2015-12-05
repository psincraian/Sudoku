package propias.presentacion;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import propias.presentacion.ControllerViewBoard.MouseManage;

/**
 * Controll the User entry class. Sets the listeners for the buttons.
 * 
 * @author daniel sánchez martínez
 */
public class ControllerUserEntry {
	
	UserEntry eu;
	ControllerPresentation cp;
	/**
	 * Construct a new ControllerUserEntry for viewLogin or ViewCreateUser
	 * @param cp: instance of ControllerPresentation to communicate data
	 * @param typeUser: viewLogin/viewCreateUser
	 */
	public ControllerUserEntry(ControllerPresentation cp, boolean typeUser){
		if(typeUser)
			eu = new ViewLogin();
		else 
			eu = new ViewCreateUser();
		this.cp = cp;
		eu.buttonListener(new MouseManage(), this.eu.accept);
		eu.buttonListener(new MouseManage(), this.eu.cancel);
		addKeyBinding();
	}
	/**
	 * Generate a message to show
	 * @param message
	 */
	public void sendMessage(String message){
		eu.sendMessage(message);
	}
	
	/**
	 * Manage the mouse clicks. There are two buttons with listeners. Accept and Cancel.
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
	public void addKeyBinding(){
		eu.panelButtons.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), "ENTER pressed");
	    eu.panelButtons.getActionMap().put("ENTER pressed", new AbstractAction() {
	        @Override
	        public void actionPerformed(ActionEvent ae) {
	        	if(cp.checkInfoUser(eu.getInfoUser())){
					eu.disableView();
					cp.getBack();
				}
	        }
	    });
	}
}
