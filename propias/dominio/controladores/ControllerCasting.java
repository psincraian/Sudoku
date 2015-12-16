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
		getMatchInfo();
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
	 * Permet obtenir l'usuari
	 * actual a persistencia.
	 * @return Usuari l'usuari
	 */
	public Usuari getUser() throws Exception {
		byte[] bytes = DatatypeConverter.parseBase64Binary(cp.getUser());
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
    	ObjectInputStream ois = new ObjectInputStream(bis);
    	Usuari user = (Usuari) ois.readObject();
    	bis.close();
    	ois.close();
    	return user;
	}

	/**
	* Permet esborrar l'usuari
	* demanat.
	* @param name nom de l'usuari
	* a esborrar.
	*/
	public void deleteUser() throws Exception {		
		changeNameObjects(cp.getNameUserDB(), cp.getNameUserDB() + "(eliminat)");
		cp.deleteUser();
	}

	/**
	* Permet modificar l'usuari carregat a
	* persistencia per un altre.
	*/
	public void setUser(Usuari user) throws Exception {		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
	 	ObjectOutputStream oos = new ObjectOutputStream(bos);
	 	oos.writeObject(user);
	 	oos.close();
	 	String serializeduser = new String(DatatypeConverter.printBase64Binary(bos.toByteArray()));	 	
		cp.setUser(serializeduser);
		if(!cp.getNameUserDB().equals(user.consultarNom())){
			changeNameObjects(cp.getNameUserDB(), user.consultarNom());
			cp.changeName(user.consultarNom());
		}
	}

	/**
	* Metode per canviar el nom als objectes
	* afectats al canviar el nom de l'usuari.
	* @param newName nou nom de l'usuari.
	*/
	public void changeNameObjects(String oldName, String newName) throws Exception {		
		changeNameSudokus(oldName, newName);
		changeNameListInfoMatches(oldName, newName);
		changeNameRankingGlobal(oldName, newName);
	}

	/**
	* Metode per actualitzar tots els sudokus
	* amb el nou nom d'usuari on sigui necessari.
	* @param oldName l'antic nom de l'usuari.
	* @param newName nou nom de l'usuari.
	*/
	public void changeNameSudokus(String oldName, String newName) throws Exception {
		for(int s = 0; s < 2; ++s){
			for(int dif = 0; dif < 3; ++dif){
				int size;
				if(s == 0) size = 9;
				else size = 16;
				ListSudokuInfo sudoInfo = getSudokuInfo(dif, size);
				sudoInfo.replaceMaker(oldName, newName);
				List<List<String>> idMakerGivensSudokus = sudoInfo.getInfoIdMakerGivens(0);
				introduceSudokuInfo(new ListSudokuInfo(), dif, size);
				for(int a = 0; a < idMakerGivensSudokus.size(); ++a){
					Sudoku sudo = getSudoku(size, dif, idMakerGivensSudokus.get(a).get(0));
					RankingSudoku rank = sudo.getRanking();
					List<ParamRanking> params = rank.getRanking();
					for(int pr = 0; pr < params.size(); ++pr){
						if(params.get(pr).getName().equals(oldName)){
							params.set(pr, new ParamRanking(newName, params.get(pr).getValue()));
						}
					}
					sudo.setRanking(new RankingSudoku(params));
					introduceSudoku(sudo, Integer.parseInt(idMakerGivensSudokus.get(a).get(2)));
				}
			}
		}
	}

	/**
	* Permet canviar el nom de l'usuari
	* a les llistes d'informacio de
	* partides guardades dels usuaris.
	* @param oldName l'antic nom de l'usuari.
	* @param newName nou nom de l'usuari.
	*/
	public void changeNameListInfoMatches(String oldName, String newName) throws Exception {
		List<String> users = cp.getUsersList();
		for(int a = 0; a < users.size(); ++a){
			ListMatchInfo infoMatches = getMatchInfo(users.get(a));
			infoMatches.replaceMaker(oldName, newName);
			introduceMatchInfo(infoMatches, users.get(a));
		}
	}

	/**
	* Permet canviar el nom de l'usuari
	* al ranking global.
	* @param oldName l'antic nom de l'usuari.
	* @param newName nou nom de l'usuari.
	*/
	public void changeNameRankingGlobal(String oldName, String newName) throws Exception {
		RankingGlobal rank = getRankingGlobal();
		List<ParamRanking> params = rank.getRanking();
		for(int pr = 0; pr < params.size(); ++pr){
			if(params.get(pr).getName().equals(oldName)){
				params.set(pr, new ParamRanking(newName, params.get(pr).getValue()));
			}
		}
		rank = new RankingGlobal(params);
		setRankingGlobal(rank);
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
	* Permet modificar un sudoku existent.
	* @param id identificador del sudoku a
	* modificar.
	* @param sudo el sudoku a introduir.
	* @param givens nombre de caselles
	* donades del sudoku.
	*/
	public void modifySudoku(String id, Sudoku sudo) throws Exception {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		oos.writeObject(sudo);
		oos.close();
		String serializedSudoku = new String(DatatypeConverter.printBase64Binary(bos.toByteArray()));
		cp.modifySudoku(serializedSudoku, id, sudo.returnLevel(), sudo.getSudoku().getSize());
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
		return info.getInfoIdMakerGivens(givens);
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
   	* Retorna l'identificador de les partides guardades de
   	* l'usuari carregat al controlador de persistencia.
  	* @return List<String> retorna els identificadors de
  	* les partides.
  	*/
	public List<List<String>> getIdMakerGivenSavedMatches() throws Exception {

		ListMatchInfo info = getMatchInfo();
		return info.getInfoIdMakerGivens();
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
	* Guarda el match m amb el nom name.
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
	* Guarda el match m amb el nom name
	* i actualitza la llista d'informacio
	* adicional de partides.
	* @param m partida.
	* @param name nom de la partida.
	* @param maker nom del creador del
	* sudoku de la partida.
	* @param givens nombre de caselles 
	* plenes en la partida.
	*/
	public void saveMatch(MatchTraining m, String name, String maker, int givens) throws Exception {		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
    		ObjectOutputStream oos = new ObjectOutputStream(bos);
    		oos.writeObject(m);
    		oos.close();
		String serializedMatch = new String(DatatypeConverter.printBase64Binary(bos.toByteArray()));
		cp.saveMatch(name, serializedMatch);
		ListMatchInfo info = getMatchInfo();
		info.addInfo(name, maker, givens);
		introduceMatchInfo(info);
	}

	/**
	* Metode per a borrar una partida
	* i tota la informacio derivada
	* d'aquesta de la base de dades.
	*/
	public void deleteMatch(String name) throws Exception {
		cp.deleteMatch(name);
		ListMatchInfo info = getMatchInfo();
	 	info.removeInfo(name);
	 	introduceMatchInfo(info);
	}

	/**
	* Metode per a modificar la informacio
	* adicional de les partides guardades a
	* les bases de dades.
	* @param info la nova informacio a
	* escriure al fitxer.
	*/
	private void introduceMatchInfo(ListMatchInfo info) throws Exception {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		oos.writeObject(info);
		oos.close();
		String serializedInfo =  new String(DatatypeConverter.printBase64Binary(bos.toByteArray()));
		cp.introduceMatchInfo(serializedInfo);
	}

	/**
	* Metode per a modificar la informacio
	* adicional de les partides guardades a
	* les bases de dades.
	* @param info la nova informacio a
	* escriure al fitxer.
	* @param user usuari a qui li actualitzem
	* dades.
	*/
	private void introduceMatchInfo(ListMatchInfo info, String user) throws Exception {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		oos.writeObject(info);
		oos.close();
		String serializedInfo =  new String(DatatypeConverter.printBase64Binary(bos.toByteArray()));
		cp.introduceMatchInfo(serializedInfo, user);
	}

	/**
	* Metode per a obtenir la informacio
	* adicional de les partides guardades a
	* les bases de dades.
	* @return ListMachInfo informacio de les
	* partides.
	*/
	private ListMatchInfo getMatchInfo() throws Exception {
		byte[] bytes;
		try {
			bytes = DatatypeConverter.parseBase64Binary(cp.getMatchInfo());
		} catch(FileNotFoundException fnfe) {
			introduceMatchInfo(new ListMatchInfo());
			bytes = DatatypeConverter.parseBase64Binary(cp.getMatchInfo());
		}		
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
    		ObjectInputStream ois = new ObjectInputStream(bis);
    		return (ListMatchInfo) ois.readObject();
	}

	/**
	* Metode per a obtenir la informacio
	* adicional de les partides guardades a
	* les bases de dades.
	* @param user usuari a qui li actualitzem
	* dades.
	* @return ListMachInfo informacio de les
	* partides.
	*/
	private ListMatchInfo getMatchInfo(String user) throws Exception {
		byte[] bytes;
		try {
			bytes = DatatypeConverter.parseBase64Binary(cp.getMatchInfo(user));
		} catch(FileNotFoundException fnfe) {
			introduceMatchInfo(new ListMatchInfo());
			bytes = DatatypeConverter.parseBase64Binary(cp.getMatchInfo(user));
		}		
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
    		ObjectInputStream ois = new ObjectInputStream(bis);
    		return (ListMatchInfo) ois.readObject();
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

}
