package propias.dominio.clases;

import java.util.ArrayList;
import java.util.List;

/** La classe UsuariGeneral ofereix la implementació bàsica d'usuari.
 * 
 * @author Petru Rares Sincraian
 *
 */
public abstract class UsuariGeneral implements java.io.Serializable {
	
	private String nom;
	
	/** La constructora per defecte. Crea un usuari amb el nom especificat. 
	 * 
	 * @param nom El nom del usuari
	 */
	public UsuariGeneral(String nom) {
		this.nom = nom;
	}
	
	/** retorna el nom del usuari 
	 * 
	 * @return El nom del usuari
	 */
	public String consultarNom() {
		return nom;
	}
	/**
	 * posa el nom a un Usuari
	 * @param name
	 */
	public void setNom(String name){
		this.nom = name;
	}
}
