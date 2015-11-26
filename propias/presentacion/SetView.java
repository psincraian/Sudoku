package propias.presentacion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Sets the father View. This view is just for herency. 
 * 
 * @author daniel sanchez martinez
 *
 */

public class SetView extends JFrame{
	
	protected JPanel panelN;
	/**
	 * constructor
	 */
	public SetView(){
		super("Sudoku");
		setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void setTitle(String t){
		panelN = new JPanel();
		panelN.setBackground(Color.white);
		panelN.setLayout(new FlowLayout(FlowLayout.CENTER));
		JLabel title = new JLabel(t);
		title.setHorizontalAlignment(SwingConstants.CENTER);
		Component verticalStrut = Box.createVerticalStrut(60);
		Component horizontalStrut = Box.createHorizontalStrut(20);
		panelN.add(verticalStrut);
		panelN.add(title);
		panelN.add(horizontalStrut);
		getContentPane().add(panelN, BorderLayout.NORTH);
	}
}
