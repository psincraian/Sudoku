package propias.dominio.controladores;
import propias.dominio.controladores.generator.SudokuSolver;
import propias.dominio.controladores.generator.SudokuVerification;
import propias.dominio.clases.Board;
/**
 * 
 * @author Brian
 *
 */
public class ControllerBoard {
	/**
	 * Constructora
	 */
	public ControllerBoard() {
	}
	/**
	 * 
	 * @param m Matrix a convertir
	 * @return Retorna el taulell de la Matriu m
	 */
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
	 * @param m Matriu a comprobar
	 * @return Si la matriu te solucio, aquesta es unica i compleix les regles del joc
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
