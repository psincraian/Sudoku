package propias.dominio.clases;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Petru Rares Sincraian
 *
 */
public abstract class UsuariGeneral {
	
	private static String nom;
	List<Integer> llistaSudokus;
	
	public UsuariGeneral(String nom) {
		this.nom = nom;
		llistaSudokus = new ArrayList<Integer>();
	}
	
	public void addSudoku(int id) {
		llistaSudokus.add(id);
	}
	
	public List<Integer> consultarSudokus() {
		return llistaSudokus;
	}
	
	public String consultarNom() {
		return nom;
	}
}
