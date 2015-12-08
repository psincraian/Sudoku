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

	@Override
	public void modRanking(ParamRanking pr) {
		int index = isIn(pr);
		if(index == -1)
			addParam(pr);
		else{
			pr.setValue(pr.getValue() + ranking.get(index).getValue());
			ranking.set(index, pr);
			Collections.sort(ranking);	
		}
	}
}