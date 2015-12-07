package propias.dominio.clases;

import java.util.*;

/**
 * This class define a Ranking. The type of the ranking can be:
 * RankingGlobal, it refers to the users and its points
 * RankingSudoku, it refers to the users and its time for the sudoku 
 *
 * @author Daniel Sanchez Martinez
 */
public abstract class Ranking implements java.io.Serializable {
	/**
	 * Constructor
	 */
	protected List<ParamRanking> ranking = new ArrayList<ParamRanking>();
	
	/**
	 * Constructor 
	 * @param ranking
	 */
	public Ranking(List<ParamRanking> ranking){
		this.ranking = ranking;
	}
	/**
	 * Return ranking
	 * @return
	 */
	public List<ParamRanking> getRanking()
	{
		return ranking;
	}
	
	/**
	 * Check if the user is in ranking
	 * @param pr
	 * @return
	 */
	public int isIn(ParamRanking pr){
		for(ParamRanking prAux : ranking){
			if(prAux.getName().equals(pr.getName()))
				return ranking.indexOf(prAux);
		}
		return -1;
	}
	/**
	 * Add a new entry to the ranking
	 * @param pr
	 */
	public void addParam(ParamRanking pr){
		ranking.add(pr);
		Collections.sort(ranking);
	}
	
	/**
	 * Modify ranking
	 * @param pr
	 * @param index
	 */
	public abstract void modRanking(ParamRanking pr, int index);
}
