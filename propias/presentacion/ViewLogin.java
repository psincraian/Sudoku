package propias.presentacion;

/**
* 
*
* @author  Daniel Sánchez Martinez
*/
public class ViewLogin extends UserEntry{

	public ViewLogin(){
		super();
		setTitle("Login d'usuari registrat");
		frame.pack();
	}

	public String getPassword(){
		return String.valueOf(pf.getPassword());
	}
}