package propias.persistencia;

import java.util.*;
import java.util.regex.Pattern;
import java.io.*;
import java.nio.file.*;

/**
* ControllerPersistance es un controlador de 
* persistencia que intenta simular el funcionament 
* d'una base de dades amb fitxers.
*
* @author  Adrián Sánchez Albanell
*/
public class ControllerPersistance {

	private String user;
	private String autoPath;
	
	public ControllerPersistance(){
		autoPath = Paths.get("").toAbsolutePath().toString();
	}

	/**
	*Creadora per a fer servir amb el drivers
	*(ja que esta en una ubicacio diferent)
	*@param driver true si es tracta d'un driver.
	*/
	public ControllerPersistance(boolean driver){
		autoPath = Paths.get("").toAbsolutePath().toString();
		if(driver){			
			autoPath = autoPath + "/../../";
		}
	}

	/**
   	* Converteix un sudoku en format int[][] a una String
   	* amb format: 
  	* 7.4.....2...8.1...3.........5.6..1..2...4...........5...
  	* .37.....9....6...8.....9.
  	* Els nombres superiors a 10 els representa amb lletres
  	* (A, B, C, etc).
  	* @param sudoku matriu a convertir en String.
  	* @return String representant el sudoku.
  	*/
	public String sudokuToString(int[][] sudoku){
		String sudo = new String();
		int size = sudoku.length;
		for(int i = 0; i < size; ++i){
			for(int j = 0; j < size; ++j){
				if(sudoku[i][j] == 0){
					sudo = sudo + '.';
				}
				else{
					int num = sudoku[i][j];
					if(num < 10){
						sudo = sudo + Integer.toString(num);
					}
					else if(num == 16){
						sudo = sudo + "G";
					}
					else{
						sudo = sudo + 
						Integer.toHexString(num).toUpperCase();
					}
					
				}
			}
		}
		return sudo;
	}

