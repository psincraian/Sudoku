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
	 * 
	 */
    public ControllerDomain(){
        cp = new ControllerPersistance();
    }
    
        public String checkCredentials(List<String> credentials, boolean correct){
        String user = credentials.get(0);
        String pass1 = credentials.get(1);
        if (credentials.size() == 3) { //create user
            try {
                String pass2 = credentials.get(2);
                if (user.equals("")) return "El usuario no puede ser vacio";
                boolean b = cp.existsUser(user);
                if (b)  return "El usuario ya existe";  //Usuari ja existent
                else if (pass1.equals("") || pass2.equals("")) return "Las contraseñas no pueden ser vacias";
                int i;
                for(i=0;i<pass1.length(); ++i) {
                    if (pass1.charAt(i) < '1' || (pass1.charAt(i) >'9' && pass1.charAt(i) < 'A') || (pass1.charAt(i) > 'Z' && pass1.charAt(i) < 'a') || pass1.charAt(i) > 'z'){
                        return "Las contraseñas solo pueden tener numeros y letras";
                    } 
                }
                if (!pass1.equals(pass2)) return "Las contraseñas no coinciden"; //Les contrasenyes no coincideixen
                else{
                    cp.newUser(user,pass1);
                    correct = true;
                    return "Se ha creado el usuario"; //tot correcte
                }
            }
                catch (Exception e) {
                    return null;
            }
            
        }
        else { //login
            String passWordOk = "";
            try {
                passWordOk = cp.getPasswordUser(user);
                if (passWordOk.equals(pass1)) {
                    username = user;
                    cp.userDBInit(username);
                    correct = true;
                    return "Login correcto";
                }
                else return "Nombre o contraseña incorrecto";
            } 
            catch (Exception e1) {
                return null;
            }
            
            
        }   
    }

    /**
     * 
     * @param c
     * @return
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
    
    public void newMatch(int size){
		match = new Match(username, size);
    }
    
    public boolean isCompetition(){
        return (type == 2);
    }
    /**
     * 
     * @param b
     * @param mida
     * @return
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
     * @param m
     * @param mida
     * @return
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
     * 
     * @return
     */
    public boolean compareSolution() {
    	ControllerBoard cb = new ControllerBoard();
    	int[][] m = convertToMatrix(match.getSudoku());
    	return cb.verify(m);
    	
    }
    /**
     * 
     * @param id
     * @return
     */
    public List<int[][]> getSavedMatches(String id){
    	return null;
    }
    public void saveBoard(){
        int[][] m = convertToMatrix(enunciat.getSudoku());
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
     * @param i
     * @param j
     * @return
     */
    public int modify(int i, int j) {
    	return 0;
    }
    /**
     * 
     * @return
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
     * @param names
     * @param values
     * @param global
     * @return
     */
    public static List<ParamRanking> createParams(List<String> names, List<Long>values, boolean global){
    	List<ParamRanking> l = new ArrayList<ParamRanking>();
    	if (global) {
    		for(int i = 0; i<names.size(); ++i){
    			String n = names.get(i);
    			long v = values.get(i);
    			ParamRanking p = new ParamRanking(n,v);
    			l.add(p);
        	}
    	}
    	else {
    		for(int i = 0; i< names.size(); ++i){
    			String n = names.get(i);
    			long v = values.get(i);
    			ParamRanking p = new ParamRanking(n,v);
        		l.add(p);
        	}
    	}
    	return l;
    }
    /**
     * 
     * @param global
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
	    			val.add(Long.parseLong(p.get(1)));
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
	    			names.add(p.get(0));
	    			val.add(Long.parseLong(p.get(1)));
	    		}/*jggliugybjniliyukfdt*/
	    		List<ParamRanking> par = createParams(names, val,false);	
	    	}
    	}
    	catch(Exception e){
    	}
    }
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
     * @param username
     * @param values
     */
    public void getRanking(List<String> username, List<Long> values) {
    	List<ParamRanking> l = cr.getRanking();
    	int mida = l.size();
        if (mida >10) mida = 10;
    	for(int i=0; i<mida; ++i) {
    		username.set(i,l.get(i).getName());
    		values.set(i,l.get(i).getValue());
    	}
    	
	    	 
    }
    /**
     * 
     */
    public boolean checkBoard(){
    	return compareSolution();
    }
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
     * 
     * @param row
     * @param col
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
     * @return
     */
    public int getNextSol() {
    	return  SudokuHelps.getNextSolution(match.getSolution(), match.getSudoku());
    }
    /**
     * 
     * @param row
     * @param col
     * @return
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
     * @return
     */
    public List<Long> getStadistics(){
        try {
			return cp.getStadistics();
		} catch (Exception e) {
			return null;
		}
        
    }
    /**
     * 
     */
    public void createStadistics() {
    	s = new Stadistics(getStadistics());
    }
    /**
     * 
     * @return
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
     * @return
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
     * @return
     */
    public long[] returnBestTime() {
    	long[] a = new long[3];
    	a[0] = s.bestTimeEasyMatches;
    	a[1] = s.bestTimeMediumMatches;
    	a[2] = s.bestTimeHardMatches;
    	return a;
    }
    
}
