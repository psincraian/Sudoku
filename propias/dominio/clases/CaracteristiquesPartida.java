package propias.dominio.clases;

/**
 * 
 * @author Petru Rares Sincraian
 *
 * Es una classe auxiliar per definir les dades bàsiques d'una {@link Match}
 */
public class CaracteristiquesPartida implements java.io.Serializable{	

	public int mida;
	public int dificultat;
	public int tipus;
	public int givenNumbers;
	public boolean newSudoku;
	
	/** Modifica la mida del objecte
	 * 
	 * @param mida La nova mida
	 */
	public void setMida(int mida) {
		this.mida = mida;
	}

	/** Modifica la dificultat del objecte
	 * 
	 * @param dificultat La nova dificultat
	 */
	public void setDificultat(int dificultat) {
		this.dificultat = dificultat;
	}
	
	/** Modifica els nombre de nombres del objecte
	 * 
	 * @param numbers El nou valor
	 */
	public void setGivenNumbers(int numbers) {
		this.givenNumbers=  numbers;
	}

	/** Modifica el tipus de la partida del Objecte
	 * 
	 * @param tipus El nou tipus
	 */
	public void setTipusPartida(int tipus) {
		this.tipus = tipus;
	}
	
	/** Obte la mida del objecte
	 * 
	 * @return La mida del objecte
	 */
	public int getMida() {
		return mida;
	}
	
	/** Obte la dificultat del objecte
	 * 
	 * @return La dificultat del objecte
	 */
	public int getDificultat() {
		return dificultat;
	}
	
	/** Obte el tipus de la partida
	 * 
	 * @return El tipus de la partida del objecte
	 */
	public int getTipusPartida() {
		return tipus;
	}
	
	/** Retorna el nombre de caselles buides
	 * 
	 * @return Un enter amb el nombre de caseles buides
	 */
	public int getGivenNumbers() {
		return givenNumbers;
	}
	/**
	 * Indica si el sudoku a jugar es nou o de la BBDD
	 * @return si el sudoku es nou o de la BBDD
	 */
	public boolean getNewSudoku(){
		return newSudoku;
	}
	
	/** Indica si el sudoku vol ser de la BBDD o nou
	 * 
	 * @param bool True si vol ser nou i false de la BBDD
	 */
	public void setNewSudoku(boolean newSudoku) {
		this.newSudoku = newSudoku;
	}
}
