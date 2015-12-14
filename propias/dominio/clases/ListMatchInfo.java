package propias.dominio.clases;

import java.util.*;

/**
 * Classe que defineix la informacio adicional
 * necessaria de les partnamees.
 *
 * @author Adrian Sanchez Albanell
 * 
 */
public class ListMatchInfo implements java.io.Serializable {

	private class infoMatch implements java.io.Serializable {
		public String name;
		public String maker;
		public int givens;
	};

	private List<infoMatch> listInfo;

	public ListMatchInfo(){
		listInfo = new ArrayList<infoMatch>();
	}

	/**
	* Permet afegir la informacio d'un partnamea
	* a la llista.
	* @param name nameentificador de la partnamea.
	* @param maker creador de la partnamea.
	* @param givens caselles inicials de la
	* partnamea.
	*/
	public void addInfo(String name, String maker, int givens){
		infoMatch i = new infoMatch();
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
	* Metode per a borrar una partnamea.
	*/
	public void removeInfo(String name){
		int pos = searchPosition(name);
		if(pos != -1) listInfo.remove(pos);
	}

	/**
	* Permet buscar en quina posicio es
	* troba una partnamea dins la llista 
	* d'informacio de les partnamees.
	* @return int la posicio de la 
	* partnamea en la llista d'informacio,
	* -1 si no hi es.
	*/
	private int searchPosition(String name){		
		for(int p = 0; p < listInfo.size(); ++p){
			if(listInfo.get(p).name.equals(name)) return p;
		}
		return -1;
	}

	/**
	* Permet obtenir una llista amb els name
	* i el creador de cada partnamea, i una
	* llista amb les caselles plenes de
	* cadascuna d'elles.
	* @return List<List<String>> amb el name
	* de cada partnamea i el seu creador. 
	* L'ultima llista conte les caselles
	* plenes a cada partnamea.
	*/
	public List<List<String>> getInfoIdMakerGivens(){
		List<List<String>> infoIdMaker = new ArrayList<List<String>>();
		List<String> listGivens = new ArrayList<String>();
		for(int a = 0; a < listInfo.size(); ++a){			
				List<String> tmp = new ArrayList<String>();
				tmp.add(listInfo.get(a).name);
				tmp.add(listInfo.get(a).maker);
				infoIdMaker.add(tmp);
				listGivens.add(String.valueOf(listInfo.get(a).givens));
		}
		infoIdMaker.add(listGivens);
		return infoIdMaker;
	}

}
