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
	 * Comprova si els dos passwords son correctes
	 * 
	 * @return 
	 * 		si retorna 0, no s'esta ficant "coses" que no son paraules ni numeros
	 * 		si retorna 1, contrasenya buida
	 * 		si retorna 2, les dues contrasenya no son iguals
	 * 		si retorna -1, les contrasenyes son iguals
	 * 
	 */
	public int isEqual(){
		for(int i = 0; i < pass1.length(); ++i) {
			Character c = pass1.charAt(i);
            if (c < '1' || (c >'9' && c < 'A') || (c > 'Z' && c < 'a') || c > 'z')
                return 0;
        }
		if(pass1 == "" || pass2 == "")
			return 1;
		else if(pass1 == pass2)
			return -1;
		else
			return 2;
	}
	
	public Usuari createUser(){
		Usuari user = new Usuari(nom);
		user.setPassword(pass1);
		return user;
		
	}
}
