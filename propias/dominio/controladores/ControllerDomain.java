package propias.dominio.controladores;

import java.util.*;
import propias.dominio.clases.*;
import propias.persistencia.ControllerPersistance;

/**
 * 
 * @author Brian
 *
 */
public class ControllerDomain {
	ControllerPersistance cp;
	ControllerRanking cr;
    Stadistics s;
	int size = 0;
	int dificult = 0;
    int type = 0;
	List<String> list;
	String id;
	Match match;
	Match enunciat;
    Board create;
	Board b;
	String username;
	
	/**
	 * Constructora
	 */
    public ControllerDomain(){
        cp = new ControllerPersistance();
    }
    /**
     * 
     * @param credentials Contiene la informacion del usuario
     * @return Si el login o el crear usuario se ha relaizado correctamente
     */
    public String checkCredentials(List<String> credentials){
        String user = credentials.get(0);
        String pass1 = credentials.get(1);
        if (credentials.size() == 3) { //create user
            try {
                String pass2 = credentials.get(2);
                if (user.equals("")) return "El usuario no puede ser vacio";
                boolean b = cp.existsUser(user);
                if (b)  return "El usuario ya existe";  //Usuari ja existent
                else if (pass1.equals("") || pass2.equals("")) return "Las contraseñas no pueden ser vacias";
                else if (!pass1.equals(pass2)) return "Las contraseñas no coinciden"; //Les contrasenyes no coincideixen
                int i;
                for(i=0;i<pass1.length(); ++i) {
                    if (pass1.charAt(i) < '1' || (pass1.charAt(i) >'9' && pass1.charAt(i) < 'A') || (pass1.charAt(i) > 'Z' && pass1.charAt(i) < 'a') || pass1.charAt(i) > 'z'){
                        return "Las contraseñas solo pueden tener numeros y letras";
                    } 
                }
                cp.newUser(user,pass1);
                return "Se ha creado el usuario"; //tot correcte
            }
                catch (Exception e) {
                    return null;
            }
            
        }
        else { //login
            String passWordOk = "";
            try {
                if(cp.existsUser(user)) {
                    passWordOk = cp.getPasswordUser(user);
                    if (passWordOk.equals(pass1)) {
                        username = user;
                        cp.userDBInit(username);
                        return "Login correcto";
                    }
                    else return "Nombre o password incorrecto";
                }
                else return "Nombre o password incorrecto";
            } 
            catch (Exception e1) {
                return null;
            }
            
            
        }   
    }
    /**
     * 
     * @param c Contiene las caracteristicas de la nueva partia
     * @return Una nueva partida con caracteristicas c
     */
    public int[][] createMatch(CaracteristiquesPartida c){
    	try {
    		match = new Match(username, c);
    		enunciat = match;
    		b = match.getSudoku();
    		return convertToMatrix(b);
    	} catch (Exception e) {
    		e.printStackTrace(); // mai arribarem
    		return null;
    	}
    }
    /**
     * Crea una partida que ya tenia cargada el usuario
     * @param size Indica el tamaño del tablero
     */
    public void newMatch(int size){
		match = new Match(username, size);
    }
    /**
     * 
     * @return Indica si la partida es entrenamiento o competicion
     */
    public boolean isCompetition(){
        return (type == 2);
    }
    /**
     * 
     * @param b Tablero
     * @param mida Tamaño del tablero
     * @return Convierte un Tablero a Matriz
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
     * 
     * @param m Matriz
     * @param mida Indica el tamaño de la matriz
     * @return Convierte una matriz a Tablero
     */
    public Board convertToBoard(int[][] m, int mida) {
    	
		try {
			Board b = new Board(mida);
	    	for (int i=0; i<mida; ++i){
	    		for(int j=0; j<mida; ++j)
						b.setCellValue(i, j, m[i][j]);
					}
                    return b; 
        }      
        catch (Exception e) {
		} 
	       return null;
	}  
		
