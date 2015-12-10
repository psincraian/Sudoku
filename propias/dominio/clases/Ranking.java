package propias.dominio.clases;

import java.util.*;

/**
 * Aquesta clase defineix un ranking. El tipus de ranking pot ser:
 * global en el cas de que representi el nom d'un usuari
 * i la seva puntuació en una partida o el nom de l'usuari i el
 * temps que ha emplat per tal de terminar el sudoku 
 *
 * @author Daniel Sanchez Martinez
 * 
 */
public abstract class Ranking implements java.io.Serializable {
	
	protected List<ParamRanking> ranking;
	
	/**
	 * 
	 * Constructor de ranking.
	 *  
	 */
	public Ranking(List<ParamRanking> ranking){
		this.ranking = ranking;
	}
	
	/**
	 *
	 * @return  Retorna el ranking
	 * 
	 */
	public List<ParamRanking> getRanking(){
		return ranking;
	}
	
	/**
	 * 
	 * Comproba si un usuari està al ranking
	 * 
	 * @param id : nom de l'usuari a ser comprovat
	 * @return si l'usuari es troba al ranking, retorna cert
	 * sino false
	 * 
	 */
	public int isIn(String name){
		for(ParamRanking prAux : ranking){
			if(prAux.getName().equals(name))
				return ranking.indexOf(prAux);
		}
		return -1;
	}
	
	/**
	 * 
	 * Afegeix una nova entrada al ranking. Després
	 * ordena el ranking.
	 * 
	 * @param pr : ParamRanking que representa 
	 * a un usuari i una puntuació/temps
	 * 
	 */
	protected void addParam(ParamRanking pr){
		ranking.add(pr);
		Collections.sort(ranking);
	}
	
	/**
	 * 
	 * Modifica el ranking. El que es fa es comprovar si l'usuari
	 * hi es al ranking i s'actualitza si es dona el cas.
	 * 
	 * @param pr : ParamRanking que representa 
	 * a un usuari i una puntuació/temps
	 * 
	 */
	public abstract void modRanking(ParamRanking pr);
}
