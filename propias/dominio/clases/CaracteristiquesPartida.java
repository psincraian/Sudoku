package propias.dominio.clases;

/**
 * 
 * @author Petru Rares Sincraian
 *
 */
public class CaracteristiquesPartida {	

	int mida;
	int dificultat;
	int tipus;
	
	public void setMida(int mida) {
		this.mida = mida;
	}

	public void setDificultat(int dificultat) {
		this.dificultat = dificultat;
	}

	public void setTipusPartida(int tipus) {
		this.tipus = tipus;
	}
	
	public int getMida() {
		return mida;
	}
	
	public int getDificultat() {
		return dificultat;
	}
	
	public int getTipusPartida() {
		return tipus;
	}
}