package propias.dominio.clases;

import propias.dominio.clases.Cell;
import propias.dominio.clases.CellType;

/**
 * 
 * @author Petru Rares Sincraian
 *
 */
public class Board {
	
	private int size;
	private Cell board[][];

	/** Construeix un Board de mida 0 
	 * 
	 */
	public Board() {
		size = 0;
	}
	
	/** Construeix un Board buit de mida size
	 * 
	 * @param size el tamany del board que es vol contruir
	 * @throws NegativeArraySizeException retorna @see NegativeArraySizeException si size es menor
	 * que 0.
	 */
	public Board(int size) throws NegativeArraySizeException {
		if (size >= 0) {
			this.size = size;
			board = new Cell[this.size][this.size];
			initializeBoard();
		} else
			throw new NegativeArraySizeException();
	}

	/** Retorna un board a partir del array d'enters. La copia es realitza
	 * per columnes.
	 * 
	 * @param board El board que es passa.
	 */
	public Board(int board[][]) {
		size = board.length;
		this.board = new Cell[size][size];
		
		for (int i = 0; i < size; ++i) {
			for (int j = 0; j < size; ++j){
				try {
					this.board[i][j] = new Cell(board[i][j]);
				} catch (Exception e) {
					System.out.println("Board no valid");
					e.printStackTrace();
				}
			}
		}
	}
	
	/** Constructor de copia
	 * 
	 * @param board El board que es vol copiar
	 */
	public Board(Board board){
	    this.size = board.getSize();
		this.board = new Cell[this.size][this.size];
	    for(int i = 0; i < this.size; ++i){
		    for(int j = 0; j < this.size; ++j){
		    	this.board[i][j] = new Cell(board.getCell(i, j));
		    }
	    }  
	}
	
	/** Retorna la mida del board
	 * 
	 * @return un enter amb la mida del board
	 */
	public int getSize() {
		return size;
	}
	
	/** Retorna l'enter de la posició 
	 * 
	 * @param x
	 * @param y
	 * @return
	 * @throws ArrayIndexOutOfBoundsException
	 */
	public int getCellValue(int x, int y) throws ArrayIndexOutOfBoundsException {
		if (isInBoard(x, y)) {
			return board[x][y].getValue();
		} else
			throw new ArrayIndexOutOfBoundsException();
	}
	
	public Cell getCell(int x, int y) throws ArrayIndexOutOfBoundsException {
		if (isInBoard(x, y)) {
			return board[x][y];
		} else
			throw new ArrayIndexOutOfBoundsException();
	}
	
	public void setCellValue(int x, int y, int value) throws Exception {
		if (isInBoard(x, y)) {
			board[x][y].setValue(value);
		} else
			throw new ArrayIndexOutOfBoundsException();
	}
	
	public void deleteCellValue(int x, int y) throws Exception {
		if (isInBoard(x, y)) {
			board[x][y].setValue(0);
		} else
			throw new ArrayIndexOutOfBoundsException();
		
	}
	
	public void setCellType(int x, int y, CellType type) throws ArrayIndexOutOfBoundsException {
	    if (isInBoard(x, y)) {
			board[x][y].setType(type);
		} else
			throw new ArrayIndexOutOfBoundsException();	    
	}

	/* AIXO S'HAURA DE BORRAR! ESTA EN CATALA! */

	public int getCasella(int x, int y) throws ArrayIndexOutOfBoundsException {
		if (isInBoard(x, y)) {
			return board[x][y].getValue();
		} else
			throw new ArrayIndexOutOfBoundsException();
	}

	public void setCasella(int x, int y, int value) throws Exception {
		if (isInBoard(x, y)) {
			board[x][y].setValue(value);
		} else
			throw new ArrayIndexOutOfBoundsException();
	}

	public void deleteCasella(int x, int y) throws Exception {
		if (isInBoard(x, y)) {
			board[x][y].setValue(0);
		} else
			throw new ArrayIndexOutOfBoundsException();	
	}

	public void setTypeCasella(int x, int y, CellType type) throws ArrayIndexOutOfBoundsException {
	    if (isInBoard(x, y)) {
			board[x][y].setType(type);
		} else
			throw new ArrayIndexOutOfBoundsException();	    
	}
	public CellType getCellType(int x, int y) throws ArrayIndexOutOfBoundsException {
		if (!isInBoard(x, y)) throw new ArrayIndexOutOfBoundsException();	    
	    return board[x][y].getType();
	}
	private boolean isInBoard(int x, int y) {
		return x >= 0 && x < size && y >= 0 && y < size;
	}
	
	private void initializeBoard() {
		for (int i = 0; i < size; ++i) {
			for (int j = 0; j < size; ++j)
				try {
					board[i][j] = new Cell(0);
				} catch (Exception e) { // no tindriem que arribar aqui
					e.printStackTrace();
				}
		}
	}

}
