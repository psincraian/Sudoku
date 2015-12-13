package propias.dominio.clases;

import java.util.ArrayList;

import propias.dominio.clases.Position;

/**
 * 
 * @author Petru Rares Sincraian
 *
 */
public class Sudoku implements java.io.Serializable {
	
	/**
	 * 
	 */
	private Board sudoku;	
	private Board solution;
	private int level;
	private RankingSudoku ranking;
	private String maker;
	
	/** Retorna Sudoku amb un enunciat i una solució
	 * 
	 * @param sudoku L'enunciat. Ha de ser un Sudoku valid. Només ha de tenir
	 * una única solució.
	 * @param solution La solució del sudoku. Ha de ser un sudoku valid.
	 */
	public Sudoku(Board sudoku, Board solution) {
		this.sudoku = sudoku;
		this.solution = solution;
		ranking = new RankingSudoku(new ArrayList<ParamRanking>());
	}
	
		/** Retorna Sudoku amb un enunciat i una solució
	 * 
	 * @param sudoku L'enunciat. Ha de ser un Sudoku valid. Només ha de tenir
	 * una única solució.
	 * @param solution La solució del sudoku. Ha de ser un sudoku valid.
	 * @param level El nivell del sudoku. S'ha de correspondre amb el
	 * sudoku donat.
	 */
	public Sudoku(Board sudoku, Board solution, int level) {
		this.sudoku = sudoku;
		this.solution = solution;
		this.level = level;
		ranking = new RankingSudoku(new ArrayList<ParamRanking>());
	}
	
	/** Retorna Sudoku amb un enunciat i una solució
	 * 
	 * @param sudoku L'enunciat. Ha de ser un Sudoku valid. Només ha de tenir
	 * una única solució.
	 * @param solution La solució del sudoku. Ha de ser un sudoku valid.
	 * @param level El nivell del sudoku. S'ha de correspondre amb el
	 * @param maker creador del sudoku.
	 * sudoku donat.
	 * @author Adrián Sánchez Albanell
	 */
	public Sudoku(Board sudoku, Board solution, int level, String maker) {
		this.sudoku = sudoku;
		this.solution = solution;
		this.level = level;
		ranking = new RankingSudoku(new ArrayList<ParamRanking>());
		this.maker = maker;
	}
	/** Retorna Sudoku amb un enunciat i una solució
	 * 
	 * @param sudoku L'enunciat. Ha de ser un Sudoku valid. Només ha de tenir
	 * una única solució.
	 * @param solution La solució del sudoku. Ha de ser un sudoku valid.
	 * @param maker creador del sudoku.
	 * sudoku donat.
	 * @author Adrián Sánchez Albanell
	 */
	public Sudoku(Board sudoku, Board solution, String maker) {
		this.sudoku = sudoku;
		this.solution = solution;
		ranking = new RankingSudoku(new ArrayList<ParamRanking>());
		this.maker = maker;
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
	 * @return es un enter entre 0 i 2.
	 */
	public int returnLevel() {
		return level;
	}
	
	/** Retorna el nivell del Sudoku
	 * 
	 * @return es un enter entre 0 i 2.
	 * @author Adrián Sánchez Albanell
	 */
	public String returnMaker() {
		return maker;
	}
	public void setMaker(String maker) {
		this.maker = maker;
	}
	
	/** Permet donar el nivell al Sudoku
	 * 
	 * @rparam level el nou nivell del sudoku.
	 */
	public void setLevel(int level) {
		this.level = level;
	}
	
	/** Actualitza el ranking amb les noves dades passades com a parametre
	 * 
	 * @param username El nom d'usuari del nou score
	 * @param score La puntuació aconseguida per l'usuari
	 */
	public void updateRanking(String username, int score) {
		ParamRanking param = new ParamRanking(username, score);
		ranking.modRanking(param);
	}
	
	/** Retorna el ranking d'aquest sudoku
	 * 
	 * @return Retorna el Ranking
	 */
	public RankingSudoku getRanking() {
		return ranking;
	}
}
