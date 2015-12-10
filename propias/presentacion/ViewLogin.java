package propias.presentacion;

import java.util.ArrayList;
import java.util.List;

/**
* 
* Configura la vista de login. 
* 
* @author  Daniel Sanchez Martinez
* 
*/
public class ViewLogin extends UserEntry{

	/**
	 * 
	 * Constructora
	 * 
	 */
	public ViewLogin(){
		super();
		setTitle("Login d'usuari registrat");
		setPanelSize(250,2);
	}
	
	/**
	 * 
	 * Envia el password per tal de ser verificat contra
	 * la base de dades
	 * 
	 */
	public List<String> getInfoUser(){
		List<String> user = new ArrayList<String>();
		user.add(getName());
		user.add(String.valueOf(pf.getPassword()));
		return user;
	}
}
