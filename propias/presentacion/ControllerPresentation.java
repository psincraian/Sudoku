package propias.presentacion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;


import propias.dominio.clases.*;
import propias.dominio.controladores.*;

/**
 * 
 * @author Brian Martinez Alvarez i petites modificacions per Petru Rares
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
	ViewSelectSudoku.selectSudoku,
	ViewProfile.ProfileReturnListener {
	
	JFrame frame;
    ControllerDomain cd;
    boolean isGuest; 
    boolean createSudoku; // true: crear un sudoku, false: partidaRapida
    boolean loadMatch;
    CaracteristiquesPartida caracteristiques;
    /**
     * Constructora
     */
    public ControllerPresentation() {
        cd = new ControllerDomain();
    }
    /**
     * Crea la vista
     */
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
    	createGUI();
        frame.getContentPane().removeAll();
        frame.getContentPane().setLayout(new BorderLayout());
        new ControllerStart(this,frame);
        revalidateContentPane(frame);
    }
    
    /**
     * Inicia el login
     */
    public void startUser(){
    	isGuest = false;
    	frame.getContentPane().removeAll();
        frame.setLayout(new BorderLayout());
    	new ControllerUserEntry(ControllerUserEntry.LOGIN_VIEW ,frame,this);
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
    	new ControllerUserEntry(ControllerUserEntry.REGISTRATION_VIEW ,frame,this);
    	revalidateContentPane(frame);
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
		int[][] sudoku;
    	if (loadMatch){
    		sudoku = cd.getSavedMatch(id);
    		playMatch(sudoku, false);
            updateSudokuCells(sudoku.length);
		}
    	else{
    		cd.selectSudoku(id);
    		sudoku = cd.createMatch(this.caracteristiques, isGuest);
    		playMatch(sudoku, this.caracteristiques.getTipusPartida() == 1);
    	}
		
    }
    
    @Override
    public void setBoardFast(String s){
    	ControllerViewBoard c = ControllerViewBoard.getInstance();
    	int mida = 0;
    	if (s.length() <= 81) mida = 81;
    	else if (s.length()<=256) mida = 256;
    	else c.sendMessage("Has posat massa valors");
    	int i = 0, j = 0;
    	String posx, posy, pos;
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
    
    /**
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
    	ControllerViewBoard c = ControllerViewBoard.getInstance();
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
    
    private void playMatch(int[][] sudoku, boolean competition) {
		frame.getContentPane().removeAll();
		frame.setLayout(new BorderLayout());
		new ControllerViewBoard(sudoku, ControllerViewBoard.VIEW_PLAY_SUDOKU,
			isGuest, competition, frame, this);
    	revalidateContentPane(frame);
    }
    
    private void updateSudokuCells(int size) {
    	ControllerViewBoard c = ControllerViewBoard.getInstance();
		for(int i= 0; i < size; ++i) {
			for(int j=0; j < size; ++j) {
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
       return cd.getDifferentCells(); 
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
      ControllerUserEntry.getInstance().sendMessage(result);
      if (result.equals("Login correcte") || result.equals("S'ha creat l'usuari correctament"))
    	  correct = true;
      return correct;
    }
    
    private void showRanking() {
    	 List<String> names = new ArrayList<String>();
         List<Long> values = new ArrayList<Long>();
         cd.getRanking(names,values);
         
         frame.getContentPane().removeAll();
         frame.setLayout(new BorderLayout());
         ViewRanking vr = new ViewRanking(names, values, this);
         frame.add(vr);
         revalidateContentPane(frame);
    }
    
    private void showCreateSudoku() {
    	frame.getContentPane().removeAll();
    	frame.setLayout(new GridBagLayout());
    	SelectSize sc = new SelectSize(this);
    	frame.add(sc);
    	revalidateContentPane(frame);
    }
    
	private void showLoadMatch() {
		List<String> id = cd.getIDMatches();
		if (id.size() == 0) {
			JOptionPane.showMessageDialog(null, "No hi ha partides guardades");
			showMainMenu();
		}
		else{
			frame.getContentPane().removeAll();
			frame.setLayout(new BorderLayout());
			ViewSelectSudoku vl = new ViewSelectSudoku(id, this);
			frame.add(vl, BorderLayout.CENTER);;
			revalidateContentPane(frame);
		}
	}
	
	private void showSelectCharacteristics() {
        frame.getContentPane().removeAll();
		SelectCharacteristics sc = new SelectCharacteristics(this);
		frame.add(sc);
		revalidateContentPane(frame);
	}
	
	private void showProfile() {
		frame.getContentPane().removeAll();
		frame.setLayout(new BorderLayout());
		ViewProfile vp = new ViewProfile(cd.returnMatches(), cd.returnTime(), cd.returnBestTime(), this);
		frame.add(vp);
		revalidateContentPane(frame);
	}
	
	private void showSelectFromBD(CaracteristiquesPartida caracteristiques) {
		List<String> list = cd.getIDSudokus(caracteristiques);
		if (list == null || list.size() == 0) {
			JOptionPane.showMessageDialog(null, "No hi ha partides guardades");
			showMainMenu();
		}
		else {
			frame.getContentPane().removeAll();
			frame.setLayout(new BorderLayout());
			ViewSelectSudoku vl = new ViewSelectSudoku(list, this);
			frame.add(vl, BorderLayout.CENTER);;
			revalidateContentPane(frame);
		}
	}
	
    @Override
    public void getParameters(CaracteristiquesPartida caracteristiques){
    	int sudoku[][];
    	if (caracteristiques.getNewSudoku()) {
    		sudoku = cd.createMatch(caracteristiques, isGuest);
    		playMatch(sudoku, caracteristiques.getTipusPartida() == 1);
    	} else
    		this.caracteristiques = caracteristiques;
    		showSelectFromBD(caracteristiques);
    }
    
	@Override
	public void getOption(OptionsMenu om) {
		switch (om) {
		case PartidaRapida:
			loadMatch = false;
			createSudoku = false;
			showSelectCharacteristics();
			break;
		case CargarPartida:
			loadMatch = true;
			showLoadMatch();
			break;
		case CrearSudoku:
			createSudoku = true;
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
        new ControllerViewBoard(m, ControllerViewBoard.VIEW_CREATE_SUDOKU,
    			isGuest, false, frame, this);;
        revalidateContentPane(frame);
	}
}