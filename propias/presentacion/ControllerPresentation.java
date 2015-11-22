package propias.presentacion;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import propias.presentacion.*;
import propias.dominio.clases.*;
import propias.dominio.controladores.*;
/**
 * 
 * @author Brian Martinez Alvarez
 *
 */
public class ControllerPresentation {
	
    ControllerDomain cd;
    ControllerUserEntry cu;
    String name;
    boolean correct;
    /**
     * Constructora
     */
    public ControllerPresentation() {
        cd = new ControllerDomain();
    }
    /**
     * Inicia el joc
     */
    public void start() {
    	correct = false;
        ViewStart vi = new ViewStart();
        vi.show();
        Options op = vi.getOption();
        if (op == Options.IniciarSessio)
             cu = new ControllerUserEntry(this, true);
        else if (op == Options.IniciarConvidat) {
            name = "Convidat";
            Menu(true);
        }
        else if (op == Options.RegistrarUsuari){
            cu = new ControllerUserEntry(this, false);
        }
        else if (op == Options.Sortir) System.exit(0);
        
    }
    /**
     * 
     * @param credentials Credencials de l'usuari a registrar o loguejar
     * @return si el logueig o la creacio d'usuari s'ha fet correctament
     */
    public boolean checkInfoUser(List<String> credentials) {
      name = credentials.get(0);
      String result = cd.checkCredentials(credentials);
      if (result.equals("Login correcto") || result.equals("Se ha creado el usuario")) correct = true;
      cu.sendMessage(result);
      return correct;
    }
      
    /**
     * Envia a l'usuari directament al menu principal
     */
    public void getBack() {
        if (name.equals("Convidat"))
            Menu(true);
        else
            Menu(false);
    }
    
