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
	
	private static ViewLogin instance;

	/**
	 * 
	 * Constructora
	 * 
	 */
	public ViewLogin(){
		super();
	}
	
	/**
	 * 
	 * Funcio que s'encarrega de llençar 
	 * la vista ViewLogin
	 * 
	 */
	public void launchView(){
		setTitle("Login d'usuari registrat");
		setPanelSize(250,2);
	}
	
	/**
	 * 
	 * Funcio que s'encarrega de retorna l'instancia d'aquesta vista
	 * per tal existeixi un sola instancia(singleton)
	 * 
	 * @return instancia de ViewLogin
	 * 
	 */
	public static ViewLogin  getInstance(){
		if(instance == null)
			instance = new ViewLogin();
		return instance;
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
