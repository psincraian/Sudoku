package propias.presentacion;

import java.awt.BorderLayout;
import java.awt.Color;
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
public class ViewRanking extends SetView{
	
	JPanel nameP;
	JPanel valueP;
	JLabel[] nameL;
	JLabel[] valueL;
	List<String> name;
	List<Long> value;
	JTabbedPane difficulty;
	
	public ViewRanking(List<String> name, List<Long> value){
		super();
		this.name=name;
		this.value=value;
		getContentPane().setSize(500, 500);
		setTitle("Ranking");
		setMinimumSize(380,285);
		setPreferredSize(976,728);
		setMaximumSize(1000,750);
		initialize();
		pack();
		setVisible(true);
	}
	
	public void initialize(){
		
		setRanking();
		
		/*Center*/
		Box verticalBox = Box.createVerticalBox();
		verticalBox.add(Box.createGlue());
		JPanel rank = new JPanel();
		rank.add(nameP);
		rank.add(Box.createHorizontalStrut(20));
		rank.add(valueP);
		rank.setBackground(Color.white);
		verticalBox.add(rank);
		add(verticalBox, BorderLayout.CENTER);
		//difficulty.addTab("Nivell facil", easy);

		/*JPanel medium = new JPanel();
		difficulty.addTab("Nivell mitja", medium);
		
		JPanel d = new JPanel();
		difficulty.addTab("Nivell dificil", d);
		add(difficulty, BorderLayout.CENTER);*/
		
		/*North*/
		//add(Box.createVerticalStrut(100),BorderLayout.NORTH);
		
		/*South*/
		add(Box.createGlue(),BorderLayout.SOUTH);
		
		/*East*/
		add(Box.createGlue(),BorderLayout.EAST);
		
		/*West*/
		add(Box.createGlue(),BorderLayout.WEST);
	}
	
	public void setRanking(){
		nameL = new JLabel[name.size()];
		valueL = new JLabel[name.size()];
		nameP = new JPanel();
		valueP = new JPanel();
		nameP.setLayout(new BoxLayout(nameP,BoxLayout.Y_AXIS));
		valueP.setLayout(new BoxLayout(valueP,BoxLayout.Y_AXIS));
		nameP.setBackground(Color.white);
		valueP.setBackground(Color.white);
		//difficulty = new JTabbedPane();
		
		for(int i = 0; i < name.size(); ++i){
			nameL[i] = new JLabel(name.get(i));
			valueL[i] = new JLabel(String.valueOf(value.get(i)));
			nameP.add(nameL[i]);
			valueP.add(valueL[i]);
		}
		
	}
}

