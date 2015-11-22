package propias.dominio.controladores.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.lang.Math;
import propias.dominio.clases.Board;
import propias.dominio.clases.CellType;

/**
*Clase con metodos para hacer agujeros en
*un tablero sudoku de tamaño 9x9 o 16x16
*para crear sudokus jugables de diferentes
*dificultades.
*Usa diferentes patrones y variables
*restrictivas para la creacion del
*sudoku.
*
*APUNTE: En principio los algorismos usados para
*definir el patron de agujeros ya deverian
*permitirnos obtener sudokus con dificultat
*correcta, aun asi en cuanto consiga el correcto
*funcionamiento de la clase LevelSearch.java
*(que usa diferentes tecnicas para definir la
*dificultat de un sudoku) tengo pensado 
*modificar esta clase para asegurar que los
*diferentes niveles se consiguen de forma
*correcta sin ninguna duda y con qualquier 
*tamano (dentro de lo posible) para todos
*los niveles.
*
* @author  Adrián Sánchez Albanell
*/
public class SudokuDiggingHoles {

	private double MAX_HOLES_EASY = 6./9;
	private double MAX_HOLES_MEDIUM = 60./81;
	private double MAX_HOLES_DIFFICULT = 61./81;
	private double MAX_HOLES_EVIL = 64./81;
	private double MAX_HOLES_RC_EASY = 6./9;
	private double MAX_HOLES_RC_MEDIUM = 7./9;
	private double MAX_HOLES_RC_DIFFICULT = 7./9;
	private double MAX_HOLES_RC_EVIL = 1;
	
	private int size;
	private int sqrtSize;
	private int numHoles;
	private int maxHolesFC;
	private Board sudoku;
	List<Integer> pos;
	int[] numsRow;
	int[] numsCol;
	
	/**
	 * Crea un nuevo SudokuDiggingHoles con sudoku igual
	 * a board
	 * @param board Board con el que inicializaremos sudoku.
	 */
	public SudokuDiggingHoles(Board board) throws Exception { 
	    this.sudoku = new Board(board);
	    size = sudoku.getSize();	
	    sqrtSize = (int)Math.sqrt(size);
	    pos = new ArrayList<Integer>();
		for(int p = 0; p < size*size; ++p){
			if(sudoku.getCellValue(p/size, p%size) != 0) pos.add(p);
		}
		numsRow = new int[size];
		numsCol = new int[size];
	}
	
	/**
	 * Permite crear un sudoku de dificultat
	 * facil, media o dificil
	 * @param dificultat 0 para facil, 1 para media
	 * y 2 para dificil.
	 * @return Board sudoku de la dificultat elegida.
	 */
	public Board digHoles(int dificultat) throws Exception {
		Random rand = new Random();			
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
	    return sudoku;
	}
	
	/**
	 * Genera un sudoku de dificultat facil.
	 */
	private void easyDigging() throws Exception {
		Random rand = new Random();
		numHoles = rand.nextInt((int)(MAX_HOLES_EASY*(size*size)) - 2*size) + 2*size + 1;
		maxHolesFC = (int)(MAX_HOLES_RC_EASY*size);
		RandomizingGlobal();
	}
	
	/**
	 * Genera un sudoku de dificultat media.
	 */
	private void mediumDigging() throws Exception {
		Random rand = new Random();
		numHoles = 
			rand.nextInt((int)((MAX_HOLES_MEDIUM)*(size*size)) - (int)(MAX_HOLES_EASY*(size*size)))
															 + (int)(MAX_HOLES_EASY*(size*size)) + 1;
		maxHolesFC = (int)(MAX_HOLES_RC_MEDIUM*size);
		Jumper();
	}
	
