package propias.Driver;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import propias.presentacion.*;

public class Driver_ViewSelectSudoku {

	public static void main(String[] args){
		List<String> id = new ArrayList<String>();
		id.add("e001");
		id.add("e002");
		id.add("e003");
		id.add("e004");
		id.add("e005");
		id.add("e006");
		id.add("e007");
		id.add("e008");
		id.add("e009");
		id.add("e010");
		id.add("e007");
		id.add("e008");
		id.add("e009");
		id.add("e010");

		
		
		JFrame frame = new JFrame("Sudoku");
        frame.getContentPane().setLayout(new BorderLayout());
		frame.setBackground(Color.WHITE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setMaximumSize(new Dimension(1000, 750));
		frame.setPreferredSize(new Dimension(1000, 750));	
		JPanel panel =  new ViewSelectSudoku(id, new ControllerPresentation());
		frame.add(panel, BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);
	}
}
