package propias.dominio.clases.dlx;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import propias.dominio.clases.Board;
import propias.dominio.clases.Cell;

/**
 * 
 * @author Petru Rares Sincraian
 *
 */
public class DLXHandler {
	
	/** Ens converteix la matrius de booleans en una matriu DLX. matrix ens 
	 * indica els contraints del sudoku que volem resoldre.
	 * 
	 * @param matrix Matriu de booleans a convertir
	 * @return Ens retorna el header de la matrius DLX
	 */
    public static ColumnNode convertToDLXBoard(boolean[][] matrix){
        final int columns = matrix[0].length;
        final int rows = matrix.length;

        ColumnNode headerNode = new ColumnNode("HEADER");
        List<ColumnNode> columnNodes = new ArrayList<ColumnNode>();

        addColumnNodes(columnNodes, headerNode, columns);
        addNodes(columnNodes, matrix, rows, columns);
        
        return headerNode;
    }

    /** Ens retorna una matriu de booleans amb els constraints del sudoku
     * 
     * @param sudoku El Board del qual volem obtenir la matrius
     * @return Una matriu de booleans amb els contraints del sudoku
     */
    public static boolean[][] makeExactCoverGrid(Board sudoku) {
        boolean[][] cover = sudokuExactCover(sudoku.getSize());
        
		for (int i = 1; i <= sudoku.getSize(); i++){
		    for (int j = 1; j <= sudoku.getSize(); j++){
		        Cell cell = sudoku.getCell(i - 1, j - 1);
		        if (cell.isValid()) {
		            for (int num = 1; num <= sudoku.getSize(); num++) {
		                if (num != cell.getValue()) {
		                    Arrays.fill(cover[getRowIndex(i, j, num, sudoku.getSize())], false);
		                }
		            }
		        }
		    }
		}
        
        return cover;
    }
    
    /** Ens retorna la solució de la llista de nodes 
     * 
     * @param answer Es la solució del sudoku. Es del format DLX
     * @param size Es la mida del sudoku 
     * @return Ens retorna un array amb la solució del Board
     */
    public static int[][] getArraySolution(List<Node> answer, int size) {
 		int[][] result = new int[size][size];
 		for(Node n : answer) {
 			Node rowColumnNode = n;
 			int min = Integer.parseInt(rowColumnNode.columnNode.getName());
 			
 			for(Node tmp = n.getRightNode(); tmp != n; tmp = tmp.getRightNode()) {
 				int value = Integer.parseInt(tmp.columnNode.getName());
 				if (value < min) {
 					min = value;
 					rowColumnNode = tmp;
 				}
 			}
 			
 			int ans1 = Integer.parseInt(rowColumnNode.columnNode.getName());
 			int ans2 = Integer.parseInt(rowColumnNode.getRightNode().columnNode.getName());
 			int r = ans1 / size;
 			int c = ans1 % size;
 			int num = (ans2 % size) + 1;
 			result[r][c] = num;
 		}
 		return result;
 	}
    
    /** Afegeix els nodes columna a la matriu DLX
     * 
     * @param columnNodes Els nodes columna de la matriu DLX
     * @param headerNode El header de la matriu DLX
     * @param columns El nombre de columnes de la matriu DLX
     */
    private static void addColumnNodes(List<ColumnNode> columnNodes, 
    		ColumnNode headerNode, int columns) {
    	for (int i = 0; i < columns; i++) {
            ColumnNode n = new ColumnNode(Integer.toString(i));
            columnNodes.add(n);
            headerNode.addNodeLeft(n);
        }
    }
    
    /** Afegeix els Nodes interns
     * 
     * @param columnNodes Els nodes columna de la matriu DLX
     * @param matrix La matriu de booleans amb els contraints
     * @param rows	El nombre de files
     * @param columns El nombre de columnes
     */
    private static void addNodes(List<ColumnNode> columnNodes, boolean matrix[][], 
    		int rows, int columns) {
    	
    	for (int i = 0; i < rows; i++) {
            Node prev = null;
            
            for(int j = 0; j < columns; j++) {
                if (matrix[i][j]) {
                    ColumnNode col = columnNodes.get(j);
                    Node newNode = new Node(col);
                    if (prev == null)
                        prev = newNode;
                    col.getUpNode().addNodeDown(newNode);
                    prev.addNodeLeft(newNode);
                    col.incrementSize();;
                }
            }
        }
    }
    
