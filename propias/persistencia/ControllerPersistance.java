package propias.persistencia;

import java.util.*;
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
		recoverCorruptData();
		
	}

	/**
	* Permet recuperar l'estructura que ha de tenir
	* data en cas que falti algun directori necessari.
	*/
	private void recoverCorruptData(){
		File d = new File(autoPath + "/data/Ranking/");
		if(!d.exists()){
			new File(autoPath + "/data/Ranking/").mkdirs();
		}
		d = new File(autoPath + "/data/Users/");
		if(!d.exists()){
			new File(autoPath + "/data/Users/").mkdirs();			
		}
		d = new File(autoPath + "/data/Sudokus/9x9/Facil/");
		if(!d.exists()){
			new File(autoPath + "/data/Sudokus/9x9/Facil/").mkdirs();			
		}
		d = new File(autoPath + "/data/Sudokus/9x9/Medio/");
		if(!d.exists()){
			new File(autoPath + "/data/Sudokus/9x9/Medio/").mkdirs();			
		}
		d = new File(autoPath + "/data/Sudokus/9x9/Dificil/");
		if(!d.exists()){
			new File(autoPath + "/data/Sudokus/9x9/Dificil/").mkdirs();			
		}
		d = new File(autoPath + "/data/Sudokus/16x16/Facil/");
		if(!d.exists()){
			new File(autoPath + "/data/Sudokus/16x16/Facil/").mkdirs();			
		}
		d = new File(autoPath + "/data/Sudokus/16x16/Medio/");
		if(!d.exists()){
			new File(autoPath + "/data/Sudokus/16x16/Medio/").mkdirs();			
		}
		d = new File(autoPath + "/data/Sudokus/16x16/Dificil/");
		if(!d.exists()){
			new File(autoPath + "/data/Sudokus/16x16/Dificil/").mkdirs();			
		}
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
   	* Permet saber si un usuari existeix o no. 
  	* @param user identificador de l'usuari.
  	* @return boolean true si existeix, false si no.
  	*/
	public boolean existsUser(String user) {
		File u = new File(autoPath + "/data/Users/" + user);
		return u.exists();
	}

	/**
   	* Metode que retorna un usuari.
  	* @param user nom de l'usuari.
  	* @return String representant l'usuari.
  	*/
	public String getUser(String user) throws Exception {
		FileReader fr = new FileReader(autoPath + "/data/Users/" + user + "/user");
		Scanner scn = new Scanner(fr);
		String userObj = scn.next();
		scn.close();
		fr.close();
		return userObj;
	}

	/**
   	* Metode que retorna l'usuari
   	* carregat a persistencia.
  	* @param user nom de l'usuari.
  	* @return String representant l'usuari.
  	*/
	public String getUser() throws Exception {
		FileReader fr = new FileReader(autoPath + "/data/Users/" + user + "/user");
		Scanner scn = new Scanner(fr);
		String userObj = scn.next();
		scn.close();
		fr.close();
		return userObj;
	}

	public void changePasswordUser(String serializedUser) throws Exception {
		File f = new File(autoPath + "/data/Users/" + user + "/user");
		f.delete();
		f.createNewFile();
		FileWriter fw = new FileWriter(f.getAbsolutePath());
		PrintWriter pw = new PrintWriter(fw);
		pw.print(serializedUser);
		fw.close();
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
   	* Permet la creacio d'un nou usuari
   	* (i tots els directoris, arxius
   	*  i modificacions d'arxius
   	*  corresponents).
  	* @param name String amb el nom d'usuari.
  	* @param user l'usuari.
  	*/
	public void newUser(String name, String user, String stadistics) throws Exception {
		new File(autoPath + "/data/Users/" + name + "/Partidas/").mkdirs();
		File userObj = new File(autoPath + "/data/Users/" + name + "/user");
		userObj.createNewFile();
		FileWriter fw = new FileWriter(autoPath + "/data/Users/" + name + "/user");
		PrintWriter pw = new PrintWriter(fw);
		pw.print(user);
		pw.close();
		fw.close();
		File userStadistics = new File(autoPath + "/data/Users/" + name + "/stadistics");
		userStadistics.createNewFile();
		FileWriter fw2 = new FileWriter(autoPath + "/data/Users/" + name + "/stadistics");
		PrintWriter pw2 = new PrintWriter(fw2);
		pw2.print(stadistics);
		pw2.close();
		fw2.close();
	}

	/**
   	* Permet obtenir les estadistiques de l'usuari
   	* carregat al controlador.
   	* @return String estadistiques serialitzades en
   	* una String.
  	*/
	public String getStadistics() throws Exception {
		    FileReader fr = new FileReader(autoPath + "/data/Users/" + user + "/stadistics");
			Scanner scn = new Scanner(fr);
			String std = scn.next();
			fr.close();
		    return std;
	}

	/**
   	* Permet actualitzar les estadistiques de l'usuari
   	* carregat al controlador.
  	* @param stadistics noves estadistiques.
  	*/
	public void setStadistics(String stadistics) throws Exception {
		    FileWriter fw = new FileWriter(autoPath + "/data/Users/" + user + "/stadistics");
		    PrintWriter pw = new PrintWriter(fw);
		    pw.print(stadistics);
		    fw.close();
	}

	/**
   	* Introdueix un nou sudoku als fitxers.
  	* @param sudoku sudoku a guardar.
  	* @param dif dificultat del sudoku.
  	* @param size mida del sudoku.
  	*/
	public String introduceSudoku(String sudoku, int dif, int size) throws Exception {
		String difSt;
		String difID;
		if(dif == 0){
			difSt = "Facil";
			difID = "e";
		}
		else if(dif == 1){
			difSt = "Medio";
			difID = "m";
		}
		else{
			difSt = "Dificil";
			difID = "d";
		}
		String sizeStr = Integer.toString(size);
		String dir = autoPath + "/data/Sudokus/" + size + "x" + size + "/" + difSt;
		File fl = new File(dir);		
		if(size == 16){
			difID = difID.toUpperCase();
		}
		String id;
		int length = fl.list().length;
		if(length == 0) id = difID + Integer.toString(length + 1);
		else id = difID + Integer.toString(length);
		FileWriter fw = new FileWriter(autoPath + "/data/Sudokus/" + size + "x" + size + "/" + difSt + "/" + id);
		PrintWriter pw = new PrintWriter(fw);
		pw.print(sudoku);
		fw.close();
		return id;
	}

	/**
	* Metode per a modificar la informacio guardada
	* sobre els sudokus amb una certa dificultat i
	* mida.
	* @param infoSudokuList informacio a guardar
	* serialitzada.
	* @param dif dificultat dels sudokus representats 
	* per la informacio.
	* @param size nivell dels sudokus representats 
	* per la informacio.
	*/
	public void introduceSudokuInfo(String infoSudokuList, int dif, int size) throws Exception {
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
		String dir = autoPath + "/data/Sudokus/" + size + "x" + size + "/" + difSt + "/infoSudokus";
		File f = new File(dir);	
		f.delete();
		f.createNewFile();
		FileWriter fw = new FileWriter(f.getAbsolutePath());
		PrintWriter pw = new PrintWriter(fw);
		pw.print(infoSudokuList);
		fw.close();
	}

	/**
	* Metode per a obtenir la informacio guardada
	* dels sudokus amb una certa dificultat i
	* mida.
	* @param dif dificultat dels sudokus representats 
	* per la informacio.
	* @param size nivell dels sudokus representats 
	* per la informacio.
	*/
	public String getSudokuInfo(int dif, int size) throws Exception {
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
		String dir = autoPath + "/data/Sudokus/" + size + "x" + size + "/" + difSt + "/infoSudokus";
		FileReader fr = new FileReader(dir);
		Scanner scn = new Scanner(fr);
		String info = scn.next();
		fr.close();
		return info;
	}

	/**
   	* Permet obtenir el ID d'un sudoku d'una certa mida i
   	* dificultat.
   	* @param size mida del sudoku.
   	* @param dif dificultat del sudoku(0, 1 o 2 de menor
   	* a major dificultat).
  	* @return String la ID d'un sudoku que cumpleixi
  	* la mida i dificultat donades.
  	*/
	public String getIDSudokuRandom(int size, int dif) throws Exception {
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
		return sudokus[pos].getName();
	}

	/**
	* Permet obtenir tots els
	* identificadors de sudoku
	* dels sudokus amb una certa
	* mida i nivell de dificultat.
	* @param size la mida.
	* @param dif la dificultat.
	* @return List<String> amb
	* els identificadors dels
	* sudokus.
	*/
	public List<String> getIDSudokus(int size, int dif){
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
		List<String> idSudokus = new ArrayList<String>();
		for(int a = 0; a < sudokus.length; ++a){
			idSudokus.add(sudokus[a].getName());
		}
		return idSudokus;
	}

	/**
	* Permet obtenir el sudoku amb
	* la id donada.
	* @param id identificador del sudoku.
	* @param size mida del sudoku.
	* @param dif dificultat del sudoku.
	* @return String sudoku serialitzat.
	*/
	public String getSudoku(String id, int size, int dif) throws Exception {
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
		FileReader fr = new FileReader(autoPath + "/data/Sudokus/" + size + "x" + size + "/" + difSt + "/" + id);
		Scanner scn = new Scanner(fr);
		String sudo = scn.next();
		return sudo;
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

	/**
   	* Retorna una partida.
  	* @return String partida serialitzada.
  	*/
	public String getSavedMatch(String idMatch) throws Exception {
		FileReader fr = new FileReader(autoPath + "/data/Users/" + user + "/Partidas/" + idMatch);
		Scanner scn = new Scanner(fr);
		String savedMatch = scn.next();
		fr.close();
		return savedMatch;
	}

	/**
   	* Guarda una partida de l'usuari.
   	* @param idSudoku id del sudoku
   	* de la partida a guardar.
   	* @param match partida.
  	*/
	public void saveMatch(String idSudoku, String match) throws Exception {
		File f = new File(autoPath + "/data/Users/" + user + "/Partidas/" + idSudoku);
		f.delete();
		f.createNewFile();
		FileWriter fw = new FileWriter(f.getAbsolutePath());
		PrintWriter pw = new PrintWriter(fw);
		pw.print(match);
		fw.close();
	}

	/**
	* Borra una partida de l'usuari.
	* @param idSudoku id del sudoku 
	* de la partida a eliminar.
	*/
	public void deleteMatch(String idSudoku) throws Exception {
		new File(autoPath + "/data/Users/" + user + "/Partidas/" + idSudoku).delete();
	}

	/**
	* Metode per a modificar la informacio guardada
	* sobre les partides de l'usuari.
	* @param serializedInfo informacio a guardar
	* serialitzada.
	*/
	public void introduceMatchInfo(String serializedInfo) throws Exception {
		File f = new File(autoPath + "/data/Users/" + user + "/Partidas/infoMatches");
		f.delete();
		f.createNewFile();
		FileWriter fw = new FileWriter(f.getAbsolutePath());
		PrintWriter pw = new PrintWriter(fw);
		pw.print(serializedInfo);
		fw.close();
	}

	/**
	* Metode per a obtenir la informacio guardada
	* de les partides de l'usuari.
	*/
	public String getMatchInfo() throws Exception {
		FileReader fr = new FileReader(autoPath + "/data/Users/" + user + "/Partidas/infoMatches");
		Scanner scn = new Scanner(fr);
		String info = scn.next();
		fr.close();
		return info;
	}

	/**
	* Metode per saver si existeix o no
	* Ranking Global
	* @return boolean Retorna true si existeix
	* el RankingGlobal, false altrament.
	*/
	public boolean existRankingGlobal(){
		File u = new File(autoPath + "/data/Ranking/RankingGlobal");
		return u.exists();
	}

	/**
   	* Retorna el RankingGlobal. 
  	* @return String RankingGlobal serialitzat.
  	*/
	public String getRankingGlobal() throws Exception {
		FileReader fr = new FileReader(autoPath + "/data/Ranking/RankingGlobal");
		Scanner scn = new Scanner(fr);
		String ranking = scn.next();
		fr.close();
		return ranking;
	}

	/**
	* Afegeix o modifica RankingGlobal.
	* @param rankingGLobal nou RankingGlobal.
  	*/
	public void setRankingGlobal(String rankingGlobal) throws Exception {
		File f = new File(autoPath + "/data/Ranking/RankingGlobal");
		f.delete();
		f.createNewFile();
		FileWriter fw = new FileWriter(f.getAbsolutePath());
		PrintWriter pw = new PrintWriter(fw);
		pw.print(rankingGlobal);
		fw.close();
	}
}
