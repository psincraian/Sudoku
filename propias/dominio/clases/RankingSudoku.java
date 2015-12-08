package propias.dominio.clases;

import java.util.*;

/**
 * Ranking of Sudoku type
 *
 * @author Daniel Sanchez Martinez
 */
public class RankingSudoku extends Ranking{
	
	/**
	 * Constructor
	 * @param ranking
	 */
	public RankingSudoku(List<ParamRanking> ranking){
		super(ranking);
	}

	@Override
	public void modRanking(ParamRanking pr) {
		int index = isIn(pr);
		if(index == -1)
			addParam(pr);
		else if(ranking.get(index).getValue() > pr.getValue()){
				ranking.get(index).setValue(pr.getValue());
				Collections.sort(ranking);
		}
	}
}
