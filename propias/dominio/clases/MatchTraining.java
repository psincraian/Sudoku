package propias.dominio.clases;

import java.util.List;
import propias.dominio.clases.Position;
import propias.dominio.controladores.generator.CntrlSudokuHelps;

/** La classe {@link MatchTraining} es una sublasse de {@link Match}. A part de totes
 * les caracteristiques de {@link Match} també proporciona varies ajudes auxiliars
 * per resoldre el Sudoku.
 * 
 * @author Petru Rares Sincraian
 *
 */
public class MatchTraining extends Match {

	/** La cosntructora per defecte. Necessita un nom d'usuari i un sudoku
	 * 
	 * @param username El nom del usuari
	 * @param sudoku El sudoku del usuari
	 */
	public MatchTraining(String username, Sudoku sudoku) {
		super(username, sudoku);
	}

	/** Obte els candidats que poden anar en el Sudoku de la posició especificada
	 * 
	 * @param position La posició del qual es volen obtenir les ajudes
	 * @return Retorna una llista de candidats
	 */
	public List<Integer> getCellCandidates(Position position) {
		return CntrlSudokuHelps.getCandidates(position, getSudoku());
	}
	
	/** Retorna la solució de la posició especificada
	 * 
	 * @param position La posició del qual es vol obtenir la solució
	 * @return El únic valor que pot anar en aquella casella.
	 */
	public Integer getCellSolution(Position position) {
		return CntrlSudokuHelps.getCellSolution(getSolution(), position);
	}
}
