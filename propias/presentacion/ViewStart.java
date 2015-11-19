package propias.presentacion;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import propias.dominio.clases.Options;

public class ViewStart {
	
	
	
	private List<Options> options;
	
	public ViewStart() {
		options = new ArrayList<Options>();
		
		for (Options op : Options.values())
			options.add(op);
	}
	
	public void show() {
		int i = 1;
		for (Options op : options)
			System.out.println(Integer.toString(i++) + ". " + op);
	}
	
	public void desable(Options op) {
		options.remove(op);
	}
	
	public Options getOption() {
		Scanner scanner = new Scanner(System.in);
		
		int opcioEscollida = scanner.nextInt();
		while (optionNoValid(opcioEscollida))
			opcioEscollida = scanner.nextInt();
		
		return options.get(opcioEscollida -1);
	}
	
	private boolean optionNoValid(int i) {
		return i < 1 || i > options.size();
	}
}
