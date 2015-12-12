package propias.presentacion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import propias.presentacion.ViewSelectSudoku.selectSudoku;

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
	JButton button;
	private ranking r;
	
	interface ranking{
		public void getBack();
	}
	/**
	 * 
	 * Constructor
	 * 
	 * @param name : llista que cont� els noms a ser mostrats
	 * @param value : llsta que cont� els punts/temps asociats a un usuari
	 * 
	 */
	public ViewRanking(List<String> name, List<Long> value, Object container){
		super();
		this.name=name;
		this.value=value;
		this.r = (ranking) container;
		setTitle("Ranking");
		initialize();
	}
	
	/**
	 * 
	 * Funci� que s'encarrega de configura la vista.
	 * 
	 */
	public void initialize(){
		
		setRanking();
		
		/*Center*/
		Box verticalBox = Box.createVerticalBox();
		verticalBox.add(Box.createGlue());
		JPanel rank = new JPanel();
		rank.setPreferredSize(new Dimension(200,40*nameL.length));
		rank.setMinimumSize(new Dimension(200,40*nameL.length));
		rank.setMaximumSize(new Dimension(200,40*nameL.length));
		rank.setBorder(BorderFactory.createRaisedBevelBorder());
		rank.add(nameP);
		rank.add(Box.createHorizontalStrut(20));
		rank.add(valueP);
		rank.setBackground(Color.white);
		verticalBox.add(rank);
		verticalBox.add(Box.createGlue());
		add(verticalBox, BorderLayout.CENTER);
						
		/*East*/
		button = new JButton("Tornar");
		JPanel east = new JPanel();
		east.setBackground(Color.white);
		east.add(button);
		add(east, BorderLayout.EAST);		
	}
	
	/**
	 * 
	 * Funci� que s'encarrega de inicialitzar els components 
	 * de la vista.
	 * 
	 */
	public void setRanking(){
		nameL = new JLabel[name.size()];
		valueL = new JLabel[name.size()];
		nameP = new JPanel();
		valueP = new JPanel();
		nameP.setLayout(new BoxLayout(nameP,BoxLayout.Y_AXIS));
		valueP.setLayout(new BoxLayout(valueP,BoxLayout.Y_AXIS));
		nameP.setBackground(Color.white);
		valueP.setBackground(Color.white);
		
		for(int i = 0; i < name.size(); ++i){
			nameL[i] = new JLabel(name.get(i));
			valueL[i] = new JLabel(String.valueOf(value.get(i)));
			nameP.add(nameL[i]);
			valueP.add(valueL[i]);
		}
	}
	
	/**
	 * 
	 * Listener que s'encarrega de gestionar el bot� 'Tornar'
	 * 
	 * @param ma
	 * 
	 */
	public void listener(MouseAdapter ma){
		button.addMouseListener(ma);
	}
	
	
	public class MouseManage extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			JButton b = (JButton)e.getSource();
			if(b.getText().equals("Tornar"))
				r.getBack();
		}
	}
}