	/**
   	* Converteix un sudoku en format String
   	* de la forma: 
  	* 7.4.....2...8.1...3.........5.6..1..2...4...........5...
  	* .37.....9....6...8.....9.
  	* On els nombres superiors a 10 els representa amb lletres
  	* (A, B, C, etc).
  	* En una matriu d'enters.
  	* @param sudoku String a convertir en int[][].
  	* @return int[][] representant el sudoku.
  	*/
	public static int[][] stringToMatrix(String s) throws Exception {
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

	/**
   	* Permet saber si un usuari existeix o no. 
  	* @param user identificador de l'usuari.
  	* @return boolean true si existeix, false si no.
  	*/
	public boolean existsUser(String user) {
		File u = new File(autoPath + "/data/Users/" + user);
			    
	    //Path currentRelativePath = Paths.get("").toAbsolutePath().toString();
		//String s = currentRelativePath.toAbsolutePath().toString();
		//System.out.println("Current relative path is: " + s);
		//System.out.println("PATH: " + autoPath + "/data/Users/" + user);
		return u.exists();
	}

	/**
   	* Converteix un sudoku en format int[][] a una String
   	* amb format: 
  	* 7.4.....2...8.1...3.........5.6..1..2...4...........5...
  	* .37.....9....6...8.....9.
  	* Els nombres superiors a 10 els representa amb lletres
  	* (A, B, C, etc).
  	* @param sudoku matriu a convertir en String.
  	* @return String representant el sudoku.
  	*/
	public String getPasswordUser(String user) throws Exception {
		String password = new String();
		FileReader fr = new FileReader(autoPath + "/data/Users/" + user + "/" + "infoUser.txt");
		Scanner scn = new Scanner(fr);
		password = scn.next();
		fr.close();
		return password;
	}

	/**
   	* Inicialitza el controlador per a un usuari 
   	* concret.
  	* @param user identificador de l'usuari
  	*/
	public void userDBInit(String user){
		this.user = user;
	}

	/**
   	* A partir d'una matriu que representi el sudoku i una altra
   	* que representi la solucio, amb la dificultat de sudoku
   	* corresponent, et guarda el sudoku amb el format
  	* 7.4.....2...8.1...3.........5.6..1..2...4...........5...
  	* .37.....9....6...8.....9.
  	* per a cada matriu donantli un identificador unic al
  	* fitxer on es guardarà.
  	* Els nombres superiors a 10 els representa amb lletres
  	* (A, B, C, etc).
  	* @param sudoku matriu a convertir en String.
  	* @return String representant el sudoku.
  	*/
	public void introduceSudoku(int[][] sudoku, int[][] solution, int dif) throws Exception{
		String difSt;
		if(dif == 0){
			difSt = "Facil";
		}
		else if(dif == 1){
			difSt = "Medio";
		}
		else{
			difSt = "Dificil";
		}
		String size = Integer.toString(sudoku.length);
		String dir = autoPath + "/data/Sudokus/" + size + "x" + size + "/" + difSt;
		File fl = new File(dir);
		String id = size + "s" + Integer.toString(fl.list().length + 1) + "l" + dif;
		FileWriter fw = new FileWriter(autoPath + "/data/Sudokus/" + size + "x" + size + "/" + difSt + "/" + id +".txt");
		PrintWriter pw = new PrintWriter(fw);
		pw.println(sudokuToString(sudoku));
		pw.println(sudokuToString(solution));
		fw.close();
		FileWriter fwRank = new FileWriter(autoPath + "/data/Ranking/" + id + ".txt");
		fwRank.close();
	}

	/**
   	* Permet la creacio d'un nou usuari
   	* (i tots els directoris, arxius
   	*  i modificacions d'arxius
   	*  corresponents).
  	* @param name String amb el nom d'usuari.
  	* @param password String amb la password d'usuari.
  	*/
	public void newUser(String name, String password) throws Exception {
		new File(autoPath + "/data/Users/" + name + "/Partidas/").mkdirs();
		File info = new File(autoPath + "/data/Users/" + name + "/" + "infoUser.txt");
		info.createNewFile();
		FileWriter fw = new FileWriter(autoPath + "/data/Users/" + name + "/infoUser.txt");
		PrintWriter pw = new PrintWriter(fw);
		pw.println(password);
		for(int i = 0; i < 9; ++i){
			pw.println("0");
		}
		fw.close();		
	    List<List<String>> rankingGlob = new  ArrayList<List<String>>(getRanking("RankingGlobal"));
	    List<String> userRank = new ArrayList<String>();
	    userRank.add(name);
	    userRank.add("0");
	    rankingGlob.add(userRank);
	    setRanking("RankingGlobal", rankingGlob);
	}

	/**
   	* Permet obtenir les estadistiques de l'usuari
   	* carregat al controlador.
  	* @return List<Long> amb nombre de partides facils,
  	* mitjanes i dificils jugades, temps dedicat a partides 
  	* facils, mitjanes i dificils i millor temps en 
  	* partides mitjanes, facils i dificils.
  	*/
	public List<Long> getStadistics() throws Exception {
		    List<Long> stadistics = new ArrayList<Long>();
		    FileReader fr = new FileReader(autoPath + "/data/Users/" + user + "/" + "infoUser.txt");
			Scanner scn = new Scanner(fr);
			scn.next();
			for(int i = 0; i < 9; ++i){
				stadistics.add(Long.parseLong(scn.next(), 10));
			}
			fr.close();
		    return stadistics;
	}

	/**
   	* Permet actualitzar les estadistiques de l'usuari
   	* carregat al controlador.
  	* @param List<Long> amb nombre de partides facils,
  	* mitjanes i dificils jugades, temps dedicat a partides 
  	* facils, mitjanes i dificils i millor temps en 
  	* partides mitjanes, facils i dificils.
  	*/
	public void setStadistics(List<Long> stadistics) throws Exception {
			FileReader fr = new FileReader(autoPath + "/data/Users/" + user + "/" + "infoUser.txt");
			Scanner scn = new Scanner(fr);
			String password = scn.next();
			fr.close();
		    FileWriter fw = new FileWriter(autoPath + "/data/Users/" + user + "/" + "infoUser.txt");
		    PrintWriter pw = new PrintWriter(fw);
		    pw.println(password);
		    for(int i = 0; i < stadistics.size(); ++i){
		    	pw.println(stadistics.get(i));
		    }
		    fw.close();
	}

	/**
   	* Permet obtenir un sudoku d'una certa mida i
   	* dificultat.
   	* @param size mida del sudoku.
   	* @param dif dificultat del sudoku(0, 1 o 2 de menor
   	* a major dificultat).
  	* @return List<String> llista amb la ID del sudoku,
  	* el sudoku amb forats i la solucio al sudoku.
  	*/
	public List<String> getSudoku(int size, int dif) throws Exception {
		List<String> infoSudoku = new ArrayList<String>();
		String difSt;
		if(dif == 0){
			difSt = "Facil";
		}
		else if(dif == 1){
			difSt = "Medio";
		}
		else{
			difSt = "Dificil";
		}
		File f = new File(autoPath + "/data/Sudokus/" + size + "x" + size + "/" + difSt);
		File[] sudokus = f.listFiles();
		Random rand = new Random();
		int pos = rand.nextInt(sudokus.length);
		infoSudoku.add(sudokus[pos].getName());
		FileReader fr = new FileReader(sudokus[pos].getAbsolutePath());
		Scanner scn = new Scanner(fr);
		while(scn.hasNext()){
			infoSudoku.add(scn.next());
		}
		fr.close();
		return infoSudoku;
	}

	/**
   	* Retorna l'identificador de les partides guardades de
   	* l'usuari carregat al controlador.
  	* @return List<String> retorna els identificadors de
  	* les partides.
  	*/
	public List<String> getIdMatches() throws Exception {
			List<String> savedMatches = new ArrayList<String>();
			File f = new File(autoPath + "/data/Users/" + user + "/Partidas");
			File[] partidas = f.listFiles();
			for(int a = 0; a < partidas.length; ++a){
				savedMatches.add(partidas[a].getName());
			}
			return savedMatches;
	}

	/*
		devuelve 3 matrices: actual de la partida, inicial con agujeros, solucion.
	*/
	/**
   	* Retorna una llista de tres matrius que representen 
   	* una partida a mitges:
   	* Una matriu amb el progres actual de la partida.
   	* una matriu amb els valors inicials del sudoku.
   	* una matriu amb la solucio al sudoku.
   	* l'usuari carregat al controlador.
  	* @return List<String> retorna els identificadors de
  	* les partides.
  	*/
	public List<int[][]> getSavedMatch(String idMatch) throws Exception {
		List<int[][]> savedMatch = new ArrayList<int[][]>();
		FileReader fr = new FileReader(autoPath + "/data/Users/" + user + "/Partidas/" + idMatch);
		Scanner scn = new Scanner(fr);
		while(scn.hasNext()){
			savedMatch.add(stringToMatrix(scn.next()));
		}
		fr.close();
		return savedMatch;
	}

	/**
   	* Guarda una partida de l'usuari.
   	* @param idSudoku id del sudoku a guardar.
   	* @param match partida.
  	*/
	public void saveMatch(String idSudoku, List<int[][]> match) throws Exception {
		File f = new File(autoPath + "/data/Users/" + user + "/Partidas/" + idSudoku);
		f.delete();
		f.createNewFile();
		FileWriter fw = new FileWriter(f.getAbsolutePath());
		PrintWriter pw = new PrintWriter(fw);
		String sudoku;
		for(int i = 0; i < match.size(); ++i){
			sudoku = sudokuToString(match.get(i));
			pw.println(sudoku);
		}
		fw.close();
	}

	/**
   	* Retorna un ranking, ja sigui RankingGlobal o el d'un sudoku. 
  	* @param id identificador del sudoku o RankingGlobal en cas de 
    * ranking global.
  	* @return List<List<String>> retorna la representacio del ranking
  	* en llistes on hi ha el nom de l'usuari i la seva puntuacio.
  	*/
	public List<List<String>> getRanking(String id) throws Exception {
		List<List<String>> nameScore = new ArrayList<List<String>>();
		FileReader fr = new FileReader(autoPath + "/data/Ranking/" + id + ".txt");
		Scanner scn = new Scanner(fr);
		Pattern delimiter = Pattern.compile("[;\n]");
		scn.useDelimiter(delimiter);
		while(scn.hasNext()){
			List<String> tmp = new ArrayList<String>();
			tmp.add(scn.next());
			tmp.add(scn.next());
			nameScore.add(tmp);
		}		
		fr.close();
		return nameScore;
	}

	/**
   	* Modifica o crea un ranking.
  	* @param id identificador del sudoku o RankingGlobal en cas de 
    * ranking global.
    * @param ranking valors del ranking, llistes de String
    * amb nom i puntuacio.
  	*/
	public void setRanking(String id, List<List<String>> ranking) throws Exception {
		File f = new File(autoPath + "/data/Ranking/" + id + ".txt");
		f.delete();
		f.createNewFile();
		FileWriter fw = new FileWriter(f.getAbsolutePath());
		PrintWriter pw = new PrintWriter(fw);
		String sudoku;
		for(int i = 0; i < ranking.size(); ++i){
			List<String> tmp = ranking.get(i);
			pw.print(tmp.get(0) + ";" + tmp.get(1) + "\n");
		}
		fw.close();
	}
}