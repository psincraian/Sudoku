package propias.dominio.clases;

import java.lang.Integer;
import java.lang.Math;
import java.util.List;
import java.util.ArrayList;
import propias.dominio.clases.Board;
import propias.dominio.clases.BoardIO;

/**
*CLASSE EN FASE DE CONSTRUCCIO!
* -De moment nomes te implementades funcions que simulen tactiques facils de resoldre sudokus.
* -Conte Xivatos i falta fer agregació de classes desglosades per a comprovar el seu correcte funcionament.
*
*@autor Adrián Sánchez Albanell.
*
*/
public class LevelSearch {

	/* Mascara en base 2, 1 -> possible, 0 -> no */
	//Possible numbers in rows/columns/block
	private List<Integer> numsPosRows;
	private List<Integer> numsPosColumns;
	private List<Integer> numsPosBlocks;

	//Possible numbers in a Cell 
	private List<Integer> numsPosCell;
	private List<Boolean> foundCell;

	private int size;
	private int sqrtSize;
	private int mask;//maskara amb tots els bits a 1 fins al bit numero size-1.
	private Board sudoku;
	private int level;//nivell de dificultat.

	private boolean found(){
		for(int p = 0 ; p < size; ++p){
			if(Integer.bitCount(numsPosCell.get(p)) != 1) return false;
		}
		return true;
	}

	//Code i decode.
	private double log2(int num){
		return Math.log(num)/Math.log(2);
	}

	/*
		codifica els nombres de tal manera que
		1 = 000000001, 2 = 000000010, etc.
	*/
	private int codeNum(int num){
		return 1 << (num-1);
	}

	private int decodeNum(int num){
		return (int)log2(num) + 1;
	}

	private void decode() throws Exception {
		for(int p = 0; p < size*size; ++p){
			int codedNum = numsPosCell.get(p);
			if(Integer.bitCount(codedNum) == 1) sudoku.setCellValue(posiCell(p), posjCell(p), decodeNum(codedNum));
		}
	}

	//conversors de posicio (p -> i, j; i, j -> p; i, j -> block)
	private int blockNum(int i, int j){
		return sqrtSize*(i/sqrtSize) + j/sqrtSize;
	}

	private int posCell(int i, int j){
		return i*size + j;
	}
	private int posiCell(int pos){
		return pos/size;
	}

	private int posjCell(int pos){
		return pos%size;
	}

	//get, set, actualitzar nombres..
	/*
		retorna els nombres possibles sense num (possible i num codificats).
	*/
	public int delNum(int possible, int num){
		return possible & ~num;
	}

	/*
		Actualitza els possibles de la casella a i j amb numsMask (mascara amb els nums possibles).
	*/
	private void actPosCellMask(int i, int j, int numsMask){
		int numsPos = numsPosCell.get(posCell(i, j)) & numsMask;
	}

	private void actPosCellRCB(int i, int j){
		int numsPos = numsPosCell.get(posCell(i, j)) & numsPosRows.get(i) 
					& numsPosColumns.get(j) & numsPosBlocks.get(blockNum(i, j));
		numsPosCell.set(posCell(i, j), numsPos);
	}

	/*
		Metode per actualitzar les variables globals:
		numsPosRows, numsPosColumns, numsPosBlocks eliminant els nombres
		possibles en la casella a posi-posj dels seus elements corresponents.
	*/
	private void actVarGLobPos(int posi, int posj){
		System.out.println("actVarGlobPos");
		int numsPos, block, nums;		
		nums = numsPosCell.get(posCell(posi, posj));
		numsPos = numsPosRows.get(posi);
		numsPosRows.set(posi, delNum(numsPos, nums));
		numsPos = numsPosColumns.get(posj);
		numsPosColumns.set(posj, delNum(numsPos, nums));
		block = blockNum(posi, posj);
		numsPos = numsPosBlocks.get(block);
		numsPosBlocks.set(block, delNum(numsPos, nums));
	}

	private void printPosNums(){
			System.out.println();
			for(int i = 0; i < size; ++i){
				for(int j = 0; j < size; ++j){
					System.out.print(" " + numsPosCell.get(posCell(i, j)) + " ");
					//System.out.print(" " + foundCell.get(posCell(i, j)) + " ");
				}
				System.out.println();
			}	
	}

	private void searchError(){
		boolean changes = true;
		while(changes){
			changes = false;
			for(int p = 0; p < size*size; ++p){
				if(!foundCell.get(p)){
					if(Integer.bitCount(numsPosCell.get(p)) == 0){
							printPosNums();
							System.exit(1);
					}
				}
			}			
		}
	}

	//Metodes resoldre per a diferents nivells.
	/*
		buscar isolated cells/tambe serveix per actualitzar al taulell sencer les caselles amb solucio unica.
	*/
	private boolean isolatedCells(){
		boolean changes = true;
		while(changes){
			changes = false;
			for(int p = 0; p < size*size; ++p){
				if(!foundCell.get(p)){
					actPosCellRCB(posiCell(p), posjCell(p));
					if(Integer.bitCount(numsPosCell.get(p)) == 1){
							actVarGLobPos(posiCell(p), posjCell(p));
							foundCell.set(p, true);
							changes = true;
					}
				}
			}							
					searchError();
		}
		
		return changes;
	}

