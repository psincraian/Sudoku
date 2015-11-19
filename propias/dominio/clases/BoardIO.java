package propias.dominio.clases;

import java.util.Scanner;
import propias.dominio.clases.Board;

public class BoardIO {

	//@Adr
	//Input estil:
	//0 1 0 0 2 3 4 5 (fins a l'ultim nombre del sudoku).
	//si es sudoku de 16x16 s'utilitzen nombres (10, 11, 12, etc)
	public static void read(Board board) throws Exception {
		Scanner scn = new Scanner(System.in);
		int size = board.getSize();
		System.out.println("Introduce the data: ");
		for(int i = 0; i < size; ++i) {
			for(int j = 0; j < size; ++j) {
				int num = scn.nextInt();
				board.setCasella(i, j, num);
			}
		}
		scn.close();
	}
	
	// @author Petru
	public static Board read() throws Exception {
		Scanner s = new Scanner(System.in);
		System.out.println("Mida del taulell:" );
		int size = s.nextInt();
		Board board = new Board(size);
		
		//s.useDelimiter(separator);
		System.out.println("Taulell: ");
		for(int i = 0; i < size; ++i) {
			for(int j = 0; j < size; ++j) {
				int num = s.nextInt();
				board.setCasella(i, j, num);
			}
		}
		s.close();
		
		return board;
	}
	
	// @author Petru
	// Al input hi ha un string del estil:
	// 7.4.....2...8.1...3.........5.6..1..2...4...........5....37.....9....6...8.....9.
	// o si es de 16x16 conte els caracters ABCDEFG
	public static Board readFromString() throws Exception {
		Scanner scanner = new Scanner(System.in);
		String s = scanner.nextLine();
		scanner.close();
		
		int size = (int) Math.sqrt(s.length());
		Board board = new Board(size);
		for (int i = 0; i < size*size; i++) {
		    char c = s.charAt(i);
		    int row = i / size;
		    int col = i % size;
		    if(c != '.' && c >= '1' && c <= '9')
		        board.setCasella(row, col, c - '0');
	        else if (c != '.')
		        board.setCasella(row, col, c - 'A' + 10);
		}
		
	    return board;
	}
	
	// @author Petru
	public static void printBoardStringLine(Board board) {
		for(int i = 0; i < board.getSize(); ++i) {
			for(int j = 0; j < board.getSize(); ++j) {
				System.out.print(board.getCasella(i, j) + " ");
			}
		}
	}
	
	// @author Petru
	public static void printBoardFormated(Board board) {
		int quadrantSize = (int) Math.sqrt(board.getSize());
		int count = 0;
		for (int i = 0; i < board.getSize(); ++i) {
			printRow(board, i);
			if (++count == quadrantSize) {
				printRowSeparator(board);
				count = 0;
			}
		}
	}
	
	// @author Petru
	private static void printRowSeparator(Board board) {		
		int quadrantSize = (int) Math.sqrt(board.getSize());
		int count = 0;
		for (int i = 0; i < board.getSize(); ++i) {
			formatPrintCell(board, "-");
			if (++count == quadrantSize) {
				formatPrintCell(board, "|");
				count = 0;
			}
		}
		
		System.out.println();
	}
	
	// @author Petru
	private static void printRow(Board board, int row) {
		int quadrantSize = (int) Math.sqrt(board.getSize());
		int count = 0;
		for (int i = 0; i < board.getSize(); ++i) {
			String value = Integer.toString(board.getCasella(row, i));
			formatPrintCell(board, value);
			if (++count == quadrantSize) {
				formatPrintCell(board, "|");
				count = 0;
			}
		}
		System.out.println();
	}
	
	// @author Petru
	private static void formatPrintCell(Board board, String c) {
		if (board.getSize() < 9 || c.length() == 2)
			System.out.print(" " + c);
		else
			System.out.print(" " + c + " ");
	}

}
