package propias.Driver;
 
import propias.dominio.controladores.ControllerRanking;
 
import java.util.ArrayList;
import java.util.List;
 
import propias.dominio.clases.ParamRanking;

 /*@Author Daniel Sanchez Martinez*/

public class Driver_ControllerRanking {
 
    public static void main(String[] args){
        ParamRanking pr = new ParamRanking("Daniel", 100);
        List<ParamRanking> lista = new ArrayList<ParamRanking>();
        lista.add(pr);
        ControllerRanking cont = new ControllerRanking(lista,true);
        System.out.println("Posicion: " + cont.positionRanking("Daniel"));
        lista = cont.getRanking();
        for(int i = 0; i < lista.size(); ++i)
            System.out.println("Usuario: "+ lista.get(i).getName() + " Valor: "+ lista.get(i).getValue());
        ParamRanking pr2 = new ParamRanking("David", 50);
        cont.modRanking(pr2);
        for(int i = 0; i < lista.size(); ++i)
            System.out.println("Usuario: "+ lista.get(i).getName() + " Valor: "+ lista.get(i).getValue());
        pr = new ParamRanking("Daniel",30);
        cont.modRanking(pr);
        lista = cont.getRanking();
        for(int i = 0; i < lista.size(); ++i)
            System.out.println("Usuario: "+ lista.get(i).getName() + " Valor: "+ lista.get(i).getValue());
    }
}
