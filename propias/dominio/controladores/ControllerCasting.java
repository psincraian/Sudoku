package propias.dominio.controladores;

import propias.dominio.clases.*;
import propias.persistencia.ControllerPersistance;
import java.io.*;
import java.util.List;
import java.util.ArrayList;
import javax.xml.bind.DatatypeConverter; 

public class ControllerCasting {

	ControllerPersistance cp;

	public ControllerCasting(){
		cp = new ControllerPersistance();
	}

	/**
   	* Permet saber si un usuari existeix o no. 
  	* @param user identificador de l'usuari.
  	* @return boolean true si existeix, false si no.
  	*/
	public boolean existsUser(String user) {
		return cp.existsUser(user);
	}

	/**
	* Permet crear un nou usuari.
	* @param user usuari a afegir.
	*/
	public void createUser(Usuari user) throws Exception {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		oos.writeObject(user);
		oos.close();
		String serializedUser = new String(DatatypeConverter.printBase64Binary(bos.toByteArray()));	
		ByteArrayOutputStream bos2 = new ByteArrayOutputStream();
		ObjectOutputStream oos2 = new ObjectOutputStream(bos2);
		oos2.writeObject(new Stadistics());
		oos2.close();
		String serializedStd = new String(DatatypeConverter.printBase64Binary(bos2.toByteArray()));   	
		cp.newUser(user.consultarNom(), serializedUser, serializedStd);
		RankingGlobal rank = new RankingGlobal(new ArrayList<ParamRanking>());
		if(cp.existRankingGlobal()){
			rank = getRankingGlobal();
		}
		ParamRanking pr = new ParamRanking(user.consultarNom(), 0L);
		rank.modRanking(pr);
		setRankingGlobal(rank);
	}

	/**
	 * Permet obtenir un usuari
	 * concret de persistencia.
	 * @param nomUser nom de 
	 * l'usuari.
	 * @return Usuari l'usuari
	 */
	public Usuari getUser(String nomUser) throws Exception {
		byte[] bytes = DatatypeConverter.parseBase64Binary(cp.getUser(nomUser));
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
    		ObjectInputStream ois = new ObjectInputStream(bis);
    		Usuari user = (Usuari) ois.readObject();
    		bis.close();
    		ois.close();
    		return user;
	}

	/**
   	* Inicialitza el controlador de persistencia
   	* per a un usuari concret.
  	* @param user identificador de l'usuari
  	*/
	public void userDBInit(String user){
		cp.userDBInit(user);
	}

	/**
	* Permet obtenir les caracteristiques
	* de l'usuari actual.
	* @return Stadistics estadistiques.
	*/
	public Stadistics getStadistics() throws Exception {	
		byte[] bytes = DatatypeConverter.parseBase64Binary(cp.getStadistics());
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
    		ObjectInputStream ois = new ObjectInputStream(bis);
    		return (Stadistics) ois.readObject();
	}

	/**
	* Permet actualitzar les estadistiques
	* amb unes noves.
	* @param std les noves estadistiques.
	*/
	public void setStadistics(Stadistics std)  throws Exception {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		oos.writeObject(std);
		oos.close();
		String serializedStd = new String(DatatypeConverter.printBase64Binary(bos.toByteArray())); 	
		cp.setStadistics(serializedStd);
	}
	
	/**
	* Permet introduir un nou sudoku als
	* fitxers.
	* @param sudo el sudoku a introduir.
	* @param givens nombre de caselles
	* donades del sudoku.
	* @return String la id del sudoku.
	*/
	public String introduceSudoku(Sudoku sudo, int givens) throws Exception {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		oos.writeObject(sudo);
		oos.close();
		String serializedSudoku = new String(DatatypeConverter.printBase64Binary(bos.toByteArray()));  
		String id = cp.introduceSudoku(serializedSudoku, sudo.returnLevel(), sudo.getSudoku().getSize());
		ListSudokuInfo info = getSudokuInfo(sudo.returnLevel(), sudo.getSudoku().getSize());
		info.addInfo(id, sudo.returnMaker(), givens);
		introduceSudokuInfo(info, sudo.returnLevel(), sudo.getSudoku().getSize());
		return id;
	}

	/**
	* Metode per a modificar la informacio
	* adicional dels sudokus guardada en
	* les bases de dades.
	* @param info la nova informacio a
	* escriure al fitxer.
	* @param lvl nivell dels sudokus
	* representats en la informacio.
	* @param size mida dels sudokus
	* representats en la informacio.
	*/
	private void introduceSudokuInfo(ListSudokuInfo info, int lvl, int size) throws Exception {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		oos.writeObject(info);
		oos.close();
		String serializedInfo =  new String(DatatypeConverter.printBase64Binary(bos.toByteArray()));
		cp.introduceSudokuInfo(serializedInfo, lvl, size);
	}

