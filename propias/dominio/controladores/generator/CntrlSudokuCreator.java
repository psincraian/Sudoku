package propias.dominio.controladores.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.lang.Math;
import propias.dominio.clases.Sudoku;
import propias.dominio.clases.Board;
import propias.dominio.clases.CellType;

/**
*Clase amb metodes per a fer forats en
*un taulell sudoku de mida 9x9 o 16x16
*creant sudokus de diferents dificultats.
*
* @author  Adrián Sánchez Albanell
*/
public class CntrlSudokuCreator {

	private Board sudoku;
	private Board solution;
	private int size;
	private int sqrtSize;
	private int minGivens;
	private int givens;
	private double MIN_GIVENS_PORTION = 0.22;
	private double EASY_AVERAGE = 0.48;
	private double MEDIUM_AVERAGE = 0.20;
	private double DIFFICULT_AVERAGE = 0.10;
	
	private List<Integer> pos;


	/**
	 * Permet crear un sudoku de dificultat
	 * facil, mitjana o dificil a partir d'una
	 * plantilla (sera un sudoku amb solucion
	 * isomorfa a la de la plantilla, pero no
	 * necesariament la mateixa).
	 * @param dificultat 0 per a facil, 1 per a
	 * mitjana i 2 per a dificil.
	 * @param board la plantilla inicial.
	 * @return Sudoku el nou sudoku generat.
	 */
	public Sudoku create(int dificultat, Board board) throws Exception {
		Random rand = new Random();

		solution = new Board(board);
		sudoku = new Board(board);
		size = board.getSize();
		sqrtSize = (int)Math.sqrt(size);
		minGivens = (int)(MIN_GIVENS_PORTION*(size*size));
		givens = size*size;

		pos = new ArrayList<Integer>();
		for(int p = 0; p < size*size; ++p){
			pos.add(p);
		}

		if(dificultat == 0){
			int random = (int)((size*size)*EASY_AVERAGE);//rand.nextInt((int)((size*size)*EASY_AVERAGE));
			minGivens += random;
			easyDigging();
	    }
	    else if(dificultat == 1){
	    	int random = (int)((size*size)*MEDIUM_AVERAGE);//rand.nextInt((int)((size*size)*MEDIUM_AVERAGE));
			minGivens += random;
			mediumDigging();
	    }
	    else{			
	    	int random = (int)((size*size)*DIFFICULT_AVERAGE);//rand.nextInt((int)((size*size)*DIFFICULT_AVERAGE));
			minGivens += random;
			difficultDigging();
	    }
	    randomizeSudoku(rand.nextInt(10)*2000);	 
	    setTypeCells();   
	    return new Sudoku(sudoku, solution, dificultat, "creació automàtica");
	}

	/**
	 * Permet crear un sudoku de dificultat
	 * facil, mitjana o dificil a partir d'una
	 * plantilla (sera un sudoku amb solucion
	 * isomorfa a la de la plantilla, pero no
	 * necesariament la mateixa).
	 * Permet triar el minim de caselles que
	 * tindra donades el sudoku inicial.
	 * @param dificultat 0 per a facil, 1 per a
	 *  mitjana i 2 per a dificil.
	 * @param board la plantilla inicial.
	 * @param minGivens el minim de caselles
	 * inicials.
	 * @return Sudoku el nou sudoku generat.
	 */
	public Sudoku createWithMinCells(int dificultat, Board board, int minGivens) throws Exception {
		Random rand = new Random();

		solution = new Board(board);
		sudoku = new Board(board);
		size = board.getSize();
		sqrtSize = (int)Math.sqrt(size);
		if(minGivens < (int)((size*size)*MIN_GIVENS_PORTION)) 
			minGivens = (int)((size*size)*MIN_GIVENS_PORTION);
		this.minGivens = minGivens;
		givens = size*size;

		pos = new ArrayList<Integer>();
		for(int p = 0; p < size*size; ++p){
			pos.add(p);
		}

		if(dificultat == 0){
			easyDigging();
	    }
	    else if(dificultat == 1){
			mediumDigging();
	    }
	    else{			
			difficultDigging();
	    }
	    randomizeSudoku(rand.nextInt(10)*2000);	 
	    setTypeCells();   
	    return new Sudoku(sudoku, solution, dificultat, "creacion automatica");
	}

	/**
	* Metode per obtenir les caselles donades
	* en l'ultim sudoku creat.
	* @return int nombre de caselles donades
	* en l'ultim sudoku.
	*/
	public int getGivensLastSudoku(){
		return givens;
	}

