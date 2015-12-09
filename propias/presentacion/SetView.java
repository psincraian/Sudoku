package propias.presentacion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

/**
 * Sets the father View. This view is just for herency. 
 * 
 * @author daniel sanchez martinez
 *
 */

public class SetView extends JFrame{
	
	Component verticalStrut;
	JPanel panelN;
	
	/**
	 * constructor
	 */
	public SetView(){
		super("Sudoku");
		getContentPane().setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void setTitle(String t){
		panelN = new JPanel();
		panelN.setBackground(Color.WHITE);
		panelN.setLayout(new FlowLayout(FlowLayout.CENTER));
		JLabel title = new JLabel(t);
		title.setHorizontalAlignment(SwingConstants.CENTER);
		verticalStrut = Box.createVerticalStrut(60);
		Component horizontalStrut = Box.createHorizontalStrut(20);
		panelN.add(verticalStrut);
		panelN.add(title);
		panelN.add(horizontalStrut);
		getContentPane().add(panelN, BorderLayout.NORTH);
		setMaximumSize(1000,750);
		setPreferredSize(976,728);
	}
	
	public void setMaximumSize(int x, int y){
		setMaximumSize(new Dimension(x,y));
	}
	
	public void setMinimumSize(int x, int y){
		setMinimumSize(new Dimension(x,y));
	}
	
	public void setPreferredSize(int x, int y){
		setPreferredSize(new Dimension(x,y));
	}
}
