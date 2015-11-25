package propias.presentacion;

import java.awt.BorderLayout;
import java.util.*;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 * Show ranking view.
 * 
 * @author Daniel Sanchez Martinez
 */
public class ViewRanking {
	
	JPanel nameP;
	JPanel valueP;
	JFrame frame;
	JLabel[] nameL;
	JLabel[] valueL;
	List<String> name;
	List<Long> value;
	JTabbedPane difficulty;
	
	public ViewRanking(List<String> name, List<Long> value){
		this.name=name;
		this.value=value;
		frame = new JFrame("sudoku");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setSize(500, 500);
		initialize();
		frame.pack();
		frame.setVisible(true);
	}
	
	public void initialize(){
		
		setRanking();
		
		/*Center*/
		JPanel easy = new JPanel();
		easy.add(nameP);
		easy.add(Box.createHorizontalStrut(20));
		easy.add(valueP);
		difficulty.addTab("Nivell facil", easy);

		JPanel medium = new JPanel();
		medium.add(nameP);
		medium.add(Box.createHorizontalStrut(20));
		medium.add(valueP);
		difficulty.addTab("Nivell mitja", medium);
		
		JPanel d = new JPanel();
		d.add(nameP);
		d.add(Box.createHorizontalStrut(20));
		d.add(valueP);
		difficulty.addTab("Nivell dificil", d);
		frame.add(difficulty, BorderLayout.CENTER);
		
		/*North*/
		frame.add(Box.createVerticalStrut(100),BorderLayout.NORTH);
		
		/*South*/
		frame.add(Box.createVerticalStrut(100),BorderLayout.SOUTH);
		
		/*East*/
		frame.add(Box.createHorizontalStrut(100),BorderLayout.EAST);
		
		/*West*/
		frame.add(Box.createHorizontalStrut(100),BorderLayout.WEST);
	}
	
	public void setRanking(){
		nameL = new JLabel[name.size()];
		valueL = new JLabel[name.size()];
		nameP = new JPanel();
		valueP = new JPanel();
		nameP.setLayout(new BoxLayout(nameP,BoxLayout.Y_AXIS));
		valueP.setLayout(new BoxLayout(valueP,BoxLayout.Y_AXIS));
		difficulty = new JTabbedPane();
		
		for(int i = 0; i < name.size(); ++i){
			nameL[i] = new JLabel(name.get(i));
			valueL[i] = new JLabel(String.valueOf(value.get(i)));
			nameP.add(nameL[i]);
			valueP.add(valueL[i]);
		}
		
	}
}

