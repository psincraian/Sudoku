package propias.dominio.clases;

/**
 * 
 * Clase que s'encarrega de crear un nou usuari
 * quan sobre la vista de creació d'un nou usuari 
 * 
 * @author Daniel Sanchez Martinez
 * 
 */
public class CreateUser {

	private String nom, pass1, pass2;
	
	/**
	 * 
	 * Constructor
	 * @param nom : nom del nou usuari
	 * @param pass1 : contrasenya 1
	 * @param pass2 : contrasenya 2
	 * 
	 */
	public CreateUser (String nom, String pass1, String pass2){
		this.nom = nom;
		this.pass1 = pass1;
		this.pass2 = pass2;
	}
	
	/**
	 *  
	 * @return name : retorna el nom de l'usuari nou
	 * 
	 */
	public String getName(){
		return nom;
	}
	
	/**
	 * 
	 * @return Comprova si els dos passwords son correctes
	 * 
	 */
	public boolean isEqual(){
		if(pass1 == "" || pass2 == "")
			return false;
		return pass1 == pass2;
	}
	
	public Usuari createUser(){
		Usuari user = new Usuari(nom);
		user.setPassword(pass1);
		return user;
		
	}
}