	private boolean hidenIsolatedCells(int i, int j){
		
		int numR = 0;
		int numC = 0;
		int numB = 0;
		int iBlockIni = sqrtSize*(i/sqrtSize);
		int jBlockIni = sqrtSize*(j/sqrtSize);
		int iBlock, jBlock;
		for(int a = 0; a < size; ++a){
			if(a != j) numR = numR | numsPosCell.get(posCell(i, a));
			if(a != i) numC = numC | numsPosCell.get(posCell(a, j));
			iBlock = iBlockIni + a/sqrtSize;
			jBlock = jBlockIni + a%sqrtSize;
			if(iBlock != i || jBlock != j) numB = numB | numsPosCell.get(posCell(iBlock, jBlock));
		}

		int numA = numsPosCell.get(posCell(i, j));
		numR = numA & ~numR;
		if(Integer.bitCount(numR) == 1){
			numsPosCell.set(posCell(i, j), numR);
			isolatedCells();//Actualitzar tauler sencer.
			return true;
		}
		numC = numA & ~numC;
		if(Integer.bitCount(numC) == 1){
			numsPosCell.set(posCell(i, j), numC);
			isolatedCells();//Actualitzar tauler sencer.
			return true;
		}
		numB = numA & ~numB;
		if(Integer.bitCount(numB) == 1){
			numsPosCell.set(posCell(i, j), numB);
			isolatedCells();//Actualitzar tauler sencer.
			return true;
		}

		return false;

	}

	/*!! NO ESTA FETA!!*/
	private boolean tripleSearch(int i, int j, int b, int c){
		boolean changes = false;
		

		return changes;
	}


	/*!! NO ESTA FETA!!*/
	private boolean quadSearch(int i, int j, int b, int c, int d){
		boolean changes = false;

		return changes;
	}

	private boolean recursiveHidenIsolatedCellsR(int p){
		int num = 0;
		int i = posiCell(p);
		int j = posjCell(p);
		for(int a = 0; a < size; ++a){
			if(a != j) num = num | numsPosCell.get(posCell(i, a));
		}
		int numA = numsPosCell.get(p);
		num = numA & ~num;
		if(Integer.bitCount(num) == 1){
			numsPosCell.set(p, num);
			return true;
		}
		return false;
	}

	private boolean recursiveHidenIsolatedCellsC(int p){
		int num = 0;
		int i = posiCell(p);
		int j = posjCell(p);
		for(int a = 0; a < size; ++a){
			if(a != i) num = num | numsPosCell.get(posCell(a, j));
		}
		int numA = numsPosCell.get(p);
		num = numA & ~num;
		if(Integer.bitCount(num) == 1){
			numsPosCell.set(p, num);
			return true;
		}
		return false;
	}

	private boolean recursiveHidenIsolatedCellsB(int p){
		int num = 0;
		int i = posiCell(p);
		int j = posjCell(p);
		int iBlockIni = sqrtSize*(i/sqrtSize);
		int jBlockIni = sqrtSize*(j/sqrtSize);
		int iBlock, jBlock;
		for(int a = 0; a < size; ++a){
			iBlock = iBlockIni + a/sqrtSize;
			jBlock = jBlockIni + a%sqrtSize;
			if(iBlock != i || jBlock != j) num = num | numsPosCell.get(posCell(iBlock, jBlock));
		}
		int numA = numsPosCell.get(p);
		num = numA & ~num;
		if(Integer.bitCount(num) == 1){
			numsPosCell.set(p, num);
			return true;
		}
		return false;
	}

	private boolean recursiveHidenIsolatedCells(int p){
		if(recursiveHidenIsolatedCellsB(p) || recursiveHidenIsolatedCellsR(p) 
											|| recursiveHidenIsolatedCellsC(p)) return true;
		return false || recursivePairSearch(p);
	}

	private boolean recursivePairSearchR(int p, int b){
		int i = posiCell(p);
		int j = posjCell(p);
		int jb = j + b;
		if(jb >= size) return false;
		if(foundCell.get(p + b)) return recursivePairSearchR(p, b+1);
		int cellA = numsPosCell.get(p);
		int cellB = numsPosCell.get(p + b);
		//Naked pair
		if(Integer.bitCount(cellA) == 2 && Integer.bitCount(cellB) == 2 && cellA == cellB){
			int tmpMask = ~cellA;
			int num, tmpComp;
			boolean changes = false;
			for(int a = 0; a < size; ++a){
				if(a != j && a != jb){
					tmpComp = numsPosCell.get(posCell(i, a));
					num = numsPosCell.get(posCell(i, a)) & tmpMask;
					if(tmpComp != num){						
						numsPosCell.set(posCell(i, a), num);
						changes = true;
					} 
				}
			}
			return changes;
		}
		//Hiden pair
		else{//Si les dos tenen exactament dos nombres possibles... on esta el "hiden" del pair? x'D
			int pre = cellA & cellB;
			if( Integer.bitCount(pre) >= 2){
				int num = 0;
				for(int a = 0; a < size; ++a){
					if(a != j && a != jb){
						num = num | numsPosCell.get(posCell(i, a));
					}
				}
				num = pre & ~num;
				if(Integer.bitCount(num) == 2){					
					numsPosCell.set(p, num);
					numsPosCell.set((p + b), num);
					return true;
				}
			}
		}
		return recursivePairSearchR(p, b+1);
	}

