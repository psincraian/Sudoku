package propias.dominio.controladores.generator.dlx;

/**
 * 
 * @author Petru Rares Sincraian
 *
 */
public class Node {
	
	private Node leftNode;
	private Node rightNode;
	private Node upNode;
	private Node downNode;
	protected ColumnNode columnNode;
	
	public Node() {
		leftNode = rightNode = upNode = downNode = this;
		columnNode = null;
	}
	
	public Node(ColumnNode columnNode) {
		this();
		this.columnNode = columnNode;
	}
	
	// afegeix el Node node sota el referenciat
	public void addNodeDown(Node node) {
		this.downNode.upNode = node;
		node.downNode = this.downNode;
		this.downNode = node;
		node.upNode = this;
	}
	
	// afegeix el Node node a l'esquerra del referenciat
	public void addNodeLeft(Node node) {
		this.leftNode.rightNode = node;
		node.leftNode = this.leftNode;
		this.leftNode = node;
		node.rightNode = this;
	}
	
	// elimina el parametre implicit de la llista horitzontal
	public void unlinkLeftRight() {
		this.leftNode.rightNode = this.rightNode;
		this.rightNode.leftNode = this.leftNode;
	}
	
	// torna a afegir el parametre implicit a la llista horitzontal
	public void relinkLeftRight() {
		this.leftNode.rightNode = this;
		this.rightNode.leftNode = this;
	}
	
	// elimina el parametre implicit de la llista vertical
	public void unlinkUpDown() {
		this.upNode.downNode = this.downNode;
		this.downNode.upNode = this.upNode;
	}
	
	// torna a afegir el parametre implicit a la llista vertical
	public void relinkUpDown() {
		this.downNode.upNode = this;
		this.upNode.downNode = this;
	}
	
	public Node getDownNode() {
		return downNode;
	}
	
	public Node getUpNode() {
		return upNode;
	}
	
	public Node getLeftNode() {
		return leftNode;
	}
	
	public Node getRightNode() {
		return rightNode;
	}
}
