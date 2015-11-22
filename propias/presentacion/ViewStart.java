package propias.presentacion;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import propias.dominio.clases.Options;
/**
 * 
 * @author Brian Martinez Alvarez
 *
 */
public class ViewStart {
	
	
	
	private List<Options> options;
	/**
	 * Constructora
	 */
	public ViewStart() {
		options = new ArrayList<Options>();
		
		for (Options op : Options.values())
			options.add(op);
	}
	/**
	 * Mostra les opcions d'inici
	 */
	public void show() {
		int i = 1;
		for (Options op : options)
			System.out.println(Integer.toString(i++) + ". " + op);
	}
	/**
	 * Inhabilita una opcio
	 * @param op
	 */
	public void desable(Options op) {
		options.remove(op);
	}
	/**
	 * 
	 * @return La opcio desitjada
	 */
	public Options getOption() {
		Scanner scanner = new Scanner(System.in);
		
		int opcioEscollida = scanner.nextInt();
		while (optionNoValid(opcioEscollida))
			opcioEscollida = scanner.nextInt();
		
		return options.get(opcioEscollida -1);
	}
	/**
	 * 
	 * @param i Posicio escollida
	 * @return Si la posicio escollida es valida
	 */
	private boolean optionNoValid(int i) {
		return i < 1 || i > options.size();
	}
}
