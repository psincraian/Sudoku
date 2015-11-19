package propias.dominio.clases;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import propias.dominio.clases.Board;

/**
 * 
 * @author Petru Rares Sincraian
 *
 */
public class SudokuGenerator {
	private boolean rows[][];
	private boolean columns[][];
	private boolean blocks[][];
	private Board Sudoku;
	private int size;
	private boolean found;
	
	/** Instancia un SudokuGenerator de mida size
	 * 
	 * @param size El tamany del Sudoku
	 * @throws NegativeArraySizeException Llenca NegativeArraySizeException si
	 * size es menor que 0
	 */
	public SudokuGenerator(int size) throws NegativeArraySizeException {
		this.size = size;
		rows = returnTrueMatrix(size);
		columns = returnTrueMatrix(size);
		blocks = returnTrueMatrix(size);
		Sudoku = new Board(size);
	}
	
	/** Retorna un @see Board aleatori de la mida especificada en el constructor.
	 * 
	 * @return retorna un @Board aleatori.
	 */
	public Board generateBoard() {
		backtracking(0);
		return Sudoku;
	}
	
	/** Retorna si el nombre de la posició pos no ha estat utilitzat ni en el row,
	 * column ni block
	 * 
	 * @param row la fila dels nombres utilitzats
	 * @param column la columna dels nombres utilitzats
	 * @param block el block dels nombres utilitzats
	 * @param pos la posició dels nombres utilitzats
	 * @return retorna true si el element no ha estat utilitzat
	 */
	private boolean isAValidNumber(boolean row[], boolean column[], boolean block[], int pos) {
		return row[pos] && column[pos] && block[pos];
	}
	
	/** Retorna la llista de possibles nombres que poden anar en aquella columna,
	 * fila i block
	 * 
	 * @param row els possibles nombres que poden anar en la fila
	 * @param column els possibles nombres que poden anar en la columna
	 * @param block els possibles nombres que poden anar en el block
	 * @return retorna la llista de possibles enters
	 */
	private List<Integer> getNumbers(boolean row[], boolean column[], boolean block[]) {
		List<Integer> possibles = new ArrayList<>(size);
		for (int i = 0; i < size; ++i) {
			if (isAValidNumber(row, column, block, i))
				possibles.add(i + 1);
		}
		
		Collections.shuffle(possibles);
		return possibles;
	}
	
	/** Retorna el block a partir la profunditat de la casella
	 * 
	 * @param depth La profunditat de la casella
	 * @return El bloc, amb numero de 0 a 1
	 */
	private int returnBlockFromDepth(int depth) {
		return Math.floorDiv(depth%size,(int) Math.sqrt(size)) + 
				Math.floorDiv(depth,(int) Math.sqrt(size)*size)*(int) Math.sqrt(size);
	}
	
	/** El generador en si
	 * 
	 * @param depth indica la profonditat del seguent nombre a calcular
	 */
	private void backtracking(int depth) {
		found = depth == size*size;
		if (!found) {
			int rowNumber = Math.floorDiv(depth,size);
			int columnNumber = depth%size;
			int blockNumber = returnBlockFromDepth(depth);
			
			List<Integer> possibilities = getNumbers(rows[rowNumber], 
					columns[columnNumber], blocks[blockNumber]);
			
			for (int candidate : possibilities) {
				setSudokuCandidate(rowNumber, columnNumber, candidate);
				rows[rowNumber][candidate - 1] = false;
				columns[columnNumber][candidate - 1] = false;
				blocks[blockNumber][candidate - 1] = false;
				
				backtracking(depth + 1);
				
				if (found)
					return;
				
				rows[rowNumber][candidate - 1] = true;
				columns[columnNumber][candidate - 1] = true;
				blocks[blockNumber][candidate - 1] = true;
			}
		}
	}
	
	/** Modifica la variable sudoku posant el canditat en la posició especificada
	 * 
	 * @param rowNumber la fila del nombre a modificar
	 * @param columnNumber la columna del nombre a modificar
	 * @param candidate el canditat
	 */
	private void setSudokuCandidate(int rowNumber, int columnNumber, int candidate) {
		try {
			Sudoku.setCasella(rowNumber, columnNumber, candidate);
		} catch (Exception e) { // no tindriem que arribar aqui
			e.printStackTrace();
		}
	}
	
	/** Retorna una matriu de trues de la mida indicada
	 * 
	 * @param size La mida de la matriu
	 * @return una matriu de booleans plena de trues
	 */
	private static boolean[][] returnTrueMatrix(int size) {
		boolean matrix[][] = new boolean[size][size];
		for (int i = 0; i < size; ++i)
			for (int j = 0; j < size; ++j)
				matrix[i][j] = true;
		return matrix;
	}
}