	private boolean recursivePairSearchC(int p, int b){
		int i = posiCell(p);
		int ib = i + b;
		int j = posjCell(p);
		if(ib >= size) return false;
		if(foundCell.get(p + b*size)) return recursivePairSearchC(p, b+1);
		int cellA = numsPosCell.get(p);
		int cellB = numsPosCell.get(p + b*size);
		//Naked pair.
		if(Integer.bitCount(cellA) == 2 && Integer.bitCount(cellB) == 2 && cellA == cellB){
			int tmpMask = ~cellA;			
			int num, tmpComp;
			boolean changes = false;
			for(int a = 0; a < size; ++a){
				if(a != i && a != ib){
					tmpComp = numsPosCell.get(posCell(a, j));
					num = tmpComp & tmpMask;
					if(tmpComp != num){
						numsPosCell.set(posCell(a, j), num);
						changes = true;//Si no ha canviat res.. doncs no hi ha hagut canvis =.= (si no pensara que si i entrara en bucle infinit).						
					} 
				}
			}
			return changes;
		}
		//Hiden pair.
		else{
			int pre = cellA & cellB;
			if( Integer.bitCount(pre) >= 2){
				int num = 0;
				for(int a = 0; a < size; ++a){
					if(a != i && a != ib){
						num = num | numsPosCell.get(posCell(a, j));
					}
				}
				num = pre & ~num;
				if(Integer.bitCount(num) == 2){//No cal comprovar si hi ha hagut canvis, ja que s'acaben de convertir en "naked", si no haguessin canviat hauria saltat el "if".
					numsPosCell.set(p, num);
					numsPosCell.set((p + b*size), num);
					return true;
				}
			}
		}
		return recursivePairSearchC(p, b+1);
	}

	private boolean recursivePairSearchB(int p, int b){
		int i = posiCell(p);
		int j = posjCell(p);
		int iBlockIni = sqrtSize*(i/sqrtSize);
		int jBlockIni = sqrtSize*(j/sqrtSize);		
		int pB = (i - iBlockIni)*sqrtSize + (j - jBlockIni);
		int bB = pB + b;
		if(bB == size) return false;		
		int ib = iBlockIni + bB/sqrtSize;
		int jb = jBlockIni + bB%sqrtSize;		
		if(foundCell.get(posCell(ib, jb))) return recursivePairSearchB(p, b+1);
		int cellA = numsPosCell.get(p);
		int cellB = numsPosCell.get(posCell(ib, jb));
		//Naked pair.
		if(Integer.bitCount(cellA) == 2 && Integer.bitCount(cellB) == 2 && cellA == cellB){
			int tmpMask = ~cellA;
			int num, iBlock, jBlock, tmpComp;
			boolean changes = false;
			for(int a = 0; a < size; ++a){				
				if(a != pB && a != bB){
					iBlock = iBlockIni + a/sqrtSize;
					jBlock = jBlockIni + a%sqrtSize;
					tmpComp = numsPosCell.get(posCell(iBlock, jBlock));
					num = tmpComp & tmpMask;
					if(tmpComp != num){
						numsPosCell.set(posCell(iBlock, jBlock), num);
						changes = true;
					}
				}
			}
			return changes;
		}		
		//Hiden pair.
		else{
			int pre = cellA & cellB;
			if( Integer.bitCount(pre) >= 2){
				int num = 0;
				int iBlock, jBlock;
				for(int a = 0; a < size; ++a){					
					if(a != pB && a != bB){
						iBlock = iBlockIni + a/sqrtSize;
						jBlock = jBlockIni + a%sqrtSize;
						num = num | numsPosCell.get(posCell(iBlock, jBlock));
					}
				}
				num = pre & ~num;
				if(Integer.bitCount(num) == 2){
					numsPosCell.set(p, num);
					numsPosCell.set(posCell(ib, jb), num);
					return true;
				}
			}
		}		
		return recursivePairSearchB(p, b+1);
	}
	
	private boolean recursivePairSearch(int p){
		boolean changes = false;
		boolean recursiveB = recursivePairSearchB(p, 1);
		boolean recursiveR = recursivePairSearchR(p, 1);
		boolean recursiveC = recursivePairSearchC(p, 1);
		if(recursiveR || recursiveC || recursiveB) changes = true;
		changes = changes  || recursiveTripleSearch(p);
		return changes;
	}

	private boolean recursiveTripleSearchR(int p, int b, int c){
		int i = posiCell(p);
		int j = posjCell(p);
		int jb = j + b;
		int jc = j + c;
		if(jb == size) return false;
		if(jc == size) return recursiveTripleSearchR(p, b+1, b+2);
		if(foundCell.get(p + b)) return recursiveTripleSearchR(p, b+1, b+2);
		if(foundCell.get(p + c)) return recursiveTripleSearchR(p, b, c+1);
		int cellA = numsPosCell.get(p);
		int cellB = numsPosCell.get(p + b);
		int cellC = numsPosCell.get(p + c);
		//Naked triple.
		if((Integer.bitCount(cellA | cellB | cellC)) == 3){
			int tmpMask = ~(cellA | cellB | cellC);
			int num, tmpComp;
			boolean changes = false;
			for(int a = 0; a < size; ++a){
				if(a != j && a != jb && a != jc){
					tmpComp = numsPosCell.get(posCell(i, a));
					num = numsPosCell.get(posCell(i, a)) & tmpMask;
					if(tmpComp != num){
						numsPosCell.set(posCell(i, a), num);
						changes = true;
					}
				}
			}
			return changes;
		}
		//Hiden triple.
		else{
			int pre = cellA & (cellB | cellC) | cellB & cellC;
			if( Integer.bitCount(pre) >= 3){
				int num = 0;
				for(int a = 0; a < size; ++a){
					if(a != j && a != jb && a != jc){
						num = num | numsPosCell.get(posCell(i, a));
					}
				}
				num = pre & ~num;
				if(Integer.bitCount(num) == 3){
					boolean changes = false;
					if(Integer.bitCount(num & cellA) < Integer.bitCount(cellA)){
						numsPosCell.set(p, (num & cellA));
						changes = true;
					}
					if(Integer.bitCount(num & cellB) < Integer.bitCount(cellB)){
						numsPosCell.set((p + b), (num & cellB));
						changes = true;
					}
					if(Integer.bitCount(num & cellC) < Integer.bitCount(cellC)){
						numsPosCell.set((p + c), (num & cellC));
						changes = true;
					}
					return changes;
				}
			}
		}
		return recursiveTripleSearchR(p, b, c+1);
	}

