package propias.dominio.clases;

/**
 * 
 * @author Petru Rares Sincraian
 *
 */
public class Position {
	
	private int row;
	private int column;
	
	public Position(int row, int column) throws Exception {
		if (isValidRow(row) && isValidColumn(column)) {
			this.row = row;
			this.column = column;
		} else {
			throw new Exception("Row or column is negative");
		}
	}
	
	public Position() throws Exception {
		this(0, 0);
	}
	
	public int getRow() {
		return row;
	}
	
	public int getColumn() {
		return column;
	}
	
	public void setRow(int row) throws Exception {
		if (isValidRow(row))
			this.row = row;
		else
			throw new Exception("Row is negative");
	}
	
	public void setColumn(int column) throws Exception {
		if (isValidColumn(column))
			this.column = column;
		else
			throw new Exception ("Column is negative");
	}
	
	private boolean isValidRow(int row) {
		return row >= 0;
	}
	
	private boolean isValidColumn(int columnt) {
		return column >= 0;
	}
}
