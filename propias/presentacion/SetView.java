package propias.presentacion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * 
 * Configura el panell que s'heretara per tal de que
 * totes les vistes tinguin la mateixa base
 * 
 * @author Daniel Sanchez Martinez
 *
 */

public class SetView extends JPanel{
	
	Component verticalStrut;
	JPanel panelN;
	
	/**
	 * 
	 * Constructor
	 * 
	 */
	public SetView(){
		super(new BorderLayout());
		setBackground(Color.WHITE);
	}
	
	/**
	 * 
	 * Funcio que s'encarrega de ficar un titol a la vista.
	 * 
	 * @param t : el titol en si.
	 * 
	 */
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
		add(panelN, BorderLayout.NORTH);
	}
}