	private boolean recursiveTripleSearchC(int p, int b, int c){
		int i = posiCell(p);
		int j = posjCell(p);
		int ib = i + b;
		int ic = i + c;
		if(ib == size) return false;
		if(ic == size) return recursiveTripleSearchC(p, b+1, b+2);
		if(foundCell.get(p + b*size)) return recursiveTripleSearchC(p, b+1, b+2);
		if(foundCell.get(p + c*size)) return recursiveTripleSearchC(p, b, c+1);
		int cellA = numsPosCell.get(p);
		int cellB = numsPosCell.get(p + b*size);
		int cellC = numsPosCell.get(p + c*size);
		//Naked triple.
		if(Integer.bitCount(cellA | cellB | cellC) == 3){
			int tmpMask = ~(cellA | cellB | cellC);
			int num, tmpComp;
			boolean changes = false;
			for(int a = 0; a < size; ++a){
				if(a != i && a != ib && a != ic){
					tmpComp = numsPosCell.get(posCell(a, j));
					num = numsPosCell.get(posCell(a, j)) & tmpMask;
					if(tmpComp != num){
						numsPosCell.set(posCell(a, j), num);
						changes = true;
					}
				}
			}
			return changes;
		}
		//Hiden triple.
		else{
			int pre = cellA & (cellB | cellC) | cellB & cellC;
			if( Integer.bitCount(pre) >= 3){
				int num = 0;
				for(int a = 0; a < size; ++a){
					if(a != i && a != ib && a != ic){
						num = num | numsPosCell.get(posCell(a, j));
					}
				}
				num = pre & ~num;
				if(Integer.bitCount(num) == 3){
					boolean changes = false;
					if(Integer.bitCount(num & cellA) < Integer.bitCount(cellA)){
						numsPosCell.set(p, (num & cellA));
						changes = true;
					}
					if(Integer.bitCount(num & cellB) < Integer.bitCount(cellB)){
						numsPosCell.set((p + b*size), (num & cellB));
						changes = true;
					}
					if(Integer.bitCount(num & cellC) < Integer.bitCount(cellC)){
						numsPosCell.set((p + c*size), (num & cellC));
						changes = true;
					}
					return changes;
				}
			}
		}
		return recursiveTripleSearchC(p, b, c+1);
	}

	private boolean recursiveTripleSearchB(int p, int b, int c){
		int i = posiCell(p);
		int j = posjCell(p);
		int iBlockIni = sqrtSize*(i/sqrtSize);
		int jBlockIni = sqrtSize*(j/sqrtSize);	
		int pB = (i - iBlockIni)*sqrtSize + (j - jBlockIni);
		int bB = pB + b;
		if(bB == size) return false;	
		int ib = iBlockIni + bB/sqrtSize;
		int jb = jBlockIni + bB%sqrtSize;
		int cB = pB + c;
		if(cB == size) return recursiveTripleSearchB(p, b+1, b+2);
		int ic = iBlockIni + cB/sqrtSize;
		int jc = jBlockIni + cB%sqrtSize;
		if(foundCell.get(posCell(ib, jb))) return recursiveTripleSearchB(p, b+1, b+2);
		if(foundCell.get(posCell(ic, jc))) return recursiveTripleSearchB(p, b, c+1);
		int cellA = numsPosCell.get(p);
		int cellB = numsPosCell.get(posCell(ib, jb));
		int cellC = numsPosCell.get(posCell(ic, jc));
		//Naked triple.
		if((Integer.bitCount(cellA | cellB | cellC)) == 3){
			int tmpMask = ~(cellA | cellB | cellC);
			int num, iBlock, jBlock, tmpComp;
			boolean changes = false;
			for(int a = 0; a < size; ++a){
				if(a != pB && a != bB && a != cB){
					iBlock = iBlockIni + a/sqrtSize;
					jBlock = jBlockIni + a%sqrtSize;
					tmpComp = numsPosCell.get(posCell(iBlock, jBlock));
					num = numsPosCell.get(posCell(iBlock, jBlock)) & tmpMask;
					if(tmpComp != num){
						numsPosCell.set(posCell(iBlock, jBlock), num);
						changes = true;
					}
				}
			}
			return changes;
		}
		//Hiden triple.
		else{
			int pre = cellA & (cellB | cellC) | cellB & cellC;
			if( Integer.bitCount(pre) >= 3){
				int num = 0;
				int iBlock, jBlock;
				for(int a = 0; a < size; ++a){
					if(a != pB && a != bB && a != cB){
						iBlock = iBlockIni + a/sqrtSize;
						jBlock = jBlockIni + a%sqrtSize;
						num = num | numsPosCell.get(posCell(iBlock, jBlock));
					}
				}
				num = pre & ~num;
				if(Integer.bitCount(num) == 3){
					boolean changes = false;
					if(Integer.bitCount(num & cellA) < Integer.bitCount(cellA)){
						numsPosCell.set(p, (num & cellA));
						changes = true;
					}
					if(Integer.bitCount(num & cellB) < Integer.bitCount(cellB)){
						numsPosCell.set(posCell(ib, jb), (num & cellB));
						changes = true;
					}
					if(Integer.bitCount(num & cellC) < Integer.bitCount(cellC)){
						numsPosCell.set(posCell(ic, jc), (num & cellC));
						changes = true;
					}
					return changes;
				}
			}
		}
		return recursiveTripleSearchB(p, b, c+1);
	}

