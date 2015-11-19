package propias.Driver;

import java.util.ArrayList;
import java.util.Random;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import propias.Driver.Stub_Board;

/**
 * 
 * @author daniel sanchez martinez
 *
 */
public class Stub_ControllerPresentation {
	
	int[][] board;
	//ControllerPresentationBoard cpb;
	int size;
	
	public int[][] getSudoku(){
		Stub_Board sb = new Stub_Board();
		board = sb.initializeBoard();
		size = board[0].length;
		return board;
	}
	public int getSize(){
		return size;
	}
	public List<Integer> getCandidates(String name){
		List<Integer> lista = new ArrayList<Integer>();
		Random rand = new Random();
		int aleatorio = rand.nextInt(9);
		int value;
		for(int i = 0; i < aleatorio; ++i){
			value = rand.nextInt(9);
			if(!lista.contains(value))
				lista.add(value);
		}
		return lista;
	}
	public int getCellResolved(String position){
		return 5;
	}
	public List<String> getDifferentCells(){
		List<String> lista = new ArrayList<String>();
		lista.add("13");
		lista.add("00");
		lista.add("88");
		
		return lista;
	}
	
	public boolean checkBoard(){
		return true;
	}
	
	public void start(){
		System.out.println("Finalizado");
	}
	public void saveBoard(){
		System.out.println("Guardado");
	}
	public void updateCell(String position, int value){
		int x = Character.getNumericValue(position.charAt(0));
		int y = Character.getNumericValue(position.charAt(1));
		board[x][y] = value;
		for(int i = 0; i < board.length; ++i){
			for(int j = 0; j < board.length; ++j){
				System.out.print(board[i][j]+" ");
			}
			System.out.print("\n");
		}
	}

}
