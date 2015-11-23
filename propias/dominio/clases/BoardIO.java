package propias.dominio.clases;

import java.util.Scanner;
import propias.dominio.clases.Board;

/** Classe auxiliar que serveix per llegir o escriure un {@link Board} de
 * l'entrada per defecte.
 *
 */
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
	
	/** Crea un {@link Board} a partir de l'entrada per defecte. 
	 * 
	 * Primer es llegeix la mida del taulell i després les dades. Les dades
	 * estan separades per espai.
	 * 
	 * @author Petru Rares Sincraian
	 * 
	 * @return Retorna un {@link Board} a partir de les dades de l'entrada
	 * @throws Exception Retorna {@link Exception} amb {@link Cell#ERROR_VALUE_NOT_VALID}
	 * si el valor no es valid.
	 */
	public static Board read() throws Exception {
		Scanner s = new Scanner(System.in);
		System.out.println("Mida del taulell:" );
		int size = s.nextInt();
		Board board = new Board(size);
		
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
	
	/** Crea un {@link Board} a partir de l'entrada per defecte. 
	 * 
	 * Les dades tenen el seguent format: 1...24..12. On un punt indica un valor
	 * buit i el valor pot anar de 1 a F depenent de la mida del sudoku
	 * 
	 * @author Petru Rares Sincraian
	 * 
	 * @return Retorna un {@link Board} a partir de les dades de l'entrada
	 * @throws Exception Retorna {@link Exception} amb {@link Cell#ERROR_VALUE_NOT_VALID}
	 * si el valor no es valid.
	 */
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
	
	/** Imprimeix els valors del {@link Board} amb espais 
	 * 
	 * @author Petru Rares Sincraian
	 * 
	 * @param board El board que es vol imprimir
	 */
	public static void printBoardStringLine(Board board) {
		for(int i = 0; i < board.getSize(); ++i) {
			for(int j = 0; j < board.getSize(); ++j) {
				System.out.print(board.getCasella(i, j) + " ");
			}
		}
	}
	
	/** Imprimeix els valors del {@link Board} formatejat. S'imprimeix de 
	 * manera semplant a un Sudoku.
	 * 
	 * @author Petru Rares Sincraian
	 * 
	 * @param board El board que es vol imprimir
	 */
	public static void printBoardFormated(Board board) {
		int quadrantSize = (int) Math.sqrt(board.getSize());
		int count = 0;
		for (int i = 0; i < board.getSize(); ++i) {
			printRow(board, i);
			if (++count == quadrantSize) {
				printRowSeparator(board.getSize());
				count = 0;
			}
		}
	}
	
	/** Imprimeix un separador de fila. El separador de fila té el seguent format
	 * - - - - | On '|' indica la finalització d'un bloc
	 * 
	 * @author Petru Rares Sincraian
	 * 
	 * @param size Es la mida del board
	 */
	private static void printRowSeparator(int size) {		
		int quadrantSize = (int) Math.sqrt(size);
		int count = 0;
		for (int i = 0; i < size; ++i) {
			formatPrintCell(size, "-");
			if (++count == quadrantSize) {
				formatPrintCell(size, "|");
				count = 0;
			}
		}
		
		System.out.println();
	}
	
	/** Imprimeix la fila indicada del board
	 * 
	 * @author Petru Rares Sincraian
	 * 
	 * @param board El {@link Board que es vol imprimir}
	 * @param row La fila del {@link Board} que es vol imprimir
	 */
	private static void printRow(Board board, int row) {
		int quadrantSize = (int) Math.sqrt(board.getSize());
		int count = 0;
		for (int i = 0; i < board.getSize(); ++i) {
			String value = Integer.toString(board.getCasella(row, i));
			formatPrintCell(board.getSize(), value);
			if (++count == quadrantSize) {
				formatPrintCell(board.getSize(), "|");
				count = 0;
			}
		}
		System.out.println();
	}
	
	/** Imprimeix el valor d'una cel·la de manera que totes ocupin el mateix
	 * 
	 * @param size Es la mida del board
	 * @param c Es el caracter que es vol imprimir
	 */
	private static void formatPrintCell(int size, String c) {
		if (size < 9 || c.length() == 2)
			System.out.print(" " + c);
		else
			System.out.print(" " + c + " ");
	}

}