	private boolean recursiveTripleSearch(int p){
		boolean changes = false;
		boolean recursiveB = recursiveTripleSearchB(p, 1, 2);
		boolean recursiveR = recursiveTripleSearchR(p, 1, 2);
		boolean recursiveC = recursiveTripleSearchC(p, 1, 2);
		if(recursiveR || recursiveC || recursiveB) changes = true;
		return changes || recursiveQuadSearch(p);
	}

	private boolean recursiveQuadSearchR(int p, int b, int c, int d){
		int i = posiCell(p);
		int j = posjCell(p);
		int jb = j + b;
		int jc = j + c;
		int jd = j + d;
		if(jc == size || jb == size) return false;
		if(jd == size) return recursiveQuadSearchR(p, b+1, b+2, b+3);
		if(foundCell.get(p + b)) return recursiveQuadSearchR(p, b+1, b+2, b+3);
		if(foundCell.get(p + c)) return recursiveQuadSearchR(p, b, c+1, c+2);
		if(foundCell.get(p + d)) return recursiveQuadSearchR(p, b, c, d+1);
		int cellA = numsPosCell.get(p);
		int cellB = numsPosCell.get(p + b);
		int cellC = numsPosCell.get(p + c);
		int cellD = numsPosCell.get(p + d);
		//Naked quad.
		if((Integer.bitCount(cellA | cellB | cellC | cellD)) == 4){
			int tmpMask = ~(cellA | cellB | cellC | cellD);
			int num, tmpComp;
			boolean changes = false;
			for(int a = 0; a < size; ++a){
				if(a != j && a != jb && a != jc && a != jd){
					tmpComp = numsPosCell.get(posCell(i, a));
					num = numsPosCell.get(posCell(i, a)) & tmpMask;
					if(tmpComp != num){
						numsPosCell.set(posCell(i, a), num);
						changes = true;
					}
				}
			}
			return changes;
		}
		//Hiden quad.
		else{
			int pre = cellA & (cellB | cellC | cellD) | cellB & (cellC | cellD) | cellC & cellD;
			if( Integer.bitCount(pre) >= 4){
				int num = 0;
				for(int a = 0; a < size; ++a){
					if(a != j && a != jb && a != jc && a != jd){
						num = num | numsPosCell.get(posCell(i, a));
					}
				}
				num = pre & ~num;
				if(Integer.bitCount(num) == 4){
					boolean changes = false;
					if(Integer.bitCount(num & cellA) < Integer.bitCount(cellA)){
						numsPosCell.set(p, (num & cellA));
						changes = true;
					}
					if(Integer.bitCount(num & cellB) < Integer.bitCount(cellB)){
						numsPosCell.set((p + b), (num & cellB));
						changes = true;
					}
					if(Integer.bitCount(num & cellC) < Integer.bitCount(cellC)){
						numsPosCell.set((p + c), (num & cellC));
						changes = true;
					}
					if(Integer.bitCount(num & cellD) < Integer.bitCount(cellD)){
						numsPosCell.set((p + d), (num & cellD));
						changes = true;
					}
					return changes;
				}
			}
		}
		return recursiveQuadSearchR(p, b, c, d+1);
	}

	private boolean recursiveQuadSearchC(int p, int b, int c, int d){
		int i = posiCell(p);
		int j = posjCell(p);
		int ib = i + b;
		int ic = i + c;
		int id = i + d;
		if(ib == size || ic == size) return false;
		if(id == size) return recursiveQuadSearchC(p, b+1, b+2, b+3);
		if(foundCell.get(p + b*size)) return recursiveQuadSearchC(p, b+1, b+2, b+3);
		if(foundCell.get(p + c*size)) return recursiveQuadSearchC(p, b, c+1, c+2);
		if(foundCell.get(p + d*size)) return recursiveQuadSearchC(p, b, c, d+1);
		int cellA = numsPosCell.get(p);
		int cellB = numsPosCell.get(p + b*size);
		int cellC = numsPosCell.get(p + c*size);
		int cellD = numsPosCell.get(p + d*size);
		//Naked quad.
		if(Integer.bitCount(cellA | cellB | cellC | cellD) == 4){
			int tmpMask = ~(cellA | cellB | cellC | cellD);
			int num, tmpComp;
			boolean changes = false;
			for(int a = 0; a < size; ++a){
				if(a != i && a != ib && a != ic && a != id){
					tmpComp = numsPosCell.get(posCell(a, j));
					num = numsPosCell.get(posCell(a, j)) & tmpMask;
					if(tmpComp != num){
						numsPosCell.set(posCell(a, j), num);
						changes = true;
					}
				}
			}
			return changes;
		}
		//Hiden quad.
		else{
			int pre = cellA & (cellB | cellC | cellD) | cellB & (cellC | cellD) | cellC & cellD;
			if( Integer.bitCount(pre) >= 4){
				int num = 0;
				for(int a = 0; a < size; ++a){
					if(a != i && a != ib && a != ic && a != id){
						num = num | numsPosCell.get(posCell(a, j));
					}
				}
				num = pre & ~num;
				if(Integer.bitCount(num) == 4){
					boolean changes = false;
					if(Integer.bitCount(num & cellA) < Integer.bitCount(cellA)){
						numsPosCell.set(p, (num & cellA));
						changes = true;
					}
					if(Integer.bitCount(num & cellB) < Integer.bitCount(cellB)){
						numsPosCell.set((p + b*size), (num & cellB));
						changes = true;
					}
					if(Integer.bitCount(num & cellC) < Integer.bitCount(cellC)){
						numsPosCell.set((p + c*size), (num & cellC));
						changes = true;
					}
					if(Integer.bitCount(num & cellD) < Integer.bitCount(cellD)){
						numsPosCell.set((p + d*size), (num & cellD));
						changes = true;
					}
					return changes;
				}
			}
		}
		return recursiveQuadSearchC(p, b, c, d+1);
	}

