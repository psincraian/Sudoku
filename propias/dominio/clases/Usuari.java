package propias.dominio.clases;

/** La classe usuari guarda un nom i una contrasenya per aquest usuari.
 * 
 * @author Petru Rares Sincraian
 *
 */
public class Usuari extends UsuariGeneral {
	
	private String password;

	/** Crea un usuari amb el nom especificat
	 * 
	 * @param nom El nom del usuari
	 */
	public Usuari(String nom) {
		super(nom);
	}
	
	/** Retorna la contrasenya del usuari
	 * 
	 * @return La contrasenya del usuari
	 */
	public String getPassword() {
		return password;
	}
}
