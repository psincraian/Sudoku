package propias.dominio.controladores;

import java.util.List;
import propias.dominio.clases.*;

/**
 * Ranking's controller. Its duty is to get and set entry's ranking.
 * 
 * @author Daniel Sanchez Martinez
 */
public class ControllerRanking {
	private Ranking ranking;
	
	/**
	 * Constructor
	 * @param rank
	 * @param typeRanking
	 */
	public ControllerRanking(List<ParamRanking> rank, boolean typeRanking){
		if(typeRanking)
			 this.ranking = new RankingGlobal(rank);
		else
			this.ranking = new RankingSudoku(rank);
	}
	
	/**
	 * 
	 * @return ranking to be displayed
	 */
	public List<ParamRanking> getRanking(){
		return ranking.getRanking();
	}
	/**
	 * 
	 * @param name
	 * @return Position of a name in ranking
	 */
	public int positionRanking(String name){
		if(ranking instanceof RankingGlobal)
			return ((RankingGlobal)ranking).positionRanking(name);
		return -1;
	}
	
	/**
	 * Modify ranking with a new parameter
	 * @param pr
	 */
	public void modRanking(ParamRanking pr) {
		int index = ranking.isIn(pr);
		if(index == -1)
			ranking.addParam(pr);
		else{
			ranking.modRanking(pr, index);
		}
	}
}
