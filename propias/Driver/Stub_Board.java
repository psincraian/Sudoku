package propias.Driver;

import java.util.Scanner;
/**
 * 
 * @author daniel sánchez martínez
 *
 */
public class Stub_Board {

	int[][] board;
	int size;

	public void setCasella(int i, int j, int value){
		board[i][j] = value;
	}
	public int getCasella(int i,int j){
		return board[i][j];
	}
	public int getSize(){
		return board[0].length;
	}
	public int[][] initializeBoard(){
		Scanner s = new Scanner(System.in);
		System.out.println("Mida del taulell:" );
		int size = s.nextInt();
		 board = new int[size][size];
		
		System.out.println("Taulell: ");
		for(int i = 0; i < size; ++i) {
			for(int j = 0; j < size; ++j) {
				int num = s.nextInt();
				setCasella(i, j, num);
			}
		}
		s.close();
		return board;
	}
}
