package propias.dominio.clases;

import java.util.*;

/**
 * Classe que defineix la informacio adicional
 * necessaria de les partnamees.
 *
 * @author Adrian Sanchez Albanell
 * 
 */
public abstract class ListInfo implements java.io.Serializable {

	protected class info implements java.io.Serializable {
		public String name;
		public String maker;
		public int givens;
	};

	protected List<info> listInfo;

	public ListInfo(){
		listInfo = new ArrayList<info>();
	}

	/**
	* Permet afegir la informacio d'un objecte
	* a la llista.
	* @param name nameentificador de la objecte.
	* @param maker creador de la objecte.
	* @param givens caselles inicials de la
	* objecte.
	*/
	public void addInfo(String name, String maker, int givens){
		info i = new info();
		i.name = name;
		i.maker = maker;
		i.givens = givens;
		int p = searchPosition(name);
		if(p != -1) {
			listInfo.set(p, i);
		}
		else {
			listInfo.add(i);
		}
	}

	/**
	* Metode per a borrar una objecte.
	*/
	public void removeInfo(String name){
		int pos = searchPosition(name);
		if(pos != -1) listInfo.remove(pos);
	}

	/**
	* Permet buscar en quina posicio es
	* troba una objecte dins la llista 
	* d'informacio de les partnamees.
	* @return int la posicio de la 
	* objecte en la llista d'informacio,
	* -1 si no hi es.
	*/
	private int searchPosition(String name){		
		for(int p = 0; p < listInfo.size(); ++p){
			if(listInfo.get(p).name.equals(name)) return p;
		}
		return -1;
	}

}
