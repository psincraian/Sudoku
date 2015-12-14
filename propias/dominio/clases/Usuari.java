package propias.dominio.clases;

/** La classe usuari guarda un nom i una contrasenya per aquest usuari.
 * 
 * @author Petru Rares Sincraian
 *
 */
public class Usuari extends UsuariGeneral {
	
	private String password;
	private List<String> llistaSudokus;

	/** Crea un usuari amb el nom especificat
	 * 
	 * @param nom El nom del usuari
	 */
	public Usuari(String nom) {
		super(nom);
		llistaSudokus = new ArrayList<Integer>();
	}
	
	/** Retorna la contrasenya del usuari
	 * 
	 * @return La contrasenya del usuari
	 */
	public String getPassword() {
		return password;
	}
	public void setPassword(String password){
		this.password = password;
	}
	
	public void addSudoku(String id) {
		llistaSudokus.add(id);
	}
	
	public List<String> consultarSudokus() {
		return llistaSudokus;
	}
}
