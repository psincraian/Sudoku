package propias.Driver;

import propias.persistencia.ControllerPersistance;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class DriverControladorPersistencia{

	public static void main(String[] args) {
		try{
			Scanner scn = new Scanner(System.in);
			ControllerPersistance cP = new ControllerPersistance(true);
			
			System.out.println("Driver controlador de persistencia.");
			
			System.out.println("Prueva crear nuevos usuarios: ");
			System.out.println("Cuantos usuarios desea introducir?");
			int numUsers = scn.nextInt();
			for(int i = 0; i < numUsers; ++i){
				System.out.println("Usuario numero " + (i+1) + ".");
				System.out.println("Introduce un nombre!");
				System.out.println("(no repitas nombres por favor, \npuedes pero puede dar errores que \nno tengo que tratar yo :( )");
				System.out.println("Nombre: ");
				String nom = scn.next();
				System.out.println("Introduce contraseña!");
				String contra = scn.next();
				cP.newUser(nom, contra);
			}
			
			System.out.println("Prueva existencia usuarios \ny obtencion contraseña.");
			int seguir = 0;
			do{
				System.out.println("Introduce el nombre de usuario: ");
				String nom = scn.next();
				if(cP.existsUser(nom)){
					System.out.println("Contraseña: " + cP.getPasswordUser(nom));
				}
				else{
					System.out.println("Aquest usuari no existeix!");
				}
				System.out.println("Vol seguir provant usuaris? si(1)|no(0)");
				seguir = scn.nextInt();
			}while(seguir == 1);
			
			System.out.println("Login d'un usuari");
			boolean exist = true;
			String nomUser;
			do{
				if(!exist){
					System.out.println("L'usuari no existeix! Entra un altre nom.");
				}
				System.out.println("Introdueix nom: ");
				nomUser = scn.next();
				exist = cP.existsUser(nomUser);
			}while(!exist);
			cP.userDBInit(nomUser);
			System.out.println("Prova get i set estadistiques d'un usuari: ");
			List<Long> stadistics = new ArrayList<Long>(cP.getStadistics());
			System.out.println("ESTADISTIQUES USUARI: ");
			for(int i = 0; i < stadistics.size(); ++i){
				System.out.println("Estadistica" + i + " = " + stadistics.get(i));
			}
			System.out.println("DONAR NOVES ESTADISTIQUES:");
			for(int i = 0; i < stadistics.size(); ++i){
				System.out.println("Estadistica" + i + ":");
				Long num = scn.nextLong();
				stadistics.set(i, num);
			}		
			cP.setStadistics(stadistics);		
			System.out.println("ESTADISTIQUES NOVES!");
			stadistics = new ArrayList<Long>(cP.getStadistics());
			for(int i = 0; i < stadistics.size(); ++i){
				System.out.print("Estadistica" + i + " = " + stadistics.get(i));
			}

			System.out.println("Ranking:");
			List<List<String>> ranking = new ArrayList<List<String>>(cP.getRanking("RankingGlobal"));
			for(int i = 0; i < ranking.size(); ++i){
				List<String> tmp = new ArrayList<String>(ranking.get(i));
				System.out.println("user: " + tmp.get(0) + "score: " + tmp.get(1));
			}

			System.out.println("Modificar score del teu usuari!");
			System.out.println("Nou score: ");
			String score = scn.next();
			for(int i = 0; i < ranking.size(); ++i){
				List<String> tmp = ranking.get(i);
				if(tmp.get(0).equals(nomUser)){
					tmp.set(1, score);
					ranking.set(i, tmp);
				}
			}

			System.out.println("Nou ranking:");
			ranking = new ArrayList<List<String>>(cP.getRanking("RankingGlobal"));
			for(int i = 0; i < ranking.size(); ++i){
				List<String> tmp = new ArrayList<String>(ranking.get(i));
				System.out.println("user: " + tmp.get(0) + "score: " + tmp.get(1));
			}

			cP.setRanking("RankingGlobal", ranking);

			System.out.println("Treball amb la base de dades de sudokus: ");
			System.out.println("ID dels sudokus guardats per l'usuari:");
			List<String> partides = cP.getIdMatches();
			for(int i = 0; i < partides.size(); ++i){
				System.out.println("Partida" + i + ": " + partides.get(i));
			}

			
		
 		
		}
		catch(Exception e){
			System.out.println("Algo ha petat! (Pensa que els catch d'excepcions\nno es fan a persistencia, si has intentat accedir\na algun usuari inexistent o \"base de dades\" que \n no estigui inicialitzada pot haver retornat una excepcio \n i no ser un error del funcionament del controlador).");
		}
	}

}

