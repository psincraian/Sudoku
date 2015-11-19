package propias.dominio.clases;

/**
 * Class created to use when a new player want to be
 * an user 
 * 
 * @author Daniel Sanchez Martinez
 */
public class CreateUser {

	private String nom, pass1, pass2;
	/**
	 * Constructor
	 * @param nom
	 * @param pass1
	 * @param pass2
	 */
	public CreateUser (String nom, String pass1, String pass2){
		this.nom = nom;
		this.pass1 = pass1;
		this.pass2 = pass2;
	}
	/**
	 * Empty constructor
	 */
	public CreateUser(){}
	
	/**
	 * Return the name of the user 
	 * @return name
	 */
	public String getName(){
		return nom;
	}
	/**
	 * Return the password of the user to be checked
	 * @return password
	 */
	public String getPass(){
		return pass1;
	}
	/**
	 * @return check if two passwords are equal
	 */
	public boolean isEqual(){
		return pass1 == pass2;
	}
}
