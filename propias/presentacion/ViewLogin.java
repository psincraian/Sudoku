package propias.presentacion;

import java.util.ArrayList;
import java.util.List;

/**
* 
* set the login view. Gives the possibility to play sudokus as an user of the system.
* 
* @author  Daniel Sánchez Martinez
*/
public class ViewLogin extends UserEntry{

	public ViewLogin(){
		super();
		setTitle("Login d'usuari registrat");
		pack();
		setVisible(true);
	}
	/**
	 * Send password to be checked to the controllers.
	 */
	public List<String> getInfoUser(){
		List<String> user = new ArrayList<String>();
		user.add(getName());
		user.add(String.valueOf(pf.getPassword()));
		return user;
	}
}