	private boolean recursiveQuadSearchB(int p, int b, int c, int d){
		int i = posiCell(p);
		int j = posjCell(p);
		int iBlockIni = sqrtSize*(i/sqrtSize);
		int jBlockIni = sqrtSize*(j/sqrtSize);	
		int pB = (i - iBlockIni)*sqrtSize + (j - jBlockIni);
		int bB = pB + b;
		int cB = pB + c;
		if(bB == size || cB == size) return false;	
		int dB = pB + d;
		if(dB == size) return recursiveQuadSearchB(p, b+1, b+2, b+3);
		int ib = iBlockIni + bB/sqrtSize;
		int jb = jBlockIni + bB%sqrtSize;	
		if(foundCell.get(posCell(ib, jb))) return recursiveQuadSearchB(p, b+1, b+2, b+3);
		int ic = iBlockIni + cB/sqrtSize;
		int jc = jBlockIni + cB%sqrtSize;	
		if(foundCell.get(posCell(ic, jc))) return recursiveQuadSearchB(p, b, c+1, c+2);
		int id = iBlockIni + dB/sqrtSize;
		int jd = jBlockIni + dB%sqrtSize;
		if(foundCell.get(posCell(id, jd))) return recursiveQuadSearchB(p, b, c, d+1);
		int cellA = numsPosCell.get(p);
		int cellB = numsPosCell.get(posCell(ib, jb));
		int cellC = numsPosCell.get(posCell(ic, jc));
		int cellD = numsPosCell.get(posCell(id, jd));
		//Naked quad.
		if((Integer.bitCount(cellA | cellB | cellC | cellD)) == 4){
			int tmpMask = ~(cellA | cellB | cellC | cellD);
			int num, iBlock, jBlock, tmpComp;
			boolean changes = false;
			for(int a = 0; a < size; ++a){
				if(a != pB && a != bB && a != cB && a != dB){
					iBlock = iBlockIni + a/sqrtSize;
					jBlock = jBlockIni + a%sqrtSize;
					tmpComp = numsPosCell.get(posCell(iBlock, jBlock));
					num = tmpComp & tmpMask;
					if(tmpComp != num){
						numsPosCell.set(posCell(iBlock, jBlock), num);
						changes = true;
					}
				}
			}
			return changes;
		}
		//Hiden quad.
		else{
			int pre = cellA & (cellB | cellC | cellD) | cellB & (cellC | cellD) | cellC & cellD;
			if( Integer.bitCount(pre) >= 4){
				int num = 0;
				int iBlock, jBlock;
				for(int a = 0; a < size; ++a){
					if(a != pB && a != bB && a != cB && dB != a){
						iBlock = iBlockIni + a/sqrtSize;
						jBlock = jBlockIni + a%sqrtSize;
						num = num | numsPosCell.get(posCell(iBlock, jBlock));
					}
				}
				num = pre & ~num;
				if(Integer.bitCount(num) == 4){
					boolean changes = false;
					if(Integer.bitCount(num & cellA) < Integer.bitCount(cellA)){
						numsPosCell.set(p, (num & cellA));
						changes = true;
					}
					if(Integer.bitCount(num & cellB) < Integer.bitCount(cellB)){
						numsPosCell.set(posCell(ib, jb), (num & cellB));
						changes = true;
					}
					if(Integer.bitCount(num & cellC) < Integer.bitCount(cellC)){
						numsPosCell.set(posCell(ic, jc), (num & cellC));
						changes = true;
					}
					if(Integer.bitCount(num & cellD) < Integer.bitCount(cellD)){
						numsPosCell.set(posCell(id, jd), (num & cellD));
						changes = true;
					}
					return changes;
				}
			}
		}
		return recursiveQuadSearchB(p, b, c, d+1);
	}

	private boolean recursiveQuadSearch(int p){
		boolean changes = false;
		boolean recursiveB = recursiveQuadSearchB(p, 1, 2, 3);
		boolean recursiveR = recursiveQuadSearchR(p, 1, 2, 3);
		boolean recursiveC = recursiveQuadSearchC(p, 1, 2, 3);
		if(recursiveR || recursiveC || recursiveB) return true;
		return false;
	}

