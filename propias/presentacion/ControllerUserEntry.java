package propias.presentacion;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

import propias.presentacion.ControllerViewBoard.MouseManage;

/**
 * Controll the User entry class. Sets the listeners for the buttons.
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
	public ControllerUserEntry(ControllerPresentation cp, boolean typeUser ){
		if(typeUser)
			eu = new ViewLogin();
		else 
			eu = new ViewCreateUser();
		this.cp = cp;
		this.eu.buttonListener(new MouseManage(), this.eu.accept);
		this.eu.buttonListener(new MouseManage(), this.eu.cancel);
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
}
