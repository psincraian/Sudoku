package propias.dominio.controladores;
import propias.dominio.controladores.generator.CntrlSudokuSolver;
import propias.dominio.controladores.generator.CntrlSudokuVerification;
import propias.dominio.clases.Board;
/**
 * 
 * @author Brian Martinez Alvarez
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
    		for(int j=0; j<m[0].length; ++j) {
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
	 * @return Si la matriu compleix les regles del joc
	 */
	public boolean controlLaws(int[][]m){
		Board b = convertMatrixToBoard(m);
		CntrlSudokuVerification s = new CntrlSudokuVerification();
		boolean solved = s.resolve(b);
		return solved;
	}
	/**
	 * 
	 * @param m Matriu a comprobar
	 * @return Si la matriu te solucio, aquesta es unica
	 */
	public boolean verify(int[][] m){
		Board b = convertMatrixToBoard(m);
		CntrlSudokuSolver sol = new CntrlSudokuSolver(b);
		sol.needUnique();
		sol.solve();
		return sol.isUnique();			

	}
}
