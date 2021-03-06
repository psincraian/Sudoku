package propias.dominio.clases;


/** Classe partida. La classe partida ve definida per un nom d'usuari i un Sudoku concret.
 * Proporciona metodes per poder obtenir el nom d'usuari, el sudoku, canviar valors del
 * sudoku, obtenir la solucio, etc.
 *  
 * @author Petru Rares Sincraian
 *
 */
public class Match implements java.io.Serializable {
	
	public final String ERROR_LOCKED_CELL = "Casella bloquejada";
	
	private String username;
	private Sudoku sudoku;
	
	/** Retorna una partida amb el usuari username i Sudoku sudoku
	 * 
	 * @param username El nom del usuari
	 * @param sudoku El sudoku. 
	 */
	public Match(String username, Sudoku sudoku) {
		this.username = username;
		this.sudoku = sudoku;
	}
	
	public Match(String username, int size) {
		this.username = username;
		this.sudoku = new Sudoku(new Board(size),new Board(size));
	}
	/** Retorna el Sudoku de la partida
	 * 
	 * @return el Sudoku de la partida
	 */
	public Board getSudoku() {
		return sudoku.getSudoku();
	}
	
	public Sudoku getAllSudoku() {
		return sudoku;
	}
	
	/** Retorna la solució de la partida
	 * 
	 * @return retorna el Sudoku solució de la partida
	 */
	public Board getSolution() {
		return sudoku.getSolution();
	}
	/**
	 * Retorna el creador del sudoku
	 * @return el nom del creador
	 */
	public String getMaker(){
		return sudoku.getMaker();
	}
	/**
	 * Ranking del sudoku
	 * @return el ranking del sudoku
	 */
	public RankingSudoku getRanking(){
		return this.sudoku.getRanking();
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
	
	/**
	* Retorna el valor d'una vasella en la posicio position
	* @param posigion la posicio de la casella.
	* @return int valor de la casella en la posicio donada.
	* @throws Exception si el valor de la posicio no es correcte.
	* @author Adran Sanchez Albanell
	*/
	public int getCellValue(Position position) throws Exception{
		Cell cell = sudoku.getSudoku().getCell(position.getRow(), position.getColumn());
		return cell.getValue();
	}
	
	/** Esborra la casella de la posició especificada
	 * 
	 * @param position la posicio que es vol borrar
	 * @throws Exception retorna {@link ArrayIndexOutOfBoundsException} si la posicio
	 * no es valida
	 */
	public void deleteCell(Position position) throws Exception {
		sudoku.deleteCell(position);
	}
}
