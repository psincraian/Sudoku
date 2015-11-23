package propias.presentacion;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import propias.dominio.clases.OptionsMenu;

public class VistaMenu {
	
	private List<OptionsMenu> opcions;
	
	public VistaMenu() {
		opcions = new ArrayList<OptionsMenu>();
		
		for (OptionsMenu op : OptionsMenu.values())
			opcions.add(op);
	}
	
	public void mostrar() {
		int i = 1;
		for (OptionsMenu op : opcions){
			System.out.println(i + ". " + op);
			++i;
		}
	}
	
	public void deshabilitar(OptionsMenu op) {
		opcions.remove(op);
	}
	
	public OptionsMenu obtenirOpcio() {
		Scanner scanner = new Scanner(System.in);
		
		int opcioEscollida = scanner.nextInt();
		while (opcioNoValida(opcioEscollida))
			opcioEscollida = scanner.nextInt();
		
		return opcions.get(opcioEscollida - 1);
	}
	
	private boolean opcioNoValida(int i) {
		return i < 1 || i > opcions.size();
	}
}
