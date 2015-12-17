package propias.presentacion;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;

import javax.swing.Action;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.text.DefaultEditorKit;

/**
 * 
 * Configura la vista de creacio d'un nou sudoku
 * 
 * @author Daniel Sanchez Martinez
 * 
 */
public class ViewCreateBoard extends GenerateBoard{
	
	/**
	 * 
	 * Constructor
	 * 
	 * @param board : Sudoku buit. Es requereix per tal de 
	 * configura el taulell.
	 * @param size : Mida del nou sudoku
	 * 
	 */
	public ViewCreateBoard(int[][] board, int size){
		super(board,size);
		setTitle("Creació d'un nou Sudoku");
		enableCustomProperties();
	}
	
	/**
	 * 
	 * Configura els elements necessaris per aquesta vista i que
	 * no es requereix a la vista de jugar una partida
	 * 
	 */
	public void  enableCustomProperties(){
		entryFast = new JLabel("Entrada ràpida");
		entryFast.setToolTipText("S'haurà d'introduir '.' i números de 1 al 9");
		nums = new JTextField();
		JPopupMenu menu = new JPopupMenu();
        Action cut = new DefaultEditorKit.CutAction();
        cut.putValue(Action.NAME, "Cut");
        cut.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
        menu.add( cut );
        Action copy = new DefaultEditorKit.CopyAction();
        copy.putValue(Action.NAME, "Copy");
        copy.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
        menu.add( copy );
        Action paste = new DefaultEditorKit.PasteAction();
        paste.putValue(Action.NAME, "Paste");
        paste.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
        menu.add( paste );
        nums.setComponentPopupMenu( menu );
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
	
	/**
	 * 
	 * Afegeix listeners als botons.
	 * 
	 * @param mm : Clase MouseAdaptar que s'encarrega de gestionar el listener
	 * @param b : Boto a ser observat
	 * 
	 */
	protected void buttonListener(MouseAdapter mm, JButton b){
		b.addMouseListener(mm);
	}
}
