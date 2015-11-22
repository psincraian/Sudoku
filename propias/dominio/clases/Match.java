package propias.dominio.clases;

import propias.dominio.controladores.generator.SudokuDiggingHoles;
import propias.dominio.controladores.generator.SudokuGenerator;
import propias.dominio.controladores.generator.SudokuSolver;


/**
 * 
 * @author Petru Rares Sincraian
 *
 */
public class Match {
	
	private final static String ERROR_LOCKED_CELL = "Casella bloquejada";
	
	private String username;
	private Sudoku sudoku;
	
	/** Retorna una partida amb el usuari username i Sudoku sudoku
	 * 
	 * @param username El nom del usuari
	 * @param sudoku El sudoku. 
	 * @throws Exception 
	 */
	public Match(String username, CaracteristiquesPartida caracteristiques) throws Exception {
		this.username = username;
		createSudoku(caracteristiques);
	}
	
	/** Crea un Sudoku
	 * @throws Exception 
	 * 
	 */
	private void createSudoku(CaracteristiquesPartida caracteristiques) throws Exception {
		SudokuGenerator generator = new SudokuGenerator(caracteristiques.getMida());
		Board solution = generator.generateBoard();
		SudokuDiggingHoles holes = new SudokuDiggingHoles(solution);
		Board sudoku = holes.digHoles(caracteristiques.dificultat);
		SudokuSolver sol = new SudokuSolver(sudoku);
		Board solved = new Board(sol.solve());
		this.sudoku = new Sudoku(sudoku, solved);
	}
	
	/** Retorna una partida amb el usuari username i Sudoku sudoku
	 * 
	 * @param username El nom del usuari
	 * @param sudoku El sudoku. 
	 */
	public Match(String username, Sudoku sudoku) {
		this.username = username;
		this.sudoku = sudoku;
	}
	
	public Match(String username2, int size) {
		// TODO Auto-generated constructor stub
		this.username = username2;
		this.sudoku = new Sudoku(new Board(size),new Board(size));
	}

	/** Retorna el Sudoku de la partida
	 * 
	 * @return el Sudoku de la partida
	 */
	public Board getSudoku() {
		return sudoku.getSudoku();
	}
	
	/** Retorna la solució de la partida
	 * 
	 * @return retorna el Sudoku solució de la partida
	 */
	public Board getSolution() {
		return sudoku.getSolution();
	}
	
	/** Canvia el usuari de la partida
	 * 
	 * @param username El nou usuari
	 */
	public void setUser(String username) {
		this.username = username;
	}
	
	/** Retorna el nom del usuari
	 * 
	 * @return El nom del usuari
	 */
	public String getUser() {
		return username;
	}

	/** Retorna la mida del sudoku 
	 *  
	 * @return retorna un enter amb la mida del sudoku
	 */
	public int getSudokuLevel() {
		return sudoku.returnLevel();
	}
	
	/** Retorna la mida del sudoku
	 * 
	 * @return un enter amb la mida del sudoku
	 */
	public int getSudokuSize() {
		return sudoku.getSudoku().getSize();
	}
	
	/** Modifica el sudoku de la partida
	 * 
	 * @param sudoku el nou sudoku de la partida
	 */
	public void setSudoku(Sudoku sudoku) {
		this.sudoku = sudoku;
	}
	
	/** Dona valor a la casella de la Position position
	 * 
	 * @param position la posició de la casella que es vol modificar
	 * @param value el valor final de la casella
	 * @throws Exception retorna una excepció si la casella de la posició especificada
	 * esta bloquejada o si el valor no es correcte.
	 */
	public void setCell(Position position, int value) throws Exception {
		Cell cell = sudoku.getSudoku().getCell(position.getRow(), position.getColumn());
		if (cell.getType() == CellType.Locked)
			throw new Exception(ERROR_LOCKED_CELL);
		
		sudoku.setCell(position, value);
	}
	
	/** Esborra la casella de la posició especificada
	 * 
	 * @param position la posicio que es vol borrar
	 * @throws retorna @see {@link ArrayIndexOutOfBoundsException} si la posicio
	 * no es valida
	 */
	public void deleteCell(Position position) throws Exception {
		sudoku.deleteCell(position);
	}
}
