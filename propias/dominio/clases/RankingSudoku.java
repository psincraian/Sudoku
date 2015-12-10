package propias.dominio.clases;

import java.util.*;

/**
 * Ranking del tipus sudoku. Representa
 * a un usuaria i el seu temps en resoldre 
 * el sudoku que representa el ranking
 *
 * @author Daniel Sanchez Martinez
 */
public class RankingSudoku extends Ranking{
	
	/**
	 * 
	 * Constructor del ranking sudoku
	 * 
	 */
	public RankingSudoku(List<ParamRanking> ranking){
		super(ranking);
	}

	/**
	 * 
	 * Modifica el ranking. El que es fa es comprovar si l'usuari
	 * hi es al ranking i si hi es, 
	 * 		comprova si el temps actual es menor, 
	 * 			si es dona el cas el modifica i ordena el ranking 
	 * 			sino el deixa tal com estava,
	 *  sino hi es s'afegeix al ranking
	 * 
	 * @param pr : ParamRanking que representa 
	 * a un usuari i el temps en resoldre el sudoku
	 * 
	 */
	@Override
	public void modRanking(ParamRanking pr) {
		int index = isIn(pr.getName());
		if(index == -1)
			addParam(pr);
		else if(ranking.get(index).getValue() > pr.getValue()){
				ranking.get(index).setValue(pr.getValue());
				Collections.sort(ranking);
		}
	}
}
