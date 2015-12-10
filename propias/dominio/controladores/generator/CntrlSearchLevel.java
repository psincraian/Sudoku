package propias.dominio.controladores.generator;

import java.lang.Integer;
import java.lang.Math;
import java.util.List;
import java.util.ArrayList;
import propias.dominio.clases.Board;

/**
* Classe amb metodes per a trobar la
* dificultat d'un sudoku segons les
* diferents tecniques necessaries per
* a solucionar-lo.
*
*@autor Adrián Sánchez Albanell.
*
*/
public class CntrlSearchLevel {

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//Var. Globals:
	private int size;
	private int sqrtSize;
	private int positions;

	private int mask;

	private List<List<Integer>> rowPositions;//Posicions que corresponen a cada fila.
	private List<List<Integer>> colPositions;//Posicions que corresponen a cada columna.
	private List<List<Integer>> blockPositions;//Posicions que corresponen a cada quadrant.

	private List<Integer> numsPosCell;
	private List<Boolean> foundCells;

	private List<Integer> numsLeftRow;//Nombres restants a cada fila.
	private List<Integer> numsLeftCol;//Nombres restants a cada columna.
	private List<Integer> numsLeftBlock;//Nombres restants a cada quadrant.

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//Metodes init:

	/**
	* Metode per inicialitzar
	* la matriu de posicions
	* per cada fila.
	*/
	private void initRowPositions(){
		List<Integer> row = new ArrayList<Integer>();
		int changeRow = size - 1;
		for(int k = 0; k < positions; ++k){
			row.add(k);
			if(k%size == changeRow){
				rowPositions.add(row);
				row = new ArrayList<Integer>();
			}			
		}
	}

	/**
	* Metode per inicialitzar
	* la matriu de posicions
	* per cada columna.
	*/
	private void initColPositions(){
		List<Integer> col = new ArrayList<Integer>();
		for(int k = 0; k < size; ++k){
			for(int l = 0; l < size; ++l){
				col.add(l*size + k);
			}
			colPositions.add(col);
			col = new ArrayList<Integer>();
		}
	}

	/**
	* Metode per inicialitzar
	* la matriu de posicions
	* per cada quadrant.
	*/
	private void initBlockPositions(){
		List<Integer> block = new ArrayList<Integer>();
		for(int bi = 0; bi < sqrtSize; ++bi){
			int tmpBI = bi*sqrtSize*size;
			for(int bj = 0; bj < sqrtSize; ++bj){
				int tmpBJ = bj*sqrtSize;
				for (int k = 0; k < sqrtSize; ++k) {
					int rowBlock = k*size;
					for(int l = 0; l < sqrtSize; ++l){						
						block.add(tmpBI + tmpBJ + rowBlock + l);
					}
				}
				blockPositions.add(block);
				block = new ArrayList<Integer>();
			}
		}
	}

	/**
	* Metode per inicialitzar
	* la llista de nombres possibles
	* de cada casella.
	* @param board Board inicial.
	*/
	private void initNumsPos(Board board) throws Exception {
		for(int i = 0; i < size; ++i){
			numsLeftRow.add(mask);
			numsLeftCol.add(mask);
			numsLeftBlock.add(mask);
			for(int j = 0 ; j < size; ++j){
				if(board.getCellValue(i, j) != 0){
					numsPosCell.add(codeNum(board.getCellValue(i, j)));
				}
				else{
					numsPosCell.add(mask);
				}				
			}
		}
	}

