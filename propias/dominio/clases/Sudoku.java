package propias.dominio.clases;

import java.sql.Time;

import propias.dominio.clases.Position;

/**
 * 
 * @author Petru Rares Sincraian
 *
 */
public class Sudoku implements java.io.Serializable {

	private Board sudoku;	
	private Board solution;
	private int level;
	//private RanquingSudoku ranking;
	
	/** Retorna Sudoku amb un enunciat i una solució
	 * 
	 * @param sudoku L'enunciat. Ha de ser un Sudoku valid. Només ha de tenir
	 * una única solució.
	 * @param solution La solució del sudoku. Ha de ser un sudoku valid.
	 */
	public Sudoku(Board sudoku, Board solution) {
		this.sudoku = sudoku;
		this.solution = solution;
		//ranking = new RanquingSudoku();
	}
	
	/** Retorna el sudoku
	 * 
	 * @return retorna el sudoku modificable
	 */
	public Board getSudoku() {
		return new Board(sudoku);
	}
	
	/** Retorna la solució del sudoku
	 * 
	 * @return La solució del sudoku.
	 */
	public Board getSolution() {
		return new Board(solution);
	}
	
	/** Canviar el valor de casella especificada
	 * 
	 * @param position La posició de la casella
	 * @param value El valor pel qual es vol cambiar
	 * @throws Exception Retorn @see ArrayIndexOutOfBoundsException si la posicio
	 * esta fora de rang o una excepcio amb {@link Cell#ERROR_VALUE_NOT_VALID}
	 * si el valor no es valid.
	 */
	public void setCell(Position position, int value) throws Exception {
		sudoku.setCasella(position.getRow(), position.getColumn(), value);
	}
	
	/** Esborra la cesella de la posició especificada
	 * 
	 * @param position La posició de la casella que es vol esborrar
	 * @throws Exception Retorna @see ArrayIndexOutOfBoundsException si la posicio
	 * esta fora de rang
	 */
	public void deleteCell(Position position) throws Exception {
		sudoku.deleteCasella(position.getRow(), position.getColumn());
	}
	
	/** Retorna el nivell del Sudoku
	 * 
	 * @return es un enter entre 1 i 3.
	 */
	public int returnLevel() {
		return level;
	}
	
	// TODO: implementar
	public void updateRanking(Time time) {
		
	}
}
