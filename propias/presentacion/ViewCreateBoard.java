package propias.presentacion;

/**
 * Set a type of board view
 * 
 * @author daniel sanchez martinez
 */
public class ViewCreateBoard extends GenerateBoard{
	/**
	 * Constructor
	 * @param board
	 * @param size
	 */
	public ViewCreateBoard(int[][] board, int size){
		super(board,size);
		setTitle("Creació d'un nou Sudoku");
		pack();
		setVisible(true);
		disableHintButton();
	}
	/**
	 * For this view hints buttons aren't necessary
	 */
	public void  disableHintButton(){
		extraButton[1].setEnabled(false);
		extraButton[2].setEnabled(false);
		extraButton[1].setToolTipText(extraButton[1].getText()+"-No disponible");
		extraButton[2].setToolTipText(extraButton[2].getText()+"-No disponible");
	}
}
