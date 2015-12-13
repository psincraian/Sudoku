package propias.dominio.controladores;

import java.util.*;

import propias.dominio.clases.*;
import propias.dominio.controladores.generator.CntrlSearchLevel;
import propias.dominio.controladores.generator.CntrlSudokuCreator;
import propias.dominio.controladores.generator.CntrlSudokuGenerator;
import propias.dominio.controladores.generator.CntrlSudokuHelps;

/**
 * 
 * @author Brian
 *
 */
public class ControllerDomain {
	
	
    ControllerCasting cc;
    RankingGlobal rg;
    Stadistics stad;
    int size = 0;
    int dificult = 0;
    int type = 0;
    String id;
    Match match;
    Sudoku sudoku;
    Usuari user;
    boolean createSudoku; //indica si s'esta creant un nou sudoku o no
    int cont; //indica les caselles posades per l'usuari al jugar una partida
    int points; // punts de la partida
    ErrorUserEntry errorUser;
    
    /**
     * Constructora
     */
    public ControllerDomain(){
        cc = new ControllerCasting();
    }
    /**
     * Comproba les credencials d'un usuari
     * @param credentials Contiene la informacion del usuario
     * @return Si el login o el crear usuario se ha relaizado correctamente
     */
    public String checkNewUser(List<String> credentials){
            try {
            	if(cc.existsUser(credentials.get(0))) 
            		return "El nom d'usuari ja existeix";
                CreateUser cu = new CreateUser(credentials.get(0),credentials.get(1),credentials.get(2));
                errorUser = cu.isEqual();
                if (errorUser.equals(ErrorUserEntry.LOGIN_FAIL_USER_EMPTY)) 
                	return "Usuari ha de tenir un nom";
                else if(errorUser.equals(ErrorUserEntry.LOGIN_FAIL_EMPTY_PASSWORDS)) 
                	return "Les contrasenyes no poden ser buides";
                else if(errorUser.equals(ErrorUserEntry.LOGIN_FAIL_PASSWORDS_DISTINCT)) 
                	return "Les contrasenyes no coincideixen"; 
                else if(errorUser.equals(ErrorUserEntry.LOGIN_FAIL_ONLY_VALID_CHARS_AND_NUMBERS))  
                	return "Les contrasenyes nomes poden tenir lletres i numeros";
                else {
                	user = cu.createUser();
                	cc.createUser(user);
                	cc.userDBInit(user.consultarNom());
                    stad = cc.getStadistics();
                    rg = cc.getRankingGlobal();
                	return "S'ha creat l'usuari correctament";
                } 
            }
            catch (Exception e) {
            	e.printStackTrace();
            	return null;
            }
    }
    public String checkLogin(List<String> credentials){
        try {
        	if(!credentials.get(0).equals("") && cc.existsUser(credentials.get(0))) {
            	user = cc.getUser(credentials.get(0));
                String passWordOk = user.getPassword();
                if (passWordOk.equals(credentials.get(1))) {
                    cc.userDBInit(user.consultarNom());
                    stad = cc.getStadistics();
                    rg = cc.getRankingGlobal();
                    return "Login correcte";
                }
                else return "Contrasenya incorrecte";
            }
            else return "Nom incorrecte";
        } 
        catch (Exception e) {
        	e.printStackTrace();
        	return null;
        }
    }
    /**
     * Crea una partida a partir d'unes caracteristiques posades per l'usuari
     * @param c Contiene las caracteristicas de la nueva partia
     * @return Una nueva partida con caracteristicas c
     */
    public int[][] createMatch(CaracteristiquesPartida c, Boolean isGuest){
        try {
        	createSudoku = false; // no es crea un sudoku nou
        	if (!c.getNewSudoku()) {
        		cont = c.getGivenNumbers();
        		points = 0;
        		System.out.println("cont: " + cont);
        	    Sudoku s = cc.getSudoku(c.getMida(), c.getDificultat(), this.id);
        	    this.size = c.getMida();
            	this.dificult = c.getDificultat();
            	this.type = c.getTipusPartida();
            	if (isGuest) match = new MatchTraining("Convidat", s);
            	else if(type == 0) match = new MatchTraining(this.user.consultarNom(), s);
                else match = new MatchCompetition(this.user.consultarNom(), s);
            	int[][] matrix = convertToMatrix(s.getSudoku());
            	return matrix;
        	}
			else{
				cont = c.getGivenNumbers();
				points = 0;
        		CntrlSudokuCreator cs = new CntrlSudokuCreator();
        		CntrlSudokuGenerator csg = new CntrlSudokuGenerator(c.getMida());
        		Sudoku s = cs.create(c.getDificultat(), csg.generateBoard());
        		s.setMaker("creacion automatica");
        	    String id = cc.introduceSudoku(s);
        	    this.id = id;
        	    s = cc.getSudoku(c.getMida(), c.getDificultat(), id);
        	    this.size = c.getMida();
            	this.dificult = c.getDificultat();
            	this.type = c.getTipusPartida();
            	if (isGuest) match = new MatchTraining("Convidat", s);
            	else if(type == 0) match = new MatchTraining(this.user.consultarNom(), s);
                else if (type == 1) match = new MatchCompetition(this.user.consultarNom(), s);
            	int[][] matrix = convertToMatrix(s.getSudoku());
            	return matrix;
    		}	
            
        } catch (Exception e) {
        	e.printStackTrace();
        	return null;
        }
    }
    public List<String> getIDSudokus(CaracteristiquesPartida c){
    	try {
			return cc.getIDSudokus(c.getMida(), c.getDificultat());
		} catch (Exception e) {
			e.printStackTrace();
        	return null;
		}
    }
    public void selectSudoku(String id){
    	this.id = id;
    }
    /**
 	* Converteix un string a una matriu
    * @param s String a convertir en int[][].
  	* @return int[][] representant el sudoku.
  	*/
	public int[][] stringToMatrix(String s) throws Exception {
		int size = (int) Math.sqrt(s.length());
		int[][] board = new int[size][size];
		for(int i = 0; i < size*size; i++){
		    char c = s.charAt(i);
		    int row = i / size;
		    int col = i % size;
		    if(c != '.' && c >= '1' && c <= '9'){
		        board[row][col] = c - '0';
		    }
	        else if (c != '.'){
		        board[row][col] = c - 'A' + 10;
	        }
		}		
	    return board;
	}
    public int[][] newSudoku(int size){
    	createSudoku = true;
    	cont = 0;
    	type = 0;
    	sudoku = new Sudoku(new Board(size),new Board(size), this.user.consultarNom());
    	return sudoku.getSudoku().getMatrix();
    }
    /**
     * Permet crear un nou taulell rapidament a partir d'una cadena de caracters que conte
     * tots els valors de les caselles. Caselles buides es representen amb un punt.
     * @param s Contingut de totes les caselles
     * @param position posicio de la casella
     * @param mida mida del taulell
     * @return valor de la casella a posar
     */
    public String setBoardFast(String s, int position, int mida,int i,int j){
    	if (mida == 81){
			if (position < s.length() && s.charAt(position) >= '0' && s.charAt(position) <= '9') {
				try {
					Position p = new Position();
					p.setRow(i);
					p.setColumn(j);
					sudoku.setCell(p, Character.getNumericValue(s.charAt(position)));
					++cont;
					return String.valueOf(s.charAt(position));
				} catch (Exception e) {
					e.printStackTrace();
		        	return null;
				}
			}
    	}
    	else if (mida == 256){
			if ( position < s.length() && ((s.charAt(position) >= '0' && s.charAt(position) <= '9') || (s.charAt(position) >= 'A' &&
					s.charAt(position) <= 'G')) ) {
				String value = charHexa(s.charAt(position));
				try {
					Position p = new Position();
					p.setRow(i);
					p.setColumn(j);
					sudoku.setCell(p, Character.getNumericValue(s.charAt(position)));
					return value;
				} catch (Exception e) {
					e.printStackTrace();
		        	return null;
				}
				
			}
    	}
    	return ".";
    }
    /**
     * Converteix un caracter al seu valor decimal
     * @param a Caracter a convertir a decimal
     * @return el valor en decimal del caracter a
     */
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
     * Indica si la partida que es jugara es de competicio o entrenament
     * @return Indica si la partida es entrenamiento o competicion
     */
    public boolean isCompetition(){
        return (type == 1);
    }
    /**
     * Converteix un tauell a una matriu
     * @param taulell
     * @param mida del tauell
     * @return matriu del tauell
     */
    public int[][] convertToMatrix(Board b){
        int mida = b.getSize();
        int[][] m = new int[mida][mida];
        for (int i=0; i < mida; ++i) {
            for (int j=0; j < mida; ++j) {
                m[i][j] = b.getCellValue(i, j);
            }
        }
        return m;
    }  
        
