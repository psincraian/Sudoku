package propias.presentacion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.sun.corba.se.impl.protocol.BootstrapServerRequestDispatcher;

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
	VistaMenu.MenuButtonClicked,
	ViewSelectSudoku.selectSudoku {
	
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
    int mida = 0; //mida taulell
    
    /**
     * Constructora
     */
    public ControllerPresentation() {
    	try {
			javax.swing.SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
			        createGUI();
				}
			});
		} catch (InvocationTargetException | InterruptedException e) {
			e.printStackTrace();
		}
    	
        cd = new ControllerDomain();
    }
    
    public void createGUI() {
		frame = new JFrame("Sudoku");
        frame.getContentPane().setLayout(new java.awt.GridBagLayout());
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
        revalidateContentPane(frame);
    }
    
    /**
     * Inicia el login
     */
    public void startUser(){
    	isGuest = false;
        frame.getContentPane().removeAll();
    	cu = new ControllerUserEntry(ControllerUserEntry.LOGIN_VIEW ,frame,this);
    	revalidateContentPane(frame);
    }
    
    /**
     * Inicia com a usuari Convidat
     */
    public void startGuest(){
    	isGuest = true;
    	showMainMenu();
    }
    
    /**
     * Inicia la creacio d'un nou usuari
     */
    public void startNewUser(){
        frame.getContentPane().removeAll();
    	cu = new ControllerUserEntry(ControllerUserEntry.REGISTRATION_VIEW ,frame,this);
    	revalidateContentPane(frame);
    }
    
    /**
     * Comproba el boto premut
     */
    public class MouseManage extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			JButton button = (JButton)e.getSource();
			if (button.getText() == "Tornar") {
				showMainMenu();
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
    @Override
    public void showMainMenu() {
    	frame.getContentPane().removeAll();
        frame.setLayout(new java.awt.GridBagLayout());
    	VistaMenu vm = new VistaMenu(this);
        if (isGuest)
        	  vm.updateToGuestView();
        
        frame.add(vm);
        revalidateContentPane(frame);
    }
    
    private static void revalidateContentPane(JFrame frame) {
    	frame.revalidate();
    	frame.repaint();
        frame.pack();
        frame.setVisible(true);
    }
    
    @Override
    public void selectSudoku(String id) {
    	int[][] m = cd.getSavedMatch(id);
        playMatch(m);
    }
    
    @Override
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
    @Override
    public boolean checkBoard(){
        return cd.checkBoard();
    }
    
    /**
     * 
     * @param position Coordenades d'una Cel·la
     * @param value Valor a posar a la Cel·la
     */
    @Override
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
    @Override
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
    
    private void playMatch(int[][] sudoku) {
		frame.getContentPane().removeAll();
		frame.setLayout(new BorderLayout());
		
    	if (!isGuest)
    		new ControllerViewBoard(sudoku, sudoku[0].length, ControllerViewBoard.VIEW_PLAY_SUDOKU, 
    			ControllerViewBoard.USER_NOT_GUEST, frame, this);
    	else 
    		new ControllerViewBoard(sudoku, sudoku[0].length, ControllerViewBoard.VIEW_PLAY_SUDOKU, 
        			ControllerViewBoard.USER_GUEST, frame, this);
    	revalidateContentPane(frame);
    }
    
    // TODO
    private void loadSudoku() {
    	int[][] m = new int[10][10];
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
    
    /**
     * 
     * @param s Coordenades de una Cel·la
     * @return els candidats possibles per aquella casella
     */
    @Override
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
    @Override
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
    @Override
    public int getCellResolved(String s) {
    	return cd.getCellSol(s);
    }

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
    
    //TODO
    private void showRanking() {
    	 List<String> names = new ArrayList<String>();
         List<Long> values = new ArrayList<Long>();
         cd.getRanking(names,values);
         vr = new ViewRanking(names, values, this);
         vr.listener(new MouseManage());
    }
    
    private void showCreateSudoku() {
    	frame.getContentPane().removeAll();
    	frame.setLayout(new GridBagLayout());
    	SelectSize sc = new SelectSize(this);
    	frame.add(sc);
    	revalidateContentPane(frame);
    }
    
	// TODO
	private void showLoadMatch() {
		List<String> id = cd.getIDMatches();
		if (id.size() == 0) {
			JOptionPane.showMessageDialog(null, "No hi ha partides guardades");
			showMainMenu();
		}
		
		frame.getContentPane().removeAll();
		frame.setLayout(new BorderLayout());
		ViewSelectSudoku vl = new ViewSelectSudoku(id, this);
		frame.add(vl);
		revalidateContentPane(frame);
	}
	
	private void showSelectCharacteristics() {
        frame.getContentPane().removeAll();
		SelectCharacteristics sc = new SelectCharacteristics(this);
		frame.add(sc);
		revalidateContentPane(frame);
	}
	
	// TODO
	private void showProfile() {
		vp = new ViewProfile(getMatches(), getTime(), getBestTime());
		vp.listener(new MouseManage());
	}
	
    // TODO
    @Override
    public void getParameters(CaracteristiquesPartida caracteristiques){
    	int sudoku[][];
    	sudoku = cd.createMatch(caracteristiques);
       	playMatch(sudoku);
    }
    
	@Override
	public void getOption(OptionsMenu om) {
		switch (om) {
		case PartidaRapida:
			showSelectCharacteristics();
			break;
		case CargarPartida:
			showLoadMatch();
			break;
		case CrearSudoku:
			showCreateSudoku();
			break;
		case Ranking:
			showRanking();
			break;
		case Perfil:
			showProfile();
			break;
		case Sortir:
			exitApplication();
			break;
		}	
	}

	@Override
	public void getBack() {
		showMainMenu();
	}

	@Override
	public void getSize(int size) {
        int[][] m = cd.newSudoku(size);
        
        frame.getContentPane().removeAll();
        frame.setLayout(new BorderLayout());
        new ControllerViewBoard(m, size, ControllerViewBoard.VIEW_CREATE_SUDOKU, 
    			ControllerViewBoard.USER_NOT_GUEST, frame, this);
        revalidateContentPane(frame);
	}
}