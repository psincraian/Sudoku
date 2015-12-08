package propias.Driver;
 
import propias.dominio.controladores.ControllerRanking;
 
import java.util.ArrayList;
import java.util.List;
 
import propias.dominio.clases.ParamRanking;
import propias.dominio.clases.Ranking;
import propias.dominio.clases.RankingGlobal;

/**
 * 
 * @author daniel sanchez martinez
 *
 */
public class Driver_ControllerRanking {
 
    public static void main(String[] args){
        ParamRanking pr = new ParamRanking("Daniel", 100);
        List<ParamRanking> lista = new ArrayList<ParamRanking>();
        lista.add(pr);
        Ranking rank = new RankingGlobal(lista);
        System.out.println("Posicion: " + rank.isIn("Daniel") + "\n");
        lista = rank.getRanking();
        for(int i = 0; i < lista.size(); ++i)
            System.out.println("Usuario: "+ lista.get(i).getName() + " Valor: "+ lista.get(i).getValue());
        System.out.println("\n");
        ParamRanking pr2 = new ParamRanking("David", 50);
        rank.modRanking(pr2);
        for(int i = 0; i < lista.size(); ++i)
            System.out.println("Usuario: "+ lista.get(i).getName() + " Valor: "+ lista.get(i).getValue());
        System.out.println("\n");
        pr = new ParamRanking("Daniel",30);
        rank.modRanking(pr);
        lista = rank.getRanking();
        for(int i = 0; i < lista.size(); ++i)
            System.out.println("Usuario: "+ lista.get(i).getName() + " Valor: "+ lista.get(i).getValue());
    }
}
