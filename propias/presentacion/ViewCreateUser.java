package propias.presentacion;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Configura la vista de creacio d'un nou usuari
 * 
 * @author Daniel Sanchez Martinez
 * 
 */
public class ViewCreateUser extends UserEntry{

	private JPasswordField pass2;
	private static ViewCreateUser instance;
	/**
	 * 
	 * Constructora de la vista ViewCreateUser. 
	 * 
	 */
	public ViewCreateUser() {
		super();
		
	}
	
	/**
	 * 
	 * Funcio que s'encarrega de llençar 
	 * la vista ViewCreateUser
	 * 
	 */
	public void launchView(){
		setTitle("Creacio d'un nou usuari");
		addPasswordField();
		setPanelSize(300,3);
	}
	
	/**
	 * 
	 * Funcio que s'encarrega de retorna l'instancia d'aquesta vista
	 * per tal existeixi un sola instancia(singleton)
	 * 
	 * @return instancia de ViewCreateUser
	 * 
	 */
	public static ViewCreateUser getInstance(){
		if(instance == null)
			instance = new ViewCreateUser();
		return instance;
	}
	
	/**
	 * 
	 *  Afegeix una nova linea a la vista. Un JLabel que correspon al missatge
	 *  de que torni a escriure la contrasenya i la propia contrasenya.
	 *  
	 */
	public void addPasswordField(){
		pass2 = new JPasswordField(10);
		textPanel.add(Box.createVerticalStrut(10));
		textPanel.add(pass2);
		JLabel lPass2 = new JLabel("Repeteix contrasenya: ");
		labelPanel.add(Box.createVerticalStrut(10));
		labelPanel.add(lPass2);
	}
	
	/**
	 * 
	 * Envia els dos passwords per tal de ser verificiats.
	 * Si no son iguals, s'enviara un missatge d'error. 
	 * 
	 */
	public List<String> getInfoUser(){
		List<String> user = new ArrayList<String>();
		user.add(getName());
		user.add(String.valueOf(pf.getPassword()));
		user.add(String.valueOf(pass2.getPassword()));
		return user;
	}
}
