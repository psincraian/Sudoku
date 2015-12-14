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
	* a la llista. Si ja existia un objecte amb
	* el mateix nom el sobreescriu.
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

	/**
	* Permet canviar el nom d'un 
	* creador per a tots els objectes
	* que hagi creat.
	*/
	public void replaceMaker(String oldName, String newName){
		for(int a = 0; a < listInfo.size(); ++a){
			if(listInfo.get(a).maker.equals(oldName)){
				listInfo.get(a).maker = newName;
			}
		}
	}

	/**
	* Metode per obtenir el nom de
	* tots els objectes de la ListInfo.
	* @return List<String> amb els 
	* noms.
	*/
	public List<String> getNames(){
		List<String> listName = new ArrayList<String>();
		for(int a = 0; a < listInfo.size(); ++a){
			listID.add(listInfo.get(a).name);
		}
		return listName;
	}

}
