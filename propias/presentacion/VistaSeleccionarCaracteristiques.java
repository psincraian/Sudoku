package propias.presentacion;

import java.util.Scanner;
import propias.dominio.clases.CaracteristiquesPartida;
import propias.dominio.clases.Dificultat;
import propias.dominio.clases.TipusPartida;

public class VistaSeleccionarCaracteristiques {
	
	public CaracteristiquesPartida obtenirCaracteristiques() {
		CaracteristiquesPartida caracteristiques = new CaracteristiquesPartida();
		caracteristiques.setTipusPartida(obtenirTipusPartida());
		caracteristiques.setMida(obtenirMida());
		caracteristiques.setDificultat(obtenirDificultat());
		return caracteristiques;
	}
	
	public int obtenirTipusPartida() {
		System.out.println("Escull el tipus de partida: ");
		int i = 1, t = 0;
		for (TipusPartida tipus : TipusPartida.values()){
			System.out.println(i + ". " + tipus);
			++i;
		}
		Scanner scanner = new Scanner(System.in);
		t = scanner.nextInt();
		return t;
	}
	
	public int obtenirMida() {
		System.out.println("Escull la mida del taulell:\n- 9\n- 16");
		
		Scanner scanner = new Scanner(System.in);
		int mida = scanner.nextInt();
		while (mida != 9 && mida != 16) mida = scanner.nextInt();
		return mida;
	}
	
	public int obtenirDificultat() {
		System.out.println("Escull la dificultat de la partida: ");
		int i=1;
		for (Dificultat dificultat : Dificultat.values())
			System.out.println(i++ + ". " + dificultat);
		
		Scanner scanner = new Scanner(System.in);
		int dif = 0;
		dif = scanner.nextInt();
		return dif;
	}
}
