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

	public static List<String> setInfoSudoku(String id, String maker){
		List<String> list = new ArrayList<String>();
		list.add(id);
		list.add(maker);
		return list;
	}
	
	public static void main(String[] args){
		List<List<String>> id = new ArrayList<List<String>>();
		
		id.add(setInfoSudoku("e001", "Daniel"));
		id.add(setInfoSudoku("e002", "David"));
		id.add(setInfoSudoku("e003", "Daniel"));
		id.add(setInfoSudoku("e004", "Daniel"));
		id.add(setInfoSudoku("e005", "David"));
		id.add(setInfoSudoku("e006", "Maquina"));
		id.add(setInfoSudoku("e007", "Daniel"));
		id.add(setInfoSudoku("e008", "Maquina"));
		id.add(setInfoSudoku("e009", "Daniel"));
		id.add(setInfoSudoku("e010", "Daniel"));
		id.add(setInfoSudoku("e011", "Maquina"));
		id.add(setInfoSudoku("e012", "David"));
		id.add(setInfoSudoku("e013", "Daniel"));
		id.add(setInfoSudoku("e014", "Maquina"));

		
		
		JFrame frame = new JFrame("Sudoku");
        frame.getContentPane().setLayout(new BorderLayout());
		frame.setBackground(Color.WHITE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setMaximumSize(new Dimension(1000, 750));
		frame.setPreferredSize(new Dimension(1000, 750));	
		ViewSelectSudoku ss = ViewSelectSudoku.getInstance();
		ss.launchView(id, new ControllerPresentation());
		frame.add(ss, BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);
	}
}