    /**
     * Comprueba si el Tablero actual es valido y tiene solucion unica
     * @return
     */
    public boolean compareSolution() {
    	ControllerBoard cb = new ControllerBoard();
    	int[][] m = convertToMatrix(match.getSudoku());
    	Boolean correct = cb.verify(m);
    	if (correct) {
    		updateRanking(false);
    		updateRanking(true);
    	}
    	return correct;
    }
    /**
     * 
     * @param id Indica la partida a cargar
     * @return Las partidas guardadas por el usuario
     */
    public List<int[][]> getSavedMatches(String id){
    	try{
        List<int[][]> l = cp.getSavedMatch(id); // pos 0 = progres, pos 1 = enunciat, pos 2 = solucio
        Board actual = convertToBoard(l.get(0), l.get(0).length);
        Board solucio = convertToBoard(l.get(2), l.get(2).length);
        match = new Match(username, new Sudoku(actual, solucio));
        Board inici = convertToBoard(l.get(1), l.get(1).length);
        enunciat = new Match(username, new Sudoku(inici, solucio));
        return l;
        }
        catch(Exception e){
            return null;
        }
    }
    /**
     * Guarda el Tablero actual
     */
    public void saveBoard(){
        int[][] m = convertToMatrix(match.getSudoku());
        int[][] en = convertToMatrix(enunciat.getSudoku());
        int[][] sol = convertToMatrix(match.getSolution());
        List<int[][]> l = new ArrayList<int[][]>();
        try{
	        l.add(m);
	        l.add(en);
	        l.add(sol);
	        cp.saveMatch(id, l);
	    }
	    catch(Exception e){
	    	
	    }
    }
    /**
     * 
     * @param i Coordenada de la fila
     * @param j Coordenada de la columna
     * @return pone a inicio la casilla con coordenada i,j
     */
    public int modify(int i, int j) {
    	return 0;
    }
    /**
     * 
     * @return Los identificadores de cada partida
     */
    public List<String> getIDMatches(){
    	try {
			return cp.getIdMatches();
		} catch (Exception e) {
		return null;
        }
    	
    }
    /**
     * 
     * @param names Nombres de usuarios registrados
     * @param values Valores de ranking por usuario
     * @param global Indica si el ranking es el global o el de sudoku
     * @return
     */
    public static List<ParamRanking> createParams(List<String> names, List<Long>values, boolean global){
    	List<ParamRanking> l = new ArrayList<ParamRanking>();
    	if (global) {
    		for(int i = 0; i<names.size(); ++i){
    			String n = names.get(i);
    			Long v = values.get(i);
    			ParamRanking p = new ParamRanking(n,v);
    			l.add(p);
        	}
    	}
    	else {
    		for(int i = 0; i< names.size(); ++i){
    			String n = names.get(i);
    			Long v = values.get(i);
    			ParamRanking p = new ParamRanking(n,v);
        		l.add(p);
        	}
    	}
    	return l;
    }
    /**
     * 
     * @param global Indica si el ranking es global o de sudoku
     */
    public void setRankingController(boolean global) {
    	try{
	    	List<List<String>> l;
	    	if (global) {
	    		l = cp.getRanking("RankingGlobal");
	    		List<String> names = new ArrayList<String>();
	    		List<Long> val = new ArrayList<Long>();
	    		for(int i=0; i<l.size(); ++i){
	    			List<String> p = l.get(i);
	    			names.add(p.get(0));
                    //String aux = p.get(1);
                    //val.add(Long.parseLong(aux));
	    			val.add(0L);
                    //System.out.println("pers " + p.get(0));
                    //System.out.println("pers " + p.get(1));
	    		}
	    		List<ParamRanking> par = createParams(names, val,true);	
	    		cr = new ControllerRanking(par,true);
	    	}
	    	else {
	    		l = cp.getRanking(id);
	    		List<String> names = new ArrayList<String>();
	    		List<Long> val = new ArrayList<Long>();
	    		
	    		for(int i=0; i<l.size(); ++i){
	    			List<String> p = l.get(i);
                    System.out.println("names" + p.get(0));
                    System.out.println("vals" + p.get(1));
	    			names.add(p.get(0));
	    			val.add(Long.parseLong(p.get(1)));
	    		}
	    		List<ParamRanking> par = createParams(names, val,false);	
	    	}
    	}
    	catch(Exception e){
            e.printStackTrace();
    	}
    }
    /**
     * Actualiza el Ranking global o de sudoku
     * @param global Indica si el ranking es global o de sudoku
     */
    public void updateRanking(boolean global){
    	List<List<String>> l = new ArrayList<List<String>>();
    	List<String> aux = new ArrayList<String>();
    	if(global){
    		List<ParamRanking> c = cr.getRanking();
    		for(int i=0; i< c.size(); ++i){
    			ParamRanking p = c.get(i);
    			aux.add(p.getName());
    			aux.add(Long.toString(p.getValue()));
    			l.add(aux);
    		}
    	}
    }
    /**
     * 
     * @param username Nombres de usuarios con ranking
     * @param values Valores de puntuacion por usuario
     */
    public void getRanking(List<String> username, List<Long> values) {
        List<ParamRanking> l = cr.getRanking();
    	int mida = l.size();
        if (mida >10) mida = 10;
    	for(int i=0; i<mida; ++i) {
    		ParamRanking aux = l.get(i);
    		username.add(aux.getName());
    		values.add(aux.getValue());
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
			match.setCell(new Position(row, column), value);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    /**
     * Obtiene los candidatos de la casilla con coordenadas row,col
     * @param row Fila
     * @param col Columna
     * @return
     */
    public List<Integer> getCandidates(int row, int col) {
		try {
			List<Integer> candidates = SudokuHelps.getCandidates(new Position(row,col), match.getSudoku());
			return candidates;
		} catch (Exception e) {
	    	return null;
		}
    }
    /**
     * Retorna una llista de Position amb les caselles diferents.
	 * Si una casella es buida aquesta no es considera diferent
     * @return retorna una llista de posicions amb les caselles diferents
     */
    public List<String> getDifferentCells() {
    	List<Position> p = SudokuHelps.getDifferentCells(match.getSolution(), match.getSudoku());
    	int mida = p.size();
    	List<String> l = new ArrayList<String>();
    	for(int i=0; i<mida; ++i){
    		Position pos = p.get(i);
    		int res = (pos.getRow()*10) + pos.getColumn();
    		l.add(Integer.toString(res));
    	}
    	return l;
    }
    /**
     *  
     * @return Retorna la solució de la seguent casella no valida.
     */
    public int getNextSol() {
    	return  SudokuHelps.getNextSolution(match.getSolution(), match.getSudoku());
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
			return SudokuHelps.getCellSolution(match.getSolution(), p);
		} catch (Exception e) {
		  return 0;
        }
		
    }

    /**
     * 
     * @return Obtiene las estadisticas del usuario
     */
    public List<Long> getStadistics(){
        try {
			return cp.getStadistics();
		} catch (Exception e) {
		  return null;
        }
        
    }
    /**
     * Instancia las estadisticas 
     */
    public void createStadistics() {
    	s = new Stadistics(getStadistics());
    }
    /**
     * 
     * @return Retorna el numero de partidas jugadas por el usuario
     */
    public long[] returnMatches() {
    	long[] a = new long[3];
    	a[0] = s.numEasyMatches;
    	a[1] = s.numMediumMatches;
    	a[2] = s.numHardMatches;
    	return a;
    }
    /**
     * 
     * @return Retorna el tiempo total empleado por el usuario dependiendo de la dificultad
     */
    public long[] returnTime() {
    	long[] a = new long[3];
    	a[0] = s.timeEasyMatches;
    	a[1] = s.timeMediumMatches;
    	a[2] = s.timeHardMatches;
    	return a;
    }
    /**
     * 
     * @return Retorna el mejor tiempo empleado por un usuario dependiendo de la dificultad
     */
    public long[] returnBestTime() {
    	long[] a = new long[3];
    	a[0] = s.bestTimeEasyMatches;
    	a[1] = s.bestTimeMediumMatches;
    	a[2] = s.bestTimeHardMatches;
    	return a;
    }
    
}