	private boolean recursiveIntersectionSearchR(int i){
		boolean changes = false;
		int numsB[] = new int[sqrtSize];
		for(int b = 0; b < sqrtSize; ++b){
			for(int j = 0; j < sqrtSize; ++j){
				numsB[b] = numsB[b] | numsPosCell.get(posCell(i, (j + b*sqrtSize)));				
			}
		}
		for(int b = 0; b < sqrtSize; ++b){			
			int pre = numsB[b];
			int num = 0;
			for(int r = 0; r < sqrtSize; ++r){
				if(r != b){
					num = num | numsB[r];
				}
			}
			num = pre & ~num;
			if(Integer.bitCount(num) > 0){
				num = ~num;	
				int iBlockIni = sqrtSize*(i/sqrtSize);
				int jBlockIni = b*sqrtSize;
				int numsCell, tmp;
				for(int iB = 0; iB < sqrtSize; ++iB){
					if(iB + iBlockIni != i){
						for(int jB = 0; jB < sqrtSize; ++jB){
							numsCell = numsPosCell.get(posCell(iB + iBlockIni, jB + jBlockIni));
							tmp = numsCell & num;
							if(numsCell != tmp){
								numsPosCell.set(posCell(iB + iBlockIni, jB + jBlockIni), tmp);
								changes = true;
							}
						}
					}
				}
			}
		}		
		return changes;
	}

	private boolean recursiveIntersectionSearchC(int j){
		boolean changes = false;
		int numsB[] = new int[sqrtSize];
		for(int b = 0; b < sqrtSize; ++b){
			for(int i = 0; i < sqrtSize; ++i){
				numsB[b] = numsB[b] | numsPosCell.get(posCell((i + b*sqrtSize), j));		
			}
		}
		for(int b = 0; b < sqrtSize; ++b){			
			int pre = numsB[b];
			int num = 0;
			for(int r = 0; r < sqrtSize; ++r){
				if(r != b){
					num = num | numsB[r];
				}
			}
			num = pre & ~num;
			if(Integer.bitCount(num) > 0){
				num = ~num;	
				int iBlockIni = b*sqrtSize;
				int jBlockIni = sqrtSize*(j/sqrtSize);
				int numsCell, tmp;
				for(int jB = 0; jB < sqrtSize; ++jB){
					if(jB + jBlockIni != j){
						for(int iB = 0; iB < sqrtSize; ++iB){
							numsCell = numsPosCell.get(posCell(iB + iBlockIni, jB + jBlockIni));
							tmp = numsCell & num;
							if(numsCell != tmp){
								numsPosCell.set(posCell(iB + iBlockIni, jB + jBlockIni), tmp);
								changes = true;
							}
						}
					}
				}
			}
		}
		return changes;
	}

	private boolean recursiveIntersectionSearchBR(int b){
		boolean changes = false;
		int numsB[] = new int[sqrtSize];
		int iBlockIni = sqrtSize*(b/sqrtSize);
		int jBlockIni = sqrtSize*(b%sqrtSize);
		for(int i = 0; i < sqrtSize; ++i){
			for(int j = 0; j < sqrtSize; ++j){
				numsB[i] = numsB[i] | numsPosCell.get(posCell(i + iBlockIni, j + jBlockIni));
			}
		}
		for(int i = 0; i < sqrtSize; ++i){
			int pre = numsB[i];
			int num = 0;
			for(int r = 0; r < sqrtSize; ++r){
				if(r != i){
					num = num | numsB[r];
				}
			}
			num = pre & ~num;
			if(Integer.bitCount(num) > 0){
				num = ~num;	
				int numsCell, tmp;
				for(int j = 0; j < size; ++j){
					if(j/sqrtSize != b%sqrtSize){
						numsCell = numsPosCell.get(posCell(i + iBlockIni, j));
						tmp = numsCell & num;
						if(numsCell != tmp){
						numsPosCell.set(posCell(i + iBlockIni, j), tmp);
						changes = true;
						}
					}
				}
			}
		}
		return changes;
	}

	private boolean recursiveIntersectionSearchBC(int b){
		boolean changes = false;
		int numsB[] = new int[sqrtSize];
		int iBlockIni = sqrtSize*(b/sqrtSize);
		int jBlockIni = sqrtSize*(b%sqrtSize);
		for(int j = 0; j < sqrtSize; ++j){
			for(int i = 0; i < sqrtSize; ++i){
				numsB[j] = numsB[j] | numsPosCell.get(posCell(i + iBlockIni, j + jBlockIni));
			}
		}
		for(int j = 0; j < sqrtSize; ++j){
			int pre = numsB[j];
			int num = 0;
			for(int r = 0; r < sqrtSize; ++r){
				if(r != j){
					num = num | numsB[r];
				}
			}
			num = pre & ~num;
			if(Integer.bitCount(num) > 0){
				num = ~num;
				int numsCell, tmp;
				for(int i = 0; i < size; ++i){
					if(i/sqrtSize != b/sqrtSize){
						numsCell = numsPosCell.get(posCell(i, j + jBlockIni));
						tmp = numsCell & num;
						if(numsCell != tmp){
						numsPosCell.set(posCell(i, j + jBlockIni), tmp);
						changes = true;
						}
					}
				}
			}
		}
		return changes;
	}

//
	private boolean recursiveIntersectionSearch(int rcb){
		boolean recursiveBR = recursiveIntersectionSearchBR(rcb);
		boolean recursiveBC = recursiveIntersectionSearchBC(rcb);
		boolean recursiveR = recursiveIntersectionSearchR(rcb);
		boolean recursiveC = recursiveIntersectionSearchC(rcb);
		System.out.println("changesR " + (recursiveR));
		System.out.println("changesC " + (recursiveC));
		System.out.println("changesBR " + (recursiveBR));
		System.out.println("changesBC " + (recursiveBC));
		return recursiveR || recursiveC || recursiveBR || recursiveBC;
	}