	/**
	 * Genera un sudoku de dificultat dificil.
	 */
	private void difficultDigging() throws Exception {
		Random rand = new Random();
		int evil = rand.nextInt(2);

		//evil = 0;

		if(evil >= 1){
		    numHoles = rand.nextInt((int)((MAX_HOLES_DIFFICULT)*(size*size)) - (int)(MAX_HOLES_MEDIUM*(size*size))) 
																			+ (int)(MAX_HOLES_MEDIUM*(size*size)) + 1;
			maxHolesFC = (int)(MAX_HOLES_RC_DIFFICULT*size);
		    AlgorithmS();
		    if(numHoles > 2*size) numHoles = numHoles - size;
		    RandomizingGlobal();
		}
		else{
		    numHoles = rand.nextInt((int)((MAX_HOLES_EVIL)*(size*size)) - (int)(MAX_HOLES_MEDIUM*(size*size))) 
							    										+ (int)(MAX_HOLES_MEDIUM*(size*size)) + 1;
			maxHolesFC = (int)(MAX_HOLES_RC_EVIL*size);
		    LeftRight();
		    RandomizingGlobal();
		}
	}
	
	/**
	 * Prueva a hacer un agujero en
	 * una casilla concreta. Si al hacerlo
	 * el sudoku ya no tiene solucion unica, 
	 * no lo hace.
	 * @param i fila de la casilla.
	 * @param j columna de la casilla.
	 * @return boolean si se ha hecho o no 
	 * el agujero,
	 */
	private boolean digHole(int i, int j) throws Exception {	    
	    Board temp = new Board(sudoku);
	   	temp.deleteCellValue(i, j);
	    SudokuSolver solv = new SudokuSolver(temp);
	    solv.needUnique();
	    solv.solve();
	    if(solv.isUnique()){
	    	sudoku.deleteCellValue(i, j);
	    	return true;
	    }
	    return false;
	}
	
	/**
	 * Patron random para hacer agujeros en
	 * el sudoku.
	 */
	private void RandomizingGlobal()  throws Exception {
		Random rand = new Random();	
		int p, i, j;		
		while(pos.size() > 0 && numHoles > 0){
			p = pos.remove(rand.nextInt(pos.size()));			
			i = p/size;
			j = p%size;
			if(numsCol[j] < maxHolesFC && numsRow[i] < maxHolesFC){
				if(digHole(i, j)){
				    --numHoles;
				    ++numsRow[i];
				    ++numsCol[j];
				}
		    }
		}
	}
	
	/**
	 * Patron para hacer agujeros
	 * que hace agujeros "saltando"
	 * casillas (una si, una no).
	 * Este patron da sudokus de
	 * dificultat media.
	 */
	private void Jumper() throws Exception {
	    int j;
	    int i = 0;	    
	    while(i < size){
			j = 0;
			while(j < size){
			    if(numsCol[j] < maxHolesFC && (i + j)%2 == 0){
					if(digHole(i, j)){
					    --numHoles;
					    ++numsRow[i];
					    ++numsCol[j];
					}
					if(numHoles <= 0 || numsRow[i] >= maxHolesFC){
					    break;
					}
			    }
			    ++j;
			}
			++i;
			if(numHoles <= 0) break;
	    }	    
	   	i = 0;
	   	while(i < size){
			j = 0;
			while(j < size){
			    if(numsCol[j] < maxHolesFC && (i + j)%2 != 0){
					if(digHole(i, j)){
					    --numHoles;
					    ++numsRow[i];
					    ++numsCol[j];
					}
					if(numHoles <= 0 || numsRow[i] >= maxHolesFC){
					    break;
					}
			    }
			    ++j;
			}
			++i;
			if(numHoles <= 0) break;
	    }	      
	}
	
