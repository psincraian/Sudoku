package propias.dominio.clases;

import java.util.*;
import propias.dominio.clases.ParamRanking;

/**
 * 
 * Ranking del tipus global. Representa
 * a un usuari i una puntuacio
 *
 * @author Daniel Sanchez Martinez
 * 
 */
public class RankingGlobal extends Ranking implements java.io.Serializable {

	/**
	 * Constructor del ranking global
	 * 
	 */
	public RankingGlobal(List<ParamRanking> ranking){
		super(ranking);
	}
	
	/**
	 * 
	 * Modifica el ranking. El que es fa es comprovar si l'usuari
	 * hi es al ranking i si hi es, es modifica la puntuacio
	 * i s'ordena el ranking, sino hi es s'afegeix al ranking
	 * 
	 * @param pr : ParamRanking que representa 
	 * a un usuari i una puntuacio
	 * 
	 */
	@Override
	public void modRanking(ParamRanking pr) {
		int index = isIn(pr.getName());
		if(index == -1)
			addParam(pr);
		else{
			pr.setValue(pr.getValue() + ranking.get(index).getValue());
			ranking.set(index, pr);
			Collections.sort(ranking);	
		}
	}
}
