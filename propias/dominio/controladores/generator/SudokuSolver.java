package propias.dominio.controladores.generator;

import propias.dominio.clases.Board;
import propias.dominio.controladores.generator.dlx.dlx;

/** Un solucionador de Sudokus. Donat un sudoku la classe et mostra la solució
 * d'aquest i si la solució es unica o no.
 * 
 * @author Petru Rares Sincraian
 *
 */
public class SudokuSolver {
	
	private dlx solver;
	private boolean isUnique;
	
	/** El constructor del Solver
	 * 
	 * @param board El board al que volem buscar solució
	 */
	public SudokuSolver(Board board) {
		solver = new dlx(board);
	}
	
	/** Ens indica si volem saber si la solució es única. S'ha de cridar abans
	 * de {@link SudokuSolver#isUnique()}
	 * 
	 */
	public void needUnique() {
		solver.needUnique();
	}
	
	/** Ens resol el sudoku
	 * 
	 * @return Retorna un Board amb la primera solució que s'ha trobat.
	 */
	public Board solve() {
		solver.solve();
		isUnique = solver.isUniqueSolution();
		return solver.getSolution();
	}
	
	/** Ens indica si la solució es unica. S'ha de cridar {@link SudokuSolver#isUnique()}
	 * abans que {@link SudokuSolver#solve()} per obtenir el comportament desitjat.
	 * 
	 * @return Retorna true si la solució es unica. False en cas contrari.
	 */
	public boolean isUnique() {
		return isUnique;
	}
}
