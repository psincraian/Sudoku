package propias.dominio.clases;

/**
 * 
 * Aquesta clase s'utilitza per contenir dos elements en
 * un ranking. Per un costat, el nom d'un usuari
 * i la puntuació que represant/temps obtingut d'una partida. 
 * 
 * @author Daniel Sanchez Martinez
 * 
 */
public class ParamRanking implements Comparable<ParamRanking>, java.io.Serializable  {

	private String name;
	private long value;
	
	/**
	 * 
	 * Constructor amb un nom i valor, que pot ser puntuació 
	 * o temps
	 * 
	 * @param name : nom de l'usuari
	 * @param value : puntuació/temps de la partida
	 * 
	 */
	public ParamRanking(String name, long value){
		this.name = name;
		this.value = value;
	}
	
	/**
	 * 
	 * @return Retorna el nom de l'usuari
	 * 
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * 
	 * @return Retorna la puntuació/temps de la partida
	 * 
	 */
	public long getValue(){
		return this.value;
	}
	
	/**
	 * 
	 * Configura el segon valor de ParamRanking
	 *
	 * @param value : puntuació/temps en questió
	 * 
	 */
	public void setValue(long value){
		this.value = value;
	}
	
	/**
	 * 
	 * Implementa la funció compareTo de la clase Comparable
	 * per tal de comparar dos ParamRanking i que el ranking
	 * estigui ordenat
	 * 
	 */
	public int compareTo(ParamRanking pr){	
		return this.value == pr.value ? 0 : this.value < pr.value ? -1 : 1;	
	}
}
