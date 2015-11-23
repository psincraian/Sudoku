
import propias.dominio.controladores.generator.*;
import propias.dominio.clases.*;

public class prova {

	public static void main(String[] args) {		
		try{
			init(); 
		} catch(Exception e){
			System.out.println("po vaia");
		}
	}

	public static void init() throws Exception{
		CntrlSudokuGenerator generator = new CntrlSudokuGenerator(16);
		Board sudo = generator.generateBoard();
		CntrlSudokuDiggingHoles holes = new CntrlSudokuDiggingHoles(sudo);
		Board sudoku = holes.digHoles(2);
		BoardIO bIO = new BoardIO();
		System.out.println();
		bIO.printBoardStringLine(sudoku);
		System.out.println();
		CntrlSudokuSolver ss = new CntrlSudokuSolver(sudoku);
		System.out.println();
		bIO.printBoardStringLine(ss.solve());
		System.out.println();
	}

}
