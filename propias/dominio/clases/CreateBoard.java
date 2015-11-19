package propias.dominio.clases;
import propias.dominio.clases.SudokuSolver;
import propias.dominio.clases.SudokuVerification;
/**
 * 
 * @author Brian
 *
 */
public class CreateBoard {
	/**
	 * 
	 * @param m
	 */
	public CreateBoard(int[][] m){
	}
	public CreateBoard() {
		// TODO Auto-generated constructor stub
	}
	Board convertMatrixToBoard(int[][] m){
		Board b = null;
		try {
			b = new Board(m[0].length);
		} catch (Exception e1) {
		}
    	for (int i=0; i<m[0].length; ++i){
    		for(int j=0; j<m[0].length; ++i) {
    			try {
					b.setCellValue(i, j, m[i][j]);
				} catch (Exception e) {
				} 
    		}
    	}
    	return b;
	}
	/**
	 * 
	 * @param m
	 * @return
	 */
	public boolean verify(int[][] m){
		Board b = convertMatrixToBoard(m);
		SudokuVerification s = new SudokuVerification();
		boolean solved = s.resolve(b);
		SudokuSolver sol = new SudokuSolver(b);
		sol.needUnique();
		sol.solve();
		boolean unique = sol.isUnique();
		if (unique && solved) return true;
		return false;
	}
}