	/**
	* Retorna la informacio dels
	* sudokus d'una certa mida i
	* nivell de dificultat.
	* @param lvl nivell.
	* @param size mida.
	* @return List<List<String>>
	* la informacio demanada.
	*/
	private ListSudokuInfo getSudokuInfo(int lvl, int size) throws Exception {
		byte[] bytes;
		try {
			bytes = DatatypeConverter.parseBase64Binary(cp.getSudokuInfo(lvl, size));
		} catch(FileNotFoundException fnfe) {
			introduceSudokuInfo(new ListSudokuInfo(), lvl, size);
			bytes = DatatypeConverter.parseBase64Binary(cp.getSudokuInfo(lvl, size));
		}		
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
    	ObjectInputStream ois = new ObjectInputStream(bis);
    	return (ListSudokuInfo) ois.readObject();
	}

	/**
	* Permet obtenir l'identificador
	* d'un sudoku amb mida size i
	* dificultat dif random dels fitxers.
	* @param size mida.
	* @param dif dificultat.
	* @return String l'identificador.
	*/
	public String getIDSudokuRandom(int size, int dif) throws Exception {
		return cp.getIDSudokuRandom(size, dif);
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
	public List<String> getIDSudokus(int size, int dif) throws Exception {
		return cp.getIDSudokus(size, dif);
	}

	/**
	* Permet obtenir tots els
	* identificadors de sudoku
	* i qui es el seu creador
	* dels sudokus amb una certa
	* mida i nivell de dificultat
	* i que a demes tinguin un
	* minim de caselles inicials.
	* @param size la mida.
	* @param dif la dificultat.
	* @return List<String> amb
	* els identificadors dels
	* sudokus.
	*/
	public List<List<String>> getIDSudokusAndMaker(int size, int dif, int givens) throws Exception {
		ListSudokuInfo info = getSudokuInfo(dif, size);
		return info.getInfoIdMaker(givens);
	}

	/**
	* Permet obtenir un sudoku
	* de la mida i dificultat
	* demanades i amb una certa
	* id.
	* @param size mida.
	* @param dif dificultat.
	* @return Sudoku sudoku amb les
	* caracteristiques demanades.
	*/
	public Sudoku getSudoku(int size, int dif, String id) throws Exception {
		byte[] bytes = DatatypeConverter.parseBase64Binary(cp.getSudoku(id, size, dif));
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
    	ObjectInputStream ois = new ObjectInputStream(bis);
    	return (Sudoku) ois.readObject();
	}

	/**
   	* Retorna l'identificador de les partides guardades de
   	* l'usuari carregat al controlador de persistencia.
  	* @return List<String> retorna els identificadors de
  	* les partides.
  	*/
	public List<String> getIdMatches() throws Exception {
		return cp.getIdMatches();
	}

	/**
	* Retorna la partida guardada amb identificador
	* id.
	* @param id identificador de partida.
	* @return MatchTraining partida guardada.
	*/
	public MatchTraining getSavedMatch(String id) throws Exception {
		byte[] bytes = DatatypeConverter.parseBase64Binary(cp.getSavedMatch(id));
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
    		ObjectInputStream ois = new ObjectInputStream(bis);
    		return (MatchTraining) ois.readObject();
	}

	/**
	*  Guarda el match m amb el nom name.
	* @param m partida.
	* @param name nom de la partida.
	*/
	public void saveMatch(MatchTraining m, String name) throws Exception {		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
    		ObjectOutputStream oos = new ObjectOutputStream(bos);
    		oos.writeObject(m);
    		oos.close();
	 	String serializedMatch = new String(DatatypeConverter.printBase64Binary(bos.toByteArray()));    	
	 	cp.saveMatch(name, serializedMatch);
	}

	/**
	* Retorna el Ranking Global.
	*/
	public RankingGlobal getRankingGlobal() throws Exception {		
		byte[] bytes = DatatypeConverter.parseBase64Binary(cp.getRankingGlobal());
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
    		ObjectInputStream ois = new ObjectInputStream(bis);
	 	return (RankingGlobal) ois.readObject();
	}

	/**
	* Modifica el Ranking Global.
	* @param ranking nou RankingGlobal.
	*/
	public void setRankingGlobal(RankingGlobal ranking) throws Exception {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
	 	ObjectOutputStream oos = new ObjectOutputStream(bos);
	 	oos.writeObject(ranking);
	 	oos.close();
	 	String serializedranking = new String(DatatypeConverter.printBase64Binary(bos.toByteArray()));    	
	 	cp.setRankingGlobal(serializedranking);
	}

	/*
	public static Object deserialize(String fileName) throws IOException, ClassNotFoundException {
	        FileInputStream fis = new FileInputStream(fileName);
	        BufferedInputStream bis = new BufferedInputStream(fis);
	        ObjectInputStream ois = new ObjectInputStream(bis);
	        Object obj = ois.readObject();
	        ois.close();
	        return obj;
    	}

    	public static void serialize(Object obj, String fileName) throws IOException {
	        FileOutputStream fos = new FileOutputStream(fileName);
	        BufferedOutputStream bos = new BufferedOutputStream(fos);
	        ObjectOutputStream oos = new ObjectOutputStream(bos);
	        oos.writeObject(obj);
	        oos.close();
    	}
	*/

}