	/**
	 * Patron para hacer agujeros
	 * que recorre las filas en 
	 * zig-zag (la primera de inicio
	 * a fin, la segunda de fin a inicio, 
	 * la tercera de inicio a fin.. y asi 
	 * hasta que acaba).
	 * Permite hacer sudokus dificiles.
	 */
	private void AlgorithmS() throws Exception {	    
	    int j;
	    int i = 0;
	    List<Integer> tmpList = new ArrayList<Integer>();
	    while(i < size){
			if(i%2 == 0){
			    j = 0;
			    while(j < size){
					if(numsCol[j] < maxHolesFC && (i + j)%2 == 0){						
					    if(digHole(i, j)){
							--numHoles;
							++numsRow[i];
							++numsCol[j];
					    }
					    tmpList = new ArrayList<Integer>();
						tmpList.add(i*size + j);
						pos.removeAll(tmpList);
					    if(numHoles <= 0 || numsRow[i] >= maxHolesFC){
							break;
					    }
					}
					++j;
			    }
			}
			else{
			    j = size - 1;
			    while(j >= 0){
					if(numsCol[j] < maxHolesFC && (i + j)%2 == 0){						
					    if(digHole(i, j)){
							--numHoles;
							++numsRow[i];
							++numsCol[j];
					    }							    
						tmpList = new ArrayList<Integer>();
						tmpList.add(i*size + j);
						pos.removeAll(tmpList);
					    if(numHoles <= 0 || numsRow[i] >= maxHolesFC){
							break;
					    }
					}
					--j;
			    }
			}
			++i;
			if(numHoles <= 0) break;
	    }
	}
	
	/**
	 * Patron que recorre el sudoku
	 * de izquierda a derecha y de 
	 * arriva a abajo pasando por 
	 * todas las casillas.
	 * Permite hacer sudokus de 
	 * dificultat dificil o muy 
	 * dificil.
	 */
	private void LeftRight() throws Exception {
	    int j;
	    int i = 0;
	    while(i < size){
			j = 0;
			while(j < size){
				if(digHole(i, j)){
				    --numHoles;
				}
			    ++j;
			}
			++i;
			if(numHoles <= 0) break;
	    }	    
	}
	
	/**
	 * Metodo para hacer permutaciones
	 * validas en el sudoku de forma
	 * random un numero determinado
	 * de veces.
	 * @param permutations numero de permutaciones
	 * que se haran.
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
	 * Intercambia dos filas.
	 * @param b1 bloque de la primera fila.
	 * @param b2 bloque de la segunda fila.
	 * @param r1 identificador de la primera fila.
	 * @param r2 identificador de la segunda fila.
	 */
	private void switchRows(int b1, int b2, int r1, int r2) throws Exception {
		r1 = r1 + b1*sqrtSize;
		r2 = r2 + b2*sqrtSize;
		int tmp;
		for(int j = 0; j < size; ++j){
			tmp = sudoku.getCellValue(r1, j);
			sudoku.setCellValue(r1, j, sudoku.getCellValue(r2, j));			
			sudoku.setCellValue(r2, j, tmp);
		}
	}

	/**
	 * Intercambia dos columnas.
	 * @param b1 bloque de la primera columna.
	 * @param b2 bloque de la segunda columna.
	 * @param c1 identificador de la primera columna.
	 * @param c2 identificador de la segunda columna.
	 */
	private void switchColumns(int b1, int b2, int c1, int c2) throws Exception {
		c1 = c1 + b1*sqrtSize;
		c2 = c2 + b2*sqrtSize;
		int tmp;
		for(int i = 0; i < size; ++i){
			tmp = sudoku.getCellValue(i, c1);
			sudoku.setCellValue(i, c1, sudoku.getCellValue(i, c2));
			sudoku.setCellValue(i, c2, tmp);
		}
	}
	
	/**
	 * Intercambia dos filas de quadrantes.
	 * @param b1 primer grupo de quadrantes.
	 * @param b2 segundo grupo de quadrantes.
	 */
	private void switchQuadRows(int b1, int b2) throws Exception {
		for(int i = 0; i < sqrtSize; ++i){
			switchRows(b1, b2, i, i);
		}
	}
	
	/**
	 * Intercambia dos columnas de quadrantes.
	 * @param b1 primer grupo de quadrantes.
	 * @param b2 segundo grupo de quadrantes.
	 */
	private void switchQuadColumns(int b1, int b2) throws Exception {
		for(int j = 0; j < sqrtSize; ++j){
			switchColumns(b1, b2, j, j);
		}
	}
}
