package propias.dominio.clases;

/** Classe auxiliar per encapsular la posició. La considerem útil ja que d'aquesta 
 * manera no t'has d'enrecordar de si primer en un paràmetre va la fila o la columna.
 * A més que facilita l'entendiment del mètode.
 * 
 * @author Petru Rares Sincraian
 *
 */
public class Position {
	
	public final String ERROR_VALUE_NEGATIVE = "Row or column is negative";
	
	private int row;
	private int column;
	
	/** Constructora per defecte. Crea un objecte amb la fila i la columna 
	 * especificada.
	 * 
	 * @param row La fila
	 * @param column La columna
	 * @throws Exception Retorna una {@link Exception} amb {@link Position#ERROR_VALUE_NEGATIVE}
	 * si la columna o la fila son negatius
	 */
	public Position(int row, int column) throws Exception {
		if (isValidRow(row) && isValidColumn(column)) {
			this.row = row;
			this.column = column;
		} else {
			throw new Exception(ERROR_VALUE_NEGATIVE);
		}
	}
	
	/** Crea una posicio amb fila i columna igual a 0
	 * 
	 * @throws Exception Mai retornara un error
	 */
	public Position() throws Exception {
		this(0, 0);
	}
	
	/** Obte la fila
	 * 
	 * @return Un enter major que 0 amb la fila
	 */
	public int getRow() {
		return row;
	}
	
	/** Obte la columna
	 *  
	 * @return Un enter més gran que 0 amb la columna
	 */
	public int getColumn() {
		return column;
	}
	
	/** Canvia el valor de la fila per l'especificat
	 * 
	 * @param row El nou valor
	 * @throws Exception Retorna una {@link Exception} amb {@link Position#ERROR_VALUE_NEGATIVE}
	 * si la columna o la fila son negatius	 
	 */
	public void setRow(int row) throws Exception {
		if (isValidRow(row))
			this.row = row;
		else
			throw new Exception(ERROR_VALUE_NEGATIVE);
	}
	
	/** Canvia el valor de la columna per l'especificat
	 * 
	 * @param column El nou valor
	 * @throws Exception Retorna una {@link Exception} amb {@link Position#ERROR_VALUE_NEGATIVE}
	 * si la columna o la fila son negatius	 
	 */
	public void setColumn(int column) throws Exception {
		if (isValidColumn(column))
			this.column = column;
		else
			throw new Exception ("Column is negative");
	}
	
	/** Indica si el valor de la fila es valid
	 * 
	 * @param row El valor a comprovar
	 * @return Retorna true si el valor es valid
	 */
	private boolean isValidRow(int row) {
		return row >= 0;
	}
	
	/** Indica si el valor de la columna es valid
	 * 
	 * @param columnt El valor de la columna
	 * @return retorna true si es valid, false en cas contrari
	 */
	private boolean isValidColumn(int columnt) {
		return column >= 0;
	}
}
