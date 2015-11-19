package propias.dominio.clases;

import java.lang.Integer;
import java.lang.Math;
import java.util.List;
import java.util.ArrayList;
import propias.dominio.clases.Board;

/**
* CLASSE SIMULADOR!!
* Aquesta classe simula al buscador de nivells real (Classe LevelSearch.java), ja que no he pogut acabar a temps
* per l'entrega totes les tecniques necesaries per a poder determinar si el nivell d'un sudoku
* es mitja o dificil (falten les tecniques de nivell mitja i arreglar Intersection Search que 
* no te un funcionament correcte).
*
* Donat un sudoku segons alguns parametres (nombre de caselles per fila/columna i/o nombre de caselles total)
* et retornara el nivell del sudoku (0 facil, 1 mitja, 2 dificil).
*
* Tot i que la dificultat d'un sudoku no ve determinada completament pel nombre de caselles que el formen
* (hi ha sudokus facils de 17 caselles), com a minim permetra actuar de forma correcte a les demes classes
* que necesitin diferenciar el nivell d'un sudoku i donara diferents nivells segons el sudoku introduit.
*
*@autor Adrián Sánchez Albanell.
*
*/
public class searchLevel {

	private double MAX_HOLES_EASY = 6./9;
	private double MAX_HOLES_MEDIUM = 60./81;
	private double MAX_HOLES_DIFFICULT = 61./81;
	private double MAX_HOLES_EVIL = 64./81;
	private double MAX_HOLES_RC_EASY = 6./9;
	private double MAX_HOLES_RC_MEDIUM = 7./9;
	private double MAX_HOLES_RC_DIFFICULT = 7./9;

	private Board sudoku;
	private int size;
	private int sqrtSize;
	
	/**
	*Creadora d'un searchLevel donant-li un board.
	*@param board representacio d'un sudoku.
	*/
	public searchLevel(Board board) throws Exception { 
	    this.sudoku = new Board(board);
	    size = sudoku.getSize();
	    sqrtSize = (int)Math.sqrt(size);
	}

	/**
	*Donat el sudoku de la classe et dona el seu nivell.
	*@return in el nivell: 0 facil, 1 mitja, 2 dificil.
	*/
	public int level(){
		int maxHolesRC = 0;
		int givens = 0;
		int holesRow, holesCol;
		for(int i = 0; i < size; ++i){
			holesRow = 0;
			holesCol = 0;
			for(int j = 0; j < size; ++j){
				if(sudoku.getCellValue(i, j) != 0){
					++givens;
				}
				else{
					++holesRow;
				}
				if(sudoku.getCellValue(j, i) == 0){
					++holesCol;
				}
			}
			if(maxHolesRC < holesRow){
				maxHolesRC = holesRow;
			}
			if(maxHolesRC < holesCol){
				maxHolesRC = holesCol;
			}
		}
		if(givens >= (int)(size*size*MAX_HOLES_EASY)) return 0;
		if(givens >= (int)(size*size*MAX_HOLES_MEDIUM) || maxHolesRC < (int)(MAX_HOLES_RC_DIFFICULT*size)) return 1;
		return 2;
	}






















}