package propias.presentacion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import propias.dominio.clases.*;
import propias.dominio.controladores.*;

/**
 * 
 * @author Brian Martinez Alvarez
 *
 */
public class ControllerPresentation implements 
	SelectSize.GetParametersListener, 
	SelectCharacteristics.GetParametersListener,
	ControllerStart.GetOptionsListInterface,
	ControllerUserEntry.userEntry,
	ControllerViewBoard.viewBoard,
	ViewRanking.ranking,
	VistaMenu.MenuButtonClicked {
	
	JFrame frame;
    ControllerDomain cd;
    ControllerUserEntry cu;
    ControllerViewBoard c;
    boolean isGuest; // nom de l'usuari
    boolean createSudoku; // true: crear un sudoku, false: partidaRapida
    ViewRanking vr;
    ViewProfile vp;
    ViewLoadMatch vl;
    List<String> id; //ids de partides guardades
    int view = 0; // tipus de vista(perfil, ranking, load match)
    int mida = 0; //mida taulell
    
    /**
     * Constructora
     */
    public ControllerPresentation() {
    	javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
		    	frame = new JFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		        frame.getContentPane().setLayout(new java.awt.GridBagLayout());
			}
    	});
        cd = new ControllerDomain();
        createGUI();
    }
    
    public void createGUI() {
		frame = new JFrame("Sudoku");
		frame.setBackground(Color.WHITE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setMaximumSize(new Dimension(1000, 750));
		frame.setPreferredSize(new Dimension(1000, 750));		
	}
    
    /**
     * Inicia el joc
     */
    public void start() {
        frame.getContentPane().removeAll();
        new ControllerStart(this,frame);
        frame.setVisible(true);
        frame.pack();
    }
    
    /**
     * Inicia el login
     */
    public void startUser(){
        frame.getContentPane().removeAll();
    	cu = new ControllerUserEntry(ControllerUserEntry.LOGIN_VIEW ,frame,this);
    	frame.setVisible(true);
        frame.pack();
    }
    
    /**
     * Inicia com a usuari Convidat
     */
    public void startGuest(){
    	frame.getContentPane().removeAll();
    	frame.setVisible(true);
        frame.pack();    }
    
    /**
     * Inicia la creacio d'un nou usuari
     */
    public void startNewUser(){
        frame.getContentPane().removeAll();
    	cu = new ControllerUserEntry(ControllerUserEntry.REGISTRATION_VIEW ,frame,this);
    	frame.setVisible(true);
        frame.pack();
    }
    
    /**
     * Comproba el boto premut
     */
    public class MouseManage extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			JButton button = (JButton)e.getSource();
			if (button.getText() == "Tornar") {
				if (view == 1) vp.disableView();
				else if (view == 3) vl.disableView();
				showMainMenu();
			}
			else {
				for(int i=0; i< id.size(); ++i){
					if(button.getText() == id.get(i)){
						vl.disableView();
						loadMatch(id.get(i));
					}
				}
			}
		}
							
	}
    /**
     * Surt de l'aplicacio
     */
    public void exitApplication(){
    	System.exit(0);
    }
      
    /**
     * Envia a l'usuari directament al menu principal
     */
    public void showMainMenu() {
    	frame.getContentPane().removeAll();
        VistaMenu vm = new VistaMenu(this);
        if (isGuest) {
      	  vm.updateToGuestView();
        }
        
        frame.getContentPane().add(vm, new java.awt.GridBagConstraints());
        frame.pack();
        frame.setVisible(true);
    }
    
    /**
     * Selecciona la partida a carregar
     * @param match
     */
    public void loadMatch(String match){
    	int[][] m = cd.getSavedMatch(match);
        play(m,true,true);
    }

    @Override
    public void getParameters(CaracteristiquesPartida caracteristiques){
    	if (createSudoku){
	    	int mida = caracteristiques.getMida();
	        this.mida = mida;
	        int m[][] = new int[mida][mida];
	        for(int i=0; i< mida; ++i){
	            for(int j=0; j< mida; ++j) m[i][j] = 0;
	        }
	        cd.newSudoku(mida);
	        frame.getContentPane().removeAll();
	        frame.setLayout(new BorderLayout());
	        new ControllerViewBoard(m, m[0].length,1,false,frame,this);
	        frame.setVisible(true);
	        frame.pack();
    	}
    	
    	else {
    		int[][] m;
    		if(!caracteristiques.newSudoku) {
    			selectSudoku("e1");
    		}
    		m = cd.createMatch(caracteristiques);
            if (!isCompetition()) play(m,false,false);
            else play(m,true,false);
    	}
    }
    
    public void selectSudoku(String id){
    	cd.selectSudoku(id);
    }
    
    public void setBoardFast(String s){
    	int mida = 0;
    	if (s.length() <= 81) mida = 81;
    	else if (s.length() > 81 && s.length()<=256 && this.mida == 16) mida = 256;
    	else c.sendMessage("Has posat massa valors");
    	int i = 0, j = 0;
    	String posx;
    	String posy;
    	String pos;
    	for (int position=0; position < mida; ++position){
    		if ((mida == 81 && j == 9) ||(mida == 256 && j == 16)){
				j = 0;
				++i;
			}
    		String res = cd.setBoardFast(s, position, mida,i,j);
    		posx = String.valueOf(i);
			posy = String.valueOf(j);
			pos = posx + " " + posy;
			if (res != ".") c.updateBoard(pos,res);
    		++j;
    	}
    }
    public String charHexa(char a){
    	if (a >='0' && a <= '9') return String.valueOf(a);
    	else if (a == 'A') return String.valueOf(10);
    	else if (a == 'B') return String.valueOf(11);
    	else if (a == 'C') return String.valueOf(12);
    	else if (a == 'D') return String.valueOf(13);
    	else if (a == 'E') return String.valueOf(14);
    	else if (a == 'F') return String.valueOf(15);
    	else if (a == 'G') return String.valueOf(16);
    	else return String.valueOf(0);
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
        if(!createSudoku && cd.takePointsBoard() != 0){
        	c.sendMessage("Felicitats, has omplert el sudoku. Ranking del Sudoku: " + cd.takePointsBoard());
        	showMainMenu();
        }
    }
    /**
     * Guarda la partida actual
     */
    public void saveBoard(){
        cd.saveBoard(createSudoku);
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
        frame.getContentPane().removeAll();
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);
        frame.pack();
        
    	if (!isGuest)
    		new ControllerViewBoard(m, m[0].length, ControllerViewBoard.VIEW_PLAY_SUDOKU, 
    			ControllerViewBoard.USER_NOT_GUEST, frame, this);
    	else 
    		new ControllerViewBoard(m, m[0].length, ControllerViewBoard.VIEW_PLAY_SUDOKU, 
        			ControllerViewBoard.USER_NOT_GUEST, frame, this);
    
    	if (save) {
    		for(int i= 0; i<m[0].length; ++i) {
    			for(int j=0; j<m[0].length; ++j) {
    				int res = cd.modify(i,j);
    				String row = Integer.toString(i);
    				String col = Integer.toString(j);
    				String cood = row + " " + col; 
    				if (res != 0) c.updateBoard(cood,Integer.toString(res));
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

	/**
	 * @author Petru Rares Sincraian
	 */
	@Override
	public void getOption(Options option) {
		switch (option) {
		case IniciarSessio:
			startUser();
			break;
		case IniciarConvidat:
			startGuest();
			break;
		case RegistrarUsuari:
			startNewUser();
			break;
		case Sortir:
			exitApplication();
			break;
		}
	}
	
    /**
     * 
     * @param credentials Credencials de l'usuari a registrar o loguejar
     * @return si el logueig o la creacio d'usuari s'ha fet correctament
     */
    @Override
    public boolean checkInfoUser(List<String> credentials) {
      boolean correct = false;
      String result;
      if(credentials.size() == 3)
    	  result = cd.checkNewUser(credentials);
      else
    	  result = cd.checkLogin(credentials);

      if (result.equals("Login correcte") || result.equals("S'ha creat l'usuari correctament"))
    	  correct = true;
      cu.sendMessage(result);
      return correct;
    }

    
	@Override
	public void getOption(OptionsMenu om) {
		if (om == OptionsMenu.PartidaRapida) {
	          VistaSeleccionarCaracteristiques sc = new VistaSeleccionarCaracteristiques();
	          int[][] m = cd.createMatch(sc.obtenirCaracteristiques());
	          if (!isCompetition()) play(m,false,false);
	          else play(m,true,false);
	      }
	      if (om == OptionsMenu.CargarPartida) {
	    	    view = 3; // load match
	    	    List<String> id = cd.getIDMatches();
	    	    this.id = id;
	    	    vl = new ViewLoadMatch(id, this);
		    if (id.size() == 0) {
	  		JOptionPane.showMessageDialog(null, "No hi ha partides guardades");
	  		vl.disableView();
	  		showMainMenu();
		    }
		    else{
		  		
	  		for(int i=0; i<id.size(); ++i){
	  			vl.listeners(new MouseManage(), vl.buttonList.get(i));
	  		}
	  		vl.listener(new MouseManage());
		  	}
		  
	      } else if (om == OptionsMenu.CrearSudoku) {
	          VistaSeleccionarCaracteristiques sc = new VistaSeleccionarCaracteristiques();
	          int mida = sc.obtenirMida();
	          this.mida = mida;
	          int m[][] = new int[mida][mida];
	          for(int i=0; i< mida; ++i){
	              for(int j=0; j< mida; ++j) m[i][j] = 0;
	          }

	          new ControllerViewBoard(m, m[0].length, ControllerViewBoard.VIEW_CREATE_SUDOKU, 
	      			ControllerViewBoard.USER_NOT_GUEST, frame, this);;
	      
	      } else if (om == OptionsMenu.Ranking) {
	    	  view = 2; // ranking
	    	  List<String> names = new ArrayList<String>();
	          List<Long> values = new ArrayList<Long>();
	          cd.getRanking(names,values);
	          vr = new ViewRanking(names, values, this);
	          vr.listener(new MouseManage());
	      }
	      else if (om == OptionsMenu.Perfil) {
	    	  view = 1; // perfil
	    	  vp = new ViewProfile(getMatches(), getTime(), getBestTime());
	    	  vp.listener(new MouseManage());
	      }
	      else if (om == OptionsMenu.Sortir) start();
		
	}
}