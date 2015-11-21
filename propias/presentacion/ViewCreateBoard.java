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
	}
	/**
	 * For this view, hint's button aren't necessary
	 */
	public void  disableHintButton(){
		extraButton[1].setEnabled(false);
		extraButton[2].setEnabled(false);
		extraButton[1].setToolTipText(extraButton[1].getName()+"-No disponible");
		extraButton[2].setToolTipText(extraButton[2].getName()+"-No disponible");
	}
}
