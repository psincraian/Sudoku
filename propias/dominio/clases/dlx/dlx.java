package propias.dominio.clases.dlx;

import java.util.ArrayList;
import java.util.List;
import propias.dominio.clases.Board;

/**
 * 
 * @author Petru Rares Sincraian
 *
 */
public class dlx {
	private ColumnNode header;
	private boolean uniqueSolution;
	private List<Node> answer;
	private int solutions;
	private int size;
	private boolean needUnique;
	private boolean found;
	private Board solution;
	
	public dlx(Board board) {
		boolean[][] cover = DLXHandler.makeExactCoverGrid(board);
		header = DLXHandler.convertToDLXBoard(cover);
		size = board.getSize();
		this.needUnique = false;
	}
	
	public void solve() {
		uniqueSolution = true;
		answer = new ArrayList<Node>();
		solutions = 0;
		found = false;
		search(0);
	}
	
	public void needUnique() {
		needUnique = true;
	}
	
	// retorna la resposta correcta només si s'ha especificat que es vol
	public boolean isUniqueSolution() {
		return uniqueSolution;
	}
	
	public Board getSolution() {
		return solution;
	}
	
    private ColumnNode selectColumnNodeHeuristic(){
        int min = Integer.MAX_VALUE;
        ColumnNode ret = null;
        for(ColumnNode c = (ColumnNode) header.getRightNode(); c != header; c = (ColumnNode) c.getRightNode()){
            if (c.getSize() < min){
                min = c.getSize();
                ret = c;
            }
        }
        return ret;
    }
	
	private void search(int k) {
		if (header.getRightNode() == header) {
			if (!found)
				solution = new Board(DLXHandler.getArraySolution(answer, size));
			
			++solutions;
			found = true;
			uniqueSolution = solutions < 2;
		} else {
			ColumnNode c = selectColumnNodeHeuristic();
			c.cover();
			
			for (Node r = c.getDownNode(); r != c; r = r.getDownNode()) {
				answer.add(r);
				
				for (Node j = r.getRightNode(); j != r; j = j.getRightNode())
					j.columnNode.cover();
				
				search(k + 1);
				
				if (doStop())
					return;
				
				r = answer.remove(answer.size() - 1);
				c = r.columnNode;
				for (Node j = r.getLeftNode(); j != r; j = j.getLeftNode())
					j.columnNode.uncover();
			}
			
			c.uncover();
		}
	}
	
	private boolean doStop() {
		return !needUnique && found || needUnique && !uniqueSolution;
	}
}
