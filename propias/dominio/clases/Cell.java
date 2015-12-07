package propias.dominio.clases;

import propias.dominio.clases.CellType;

/** 
 * 
 * @author Petru Rares Sincraian
 *
 */
public class Cell implements java.io.Serializable {
	
	public final String ERROR_VALUE_NOT_VALID = "Valor no valid";
	
	static int MAX_VALUE = 16;
	static int MIN_VALUE = 0;
	private int value;
	private CellType type;
	
	/** Contrueix una casella no bloquejant amb el valor especificat
	 * 
	 * @param value El valor de la casella
	 * @throws Exception Llença la exepció {@link Cell#ERROR_VALUE_NOT_VALID} si
	 * 
	 */
	public Cell(int value) throws Exception {
		if (isValidValue(value))
			this.value = value;
		else
			throw new Exception(ERROR_VALUE_NOT_VALID);
		type = CellType.Unlocked;
	}
	
	/** Construeix una casella amb el valor i tipus especificat
	 * 
	 * @param value El valor de la casella
	 * @param type El tipus de la casella
	 * @throws Exception Llença la exepció {@link Cell#ERROR_VALUE_NOT_VALID} si
	 * el valor no es vaid.
	 */
	public Cell(int value, CellType type) throws Exception {
		if (isValidValue(value))
			this.value = value;
		else
			throw new Exception("Valor no valid");
		this.type = type;
	}
	
	/** Constructor de copia
	 * 
	 * @param cell La casella a copiar
	 */
	public Cell(Cell cell) {
		this.value = cell.getValue();
		this.type = cell.getType();
	}
	
	/** Retorna el valor de la casella
	 * 
	 * @return retorna el valor de la casella
	 */
	public int getValue() {
		return value;
	}
	
	/** Retorna el tipus de la casella
	 * 
	 * @return Retorna el tipus de la casella
	 */
	public CellType getType() {
		return type;
	}
	
	/** Modifica el valor de la casella
	 * 
	 * @param value El nou valor de la casella
	 * @throws Exception Llença la exepció {@link Cell#ERROR_VALUE_NOT_VALID} si
	 * el valor no es vaid.
	 */
	public void setValue(int value) throws Exception {
		if (isValidValue(value))
			this.value = value;
		else
			throw new Exception(ERROR_VALUE_NOT_VALID);
	}
	
	/** Modifica el tipus de la casella
	 * 
	 * @param type El nou tipus de la casella
	 */
	public void setType(CellType type) {
		this.type = type;
	}
	
	/** Retorna true si la casella es valid. Té un valor considerat valid. Entre
	 * MAX i MIN
	 * 
	 * @return Retorna true si la casella es valida
	 */
	public boolean isValid() {
		return isValidValue(value) && value != 0;
	}
	
	/** Retorna true si el parametre es valid. Té un valor considerat valid.
	 * 
	 * @param value El valor a comprovar
	 * @return Retorna true si la casella es valida
	 */
	private static boolean isValidValue(int value) {
		return value <= MAX_VALUE && value >= MIN_VALUE;
	}
}
