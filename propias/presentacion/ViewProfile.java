package propias.presentacion;
/**
 * 
 * @author Brian Martinez Alvarez
 *
 */
public class ViewProfile {
	long[] m;
	long[] t;
	long[] bt;
	/**
	 * Creadora
	 * @param a Numero de partides jugades
	 * @param b Temps total empleat segons dificultat
	 * @param c Millor temps segons dificultat
	 */
	public ViewProfile(long[] a, long[] b, long[] c){
		m = a;
		t = b;
		bt = c;
	}
	/**
	 * Mostra el perfil del jugador
	 */
	public void showProfile(){
		System.out.println("PARTIDES JUGADES");
		System.out.println("Partides Facils Jugades: " + m[0]);
		System.out.println("Partides Mitjanes Jugades: " + m[1]);
		System.out.println("Partides Dificils Jugades: " + m[2] + "\n");
		System.out.println("TEMPS TOTAL");
		System.out.println("Temps Partides Facils: " + t[0]);
		System.out.println("Temps Partides Mitjanes: " + t[1]);
		System.out.println("Temps Partides Dificils: " + t[2] + "\n");
		System.out.println("TEMPS TOTAL");
		System.out.println("Millor Temps Partides Facils: " + bt[0]);
		System.out.println("Millor Temps Partides Mitjanes: " + bt[1]);
		System.out.println("Millor Temps Partides Dificils: " + bt[2] + "\n");
	}
}
