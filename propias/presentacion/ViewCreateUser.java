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
	
	public void addPasswordField(){
		pass2 = new JPasswordField(10);
		textPanel.add(pass2);
		JLabel lPass2 = new JLabel("Repeteix contrasenya: ");
		labelPanel.add(Box.createVerticalStrut(10));
		labelPanel.add(lPass2);
	}
	
	 
	public List<String> getPassword(){
		List<String> passwords = new ArrayList<String>();
		passwords.add(String.valueOf(pf.getPassword()));
		passwords.add(String.valueOf(pass2.getPassword()));
		return passwords;
	}
	
}
