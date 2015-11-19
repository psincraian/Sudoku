package propias.presentacion;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class ViewLoadMatch {
	
    public String getLoadMatch(){
		       showLoadMatch();
		       Scanner scanner = new Scanner(System.in);
		       String op = null;
		       while (op == null) {
			          try {
				             op = scanner.nextLine();
			          } 
			          catch (Exception e) {
				             op = null;
			          }
		       }
		       scanner.close();
		       return op;
 	  }
	
   	public void showLoadMatch() {
   		System.out.println("Quina partida vols continuar jugant? ");
                   ControllerPresentation c = new ControllerPresentation();
                   List<String> matches = new ArrayList<String>();
                   matches = c.getIDMatches();
                   Iterator<String> iter = matches.iterator();
                   int i = 1;
   		while(iter.hasNext())  {
   			System.out.println(i + ". " + iter.next() + "\n");
   			++i;
   		}
   	}
	
}