package propias.dominio.clases;

/**
 * 
 * @author Petru Rares Sincraian
 *
 */
public class Usuari extends UsuariGeneral {
	
	private String password;

	public Usuari(String nom) {
		super(nom);
	}
	
	public String getPassword() {
		return password;
	}
}