	/**
	* Metode per donar el tipus
	* que correspon a cada casella.
	*/
	private void setTypeCells(){
		for(int i = 0; i < size; ++i){
			for(int j = 0; j < size; ++j){
				if(sudoku.getCellValue(i, j) == 0) sudoku.setCellType(i, j, CellType.Unlocked);
				else sudoku.setCellType(i, j, CellType.Locked);
				solution.setCellType(i, j, CellType.Locked);
			}
		}
	}
	
	/**
	 * Genera un sudoku de dificultat facil.
	 */
	private void easyDigging() throws Exception {		
		Random rand = new Random();	
		int p, i, j;		
		while(pos.size() > 0 && givens > minGivens){
			p = pos.remove(rand.nextInt(pos.size()));		
			i = p/size;
			j = p%size;
			if(easyDiggingHole(i, j)){
				--givens;
			}
		}
	}
	
	/**
	 * Genera un sudoku de dificultat media.
	 */
	private void mediumDigging() throws Exception {
		Random rand = new Random();	
		int p, i, j;
		boolean trobat = false;		
		int saveGivens = givens;
		do{
			givens = saveGivens;
			while(pos.size() > 0 && givens > minGivens){
				p = pos.remove(rand.nextInt(pos.size()));		
				i = p/size;
				j = p%size;
				if(mediumDiggingHole(i, j)){
					--givens;
				}
			}
			CntrlSearchLevel cmp = new CntrlSearchLevel();
			cmp.init(new Board(sudoku));
			trobat = cmp.isARealMedium();
			//Si no l'ha trobat, torna a buscar.
			if(!trobat){
				sudoku = new Board(solution);
				pos = new ArrayList<Integer>();
				for(int a = 0; a < size*size; ++a){
					pos.add(a);
				}
			}
		}while(!trobat);
	}
	
	/**
	 * Genera un sudoku de dificultat dificil.
	 */
	private void difficultDigging() throws Exception {
		Random rand = new Random();	
		int p, i, j;
		boolean trobat = false;
		int saveGivens = givens;
		do{
			givens = saveGivens;
			while(pos.size() > 0 && givens > minGivens){
				p = pos.remove(rand.nextInt(pos.size()));
				i = p/size;
				j = p%size;
				if(difficultDiggingHole(i, j)){
					--givens;
				}
			}
			CntrlSearchLevel cmp = new CntrlSearchLevel();
			cmp.init(new Board(sudoku));
			trobat = cmp.isARealDifficult();
			//Si no l'ha trobat, torna a buscar.
			if(!trobat){				
				sudoku = new Board(solution);
				pos = new ArrayList<Integer>();
				for(int a = 0; a < size*size; ++a){
					pos.add(a);
				}			
			}
		}while(!trobat);
	}

	/**
	 * Prova a fer un forat en
	 * una casella concreta. Si al ferlo
	 * el sudoku ja no te solucio unica
	 * o deja de ser de nivel facil no el fa.
	 * @param i fila de la casella.
	 * @param j columna de la casella.
	 * @return boolean si sha fet o no el forat.
	 */
	private boolean easyDiggingHole(int i, int j) throws Exception {
		Board temp = new Board(sudoku);
	   	temp.deleteCellValue(i, j);
		CntrlSearchLevel cmpEasy = new CntrlSearchLevel();
		cmpEasy.init(temp);
		if(cmpEasy.isItEasy()){
			CntrlSudokuSolver solv = new CntrlSudokuSolver(temp);
	  	  	solv.needUnique();
	   		solv.solve();
	   		if(solv.isUnique()){
				sudoku.deleteCellValue(i, j);
	    		return true;
	    	}
		}		
		return false;
	}

	/**
	 * Prova a fer un forat en
	 * una casella concreta. Si al ferlo
	 * el sudoku ja no te solucio unica
	 * o deja de ser de nivel mitja no el fa.
	 * @param i fila de la casella.
	 * @param j columna de la casella.
	 * @return boolean si sha fet o no el forat.
	 */
	private boolean mediumDiggingHole(int i, int j) throws Exception {
		Board temp = new Board(sudoku);
	   	temp.deleteCellValue(i, j);
	   	CntrlSearchLevel cmpMedium = new CntrlSearchLevel();
		cmpMedium.init(temp);
		if(cmpMedium.isItMedium()){
			CntrlSudokuSolver solv = new CntrlSudokuSolver(temp);
	  	  	solv.needUnique();
	   		solv.solve();
	   		if(solv.isUnique()){
				sudoku.deleteCellValue(i, j);
	    		return true;
	    	}
		}
		return false;
	}
	
