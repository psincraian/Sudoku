package propias.dominio.clases;

import java.util.*;

/**
 * Aquesta clase defineix un ranking. El tipus de ranking pot ser:
 * global en el cas de que representi el nom d'un usuari
 * i la seva puntuacio en una partida o el nom de l'usuari i el
 * temps que ha emplat per tal de terminar el sudoku 
 *
 * @author Daniel Sanchez Martinez
 * 
 */
public class ListSudokuInfo extends ListInfo implements java.io.Serializable {

	public ListSudokuInfo(){
		super();
	}

	/**
	* Permet obtenir una llista amb els id
	* i el creador de cada sudoku que tingui
	* com a minim un cert nombre de caselles
	* inicials.
	* @param givens nombre minim de caselles
	* inicials.
	* @return List<List<String>> amb el id
	* de cada sudoku i el seu creador.
	*/
	public List<List<String>> getInfoIdMakerGivens(int minGivens){
		List<List<String>> infoIdMaker = new ArrayList<List<String>>();
		for(int a = 0; a < listInfo.size(); ++a){
			if(listInfo.get(a).givens >= minGivens){
				List<String> tmp = new ArrayList<String>();
				tmp.add(listInfo.get(a).name);
				tmp.add(listInfo.get(a).maker);
				tmp.add(String.valueOf(listInfo.get(a).givens));
				infoIdMaker.add(tmp);
			}
		}
		return infoIdMaker;
	}

}
