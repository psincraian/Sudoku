package propias.dominio.controladores.generator;

import java.util.ArrayList;
import java.util.List;

import propias.dominio.clases.Board;
import propias.dominio.clases.Position;

/**
 * @author Petru Rares Sincraian
 * 
 */
public class SudokuHelps {
	
	/** Retorna els possibles candidats de la posició position de board
	 * 
	 * @param position la posició en la qual es volen buscar els candidats
	 * @param board	el board del que es volen buscar els candidats
	 * @return la llista dels possibles candidats
	 */
	public static List<Integer> getCandidates(Position position, Board board) {
		List<Integer> candidates = new ArrayList<Integer>();
		
		for (int i = 0; i < board.getSize(); ++i)
			candidates.add(i + 1);
		
		deleteRowCandidate(position.getRow(), board, candidates);
		deleteColumnCancidates(position.getColumn(), board, candidates);
		deleteBlockCandidates(position.getRow(), position.getColumn(), board, candidates);
		
		return candidates;
	}
	
	/** Retorna una llista de Position amb les caselles diferents.
	 * Si una casella es buida aquesta no es considera diferent
	 * 
	 * @param solution la solució del board
	 * @param actualBoard el board al que es vol comparar
	 * @return retorna una llista de posicións amb les caselles diferents
	 */
	public static List<Position> getDifferentCells(Board solution, Board actualBoard) {
		List<Position> positions = new ArrayList<Position>();
		
		for (int i = 0; i < solution.getSize(); ++i) {
			for (int j = 0; j < solution.getSize(); ++j) {
				int result = solution.getCell(i, j).getValue();
				int cell = actualBoard.getCell(i, j).getValue();
				
				if (actualBoard.getCell(i, j).isValid() && result != cell) {
					try {
						positions.add(new Position(i, j));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		return positions;
	}
	
	/** Retorna la solució de la seguent casella no valida. 
	 * 
	 * @param solution	la solució del sudoku
	 * @param actualBoard el sudoku del qual es vol buscar la seguent solucio
	 * @return	la seguent solcio del sudoku. En cas que no hi hagi retorna 0
	 */
	public static int getNextSolution(Board solution, Board actualBoard) {
		int result = 0;
		for (int i = 0; i < solution.getSize(); ++i) {
			for (int j = 0; j < solution.getSize(); ++j) {
				if (!actualBoard.getCell(i, j).isValid())
					result = solution.getCell(i, j).getValue();
			}
		}
		
		return result;
	}
	
	/** Retorna la solucio de la posicio especificada
	 * 
	 * @param solution la solució del sudoku
	 * @param position la posició de la solució que es vol retornar
	 * @return la solució
	 */
	public static int getCellSolution(Board solution, Position position) {
		return solution.getCell(position.getRow(), position.getColumn()).getValue();
	}
	
	private static void deleteRowCandidate(int row, Board board, List<Integer> candidates) {
		for (int i = 0; i < board.getSize(); ++i) {
			int number = board.getCellValue(row, i);
			if (board.getCell(row, i).isValid())
				candidates.remove(new Integer(number));
		}
	}
	
	private static void deleteColumnCancidates(int column, Board board, List<Integer> candidates) {
		for (int i = 0; i < board.getSize(); ++i) {
			int number = board.getCellValue(i, column);
			if (board.getCell(i, column).isValid())
				candidates.remove(new Integer(number));
		}
	}
	
	private static void deleteBlockCandidates(int row, int column, Board board, List<Integer> candidates) {
		int blockSize = (int) Math.sqrt(board.getSize());
		int firstRow = getFirstRowOrColumn(row, blockSize);
		int firstColumn= getFirstRowOrColumn(column, blockSize);
		
		for (int i = firstRow; i < firstRow + blockSize; ++i) {
			for (int j = firstColumn; j < firstColumn + blockSize; ++j) {
				int number = board.getCellValue(i, j);
				if (board.getCell(i, j).isValid())
					candidates.remove(new Integer(number));
			}
		}
	}
	
	private static int getFirstRowOrColumn(int position, int boardSize) {
		return position - position%boardSize;
	}
}