    /** Retorna la base del SudokuExactCover
     * 
     * @param size es la mida del sudoku
     * @return Retorna una matriu de booleans amb els contraints del sudoku base
     */
    private static boolean[][] sudokuExactCover(int size) {
        boolean[][] cover = new boolean[size * size * size][size * size * 4];
        int coverColumn = 0;
        
        coverColumn = rowColumnConstraints(cover, coverColumn, size);
        coverColumn = rowContraints(cover, coverColumn, size);
        coverColumn = columnConstraints(cover, coverColumn, size);
        coverColumn = blockConstraints(cover, coverColumn, size);

        return cover;
    }

	/** Afegeix les restriccions de casella al cover
	 * 
	 * @param cover A on ho volem afeigir
	 * @param coverColumn A partir de quin coverColumn el volem afegir
	 * @param size La mida del sudoku
	 * @return Retorna el coverColumn modificat amb l'ultima posicio
	 */
    private static int rowColumnConstraints(boolean cover[][], int coverColumn, int size) {
        for (int row = 1; row <= size; row++) {
            for (int column = 1; column <= size; column++, coverColumn++) {
                for (int number = 1; number <= size; number++) {
                    cover[getRowIndex(row, column, number, size)][coverColumn] = true;
                }
            }
        }
        
        return coverColumn;
    }
    
    /** Afegeix les restriccions de fila al cover
	 * 
	 * @param cover A on ho volem afeigir
	 * @param coverColumn A partir de quin coverColumn el volem afegir
	 * @param size La mida del sudoku
	 * @return Retorna el coverColumn modificat amb l'ultima posicio
	 */
    private static int rowContraints(boolean cover[][], int coverColumn, int size) {
    	 for(int row = 1; row <= size; row++){
             for(int number = 1; number <= size; number++, coverColumn++){
                 for(int column = 1; column <= size; column++){
                 	 cover[getRowIndex(row, column, number, size)][coverColumn] = true;
                 }
             }
         }
    	 
    	 return coverColumn;
    }
    
    /** Afegeix les restriccions de columna al cover
	 * 
	 * @param cover A on ho volem afeigir
	 * @param coverColumn A partir de quin coverColumn el volem afegir
	 * @param size La mida del sudoku
	 * @return Retorna el coverColumn modificat amb l'ultima posicio
	 */
    private static int columnConstraints(boolean cover[][], int coverColumn, int size) {
    	for (int column = 1; column <= size; column++) {
            for (int number = 1; number <= size; number++, coverColumn++) {
                for (int row = 1; row <= size; row++) {
                    cover[getRowIndex(row, column, number, size)][coverColumn] = true;
                }
            }
        }
    	
    	return coverColumn;
    }

    /** Afegeix les restriccions de block al cover
	 * 
	 * @param cover A on ho volem afeigir
	 * @param coverColumn A partir de quin coverColumn el volem afegir
	 * @param size La mida del sudoku
	 * @return Retorna el coverColumn modificat amb l'ultima posicio
	 */
    private static int blockConstraints(boolean cover[][], int coverColumn, int size) {
    	int side = (int) Math.sqrt(size);
    	
        for (int blockRow = 1; blockRow <= size; blockRow += side) {
            for (int blockColumn = 1; blockColumn <= size; blockColumn += side) {
                for (int n = 1; n <= size; n++, coverColumn++) {
                    for (int insideRow = 0; insideRow < side; insideRow++) {
                        for (int insideColumn = 0; insideColumn < side; insideColumn++) {
                            cover[getRowIndex(
                            		blockRow + insideRow, 
                            		blockColumn + insideColumn, 
                            		n, size
                            		)][coverColumn] = true;
                        }
                    }
                }
            }
        }
        
        return coverColumn;
    }
    
    /** Ens retorna un index identificador donat una fila, columna i numero
     * 
     * @param row La fila 
     * @param col La columna
     * @param num El numero
     * @param size La mida de la matriu
     * @return
     */
	private static int getRowIndex(int row, int col, int num, int size){
		return (row - 1) * size * size + (col - 1) * size + (num - 1);
	}
}