	/**
	 * Prova a fer un forat en
	 * una casella concreta. Si al ferlo
	 * el sudoku ja no te solucio unica
	 * no el fa.
	 * @param i fila de la casella.
	 * @param j columna de la casella.
	 * @return boolean si sha fet o no el forat.
	 */
	private boolean difficultDiggingHole(int i, int j) throws Exception {		
	    Board temp = new Board(sudoku);
	   	temp.deleteCellValue(i, j);
	    CntrlSudokuSolver solv = new CntrlSudokuSolver(temp);
	    solv.needUnique();
	    solv.solve();
	    if(solv.isUnique()){
	    	sudoku.deleteCellValue(i, j);
	    	return true;
	    }
	    return false;
	}
	
	/**
	 * Metode per a fer permutacions
	 * valides en el sudoku de forma
	 * random un nombre determinat
	 * de vegades.
	 * @param permutations nombre de permutacions
	 * que se faran.
	 */
	private void randomizeSudoku(int permutations) throws Exception {
		Random rand = new Random();
		int typeOfPermutation, b1, b2, e1, e2;
		while(permutations > 0){
			typeOfPermutation = rand.nextInt(12);
			b1 = rand.nextInt(sqrtSize);	
			if(typeOfPermutation < 4){
				e1 = rand.nextInt(sqrtSize);
				e2 = rand.nextInt(sqrtSize);
				while(e1 == e2) e2 = rand.nextInt(sqrtSize);
				switchRows(b1, b1, e1, e2);
			}
			else if(typeOfPermutation < 8){
				e1 = rand.nextInt(sqrtSize);
				e2 = rand.nextInt(sqrtSize);
				while(e1 == e2) e2 = rand.nextInt(sqrtSize);
				switchColumns(b1, b1, e1, e2);
			}
			else if(typeOfPermutation == 9){
				b2 = rand.nextInt(sqrtSize);
				while(b1 == b2) b2 = rand.nextInt(sqrtSize);
				switchQuadRows(b1, b2);
			}
			else{
				b2 = rand.nextInt(sqrtSize);
				while(b1 == b2) b2 = rand.nextInt(sqrtSize);
				switchQuadColumns(b1, b2);
			}
			--permutations;
		}
	}

	/**
	 * Intercambia dues files.
	 * @param b1 bloc de la primera fila.
	 * @param b2 bloc de la segona fila.
	 * @param r1 identificador de la primera fila.
	 * @param r2 identificador de la segona fila.
	 */
	private void switchRows(int b1, int b2, int r1, int r2) throws Exception {
		r1 = r1 + b1*sqrtSize;
		r2 = r2 + b2*sqrtSize;
		int tmp;
		for(int j = 0; j < size; ++j){
			tmp = sudoku.getCellValue(r1, j);
			sudoku.setCellValue(r1, j, sudoku.getCellValue(r2, j));			
			sudoku.setCellValue(r2, j, tmp);
			tmp = solution.getCellValue(r1, j);
			solution.setCellValue(r1, j, solution.getCellValue(r2, j));			
			solution.setCellValue(r2, j, tmp);
		}
	}

	/**
	 * Intercambia dues columnes.
	 * @param b1 bloc de la primera columna.
	 * @param b2 bloc de la segona columna.
	 * @param r1 identificador de la primera columna.
	 * @param r2 identificador de la segona columna.
	 */
	private void switchColumns(int b1, int b2, int c1, int c2) throws Exception {
		c1 = c1 + b1*sqrtSize;
		c2 = c2 + b2*sqrtSize;
		int tmp;
		for(int i = 0; i < size; ++i){
			tmp = sudoku.getCellValue(i, c1);
			sudoku.setCellValue(i, c1, sudoku.getCellValue(i, c2));
			sudoku.setCellValue(i, c2, tmp);
			tmp = solution.getCellValue(i, c1);
			solution.setCellValue(i, c1, solution.getCellValue(i, c2));
			solution.setCellValue(i, c2, tmp);
		}
	}
	
	/**
	 * Intercambia dues files de quadrants.
	 * @param b1 primer grup de quadrans.
	 * @param b2 segon grup de quadrants.
	 */
	private void switchQuadRows(int b1, int b2) throws Exception {
		for(int i = 0; i < sqrtSize; ++i){
			switchRows(b1, b2, i, i);
		}
	}
	
	/**
	 * Intercambia dues columnes de quadrants.
	 * @param b1 primer grup de quadrants.
	 * @param b2 segon grup de quadrants.
	 */
	private void switchQuadColumns(int b1, int b2) throws Exception {
		for(int j = 0; j < sqrtSize; ++j){
			switchColumns(b1, b2, j, j);
		}
	}
}
