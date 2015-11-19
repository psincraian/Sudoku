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
 * @author Brian
 *
 */
public class ControllerPresentation {
    /**
     * @contructor: ControllerDomain
     */
    
    ControllerDomain cd;
    String name;
    boolean create;
    
    public ControllerPresentation() {
        cd = new ControllerDomain();
    }
    /**
     * 
     */
    public void start() {

        ViewStart vi = new ViewStart();
        vi.show();
        Options op = vi.getOption();
        if (op == Options.IniciarSessio) {
            UserEntry vl = new ViewLogin();
            try {
                name = vl.getName();
                if (login(name,(String)vl.getPassword())) {
                    cd.createStadistics();
                    cd.setRankingController(true);
                    Menu(false);
                }
                else {
                    vl = new ViewLogin();
                    System.out.println(vl.getPassword());
                }
            } 
            catch (Exception e) {
                System.out.println("Exception in start");
            }

        }
        else if (op == Options.IniciarConvidat) {
            name = "Convidat";
            Menu(true);
        }
        else if (op == Options.RegistrarUsuari){
            UserEntry cu = new ViewCreateUser();
          
        }
        else if (op == Options.Sortir) System.exit(0);
        
    }
    /**
     * 
     * @param user
     * @param pass
     * @throws Exception
     */
    private boolean login(String user, String pass)  {
          boolean correct = cd.loginCorrectUser(user, pass);
          if (correct) return true; 
          return false;
    }
    
    public void getBack() {
        if (name.equals("Convidat"))
            Menu(true);
        else
            Menu(false);
    }
    
    /**
     * 
     * @param convidat
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
          create = false;
          VistaSeleccionarCaracteristiques sc = new VistaSeleccionarCaracteristiques();
          int[][] m = cd.createMatch(sc.obtenirCaracteristiques());
          if (!isCompetition()) play(m,false,false);
          else play(m,true,false);
      }
      if (om == OptionsMenu.CargarPartida) {
          ViewLoadMatch vcp = new ViewLoadMatch();
          String s =  vcp.getLoadMatch();
          List<int[][]> m = cd.getSavedMatches(s);
          play(m.get(0),true,true);
          
      }
      else if (om == OptionsMenu.CrearSudoku) {
          create = true;
          VistaSeleccionarCaracteristiques sc = new VistaSeleccionarCaracteristiques();
          int mida = sc.obtenirMida();
          int m[][] = new int[mida][mida];
          for(int i=0; i< mida; ++i){
              for(int j=0; j< mida; ++j) m[i][j] = 0;
          }
          cd.newMatch(mida);
          ControllerPresentationBoard c = new ControllerPresentationBoard(m, m[0].length,1,this);
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
    public Boolean isCompetition(){
        return cd.isCompetition();
    }
    public boolean checkBoard(){
        return cd.checkBoard();
    }

    public void updateCell(String position, int value){
        cd.updateCell(position, value);
    }
    public void saveBoard(){
        cd.saveBoard();
    }
    /**
     * 
     * @return
     */
    private int detect(){
        System.out.println("Prem el 0 y dona a enter per tornar a l'inici");
        Scanner scanner = new Scanner(System.in);
        int op = -1;
        while (op == -1) {
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
     * @return
     */
    public long[] getMatches(){
        return cd.returnMatches();
    }
    /**
     * 
     * @return
     */
    public long[] getTime(){
        return cd.returnTime();
    }
    /**
     * 
     * @return
     */
    public long[] getBestTime(){
        return cd.returnBestTime();
    }
    /**
     * 
     * @return
     */
    public List<String> getIDMatches(){
        return cd.getIDMatches();
    }
    /**
     * 
     * @param m
     * @param competicio
     */
    private void play(int[][] m, boolean competicio, boolean save)  { //save indica si la partida es una que ja existia

      ControllerPresentationBoard c = new ControllerPresentationBoard(m, m[0].length,0,this);
      if (save) {
          for(int i= 0; i<m[0].length; ++i) {
              for(int j=0; j<m[0].length; ++j) {
                  int res = cd.modify(i,j);
                  int cood = (i*10) + j;
                  if (res != 0) c.updateBoard(Integer.toString(cood),Integer.toString(res));
              }
          }
      }
      boolean solved = cd.compareSolution();
      if (solved && competicio) {
          cd.updateRanking(true);
          cd.updateRanking(false);
      }
      
    }
    /**
     * 
     * @param s
     * @return
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
     * @return
     */
    public List<String> getDifferentCells() {
        try {
            return cd.getDifferentCells();
        } 
        catch (Exception e) {
        }
        return null;
    }
    /**
     * 
     * @return
     */
    public int getNextSol() {
        try {
            return cd.getNextSol();
        } 
        catch (Exception e) {
        }
        return 0;
    }
    /**
     * 
     * @param s
     * @return
     */
    public int getCellResolved(String s) {
        try {
            return cd.getCellSol(s);
        } 
        catch (Exception e) {
        }
        return 0;
    }
}
