package propias.presentacion;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class ViewLoadMatch {
	public ViewLoadMatch(){
		
	}
    public int getLoadMatch(List<String> id){
		       showLoadMatch(id);
		       if (id.size() != 0){
			       Scanner scanner = new Scanner(System.in);
			       int op =1;
			       while ( op > 0 && op < id.size()) {
					       op = scanner.nextInt();
			       }
			       return op;
		       }
		       else return 0;
 	  }
	
   	public void showLoadMatch(List<String> id) {
   		if (id.size() == 0) System.out.println("No hi ha partides guardades");
   		else {
   			System.out.println("Quina partida vols continuar jugant? ");
                   Iterator<String> iter = id.iterator();
                   int i = 1;
            while(iter.hasNext())  {
            	System.out.println(i + ". " + iter.next() + "\n");
            	++i;
            }
   		}
   	}
	
}
