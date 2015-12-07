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
	public ControllerRanking(Ranking rank){
		/*if(typeRanking)
			 this.ranking = new RankingGlobal(rank);
		else
			this.ranking = new RankingSudoku(rank);*/
		this.ranking = rank;
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
	/*public int positionRanking(String name){
			return ranking.positionRanking(name);
	}*/
	
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