    /**
     * 
     * @param convidat Indica si l'usuari es usuari convidat
     */
    private void Menu(boolean convidat) {
      VistaMenu vm = new VistaMenu();
      OptionsMenu om; 
      if (convidat) {
        vm.deshabilitar(OptionsMenu.Perfil);
        vm.deshabilitar(OptionsMenu.Ranking);
        vm.deshabilitar(OptionsMenu.CargarPartida);
        vm.mostrar();
        om = vm.obtenirOpcio();
      }
      else {
        vm.mostrar();
        om = vm.obtenirOpcio();
      }
      if (om == OptionsMenu.PartidaRapida) {
          VistaSeleccionarCaracteristiques sc = new VistaSeleccionarCaracteristiques();
          int[][] m = cd.createMatch(sc.obtenirCaracteristiques());
          if (!isCompetition()) play(m,false,false);
          else play(m,true,false);
      }
      if (om == OptionsMenu.CargarPartida) {
          List<String> id = cd.getIDMatches();
          ViewLoadMatch vcp = new ViewLoadMatch();
          int s =  vcp.getLoadMatch(id);
          if (s != 0) {
            List<int[][]> m = cd.getSavedMatches(id.get(s-1));
            play(m.get(0),true,true);
          }
          else Menu(false);
          
      }
      else if (om == OptionsMenu.CrearSudoku) {
          VistaSeleccionarCaracteristiques sc = new VistaSeleccionarCaracteristiques();
          int mida = sc.obtenirMida();
          int m[][] = new int[mida][mida];
          for(int i=0; i< mida; ++i){
              for(int j=0; j< mida; ++j) m[i][j] = 0;
          }
          cd.newMatch(mida);
          ControllerViewBoard c = new ControllerViewBoard(m, m[0].length,1,this);
      }
      else if (om == OptionsMenu.Ranking) {
          List<String> names = new ArrayList<String>();
          List<Long> values = new ArrayList<Long>();
          cd.getRanking(names,values);
          ViewRanking v = new ViewRanking();
          v.mostrarRanquing(names,values);
          if ( detect() == 0) Menu(false);
      }
      else if (om == OptionsMenu.Perfil) {
          ViewProfile v = new ViewProfile(getMatches(), getTime(), getBestTime());
          v.showProfile();
          if ( detect() == 0) Menu(false);
      }
      else if (om == OptionsMenu.Sortir) start();
    }
    /**
     * 
     * @return si la partida actual es de competicio o d'entrenament
     */
    public Boolean isCompetition(){
        return cd.isCompetition();
    }
    /**
     * 
     * @return si el taulell actual compleix les regles del joc i te solucio unica
     */
    public boolean checkBoard(){
        return cd.checkBoard();
    }
    /**
     * 
     * @param position Coordenades d'una Cel·la
     * @param value Valor a posar a la Cel·la
     */
    public void updateCell(String position, int value){
        cd.updateCell(position, value);
    }
    /**
     * Guarda la partida actual
     */
    public void saveBoard(){
        cd.saveBoard();
    }
    /**
     * 
     * @return El numero posat per l'usuari. Un 0 li permet sortir
     */
    private int detect(){
        System.out.println("Prem el 0 y dona a enter per tornar a l'inici");
        Scanner scanner = new Scanner(System.in);
        int op = -1;
        while (op != 0) {
            try {
                op = scanner.nextInt();
            } 
            catch (Exception e) {
                op = -1;
            }
        }
        return op;
    }
    /**
     * 
     * @return Retorna el numero de partides jugades per l'usuari
     */
    public long[] getMatches(){
        return cd.returnMatches();
    }
    /**
     * 
     * @return El temps total jugat per l'usuari segons la dificultat
     */
    public long[] getTime(){
        return cd.returnTime();
    }
    /**
     * 
     * @return El millor temps jugat per l'usuari segons la dificultat
     */
    public long[] getBestTime(){
        return cd.returnBestTime();
    }
    /**
     * 
     * @return Retorna els identificadors de les partides guardades per l'usuari
     */
    public List<String> getIDMatches(){
        return cd.getIDMatches();
    }
    /**
     * 
     * @param m taulell a jugar
     * @param competicio Indica si la partida es competicio o entrenament
     * @param save Indica si la partida es una partida nova o una partida carregada
     */
    private void play(int[][] m, boolean competicio, boolean save)  { 

      ControllerViewBoard c = new ControllerViewBoard(m, m[0].length,0,this);
      if (save) {
          for(int i= 0; i<m[0].length; ++i) {
              for(int j=0; j<m[0].length; ++j) {
                  int res = cd.modify(i,j);
                  String row = Integer.toString(i);
                  String col = Integer.toString(j);
                  String cood = row + " " + col; 
                  if (res != 0) c.updateBoard(Integer.toString(cood),Integer.toString(res));
              }
          }
      }
      
    }
    /**
     * 
     * @param s Coordenades de una Cel·la
     * @return els candidats possibles per aquella casella
     */
    public List<Integer> getCandidates(String s) {
        String[] nombres = s.split(" ");
        int row = Integer.parseInt(nombres[0]);
        int column = Integer.parseInt(nombres[1]);
        List<Integer> candidates = new ArrayList<Integer>();
        candidates = cd.getCandidates(row, column);
        return candidates;
    }
    
    /**
     * 
     * @return retorna una llista de posicions amb les caselles diferents
     */
    public List<String> getDifferentCells() {
        try {
            return cd.getDifferentCells();
        } 
        catch (Exception e) {
        	return null;
        }
        
    }
    /**
     * 
     * @return Retorna la solució de la seguent casella no valida.
     */
    public int getNextSol() {
        try {
            return cd.getNextSol();
        } 
        catch (Exception e) {
        	return 0;
        }
        
    }
    /**
     * 
     * @param s Coordenades de la casella
     * @return Retorna la solucio de la casella amb posicio s
     */
    public int getCellResolved(String s) {
        try {
            return cd.getCellSol(s);
        } 
        catch (Exception e) {
        	return 0;
        }
        
    }
}
