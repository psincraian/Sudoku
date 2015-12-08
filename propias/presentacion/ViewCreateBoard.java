package propias.presentacion;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

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
		setVisible(true);
		enableCustomProperties();
	}
	/**
	 * For this view hints buttons aren't necessary
	 */
	public void  enableCustomProperties(){
		entryFast = new JLabel("Entrada ràpida");
		entryFast.setToolTipText("S'haurà d'introduir '.' i números de 1 al 9");
		nums = new JTextField();
		actEntry = new JButton("Actualitzar");
		nums.setPreferredSize(new Dimension(100,20));
		nums.setMaximumSize(new Dimension(100,20));
		nums.setMinimumSize(new Dimension(100,20));
		nums.setAlignmentX(LEFT_ALIGNMENT);
		verticalButton.add(Box.createVerticalStrut(15));
		verticalButton.add(entryFast);
		verticalButton.add(nums);
		verticalButton.add(actEntry);
	}
	
	protected void buttonListener(MouseAdapter mm, JButton b){
		b.addMouseListener(mm);
	}
}
