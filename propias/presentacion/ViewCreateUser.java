package propias.presentacion;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Set createUser view.
 * 
 * @author Daniel Sanchez Martinez
 */
public class ViewCreateUser extends UserEntry{

	private JPasswordField pass2;

	/**
	 * Create the application.
	 */
	public ViewCreateUser() {
		super();
		setTitle("Creació d'un nou usuari");
		addPasswordField();
		frame.pack();
	}
	/**
	 *  Add a new entry at the view.(password verification)
	 */
	public void addPasswordField(){
		pass2 = new JPasswordField(10);
		textPanel.add(pass2);
		JLabel lPass2 = new JLabel("Repeteix contrasenya: ");
		labelPanel.add(Box.createVerticalStrut(10));
		labelPanel.add(lPass2);
	}
	
	/**
	 * Send the passwords to be checked to the controllers.
	 */
	public List<String> getInfoUser(){
		List<String> user = new ArrayList<String>();
		user.add(getName());
		user.add(String.valueOf(pf.getPassword()));
		user.add(String.valueOf(pass2.getPassword()));
		return user;
	}
	
}
