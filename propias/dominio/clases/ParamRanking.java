package propias.dominio.clases;

/**
 * 
 * This class contains a pair of elements. In one hand a 
 * name of an user, in the other hand it can be represented
 * by a value referencing points of a sudoku's match or 
 * the time of a sudoku's match. 
 * 
 * @author Daniel Sanchez Martinez
 */
public class ParamRanking implements Comparable<ParamRanking>, java.io.Serializable  {

	private String name;
	private long value;
	/**
	 * Constructor with name and value as parameters
	 * @param name
	 * @param value
	 */
	public ParamRanking(String name, long value){
		this.name = name;
		this.value = value;
	}
	
	/**
	 * Return name of player
	 * @return
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * Return points/time
	 * @return
	 */
	public long getValue(){
		return this.value;
	}
	
	/**
	 * Modify actual value
	 * @param value
	 */
	public void setValue(long value){
		this.value = value;
	}
	
	/**
	 * Compare values of a pair of parameters
	 */
	public int compareTo(ParamRanking pr){	
		return this.value == pr.value ? 0 : this.value < pr.value ? -1 : 1;	
	}
}
