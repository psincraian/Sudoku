package propias.Driver;

import propias.presentacion.ViewRanking; 
import java.util.ArrayList;
import java.util.List;
 /*@author Daniel Sanchez Martinez*/
public class Driver_VistaRanking {
    public static void main (String[] args){
        List<String> name = new ArrayList<String>();
        List<Long> values = new ArrayList<Long>();
        name.add("Daniel");
        name.add("David");
        values.add((long)134);
        values.add((long)120);
        ViewRanking vr = new ViewRanking();
        vr.mostrarRanquing(name, values);
    }
 
}
