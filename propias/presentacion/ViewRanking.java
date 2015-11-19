package propias.presentacion;

import java.util.*;

/**
 * @author Daniel Sanchez Martinez
 */
public class ViewRanking {
	
	/**
	 * Displays ranking to the user
	 * @param name
	 * @param value
	 */
	public void mostrarRanquing(List<String> name, List<Long> value){
		System.out.println("Nom\t\tPuntuacio");
		for(int i = 0; i < name.size(); ++i){
			System.out.println(name.get(i) + "\t\t" + value.get(i));
		}
	}
}
