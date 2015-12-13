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

/**
 * 
 * Mostra la vista de ranking. Pot representa la vista
 * de ranking global o ranking sudoku. Ranking global esta 
 * representat per usuari+puntuacio i ranking sudoku esta
 * representat per usuari+temps en resoldre el sudoku 
 * en questio
 * 
 * @author Daniel Sanchez Martinez
 * 
 */
public class ViewRanking extends SetView{
	
	JPanel nameP;
	JPanel valueP;
	JPanel rank;
	JLabel[] nameL;
	JLabel[] valueL;
	List<String> name;
	List<Long> value;
	JTabbedPane difficulty;
	JButton button;
	private ranking r;
	
	/**
	 * 
	 * Interface que s'encarregara de la comunicacio
	 * de la comunicacio amb el ControllerPresentation
	 *
	 */
	interface ranking{
		public void getBack();
	}
	
	/**
	 * 
	 * Constructor
	 * 
	 * @param name : llista que conte els noms a ser mostrats
	 * @param value : llsta que conte els punts/temps asociats a un usuari
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
	 * Funcio que s'encarrega de configura la vista.
	 * 
	 */
	public void initialize(){
		
		setRanking();
		
		/*Center*/
		Box verticalBox = Box.createVerticalBox();
		verticalBox.add(Box.createGlue());
		rank = new JPanel();
		setSize(200,40);
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
		listener();
		JPanel east = new JPanel();
		east.setBackground(Color.white);
		east.add(button);
		add(east, BorderLayout.EAST);		
	}
	
	/**
	 * 
	 * Funcio que s'encarrega de inicialitzar els components 
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
	 * Funcio que s'encarrega de mostrar la posicio del usuari
	 * en el cas de que no estigui en les posicions mostrades
	 * en la vista
	 * 
	 * @param pos : posicio del usuari en el ranking
	 * @param value : valor que representa la posicion en el
	 * ranking del usuari
	 * @param name : nom de l'usuari
	 * 
	 */
	public void addPosUser(int pos, Long value, String name){
		setSize(200,45);
		rank.add(Box.createVerticalStrut(20));
		JPanel aux = new JPanel();
		aux.setBackground(Color.WHITE);
		aux.add(new JLabel(Integer.toString(pos)+". "+name));
		aux.add(new JLabel(String.valueOf(value)));
		rank.add(aux);
	}
	
	/**
	 * 
	 * Funcio que s'encarrega de configura les dimensions del
	 * panell rank, que engloba la informacio del ranking
	 * 
	 */
	public void setSize(int x, int y){
		rank.setPreferredSize(new Dimension(x,y*nameL.length));
		rank.setMinimumSize(new Dimension(x,y*nameL.length));
		rank.setMaximumSize(new Dimension(x,y*nameL.length));
	}
	
	/**
	 * 
	 * Listener que s'encarrega de gestionar el boto 'Tornar'
	 * 
	 */
	public void listener(){
		button.addMouseListener(new MouseManage());
	}
	
	/**
	 * 
	 * Clase que gestiona els clicks per part de l'usuari. Hi han un boto amb listener, 
	 * 'Tornar'.
	 * 
	 */
	public class MouseManage extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			JButton b = (JButton)e.getSource();
			if(b.getText().equals("Tornar"))
				r.getBack();
		}
	}
}