	private void easyTry(){
		boolean changes = true;
		int maxP = size*size;
		while(changes){
			changes = false;
			isolatedCells();			
			for(int p = 0; p < maxP; ++p){
				if(!foundCell.get(p)){

						changes = changes || recursiveQuadSearchB(p, 1, 2, 3);
						changes = changes || recursiveQuadSearchR(p, 1, 2, 3);
						changes = changes || recursiveQuadSearchC(p, 1, 2, 3);
						changes = changes || recursiveTripleSearchB(p, 1, 2);	
						changes = changes || recursiveTripleSearchC(p, 1, 2);
						changes = changes || recursiveTripleSearchR(p, 1, 2);						
						changes = changes || recursivePairSearchB(p, 1);
						changes = changes || recursivePairSearchR(p, 1);
						changes = changes || recursivePairSearchC(p, 1);
						changes = changes || recursiveHidenIsolatedCellsB(p);
						changes = changes || recursiveHidenIsolatedCellsR(p);
						changes = changes || recursiveHidenIsolatedCellsC(p);
						if(changes) isolatedCells();
					
					
					if(recursiveHidenIsolatedCells(p)){
						isolatedCells();
						changes = true;
					}	
					
				//////////////	
				
				}
			}	
			for(int rcb = 0; rcb < size; ++rcb){
				changes = changes || recursiveIntersectionSearch(rcb);
			}
			printPosNums();
		}
	}


	private boolean wingsInit(int n){
		boolean positions[][] = new boolean[size][size];
		for(int i = 0; i < size; ++i){
			for(int j = 0; j < size; ++j){
				if((numsPosCell.get(posCell(i, j)) & n) != 0){
					positions[i][j] = true;
				}
			}
		}
		return false;
	}

	private boolean mediumTry(){
		boolean changes = true;
		int maxP = size*size;
		while(changes){
			changes = false;
			isolatedCells();			
			for(int p = 0; p < maxP; ++p){
				if(!foundCell.get(p)){					
					if(recursiveHidenIsolatedCells(p)){
						isolatedCells();
						changes = true;
					}				
				}
			}	
			int num = 1;
			for(int rcb = 0; rcb < size; ++rcb){
				changes = changes || recursiveIntersectionSearch(rcb);
				changes = changes || wingsInit(rcb);
				num = num << 1;
			}
			printPosNums();
		}
		return changes;
	}

	private void init(Board board){

		System.out.println("Init ");

		this.size = board.getSize();
		sqrtSize = (int)Math.sqrt(this.size);
		mask = codeNum(size + 1) - 1;

		System.out.println("MASK " + mask);

		numsPosCell = new ArrayList<Integer>();
		foundCell = new ArrayList<Boolean>();

		numsPosRows = new ArrayList<Integer>();
		numsPosColumns = new ArrayList<Integer>();		
		numsPosBlocks = new ArrayList<Integer>();
		for(int i = 0; i < size; ++i){
			numsPosRows.add(mask);
			numsPosColumns.add(mask);
			numsPosBlocks.add(mask);
		}

		System.out.println("Sudoku!! ");
		sudoku = new Board(board);

		//Omplir caselles.
		for(int i = 0; i < size; ++i){
			for(int j = 0; j < size; ++j){
				System.out.println("i " + i + "j " + j);
				if(board.getCasella(i, j) != 0){
					System.out.println("diff 0");
					int num = codeNum(board.getCasella(i, j));
					System.out.println("coded! " + num);
					numsPosCell.add(num);
					System.out.println("act");
					foundCell.add(false);			
				}
				else{
					//Caselles buides tenen tots els nombres possibles (de moment).
					numsPosCell.add(mask);
					foundCell.add(false);
				}				
			}
		}

		System.out.println();
		for(int i = 0; i < size; ++i){
			for(int j = 0; j < size; ++j){
				System.out.print(" " + numsPosCell.get(posCell(i, j)) + " ");
				//System.out.print(" " + foundCell.get(posCell(i, j)) + " ");
			}
			System.out.println();
		}

		System.out.println("numsPosCell començat! ");
		isolatedCells();//Actualitzar taula.
		System.out.println("Fi ini");

				System.out.println();
		for(int i = 0; i < size; ++i){
			for(int j = 0; j < size; ++j){
				System.out.print(" " + numsPosCell.get(posCell(i, j)) + " ");
				//System.out.print(" " + foundCell.get(posCell(i, j)) + " ");
			}
			System.out.println();
		}

	}

	public int SearchLevel(Board board) throws Exception{
		init(board);
		level = 0;
		System.out.println("Easy");
		easyTry();//Mentre hi hagi canvis o no acabi provar a resoldre amb facil.
		System.out.println("Fi Easy");
		if(found()); //return level;
		level = 1;
		System.out.println("Medium");
		mediumTry();//Mentre hi hagi canvis o no acabi provar a resoldre amb mitja.
		if(found()); //return level;
		level = 2;

		decode();
		System.out.println("Decoded! ");
		BoardIO bIO = new BoardIO();
		bIO.printBoardFormated(sudoku);
		System.out.println();
		for(int i = 0; i < size; ++i){
			for(int j = 0; j < size; ++j){
				System.out.print(" " + numsPosCell.get(posCell(i, j)) + " ");
				//System.out.print(" " + foundCell.get(posCell(i, j)) + " ");
			}
			System.out.println();
		}		
		//for(int i = 0; i < size; ++i) System.out.print(" " + numsPosRows.get(i) + " ");
		return level;
	}

}