package propias.dominio.clases;

import java.util.*;

/**
 * Classe que defineix la informacio adicional
 * necessaria de les partnamees.
 *
 * @author Adrian Sanchez Albanell
 * 
 */
public class ListMatchInfo extends ListInfo implements java.io.Serializable {

	public ListMatchInfo(){
		super();
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
		for(int a = 0; a < listInfo.size(); ++a){
			List<String> tmp = new ArrayList<String>();
			tmp.add(listInfo.get(a).name);
			tmp.add(listInfo.get(a).maker);
			tmp.add(String.valueOf(listInfo.get(a).givens));
			infoIdMaker.add(tmp);
		}
		return infoIdMaker;
	}

}
