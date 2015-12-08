package propias.Driver;
  
import java.util.ArrayList;
import java.util.List;
 
import propias.dominio.clases.ParamRanking;
import propias.dominio.clases.Ranking;
import propias.dominio.clases.RankingGlobal;
import propias.dominio.clases.RankingSudoku;

/**
 * 
 * @author daniel sanchez martinez
 *
 */
public class Driver_Ranking {
 
    public static void main(String[] args){
        /*-----> Pruebas con RankingGlobal <-----*/
    	ParamRanking pr = new ParamRanking("Daniel", 10);
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
        pr = new ParamRanking("Oscar",45);
        rank.modRanking(pr);
        lista = rank.getRanking();
        for(int i = 0; i < lista.size(); ++i)
            System.out.println("Usuario: "+ lista.get(i).getName() + " Valor: "+ lista.get(i).getValue());
        
        /*------>Pruebas con RankingSudoku<------*/
        pr = new ParamRanking("Daniel", 002055);
        lista = new ArrayList<ParamRanking>();
        lista.add(pr);
        rank = new RankingSudoku(lista);
        System.out.println("Posicion: " + rank.isIn("Daniel") + "\n");
        lista = rank.getRanking();
        for(int i = 0; i < lista.size(); ++i)
            System.out.println("Nombre: "+ lista.get(i).getName() + " tiempo: "+ lista.get(i).getValue());
        System.out.println("\n");
        pr2 = new ParamRanking("David", 001737);
        rank.modRanking(pr2);
        for(int i = 0; i < lista.size(); ++i)
            System.out.println("Usuario: "+ lista.get(i).getName() + " Valor: "+ lista.get(i).getValue());
        System.out.println("\n");
        pr = new ParamRanking("Daniel",001600);
        rank.modRanking(pr);
        pr = new ParamRanking("Oscar",002312);
        rank.modRanking(pr);
        lista = rank.getRanking();
        for(int i = 0; i < lista.size(); ++i)
            System.out.println("Usuario: "+ lista.get(i).getName() + " Valor: "+ lista.get(i).getValue());
    }
}
