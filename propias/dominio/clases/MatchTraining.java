package propias.dominio.clases;

import java.util.List;
import propias.dominio.clases.Position;

/** author: Petru Rares */
public class MatchTraining extends Match {

	public MatchTraining(String username, Sudoku sudoku) {
		super(username, sudoku);
	}

	public List<Integer> getCellCandidates(Position position) {
		return SudokuHelps.getCandidates(position, getSudoku());
	}
	
	public Integer getCellSolution(Position position) {
		return SudokuHelps.getCellSolution(getSolution(), position);
	}
}