	/**
	* Metode per inicialitzar les
	* variables globals necessaries
	* per al funcionament del 
	* programa.
	*/
	public void init(Board board) throws Exception {

		size = board.getSize();
		sqrtSize = (int)Math.sqrt(size);
		positions = size*size;

		mask = codeNum(size + 1) - 1;

		rowPositions = new ArrayList<List<Integer>>();
		initRowPositions();
		colPositions = new ArrayList<List<Integer>>();
		initColPositions();
		blockPositions = new ArrayList<List<Integer>>();
		initBlockPositions();

		numsPosCell = new ArrayList<Integer>();
		numsLeftRow = new ArrayList<Integer>(); 
		numsLeftCol = new ArrayList<Integer>();
		numsLeftBlock = new ArrayList<Integer>();
		initNumsPos(board);

		foundCells = new ArrayList<Boolean>();
		for(int a = 0; a < positions; ++a){
			foundCells.add(false);
		}

	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//Metodes codification:

	/**
	* Metode que aplica el logaritme
	* en base 2 sobre un nombre.
	* @param num enter sobre el qual
	* aplicar el logaritme.
	* @return double logaritme en
	* base 2 de num.
	*/
	private double log2(int num){
		return Math.log(num)/Math.log(2);
	}

	/**
	* Metode que codifica els nombres 
	* per a formar mascares de bits:
	* 1 = 000000001, 2 = 000000010, etc.
	* @param num nombre a codificar.
	* @return int nombre codificat.
	*/
	private int codeNum(int num){
		return 1 << (num-1);
	}

	/**
	* Metode que decodifica els nombres 
	* per a formar mascares de bits:
	* 1 = 000000001, 2 = 000000010, etc.
	* @param num nombre a codificar.
	* @return int nombre decodificat.
	*/
	private int decodeNum(int num){
		return (int)log2(num) + 1;
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//Metodes position:

	/**
	* Metode que retorna la fila de
	* la cela en la posicio p.
	* @param p posicio de la cela.
	* @return int fila de la cela.
	*/
	private int posICell(int p){
		return p/size;
	}

	/**
	* Metode que retorna la columna de
	* la cela en la posicio p.
	* @param p posicio de la cela.
	* @return int columna de la cela.
	*/
	private int posJCell(int p){
		return p%size;
	}

	/**
	* Metode que retorna el block de
	* la cela en la posicio p.
	* @param p posicio de la cela.
	* @return int block de la cela.
	*/
	private int blockCell(int p){
		return sqrtSize*(posICell(p)/sqrtSize) + posJCell(p)/sqrtSize;
	}

	/**
	* Metode que retorna la posicio de
	* la cela en la fila i i columna j.
	* @param i fila de la cela.
	* @param j columna de la cela.
	* @return int posicio de la cela.
	*/
	private int posCell(int i, int j){
		return i*size + j;
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//Metodes search easy:

	/**
	* Metode per saber si ja
	* hem resolt el sudoku.
	*/
	private boolean found(){
		for(int a = 0; a < positions; ++a){
			if(!foundCells.get(a)) return false;
		}
		return true;
	}

	/**
	* Metode per actualitzar les
	* variables de nombres possibles
	* i celes trobades.
	*/
	private void clearIsolatedCells(){
		boolean changes;
		do{
			changes = false;
			for(int p = 0; p < positions; ++p){
				if(!foundCells.get(p)){		
					int possibles = numsPosCell.get(p);			
					if(Integer.bitCount(possibles) == 1){
						int num, i, j, b;
						i = posICell(p);
						num = numsLeftRow.get(i);
						num = num & ~possibles;
						numsLeftRow.set(i, num);
						j = posJCell(p);
						num = numsLeftCol.get(j);
						num = num & ~possibles;
						numsLeftCol.set(j, num);
						b = blockCell(p);
						num = numsLeftBlock.get(b);
						num = num & ~possibles;
						numsLeftBlock.set(b, num);
						foundCells.set(p, true);
						changes = true;
					}
					else{
						int num, i, j, b;
						i = posICell(p);
						j = posJCell(p);
						b = blockCell(p);
						num = possibles & numsLeftRow.get(i) & numsLeftCol.get(j) & numsLeftBlock.get(b);
						numsPosCell.set(p, num);						
						if(num != possibles) changes = true;
					}
				}
			}
		}while(changes);
	}

	/**
	* Metode per a buscar si una cela
	* es hiden isolated respecte les
	* demes d'alguna unitat.
	* @param p posicio de la cela.
	* @param unidPositions posicions 
	* de les celes de la unitat.
	* @return boolean si s'ha trobat o no.
	*/
	private boolean hidenIsolatedCellUnitSearch(int p, List<Integer> unitPositions){
		int othersPossible = 0;
		for(int a = 0; a < size; ++a){
			int other = unitPositions.get(a);
			if(other != p) othersPossible = othersPossible | numsPosCell.get(other);
		}
		int numsOnlyInP = numsPosCell.get(p) & ~othersPossible;
		if(Integer.bitCount(numsOnlyInP) == 1){
			numsPosCell.set(p, numsOnlyInP);
			return true;
		}
		return false;
	}

	/**
	* Metode per a buscar si hi ha
	* hiden isolated cell en una
	* cela concreta.
	* @param p posicio de la cela.
	* @return boolean cert si ha trobat
	* que ho es, fals altrament.
	*/
	private boolean hidenIsolatedCellSearch(int p){
		boolean changes = false;
		List<Integer> unit;
		unit =  new ArrayList<Integer>(rowPositions.get(posICell(p)));
		if(!hidenIsolatedCellUnitSearch(p, unit)){
			unit =  new ArrayList<Integer>(colPositions.get(posJCell(p)));
			if(!hidenIsolatedCellUnitSearch(p, unit)){
				unit =  new ArrayList<Integer>(blockPositions.get(blockCell(p)));
				return hidenIsolatedCellUnitSearch(p, unit);
			}
			else return true;
		}
		else return true;	
	}

	/**
	* Metode per buscar naked
	* o hiden pairs respecte
	* una cela i les demes 
	* veines d'una de les 
	* seves unitats.
	* @param a posicio de
	* la cela on estem
	* buscant pairs dins
	* la unitat.
	* @param b posicio de
	* la cela amb la
	* que busquem un pair
	* dins la unitat.
	* @param unit posicions
	* de les celes en la unitat.
	* @return boolean cert si ha trobat
	* algun, fals altrament.
	*/
	private boolean recursivePairCellSearch(int a, int b, List<Integer> unit){
		int pA = unit.get(a);
		if(Integer.bitCount(numsPosCell.get(pA)) == 1 || b >= size) return false;
		int pB = unit.get(b);
		if(Integer.bitCount(numsPosCell.get(pB)) == 1) return recursivePairCellSearch(a, b+1, unit);
		int numsPosA = numsPosCell.get(pA);
		int numsPosB = numsPosCell.get(pB);
		//Naked pair search:
		if(Integer.bitCount(numsPosA) == 2 && Integer.bitCount(numsPosB) == 2 && numsPosA == numsPosB){
			int numsKeepOthers = ~numsPosA;
			int preNum, postNum;
			boolean changes = false;
			for(int k = 0; k < size; ++k){
				if(k != a && k != b){
					preNum = numsPosCell.get(unit.get(k));
					postNum = preNum & numsKeepOthers;					
					if(postNum != preNum){
						numsPosCell.set(unit.get(k), postNum);
						changes = true;
					}
				}
			}
			return changes;
		}
		//Hiden pair search:
		else{
			int precondition = numsPosA & numsPosB;
			if(Integer.bitCount(precondition) >= 2){
				int numsPosOthers = 0;
				for(int k = 0; k < size; ++k){
					if(k != a && k != b){
						numsPosOthers = numsPosOthers | numsPosCell.get(unit.get(k));
					}
				}
				precondition = precondition & ~numsPosOthers;
				if(Integer.bitCount(precondition) == 2){
					numsPosCell.set(pA, precondition);
					numsPosCell.set(pB, precondition);
					return true;
				}
			}
		}
		return recursivePairCellSearch(a, b+1, unit);
	}

	/**
	* Metode per a buscar si hi ha
	* hiden o naked pairs.
	* @return boolean cert si ha trobat
	* algun, fals altrament.
	*/
	private boolean pairSearch(){
		boolean changes = false;
		List<Integer> row, col, block;
		for(int iUnit = 0; iUnit < size; ++iUnit){
			row =  new ArrayList<Integer>(rowPositions.get(iUnit));
			col = new ArrayList<Integer>(colPositions.get(iUnit));
			block = new ArrayList<Integer>(blockPositions.get(iUnit));
			for(int jUnit = 0; jUnit < size-1; ++jUnit){
				boolean rowSearch = recursivePairCellSearch(jUnit, jUnit + 1, row);
				boolean colSearch = recursivePairCellSearch(jUnit, jUnit + 1, col);
				boolean blockSearch = recursivePairCellSearch(jUnit, jUnit + 1, block);
				changes = changes || rowSearch || colSearch || blockSearch;
			}
		}
		return changes;
	}

	/**
	* Metode per buscar naked
	* o hiden triples respecte
	* una cela i les demes 
	* veines d'una de les 
	* seves unitats.
	* @param a posicio de
	* la cela on estem
	* buscant triples dins
	* la unitat.
	* @param b posicio de
	* la primera cela amb la
	* que busquem un triple
	* dins la unitat.
	* @param c posicio de
	* la segona cela amb la
	* que busquem un triple
	* dins la unitat.
	* @param unit posicions
	* de les celes en la unitat.
	* @return boolean cert si ha trobat
	* algun, fals altrament.
	*/
	private boolean recursiveTripleCellSearch(int a, int b, int c, List<Integer> unit){
		int pA = unit.get(a);
		if(Integer.bitCount(numsPosCell.get(pA)) == 1 || b >= size) return false;
		int pB = unit.get(b);
		if(Integer.bitCount(numsPosCell.get(pB)) == 1 || c >= size) return recursiveTripleCellSearch(a, b+1, b+2, unit);
		int pC = unit.get(c);
		if(Integer.bitCount(numsPosCell.get(pC)) == 1) return recursiveTripleCellSearch(a, b, c+1, unit);
		int numsPosA = numsPosCell.get(pA);
		int numsPosB = numsPosCell.get(pB);
		int numsPosC = numsPosCell.get(pC);
		//Naked triple search:
		if(Integer.bitCount(numsPosA | numsPosB | numsPosC) == 3){
			int numsKeepOthers = ~(numsPosA | numsPosB | numsPosC);
			int preNum, postNum;
			boolean changes = false;
			for(int k = 0; k < size; ++k){
				if(k != a && k != b && k != c){
					preNum = numsPosCell.get(unit.get(k));
					postNum = preNum & numsKeepOthers;					
					if(postNum != preNum){
						numsPosCell.set(unit.get(k), postNum);
						changes = true;
					}
				}
			}
			return changes;
		}
		//Hiden triple search:
		else{
			int precondition = numsPosA & (numsPosB | numsPosC) | numsPosB & numsPosC;
			if(Integer.bitCount(precondition) >= 3){
				int numsPosOthers = 0;
				for(int k = 0; k < size; ++k){
					if(k != a && k != b && k != c){
						numsPosOthers = numsPosOthers | numsPosCell.get(unit.get(k));
					}
				}
				precondition = precondition & ~numsPosOthers;
				if(Integer.bitCount(precondition) == 3){
					boolean changes = false;
					int postNum = precondition & numsPosA;
					if(postNum != numsPosA){
						numsPosCell.set(pA, postNum);
						changes = true;
					}
					postNum = precondition & numsPosB;
					if(postNum != numsPosB){
						numsPosCell.set(pB, postNum);
						changes = true;
					}
					postNum = precondition & numsPosC;
					if(postNum != numsPosC){
						numsPosCell.set(pC, postNum);
						changes = true;
					}
					return changes;
				}
			}
		}
		return recursiveTripleCellSearch(a, b, c+1, unit);
	}

	/**
	* Metode per a buscar si hi ha
	* hiden o naked triples.
	* @return boolean cert si ha trobat
	* algun, fals altrament.
	*/
	private boolean tripleSearch(){
		boolean changes = false;
		List<Integer> row, col, block;
		for(int iUnit = 0; iUnit < size; ++iUnit){
			row =  new ArrayList<Integer>(rowPositions.get(iUnit));
			col = new ArrayList<Integer>(colPositions.get(iUnit));
			block = new ArrayList<Integer>(blockPositions.get(iUnit));
			for(int jUnit = 0; jUnit < size-2; ++jUnit){
				boolean rowSearch = recursiveTripleCellSearch(jUnit, jUnit + 1, jUnit + 2, row);
				boolean colSearch = recursiveTripleCellSearch(jUnit, jUnit + 1, jUnit + 2, col);
				boolean blockSearch = recursiveTripleCellSearch(jUnit, jUnit + 1, jUnit + 2, block);
				changes = changes || rowSearch || colSearch || blockSearch;
			}
		}
		return changes;
	}

	/**
	* Metode per buscar naked
	* o hiden quads respecte
	* una cela i les demes 
	* veines d'una de les 
	* seves unitats.
	* @param a posicio de
	* la cela on estem
	* buscant quads dins
	* la unitat.
	* @param b posicio de
	* la primera cela amb la
	* que busquem un quad
	* dins la unitat.
	* @param c posicio de
	* la segona cela amb la
	* que busquem un quad
	* dins la unitat.
	* @param d posicio de
	* la tercera cela amb la
	* que busquem un quad
	* dins la unitat.
	* @param unit posicions
	* de les celes en la unitat.
	* @return boolean cert si ha trobat
	* algun, fals altrament.
	*/
	private boolean recursiveQuadCellSearch(int a, int b, int c, int d, List<Integer> unit){
		int pA = unit.get(a);
		if(Integer.bitCount(numsPosCell.get(pA)) == 1 || b >= size) return false;
		int pB = unit.get(b);
		if(Integer.bitCount(numsPosCell.get(pB)) == 1 || c >= size) return recursiveQuadCellSearch(a, b+1, b+2, b+3, unit);
		int pC = unit.get(c);
		if(Integer.bitCount(numsPosCell.get(pC)) == 1 || d >= size) return recursiveQuadCellSearch(a, b, c+1, c+2, unit);
		int pD = unit.get(d);
		if(Integer.bitCount(numsPosCell.get(pD)) == 1) return recursiveQuadCellSearch(a, b, c, d+1, unit);
		int numsPosA = numsPosCell.get(pA);
		int numsPosB = numsPosCell.get(pB);
		int numsPosC = numsPosCell.get(pC);
		int numsPosD = numsPosCell.get(pD);
		//Naked quad search:
		if(Integer.bitCount(numsPosA | numsPosB | numsPosC | numsPosD) == 4){
			int numsKeepOthers = ~(numsPosA | numsPosB | numsPosC);
			int preNum, postNum;
			boolean changes = false;
			for(int k = 0; k < size; ++k){
				if(k != a && k != b && k != c && k != d){
					preNum = numsPosCell.get(unit.get(k));
					postNum = preNum & numsKeepOthers;					
					if(postNum != preNum){
						numsPosCell.set(unit.get(k), postNum);
						changes = true;
					}
				}
			}
			return changes;
		}
		//Hiden quad search:
		else{
			int precondition = numsPosA & (numsPosB | numsPosC | numsPosD) | numsPosB & (numsPosC | numsPosD) | numsPosC & numsPosD;
			if(Integer.bitCount(precondition) >= 4){
				int numsPosOthers = 0;
				for(int k = 0; k < size; ++k){
					if(k != a && k != b && k != c){
						numsPosOthers = numsPosOthers | numsPosCell.get(unit.get(k));
					}
				}
				precondition = precondition & ~numsPosOthers;
				if(Integer.bitCount(precondition) == 4){
					boolean changes = false;
					int postNum = precondition & numsPosA;
					if(postNum != numsPosA){
						numsPosCell.set(pA, postNum);
						changes = true;
					}
					postNum = precondition & numsPosB;
					if(postNum != numsPosB){
						numsPosCell.set(pB, postNum);
						changes = true;
					}
					postNum = precondition & numsPosC;
					if(postNum != numsPosC){
						numsPosCell.set(pC, postNum);
						changes = true;
					}
					postNum = precondition & numsPosD;
					if(postNum != numsPosD){
						numsPosCell.set(pD, postNum);
						changes = true;
					}
					return changes;
				}
			}
		}
		return recursiveQuadCellSearch(a, b, c, d+1, unit);
	}

	/**
	* Metode per a buscar si hi ha
	* hiden o naked quads.
	* @return boolean cert si ha trobat
	* algun, fals altrament.
	*/
	private boolean quadSearch(){
		boolean changes = false;
		List<Integer> row, col, block;
		for(int iUnit = 0; iUnit < size; ++iUnit){
			row =  new ArrayList<Integer>(rowPositions.get(iUnit));
			col = new ArrayList<Integer>(colPositions.get(iUnit));
			block = new ArrayList<Integer>(blockPositions.get(iUnit));
			for(int jUnit = 0; jUnit < size-3; ++jUnit){
				boolean rowSearch = recursiveQuadCellSearch(jUnit, jUnit + 1, jUnit + 2, jUnit + 3, row);
				boolean colSearch = recursiveQuadCellSearch(jUnit, jUnit + 1, jUnit + 2, jUnit + 3, col);
				boolean blockSearch = recursiveQuadCellSearch(jUnit, jUnit + 1, jUnit + 2, jUnit + 3, block);
				changes = changes || rowSearch || colSearch || blockSearch;
			}
		}
		return changes;
	}

	/**
	* Metode per a fer
	* una particio d'una
	* unitat.
	* @param numSlice quina
	* particio volem.
	* @param unit unitat de la
	* que en volem una part.
	* @return List<Integer>
	* particio de la unitat.
	*/
	private List<Integer> sliceUnit(int numSlice, List<Integer> unit){
		List<Integer> slice = new ArrayList<Integer>();
		for(int a = 0; a < sqrtSize; ++a){
			slice.add(unit.get(numSlice*sqrtSize + a));
		}
		return slice;
	}

	/**
	* Metode per a fer
	* una particio per
	* columnes d'un block.
	* @param numSlice quina
	* particio volem.
	* @param block block del
	* que en volem una part.
	* @return List<Integer>
	* particio del block.
	*/
	private List<Integer> sliceColBlock(int numSlice, List<Integer> block){
		List<Integer> slice = new ArrayList<Integer>();
		for(int a = 0; a < sqrtSize; ++a){
			slice.add(block.get(a*sqrtSize + numSlice));
		}
		return slice;
	}

	/**
	* Metode per fer
	* intesection removal
	* entre dos unitats.
	* @param slicePositions posicions
	* de la part on busquem els pair
	* o triple.
	* @param unitA primera unitat
	* de la interseccio.
	* @param unitB segona unitat
	* de la interseccio.
	* @return false si no hi ha
	* cap canvi al taulell, true
	* altrament.
	*/
	private boolean intersectionUnitSearch(List<Integer> slicePositions, List<Integer> unitA, List<Integer> unitB){
		boolean changes = false;
		List<Integer> otherPositions = new ArrayList<Integer>(unitA);
		otherPositions.removeAll(slicePositions);
		int numsPosSlize = 0;
		//Nombres possibles part on busquem:
		for(int a = 0; a < slicePositions.size(); ++a){
			numsPosSlize = numsPosSlize | numsPosCell.get(slicePositions.get(a));
		}
		int numsPosOthers = 0;
		//Nombres possibles en la resta de la unitat:
		for(int a = 0; a < otherPositions.size(); ++a){
			numsPosOthers = numsPosOthers | numsPosCell.get(otherPositions.get(a));
		}
		//Nombres nomes poden anar en la part del slice.
		int numsOnlyOnSlice = numsPosSlize & ~numsPosOthers;
		if(numsOnlyOnSlice > 0){
			//Nombres que no s'ha d'eliminar de la resta de la segona unitat.
			int nonRemovalNumbers = ~numsOnlyOnSlice;
			//Treure nombres que podem.
			for(int a = 0; a < size; ++a){
				int pos = unitB.get(a);
				if(!slicePositions.contains(pos)){
					int preNum, postNum;
					preNum = numsPosCell.get(pos);
					postNum = preNum & nonRemovalNumbers;
					if(preNum != postNum){
						numsPosCell.set(pos, postNum);
						changes = true;
					}
				}
			}
		}
		return changes;
	}

	/**
	* Metode per a buscar si es
	* pot fer intersection 
	* removal.
	* @return boolean cert si ha fet
	* algun, fals altrament.
	*/
	private boolean intersectionSearch(){
		boolean changes = false;
		for(int numUnit = 0; numUnit < size; ++numUnit){
			List<Integer> row = new ArrayList<Integer>(rowPositions.get(numUnit));
			List<Integer> col = new ArrayList<Integer>(colPositions.get(numUnit));
			List<Integer> block = new ArrayList<Integer>(blockPositions.get(numUnit));
			for(int numSlice = 0; numSlice < sqrtSize; ++numSlice){				
				//Block/row.
				List<Integer> blockRowSlice = sliceUnit(numSlice, block);
				int numRow = sqrtSize*(numUnit/sqrtSize) + numSlice;
				boolean intersectionBR = intersectionUnitSearch(blockRowSlice, block, rowPositions.get(numRow));
				//Block/col.
				List<Integer> blockColSlice = sliceColBlock(numSlice, block);
				int numCol = sqrtSize*(numUnit%sqrtSize) + numSlice;
				boolean intersectionBC = intersectionUnitSearch(blockColSlice, block, colPositions.get(numCol));
				int numBlock;
				//Row/block.
				List<Integer> rowSlice = sliceUnit(numSlice, row);
				numBlock = sqrtSize*(numUnit/sqrtSize) + numSlice;
				boolean intersectionRB = intersectionUnitSearch(rowSlice, row, blockPositions.get(numBlock));
				//Col/block.
				List<Integer> colSlice = sliceUnit(numSlice, col);
				numBlock = numUnit/sqrtSize + numSlice*sqrtSize;
				boolean intersectionCB = intersectionUnitSearch(colSlice, col, blockPositions.get(numBlock));
				changes = changes || intersectionBR || intersectionBC || intersectionRB || intersectionCB;
			}
		}
		return changes;
	}

	/**
	* Metode de busca utilitzant
	* diferents tecniques de nivell
	* facil sobre totes les caselles
	* del sudoku que quedin per omplir.
	* @return boolean si hi ha hagut 
	* algun canvi o no.
	*/
	private boolean easySearch(){
		boolean changes = false;
		for(int p = 0; p < positions; ++p){
			if(!foundCells.get(p)){
				boolean hiden = hidenIsolatedCellSearch(p);
				changes =  changes || hiden;
			}		
		}
		if(changes){
			clearIsolatedCells();
		}
		boolean pairs = pairSearch();
		boolean triples = tripleSearch();
		boolean quads = quadSearch();
		boolean intersections = intersectionSearch();
		changes = changes || pairs || triples || quads || intersections;
		return changes;
	}

	/**
	* Metode que utilitza tecniques
	* de nivell senzill per a 
	* intentar resoldre un sudoku.
	*/	
	private void easy(){
		boolean changes;
		do{
			changes = easySearch();
			if(changes){
				clearIsolatedCells();
			}
		}while(changes);
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//Metodes search medium:

	/**
	* Metode per actualitzar una
	* unitat segons la X-Wing
	* trobada amb algun nombre
	* en una altra unitat.
	* @param n nombre amb el
	* que s'ha trobat la X-Wing.
	* @param ab nombre de la unitat
	* amb les caselles A i B de la
	* X-Wing.
	* @param cd nombre de la unitat
	* amb les caselles C i D de la
	* X-Wing.
	* @param occurrences mascara de
	* bits que indica quines unitats
	* s'han d'actualitzar.
	* @param unitToUpdate representacio
	* de les diferents unitats que conte
	* les unitats a actualitzar.
	* @return boolean true si la X-Wing
	* ha modificat alguna casella, false
	* altrament.
	*/
	private boolean actXWingUnit(int n, int ab, int cd, int occurrences, List<List<Integer>> unitToUpdate){		
		boolean changes = false;
		int unitUpdate1 = -1;
		int unitUpdate2 = -1;
		boolean first = true;
		for(int k = 0; k < size; ++k){
			if(occurrences%2 == 1){
				if(first){
					unitUpdate1 = k;
					first = false;
				}
				else{
					unitUpdate2 = k;
					break;
				}
			}
			occurrences = occurrences >> 1;
		}
		int nonRemovalNumbers = ~codeNum(n);
		List<Integer> unit1 = unitToUpdate.get(unitUpdate1);
		List<Integer> unit2 = unitToUpdate.get(unitUpdate2);
		for(int k = 0; k < size; ++k){
			if(k != ab && k != cd){
				int pos, preNum, postNum;
				pos = unit1.get(k);
				preNum = numsPosCell.get(pos);
				postNum = preNum & nonRemovalNumbers;
				if(postNum != preNum){
					numsPosCell.set(pos, postNum);
					changes = true;
				}
				pos = unit2.get(k);
				preNum = numsPosCell.get(pos);
				postNum = preNum & nonRemovalNumbers;
				if(postNum != preNum){
					numsPosCell.set(pos, postNum);
					changes = true;
				}
			}
		}
		return changes;
	}

	/**
	* Metode que retorna una mascara
	* de bits indicant en quines posicions
	* de la unitat apareix n de la següent
	* forma (exemple amb unitats de 4 
	* posicions):
	* Ex1: 0101 apareix en les posicions 0
	* i 3.
	* Ex2: 1000 apareix en la posicio 4.
	* @param n nombre que busquem.
	* @param unit posicions de la unitat
	* on busquem.
	* @return int mascara de bits amb el
	* resultat.
	*/
	private int occurrencesSearch(int n, List<Integer> unit){
		int codedNumber = codeNum(n);
		int occurrencesPosition = 0;
		int actPosMask = 1;
		for(int a = 0; a < size; ++a){
			if((codedNumber & numsPosCell.get(unit.get(a))) != 0){
				occurrencesPosition += actPosMask;
			}
			actPosMask = actPosMask << 1;
		}
		return occurrencesPosition;
	}
	
	/**
	* Metode per a buscar si hi ha
	* X-Wing en el sudoku i actualitzarlo
	* per a un tipus d'unitat (files o
	* columnes) i per a un nombre concret.
	* @param n nombre per al que busquem
	* X-Wings.
	* @param unitWing unitats on busquem
	* les X-Wings.
	* @param unitToUpdate unitats a 
	* actualitzar en cas de trobar
	* X-Wings.
	* @return true si ha fet algun
	* canvi en el sudoku, fals si no.
	*/
	private boolean xWing(int n, List<List<Integer>> unitWing, List<List<Integer>> unitToUpdate){
		boolean changes = false;
		List<Integer> unitOccurrences = new ArrayList<Integer>();
		for(int a = 0; a < size; ++a){
			unitOccurrences.add(occurrencesSearch(n, unitWing.get(a)));
		}
		for(int ab = 0; ab < size-1; ++ab){
			int occurrencesAB = unitOccurrences.get(ab);
			if(Integer.bitCount(occurrencesAB) == 2){
				for(int cd = ab+1; cd < size; ++cd){
					int occurrencesCD = unitOccurrences.get(cd);
					if(occurrencesAB == occurrencesCD){
						boolean act = actXWingUnit(n, ab, cd, occurrencesAB, unitToUpdate);
						changes = changes || act;
					}
				}
			}
		}
		return changes;
	}

	/**
	* Metode per a buscar X-Wing
	* en el sudoku.
	* @return true si hi ha hagut
	* algun canvi en el sudoku,
	* fals altrament.
	*/
	private boolean xWingSearch(){
		boolean changes = false;
		for(int n = 1; n <= size; ++n){
			boolean xWingRows = xWing(n, rowPositions, colPositions);
			boolean xWingCols = xWing(n, colPositions, rowPositions);
			changes = changes || xWingRows || xWingCols;
		}
		return changes;
	}

	/**
	* Metode per actualitzar una
	* unitat segons la sword-Fish
	* trobada amb algun nombre
	* en una altra unitat.
	* @param n nombre amb el
	* que s'ha trobat la sword-Fish.
	* @param a nombre de la unitat
	* amb les primeres caselles de
	* la sword-Fish.
	* @param b nombre de la unitat
	* amb les segones caselles de
	* la sword-Fish.
	* @param c nombre de la unitat
	* amb les terceres caselles de 
	* la sword-Fish.
	* @param occurrences mascara de
	* bits que indica quines unitats
	* s'han d'actualitzar.
	* @param unitToUpdate representacio
	* de les diferents unitats que conte
	* les unitats a actualitzar.
	* @return boolean true si la sword-Fish
	* ha modificat alguna casella, false
	* altrament.
	*/
	private boolean actSwordFishUnit(int n, int a, int b, int c, int occurrences, List<List<Integer>> unitToUpdate){
		boolean changes = false;
		int unitUpdate1 = -1;
		int unitUpdate2 = -1;
		int unitUpdate3 = -1;
		int count = 3;
		for(int k = 0; k < size; ++k){
			if(occurrences%2 == 1){
				if(count == 3){
					unitUpdate1 = k;
					--count;
				}
				else if(count == 2){
					unitUpdate2 = k;
					--count;
				}
				else{
					unitUpdate3 = k;
					break;
				}
			}
			occurrences = occurrences >> 1;
		}
		int nonRemovalNumbers = ~codeNum(n);
		List<Integer> unit1 = unitToUpdate.get(unitUpdate1);
		List<Integer> unit2 = unitToUpdate.get(unitUpdate2);
		List<Integer> unit3 = unitToUpdate.get(unitUpdate3);
		for(int k = 0; k < size; ++k){
			if(k != a && k != b && k != c){
				int pos, preNum, postNum;
				pos = unit1.get(k);
				preNum = numsPosCell.get(pos);
				postNum = preNum & nonRemovalNumbers;
				if(postNum != preNum){
					numsPosCell.set(pos, postNum);
					changes = true;
				}
				pos = unit2.get(k);
				preNum = numsPosCell.get(pos);
				postNum = preNum & nonRemovalNumbers;
				if(postNum != preNum){
					numsPosCell.set(pos, postNum);
					changes = true;
				}
				pos = unit3.get(k);
				preNum = numsPosCell.get(pos);
				postNum = preNum & nonRemovalNumbers;
				if(postNum != preNum){
					numsPosCell.set(pos, postNum);
					changes = true;
				}
			}
		}
		return changes;
	}

	/**
	* Metode per a buscar si hi ha
	* sword-Fish en el sudoku i actualitzarlo
	* per a un tipus d'unitat (files o
	* columnes) i per a un nombre concret.
	* @param n nombre per al que busquem
	* sword-Fish.
	* @param unitWing unitats on busquem
	* les sword-Fish.
	* @param unitToUpdate unitats a 
	* actualitzar en cas de trobar
	* sword-Fish.
	* @return true si ha fet algun
	* canvi en el sudoku, fals si no.
	*/
	private boolean swordFish(int n, List<List<Integer>> unitSwordFish, List<List<Integer>> unitToUpdate){
		boolean changes = false;
		List<Integer> unitOccurrences = new ArrayList<Integer>();
		for(int a = 0; a < size; ++a){
			unitOccurrences.add(occurrencesSearch(n, unitSwordFish.get(a)));
		}
		for(int a = 0; a < size-2; ++a){
			int occurrencesA = unitOccurrences.get(a);
			if(Integer.bitCount(occurrencesA) >= 2 && Integer.bitCount(occurrencesA) < 4){
				for(int b = a+1; b < size-1; ++b){
					int occurrencesB = unitOccurrences.get(b);
					int occurrencesAll = occurrencesA | occurrencesB;
					if(Integer.bitCount(occurrencesB) >= 2 && Integer.bitCount(occurrencesAll) < 4){
						for(int c = b+1; c < size; ++c){
							int occurrencesC = unitOccurrences.get(c);
							occurrencesAll = occurrencesA | occurrencesB | occurrencesC;
							if(Integer.bitCount(occurrencesC) >= 2 && Integer.bitCount(occurrencesAll) == 3){
								boolean act = actSwordFishUnit(n, a, b, c, occurrencesAll, unitToUpdate);
								changes = changes || act;
							}							
						}
					}
				}
			}
		}
		return changes;
	}

	/**
	* Metode per a buscar
	* sword-Fish en el
	* sudoku.
	* @return true si hi ha hagut
	* algun canvi en el sudoku,
	* fals altrament.
	*/
	private boolean swordFishSearch(){
		boolean changes = false;
		for(int n = 1; n <= size; ++n){
			boolean swordFishRows = swordFish(n, rowPositions, colPositions);
			boolean swordFishCols = swordFish(n, colPositions, rowPositions);
			changes = changes || swordFishRows || swordFishCols;
		}
		return changes;
	}
	
	/**
	* Metode per a actualitzar
	* segons un Y-Wing trobat.
	* @param pAC posicio de la
	* casella AC de la Y-Wing
	* @param pBC posicio de la
	* casella BC de la Y-Wing.
	* @return boolean true si
	* hi ha hagut algun canvi,
	* false altrament.
	*/
	private boolean yWing(int pAC, int pBC){
		boolean changes = false;
		int removeC = ~(numsPosCell.get(pAC) & numsPosCell.get(pBC));	
		//Visibles AC
		List<Integer> visiblePositionsAC = new ArrayList<Integer>(rowPositions.get(posICell(pAC)));
		List<Integer> tmp = new ArrayList<Integer>(colPositions.get(posJCell(pAC)));
		List<Integer> tmpRemove = new ArrayList<Integer>(tmp);
		tmpRemove.retainAll(visiblePositionsAC);
		tmp.removeAll(tmpRemove);
		visiblePositionsAC.addAll(tmp);
		tmp = new ArrayList<Integer>(blockPositions.get(blockCell(pAC)));
		tmpRemove = new ArrayList<Integer>(tmp);
		tmpRemove.retainAll(visiblePositionsAC);
		tmp.removeAll(tmpRemove);		
		visiblePositionsAC.addAll(tmp);
		//Visibles BC
		List<Integer> visiblePositionsBC = new ArrayList<Integer>(rowPositions.get(posICell(pBC)));
		tmp = new ArrayList<Integer>(colPositions.get(posJCell(pBC)));
		tmpRemove = new ArrayList<Integer>(tmp);
		tmpRemove.retainAll(visiblePositionsBC);
		tmp.removeAll(tmpRemove);
		visiblePositionsBC.addAll(tmp);
		tmp = new ArrayList<Integer>(blockPositions.get(blockCell(pBC)));
		tmpRemove = new ArrayList<Integer>(tmp);
		tmpRemove.retainAll(visiblePositionsBC);
		tmp.removeAll(tmpRemove);		
		visiblePositionsBC.addAll(tmp);		
		//Caselles visibles tant per AC com per BC (interseccio de les visibles per cadascun d'ells).
		List<Integer> visiblePositionsACBC = new ArrayList<Integer>(visiblePositionsAC);
		visiblePositionsACBC.retainAll(visiblePositionsBC);
		int visSize = visiblePositionsACBC.size();
		for(int a = 0; a < visSize; ++a){
			int p = visiblePositionsACBC.get(a);
			if(p != pAC && p != pBC){
				int preNum, postNum;
				preNum = numsPosCell.get(p);
				postNum = preNum & removeC;
				if(postNum != preNum){
					numsPosCell.set(p, postNum);
					changes = true;
				}
			}
		}
		return changes;
	}
	
	/**
	* Metode per a buscar
	* Y-Wing en el 
	* sudoku.
	* @return true si hi ha hagut
	* algun canvi en el sudoku,
	* fals altrament.
	*/
	private boolean yWingSearch(){
		boolean changes = false;
		for(int pAB = 0; pAB < positions; ++pAB){								
			int numsPossibleAB = numsPosCell.get(pAB);
			if(Integer.bitCount(numsPossibleAB) == 2){
				List<Integer> visiblePositionsAB = new ArrayList<Integer>(rowPositions.get(posICell(pAB)));
				List<Integer> tmp = new ArrayList<Integer>(colPositions.get(posJCell(pAB)));//Per trobar quins afegir (i no tenir repetits).
				List<Integer> tmpRemove = new ArrayList<Integer>(tmp);
				tmpRemove.retainAll(visiblePositionsAB);//Nombres repetits.
				tmp.removeAll(tmpRemove);
				visiblePositionsAB.addAll(tmp);//Unio rows amb cols.
				tmp = new ArrayList<Integer>(blockPositions.get(blockCell(pAB)));
				tmpRemove = new ArrayList<Integer>(tmp);
				tmpRemove.retainAll(visiblePositionsAB);//Nombres repetits.
				tmp.removeAll(tmpRemove);
				visiblePositionsAB.addAll(tmp);//Unio rows, cols i blocks sense repetits.
				int visABsize = visiblePositionsAB.size();
				for(int ac = 0; ac < visABsize; ++ac){
					int pAC = visiblePositionsAB.get(ac);
					if(pAC != pAB){
						int numsPossibleAC = numsPosCell.get(pAC);
						if(Integer.bitCount(numsPossibleAC) == 2 && Integer.bitCount(numsPossibleAB & numsPossibleAC) == 1){
							for(int bc = 0; bc < visABsize; ++bc){
								if(bc != ac){
									int pBC = visiblePositionsAB.get(bc);
									if(pBC != pAB){									
										int numsPossibleBC = numsPosCell.get(pBC);										
										if(Integer.bitCount(numsPossibleBC) == 2 
											&& Integer.bitCount(numsPossibleAB & numsPossibleBC) == 1
											&& Integer.bitCount(numsPossibleAC & numsPossibleBC) == 1
											&& Integer.bitCount(numsPossibleAB | numsPossibleAC | numsPossibleBC) == 3){
											boolean yw = yWing(pAC, pBC);
											changes = changes || yw;
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return changes;
	}
	
	/**
	* Metode per a actualitzar
	* segons un XYZ-Wing trobat.
	* @param pXYZ posicio de la
	* casella XYZ de la XYZ-Wing
	* @param pXZ posicio de la
	* casella XZ de la XYZ-Wing.
	* @param pYZ posicio de la
	* casella YZ de la XYZ-Wing.
	* @return boolean true si
	* hi ha hagut algun canvi,
	* false altrament.
	*/
	private boolean xyzWing(int pXYZ, int pXZ, int pYZ){
		boolean changes = false;
		int removeZ = ~(numsPosCell.get(pXYZ) & numsPosCell.get(pXZ) & numsPosCell.get(pYZ));	
		//Visibles XYZ
		List<Integer> visiblePositionsXYZ = new ArrayList<Integer>(rowPositions.get(posICell(pXYZ)));
		List<Integer> tmp = new ArrayList<Integer>(colPositions.get(posJCell(pXYZ)));
		List<Integer> tmpRemove = new ArrayList<Integer>(tmp);
		tmpRemove.retainAll(visiblePositionsXYZ);
		tmp.removeAll(tmpRemove);
		visiblePositionsXYZ.addAll(tmp);
		tmp = new ArrayList<Integer>(blockPositions.get(blockCell(pXYZ)));
		tmpRemove = new ArrayList<Integer>(tmp);
		tmpRemove.retainAll(visiblePositionsXYZ);
		tmp.removeAll(tmpRemove);		
		visiblePositionsXYZ.addAll(tmp);
		//Visibles XZ
		List<Integer> visiblePositionsXZ = new ArrayList<Integer>(rowPositions.get(posICell(pXZ)));
		tmp = new ArrayList<Integer>(colPositions.get(posJCell(pXZ)));
		tmpRemove = new ArrayList<Integer>(tmp);
		tmpRemove.retainAll(visiblePositionsXZ);
		tmp.removeAll(tmpRemove);
		visiblePositionsXZ.addAll(tmp);
		tmp = new ArrayList<Integer>(blockPositions.get(blockCell(pXZ)));
		tmpRemove = new ArrayList<Integer>(tmp);
		tmpRemove.retainAll(visiblePositionsXZ);
		tmp.removeAll(tmpRemove);
		visiblePositionsXZ.addAll(tmp);
		//Visibles YZ
		List<Integer> visiblePositionsYZ = new ArrayList<Integer>(rowPositions.get(posICell(pYZ)));
		tmp = new ArrayList<Integer>(colPositions.get(posJCell(pYZ)));
		tmpRemove = new ArrayList<Integer>(tmp);
		tmpRemove.retainAll(visiblePositionsYZ);
		tmp.removeAll(tmpRemove);
		visiblePositionsYZ.addAll(tmp);
		tmp = new ArrayList<Integer>(blockPositions.get(blockCell(pYZ)));
		tmpRemove = new ArrayList<Integer>(tmp);
		tmpRemove.retainAll(visiblePositionsYZ);
		tmp.removeAll(tmpRemove);		
		visiblePositionsYZ.addAll(tmp);
		//Caselles visibles tant per XYZ com per XZ com per YZ (interseccio de les visibles per cadascun d'ells).
		List<Integer> visiblePositionsAll = new ArrayList<Integer>(visiblePositionsXYZ);
		visiblePositionsAll.retainAll(visiblePositionsXZ);
		visiblePositionsAll.retainAll(visiblePositionsYZ);
		int visSize = visiblePositionsAll.size();
		for(int a = 0; a < visSize; ++a){
			int p = visiblePositionsAll.get(a);
			if(p != pXYZ && p != pXZ && p != pYZ){
				int preNum, postNum;
				preNum = numsPosCell.get(p);
				postNum = preNum & removeZ;
				if(postNum != preNum){
					numsPosCell.set(p, postNum);
					changes = true;
				}
			}
		}
		return changes;
	}
	
	/**
	* Metode per a buscar
	* XYZ-Wing en el 
	* sudoku.
	* @return true si hi ha hagut
	* algun canvi en el sudoku,
	* fals altrament.
	*/
	private boolean xyzWingSearch(){
		boolean changes = false;
		for(int pXYZ = 0; pXYZ < positions; ++pXYZ){
			int numsPossibleXYZ = numsPosCell.get(pXYZ);
			if(Integer.bitCount(numsPossibleXYZ) == 3){
				List<Integer> visiblePositionsXYZ = new ArrayList<Integer>(rowPositions.get(posICell(pXYZ)));
				List<Integer> tmp = new ArrayList<Integer>(colPositions.get(posJCell(pXYZ)));//Per trobar quins afegir (i no tenir repetits).
				List<Integer> tmpRemove = new ArrayList<Integer>(tmp);
				tmpRemove.retainAll(visiblePositionsXYZ);//Nombres repetits.
				tmp.removeAll(tmpRemove);
				visiblePositionsXYZ.addAll(tmp);//Unio rows amb cols.
				tmp = new ArrayList<Integer>(blockPositions.get(blockCell(pXYZ)));
				tmpRemove = new ArrayList<Integer>(tmp);
				tmpRemove.retainAll(visiblePositionsXYZ);//Nombres repetits.
				tmp.removeAll(tmpRemove);
				visiblePositionsXYZ.addAll(tmp);//Unio rows, cols i blocks sense repetits.
				int visXYZsize = visiblePositionsXYZ.size();
				for(int xz = 0; xz < visXYZsize; ++xz){
					int pXZ = visiblePositionsXYZ.get(xz);
					if(pXYZ != pXZ){
						int numsPossibleXZ = numsPosCell.get(pXZ);
						if(Integer.bitCount(numsPossibleXZ) == 2 && Integer.bitCount(numsPossibleXYZ & numsPossibleXZ) == 2){
							for(int yz = 0; yz < visXYZsize; ++yz){
								if(yz != xz){
									int pYZ = visiblePositionsXYZ.get(yz);
									if(pYZ != pXYZ){
										int numsPossibleYZ = numsPosCell.get(pYZ);
										if(Integer.bitCount(numsPossibleXYZ & numsPossibleYZ) == 2
											&&Integer.bitCount(numsPossibleXZ & numsPossibleYZ) == 1
											&& Integer.bitCount(numsPossibleXYZ | numsPossibleXZ | numsPossibleYZ) == 3){
											boolean xyzw = xyzWing(pXYZ, pXZ, pYZ);
											changes = changes || xyzw;
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return changes;
	}

	/**
	* Metode de busca utilitzant
	* diferents tecniques de nivell
	* mitja sobre totes les caselles
	* del sudoku que quedin per omplir.
	* @return boolean si hi ha hagut 
	* algun canvi o no.
	*/
	private boolean mediumSearch(){
		boolean changes = false;
		boolean xw = xWingSearch();
		boolean sf = swordFishSearch();
		boolean yw = yWingSearch();
		boolean xyz = xyzWingSearch();
		changes = changes || xw || sf || yw || xyz;
		return changes;
	}

	/**
	* Metode que utilitza tecniques
	* de nivell mitja per a 
	* intentar resoldre un sudoku.
	*/
	private void medium(){
		boolean changes;
		do{
			changes = mediumSearch();
			if(changes){
				easy();
			}
		}while(changes);
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//Metode search all:

	/**
	* Metode per trobar el
	* nivell del sudoku.
	* @int nivell: 0-facil,
	* 1-mitja, 2 dificil.
	*/
	public int searchLevel(){
		clearIsolatedCells();
		easy();
		if(found()) return 0;
		medium();
		if(found()) return 1;
		return 2;
	}

	/**
	* Metode per trobar si un
	* sudoku es de nivell facil.
	*/
	public boolean isItEasy(){
		clearIsolatedCells();
		easy();
		if(found()) return true;
		return false;
	}

	/**
	* Metode per trobar si un
	* sudoku es de nivell mitja
	* o menor.
	*/
	public boolean isItMedium(){
		clearIsolatedCells();
		easy();
		medium();
		if(found()) return true;
		return false;
	}

	/**
	* Metode per trobar si un
	* sudoku es de nivell mitja.
	*/
	public boolean isARealMedium(){
		clearIsolatedCells();
		easy();
		if(found()) return false;
		medium();
		if(found()) return true;
		return false;
	}

	/**
	* Metode per trobar si un
	* sudoku es de nivell dificil.
	*/
	public boolean isARealDifficult(){
		clearIsolatedCells();
		easy();
		if(found()) return false;
		medium();
		if(found()) return false;
		return true;
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//Altres metodes:

	/**
	* Metode que retorna
	* el Board actual amb els
	* nombres que hagi trobat.
	*/
	public Board actualBoard() throws Exception {
		Board act = new Board(size);
		for(int p = 0; p < positions; ++p){
			act.setCellValue(posICell(p), posJCell(p), decodeNum(numsPosCell.get(p)));
		}
		return act;
	}


}
