package propias.presentacion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.ExecutionException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

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
	ViewMenu.MenuButtonClicked,
	ViewSelectSudoku.selectSudoku,
	ViewProfile.ProfileReturnListener {
	
	JFrame frame;
    ControllerDomain cd;
    boolean isGuest; 
    boolean createSudoku; // true: crear un sudoku, false: partidaRapida
    boolean loadMatch;
    CaracteristiquesPartida caracteristiques;
    int mida;
    
    /**
     * Constructora
     */
    public ControllerPresentation() {
        cd = new ControllerDomain();
        createGUI();
    }
    /**
     * Crea la vista
     */
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
        ControllerUserEntry cu = ControllerUserEntry.getInstance();
    	cu.launchView(ControllerUserEntry.LOGIN_VIEW ,frame,this);
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
        ControllerUserEntry cu = ControllerUserEntry.getInstance();
    	cu.launchView(ControllerUserEntry.REGISTRATION_VIEW ,frame,this);
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
    	ViewMenu vm = ViewMenu.getInstance(this);
        if (isGuest)
        	  vm.updateToGuestView();
        
        frame.add(vm);
        revalidateContentPane(frame);
    }
    /**
     * Dona propietats a la vista
     * @param frame Vista del programa
     */
    private static void revalidateContentPane(JFrame frame) {
    	frame.revalidate();
    	frame.repaint();
        frame.pack();
        frame.setVisible(true);
    }
    /**
     * Selecciona el sudoku amb identificador id
     * @param id Identidifador del sudoku
     */
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
    /** Omple un sudoku que es vol crear, ràpidament
     * @param s Sudoku en format cadena de caracters 
     */
    @Override
    public void setBoardFast(String s){
    	ControllerViewBoard c = ControllerViewBoard.getInstance();
    	int mida = 0;
    	if (s.length() <= 81) mida = 81;
    	else if (this.mida == 256 && s.length()<=256) mida = 256;
    	else c.sendMessage("Has posat massa valors");
    	int i = 0, j = 0;
    	String posx, posy, pos;
    	boolean maximum = false;
    	for (int position=0; position < mida && !maximum; ++position){
    		if ((mida == 81 && j == 9) ||(mida == 256 && j == 16)){
				j = 0;
				++i;
			}
    		String res = cd.setBoardFast(s, position, mida,i,j);
			posx = String.valueOf(i);
			posy = String.valueOf(j);
			pos = posx + " " + posy;
			if (!res.equals(".")) c.updateBoard(pos,res);
    		++j;
    	}
    }
    
    /**
     * Comproba si es pot guardar el sudoku creat
     * @return si el taulell actual compleix les regles del joc i te solucio unica
     */
    @Override
    public boolean checkBoard(){
    	ControllerViewBoard c = ControllerViewBoard.getInstance();
        if(!cd.checkLaws()) {
        	c.sendMessage("No s'ha pogut guardar, el teu Sudoku no compleix les regles del joc.");
        	return false;
        }
        else if (!cd.checkBoard()) {
        	c.sendMessage("No s'ha pogut guardar, no té solució única.");
        	return false;
        }
        else if (!cd.numbersAtCreate()){
    		c.sendMessage("No pots posar tants valors.");
    		return false;
    	}
    	return true;
    }
    /**
     * Indica que s'ha completat el sudoku correctament
     */
    public void boardCompleted(){
    	ControllerViewBoard c = ControllerViewBoard.getInstance();
        if(!createSudoku && cd.takePointsBoard() != 0 && this.caracteristiques.getTipusPartida() == 1){
        	c.sendMessage("Felicitats, has omplert el sudoku. Puntuació: " + cd.takePointsBoard());
        	showRankingSudoku();
        }
        else if (this.caracteristiques.getTipusPartida() == 0 && !createSudoku && cd.takePointsBoard() != 0){
        	c.sendMessage("Felicitats, has omplert el sudoku.");
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
     * Juga una partida
     * @param sudoku Sudoku de la partida a jugar
     * @param competition Indica si es una partida de competicio
     */
    private void playMatch(int[][] sudoku, boolean competition) {
    	if(competition) cd.startTimer();
		frame.getContentPane().removeAll();
		frame.setLayout(new BorderLayout());
		ControllerViewBoard cb = ControllerViewBoard.getInstance();
		cb.launchView(sudoku, ControllerViewBoard.VIEW_PLAY_SUDOKU,
			isGuest, competition, frame, this);
    	revalidateContentPane(frame);
    }
    /**
     * Actualitza les caselles del sudoku
     * @param size indica la mida del sudoku
     */
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
     * Soluciona la casella indicada per la posicio s
     * @param s Coordenades de la casella
     * @return Retorna la solucio de la casella amb posicio s
     */
    @Override
    public int getCellResolved(String s) {
    	int number = cd.getCellSol(s);
    	if(cd.updateCell(s,number)) {
    		boardCompleted();
    		return 0;
    	}
    	if (!cd.hintAvailable()) {
    		ControllerViewBoard c = ControllerViewBoard.getInstance();
    		c.sendMessage("No pots utilitzar aquesta ajuda.");
    		return 0;
    	}
    	else return number;
    }
	/**
     * 
     * @param position Coordenades d'una Cel·la
     * @param value Valor a posar a la Cel·la
     */
    @Override
    public void updateCell(String position, int value){
    	Boolean check = cd.updateCell(position, value);
    	if(check){
	    	if (!createSudoku) boardCompleted();
    	}
    }
    /**
     * Opcions del menu inicial
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
      ControllerUserEntry.getInstance().sendMessage(result);
      if (result.equals("Login correcte") || result.equals("S'ha creat l'usuari correctament"))
    	  correct = true;
      return correct;
    }
    /**
     * Mostra el ranking global
     */
    private void showRanking() {
    	 List<String> names = new ArrayList<String>();
         List<Long> values = new ArrayList<Long>();
         cd.getRanking(names,values);
         frame.getContentPane().removeAll();
         frame.setLayout(new BorderLayout());
         ViewRanking vr = ViewRanking.getInstance();
         vr.launchView(names, values, this,true);
         List<String> info = new ArrayList<String>(); // name, value, pos
         cd.addToRanking(info);
         if(info != null && info.size() != 0)vr.addPosUser(Integer.parseInt(info.get(2)), Long.parseLong(info.get(1)), info.get(0));
         frame.add(vr);
         revalidateContentPane(frame);
    }
    /**
     * Mostra el Ranking sudoku
     */
    private void showRankingSudoku() {
   	 List<String> names = new ArrayList<String>();
        List<Long> values = new ArrayList<Long>();
        cd.getRankingSudoku(names,values);
        frame.getContentPane().removeAll();
        frame.setLayout(new BorderLayout());
        ViewRanking vr = ViewRanking.getInstance(); 
        vr.launchView(names, values, this,false);
        List<String> info = new ArrayList<String>(); // name, value, pos
        cd.addtoRankingSudoku(info);
        if(info != null && info.size() != 0)vr.addPosUser(Integer.parseInt(info.get(2)), Long.parseLong(info.get(1)), info.get(0));
        frame.add(vr);
        revalidateContentPane(frame);
   }
    /**
     * Mostra la creacio d'un sudoku
     */
    private void showCreateSudoku() {
    	frame.getContentPane().removeAll();
    	frame.setLayout(new GridBagLayout());
    	SelectSize sc = SelectSize.getInstance(this);
    	frame.add(sc);
    	revalidateContentPane(frame);
    }
    /**
     * Mostra les partides guardades
     */
	private void showLoadMatch() {
		List<List<String>> id = cd.getIDMatchesAndMaker();
		if (id.size() == 0) {
			JOptionPane.showMessageDialog(null, "No hi ha partides guardades");
			showMainMenu();
		}
		else{
			frame.getContentPane().removeAll();
			frame.setLayout(new BorderLayout());
			ViewSelectSudoku vl = ViewSelectSudoku.getInstance(); 
			vl.launchView(id, this);
			frame.add(vl, BorderLayout.CENTER);;
			revalidateContentPane(frame);
		}
	}
	/**
	 * Mostra les caracteristiques a escollir per jugar una partida
	 */
	private void showSelectCharacteristics() {
        frame.getContentPane().removeAll();
		SelectCharacteristics sc = SelectCharacteristics.getInstance(this);
		if (isGuest)
			sc.updateToGuest();
		frame.add(sc);
		revalidateContentPane(frame);
	}
	/**
	 * Mostra el perfil de l'usuari
	 */
	public void showProfile() {
		frame.getContentPane().removeAll();
		frame.setLayout(new BorderLayout());
		ViewProfile vp = new ViewProfile(cd.returnMatches(), cd.returnTime(), cd.returnBestTime(), this);
		frame.add(vp);
		revalidateContentPane(frame);
	}
	/**
	 * Mostra les partides que compleixen les caracteristiques desitjades que estan a la BBDD
	 * @param caracteristiques caracteristiques de la partida
	 */
	private void showSelectFromBD(CaracteristiquesPartida caracteristiques) {
		List<List<String>> list = cd.getIDSudokusAndMaker(caracteristiques);
		if (list == null || list.size() == 0) {
			JOptionPane.showMessageDialog(null, "No hi ha partides guardades");
			showMainMenu();
		}
		else {
			frame.getContentPane().removeAll();
			frame.setLayout(new BorderLayout());
			ViewSelectSudoku vl = ViewSelectSudoku.getInstance(); 
			vl.launchView(list, this);
			frame.add(vl, BorderLayout.CENTER);;
			revalidateContentPane(frame);
		}
	}
	/**
	 * Obte les caracteristiques de la partida a jugar
	 * @param caracteristiques Caracteristiques de la partida
	 */
    @Override
    public void getParameters(CaracteristiquesPartida caracteristiques){
    	if(caracteristiques.getDificultat() == 0 && caracteristiques.getGivenNumbers() > 0.8 * (int) Math.pow(caracteristiques.getMida(),2)
    		||caracteristiques.getDificultat() == 1 && caracteristiques.getGivenNumbers() > 0.42 * (int) Math.pow(caracteristiques.getMida(),2)
    		|| caracteristiques.getDificultat() == 2 && caracteristiques.getGivenNumbers() > 0.32 * (int) Math.pow(caracteristiques.getMida(),2)){
    		JOptionPane.showMessageDialog(null, "Has demanat massa valors");
    		showSelectCharacteristics();
    	}
    	else{
	    	int sudoku[][];
	    	if (caracteristiques.getNewSudoku()) {
	    		sudoku = createMatch(caracteristiques);
	    		this.caracteristiques = caracteristiques;
	    		playMatch(sudoku, caracteristiques.getTipusPartida() == 1);
	    	} 
	    	else{
	    		this.caracteristiques = caracteristiques;
	    		showSelectFromBD(caracteristiques);
	    	}
    	}
    }
    
    private int[][] createMatch(CaracteristiquesPartida caracteristiques) {
    	frame.getContentPane().removeAll();
    	LoadingView view = LoadingView.newInstance();
		frame.add(view);
		view.setVisible(true);
		view.setOpaque(true);
		revalidateContentPane(frame);
		int[][] sudoku = null;
		
		SwingWorker<int[][],Void> worker = new SwingWorker<int[][], Void>() {
		    @Override
		    protected int[][] doInBackground() {
		    	return cd.createMatch(caracteristiques, isGuest);
		    }
		};
		worker.execute();
		
		try {
			sudoku = worker.get();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return sudoku;
    }
    
    /**
     * Opcions del menu principal
     */
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
		case EliminarUsuari:
			deleteUser();
			JOptionPane.showMessageDialog(null, "L'usuari s'ha eliminat correctament");
			start();
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
	/**
	 * Torna al menu principal
	 */
	@Override
	public void getBack() {
		showMainMenu();
	}
	/**
	 * Canvia el nom d'usuari
	 * @param name Nou nom a posar a l'usuari
	 */
	public void changeUserName(String name){
		if(cd.checkChangeName(name)) {
			cd.changeUserName(name);
			JOptionPane.showMessageDialog(null, "Nom d'usuari canviat");
			showMainMenu();
		}
		else {
			JOptionPane.showMessageDialog(null, "Nom d'usuari no valid");
			showMainMenu();
		}
	}
	/**
	 * Canvia la contrasenya de l'usuari
	 * @param pass1 Nova contrasenya de l'usuari
	 * @param pass2 Nova contrasenya de l'usuari
	 */
	public void changeUserPass(String pass1, String pass2){
		if(cd.checkChangePass(pass1,pass2)) {
			cd.changeUserPass(pass1);
			JOptionPane.showMessageDialog(null, "Constrasenya canviada");
			showMainMenu();
		}
		else {
			JOptionPane.showMessageDialog(null, "Constrasenyes no valides");
			showMainMenu();
		}
	}
	/**
	 * Elimina l'usuari
	 */
	public void deleteUser(){
		cd.deleteUser();
	}
	/**
	 * Obte la mida del sudoku a crear
	 */
	@Override
	public void getSize(int size) {
        int[][] m = cd.newSudoku(size);
        this.mida = size * size;
        frame.getContentPane().removeAll();
        frame.setLayout(new BorderLayout());
        ControllerViewBoard cb = ControllerViewBoard.getInstance();
        cb.launchView(m, ControllerViewBoard.VIEW_CREATE_SUDOKU,
    			isGuest, false, frame, this);;
        revalidateContentPane(frame);
	}
}