    /**
     * Comproba si el taulell actual es valid i te solucio unica
     * @return true: s'ha creat un sudoku correcte, false: altrament
     */
    public boolean compareSolution() {
        ControllerBoard cb = new ControllerBoard();
        int[][] m;
        if(!createSudoku) m = convertToMatrix(match.getSudoku());
        else m = convertToMatrix(sudoku.getSudoku());
        Boolean correct = cb.verify(m);
        return correct;
    }
    /**
     * Retorna el taulell que l'usuari vol carregar
     * @param id Indica la partida a cargar
     * @return Las partida guardada per l'usuari
     */
    public int[][] getSavedMatch(String id){
        try{
        	cont = 0;
        	createSudoku = false;
            this.id = id;
            match = cc.getSavedMatch(id);
            dificult = match.getSudokuLevel()-1;
            size = match.getSudokuSize();
            type = 0;
            int [][] enunciat = convertToMatrix(match.getSudoku());
            for(int i=0; i<enunciat.length; ++i){
            	for(int j=0; j<enunciat.length; ++j)
            		if(enunciat[i][j] != 0) ++cont; 
            }
            return enunciat;
        }
        catch(Exception e){
        	e.printStackTrace();
        	return null;
        }
    }
    /**
     * Guarda el Tablero actual
     * @param newSudoku Indica si el sudoku a guardar es nou o una partida en progres
     */
    public void saveBoard(boolean newSudoku){
        if (!newSudoku){ // es una partida amb progres
	        try{
	            cc.saveMatch((MatchTraining)match,id);
	        }
	        catch(Exception e){
	        	e.printStackTrace();
	        }
        }
        else { // es un nou sudoku
        	for(int i=0; i< this.sudoku.getSudoku().getSize(); ++i){
        		for(int j=0; j< sudoku.getSudoku().getSize(); ++j){
        			if(sudoku.getSudoku().getCellValue(i, j) != 0) sudoku.getSudoku().setCellType(i, j, CellType.Locked);
        		}
        	}
	        CntrlSearchLevel cs = new CntrlSearchLevel();
	        try {
				cs.init(sudoku.getSudoku());
				int dificultat = cs.searchLevel();
				Sudoku s = new Sudoku(sudoku.getSudoku(), sudoku.getSolution(), dificultat);
				cc.introduceSudoku(s);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
        }
        
    }
    /**
     * Modifica una casella d'un taulell amb progres per tal de retornar la partida
     * on estava l'usuari
     * @param i Coordenada de la fila
     * @param j Coordenada de la columna
     * @return pone a inicio la casilla con coordenada i,j
     */
    public int modify(int i, int j) {
        Board aux = match.getSudoku();
        int[][] auxi = convertToMatrix(aux);
        if (auxi[i][j] != 0 && aux.getCellType(i, j) != CellType.Locked) return auxi[i][j];
        return 0;
    }
    /**
     * 
     * @return Los identificadores de cada partida
     */
    public List<String> getIDMatches(){
        try {
        return cc.getIdMatches();
    } catch (Exception e) {
    	e.printStackTrace();
    	return null;
        }
    }
    
    /**
     * 
     * @param username Nombres de usuarios con ranking
     * @param values Valores de puntuacion por usuario
     */
    public void getRanking(List<String> username, List<Long> values) {
        List<ParamRanking> l = rg.getRanking();
        int mida = l.size();
        if (mida >10) mida = 10;
        for(int i=mida-1; i>=0; --i) {
            ParamRanking aux = l.get(i);
            username.add(aux.getName());
            values.add(aux.getValue());
        } 
    }
    public void addToRanking(List<String> info){
    	List<ParamRanking> l = rg.getRanking();
    	boolean found = false;
    	for (int i=l.size()-1; !found && i>10; --i){
    		ParamRanking aux = l.get(i);
    		if (aux.getName().equals(this.user.consultarNom())){
    			found = true;
    			info.add(aux.getName());
    			info.add(Long.toString(aux.getValue()));
    			info.add(Integer.toString(i));
    		}
    	}
    }
    /**
     * Comprueba si el Tablero actual es correcto y con solucion unica
     */
    public boolean checkBoard(){
        return compareSolution();
    }
    /**
     * Actualiza la celda con un nuevo valor
     * @param position Indica las coordenadas de la casilla
     * @param value Indica el valor a poner en la casilla
     */
    public void updateCell(String position, int value){
        String[] nombres = position.split(" ");
        int row = Integer.parseInt(nombres[0]);
        int column = Integer.parseInt(nombres[1]);
        try {
	        
	        if(!createSudoku){ //nomes es comproba si esta ben resolt si no s'esta creant un sudoku
	        	boolean buit = ( match.getCellValue(new Position(row, column)) == 0);
	        	match.setCell(new Position(row, column), value);
	        	if(value != 0) ++cont;
	        	else if (value == 0 && !buit) --cont;
		        if(cont == (size*size)) { // ha completado la partida correctamente
		        	if(type == 1){
			        	Sudoku s = new Sudoku(match.getSudoku(), match.getSolution());
			        	int score = ((MatchCompetition) match).score();
			        	s.updateRanking(this.user.consultarNom(), score);
			        	stad.addTime(((MatchCompetition) match).getMatchTime(), dificult); //actualizo tiempo estadisticas
			        	stad.afegirNumPartides(1, dificult); //actualizo numer partidas de estadisticas
			        	rg.modRanking(new ParamRanking(this.user.consultarNom(), ((MatchCompetition) match).getMatchTime())); //actualizo ranking global
			        	points = score;
		        	}
		        	else{
		        		points = -1;
		        	}
		        }
	        }
	        else {
	        	sudoku.setCell(new Position(row, column), value);
	        	++cont;
	        }
        } 
        catch (Exception e) {
        	e.printStackTrace();
        }
    }
    public int takePointsBoard(){
    	return points;
    }
    /**
     * Obtiene los candidatos de la casilla con coordenadas row,col
     * @param row Fila
     * @param col Columna
     * @return
     */
    public List<Integer> getCandidates(int row, int col) {
	    try {
	        List<Integer> candidates = new ArrayList<Integer>();
	        if (!createSudoku) candidates = CntrlSudokuHelps.getCandidates(new Position(row,col), match.getSudoku());
	        else candidates = CntrlSudokuHelps.getCandidates(new Position(row,col), sudoku.getSudoku());
	        return candidates;
	    } 
	    catch (Exception e) {
	    	e.printStackTrace();
        	return null;
	    }
    }
    /**
     * Retorna una llista de Position amb les caselles diferents.
     * Si una casella es buida aquesta no es considera diferent
     * @return retorna una llista de posicions amb les caselles diferents
     */
    public List<String> getDifferentCells() {
        List<Position> p = CntrlSudokuHelps.getDifferentCells(match.getSolution(), match.getSudoku());
        int mida = p.size();
        List<String> l = new ArrayList<String>();
        for(int i=0; i<mida; ++i){
            Position pos = p.get(i);
            String row = Integer.toString(pos.getRow());
            String col = Integer.toString(pos.getColumn());
            String res = row + " " + col; 
            l.add(res);
        }
        return l;
    }
    /**
     *  
     * @return Retorna la solució de la seguent casella no valida.
     */
    public int getNextSol() {
        return  CntrlSudokuHelps.getNextSolution(match.getSolution(), match.getSudoku());
    }
    /**
     * 
     * @param row Fila
     * @param col Columna
     * @return Retorna la solucion de la casilla con posicion pos
     */
    public int getCellSol(String pos){
        String[] nombres = pos.split(" ");
        int row = Integer.parseInt(nombres[0]);
        int column = Integer.parseInt(nombres[1]);
        Position p;
        try {
            p = new Position(row,column);
            int res = CntrlSudokuHelps.getCellSolution(match.getSolution(), p);
            updateCell(pos,res);
            return res;
        } catch (Exception e) {
        	e.printStackTrace();
        	return 0;
        }
        
    }

    /**
     * 
     * @return Retorna el numero de partidas jugadas por el usuario
     */
    public long[] returnMatches() {
        long[] a = new long[3];
        a[0] = stad.numEasyMatches;
        a[1] = stad.numMediumMatches;
        a[2] = stad.numHardMatches;
        return a;
    }
    /**
     * 
     * @return Retorna el tiempo total empleado por el usuario dependiendo de la dificultad
     */
    public long[] returnTime() {
        long[] a = new long[3];
        a[0] = stad.timeEasyMatches;
        a[1] = stad.timeMediumMatches;
        a[2] = stad.timeHardMatches;
        return a;
    }
    /**
     * 
     * @return Retorna el mejor tiempo empleado por un usuario dependiendo de la dificultad
     */
    public long[] returnBestTime() {
        long[] a = new long[3];
        a[0] = stad.bestTimeEasyMatches;
        a[1] = stad.bestTimeMediumMatches;
        a[2] = stad.bestTimeHardMatches;
        return a;
    }
    
}
