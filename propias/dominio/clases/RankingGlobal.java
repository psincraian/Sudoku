package propias.dominio.clases;

import java.util.*;
import propias.dominio.clases.ParamRanking;

/**
 * Ranking of global type.
 *
 * @author Daniel Sanchez Martinez
 */
public class RankingGlobal extends Ranking{

	/**
	 * Constructor
	 * @param ranking
	 */
	public RankingGlobal(List<ParamRanking> ranking){
		super(ranking);
	}
	
	/**
	 * Search an user in ranking
	 * @param name
	 * @return an int. If found returns the index, if not return -1
	 */
	public int positionRanking(String name){
		for(ParamRanking pr : ranking){
			if(pr.getName() == name)
				return ranking.indexOf(pr);
		}
		return -1;
	}
}