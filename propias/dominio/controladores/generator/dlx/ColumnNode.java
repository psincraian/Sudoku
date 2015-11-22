package propias.dominio.controladores.generator.dlx;

/**
 * 
 * @author Petru Rares Sincraian
 *
 */
public class ColumnNode extends Node {
	
	private int size; 		// numero de 1s de la columna
	private String name;	// el nom de la columna o solucio
	
	public ColumnNode(String name) {
		super();
		this.size = 0;
		this.name = new String(name);
		columnNode = this;
	}
	
	public void cover() {
		unlinkLeftRight();
		for (Node i = getDownNode(); i != this; i = i.getDownNode()) {
			for (Node j = i.getRightNode(); j != i; j = j.getRightNode()) {
				j.unlinkUpDown();
				j.columnNode.decrementSize();
			}
		}
	}
	
	public void uncover() {
		for (Node i = getUpNode(); i != this; i = i.getUpNode()) {
			for (Node j = i.getLeftNode(); j != i; j = j.getLeftNode()) {
				j.columnNode.incrementSize();
				j.relinkUpDown();
			}
		}
		relinkLeftRight();
	}
	
	public void decrementSize() {
		--size;
	}
	
	public void incrementSize() {
		++size;
	}
	
	public String getName() {
		return name;
	}
	
	public int getSize() {
		return size;
	}
}