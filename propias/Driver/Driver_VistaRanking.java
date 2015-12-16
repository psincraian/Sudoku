package propias.Driver;

import propias.presentacion.ControllerPresentation;
import propias.presentacion.ViewRanking;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
 
/**
 * 
 * @author daniel sanchez martinez
 *
 */
public class Driver_VistaRanking {
    public static void main (String[] args){
        List<String> name = new ArrayList<String>();
        List<Long> values = new ArrayList<Long>();
        name.add("Daniel");
        name.add("David");
        values.add((long)11111);
        values.add((long)2222);
      
        ViewRanking vr = ViewRanking.getInstance();
        JFrame frame = new JFrame("Sudoku");
        frame.getContentPane().setLayout(new BorderLayout());
		frame.setBackground(Color.WHITE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setMaximumSize(new Dimension(1000, 750));
		frame.setPreferredSize(new Dimension(1000, 750));	
		/*false --> sudoku    true -->global*/
		vr.launchView(name, values, new ControllerPresentation(), false);
		vr.addPosUser(14, (long)150, "Donuts");
		frame.add(vr, BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);
    }
 
}
