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
	Box verticalBox;
	private static ViewRanking instance;
	boolean global;
	
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
	 * @param name : 
	 * @param value : llsta que conte els punts/temps asociats a un usuari
	 * 
	 */
	public ViewRanking(){
		super();
	}
	/**
	 * 
	 * Funcio que s'encarrega de llençar la vista ranking
	 * 
	 * @param name : llista que conte els noms a ser mostrats
	 * @param value : llista que conte els punts/temps asociats a un usuari
	 * @param container : interface que s'encarrega de implementar
	 * els metodes per tal de comunicar-se amb el controlador
	 * 
	 */
	public void launchView(List<String> name, List<Long> value, Object container, boolean global){
		this.name = name;
		this.value = value;
		this.r = (ranking) container;
		this.global = global;
		removeAll();
		if(global)
			setTitle("Ranking global");
		else{
			System.out.print("hola");
			setTitle("Ranking sudoku");
		}
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
		verticalBox = Box.createVerticalBox();
		verticalBox.add(Box.createVerticalStrut(100));
		setSize(175,25);
		rank.setBorder(BorderFactory.createRaisedBevelBorder());
		rank.add(nameP);
		rank.add(Box.createHorizontalStrut(20));
		rank.add(valueP);
		rank.setBackground(Color.white);
		verticalBox.add(rank);
		add(verticalBox, BorderLayout.CENTER);
						
		/*SOUTH*/
		listener();
		JPanel panel = new JPanel();
		Box south = Box.createVerticalBox();
		south.setBackground(Color.white);
		south.add(button);
		south.add(Box.createVerticalStrut(25));
		panel.add(south);
		panel.setBackground(Color.WHITE);
		add(panel, BorderLayout.SOUTH);		
	}
	
	public String setToTime(Long value){

		String data;
		Long seconds = value % 60;
		Long minute = value / 60;
		minute %= 60;
		Long hour =minute / 60;
		hour %=24;
		String min = String.valueOf(minute);
		if(min.length() == 1)
			min = "0"+min;
		String h = String.valueOf(hour);
		if(h.length() == 1)
			h = "0"+h;
		String s = String.valueOf(seconds);
		if(s.length() == 1)
			s = "0"+s;
		data = h + ":" + min +":"+ s;
		return data;
	}
	
	/**
	 * 
	 * Funcio que s'encarrega de inicialitzar els components 
	 * de la vista.
	 * 
	 */
	public void setRanking(){
		rank = new JPanel();
		nameL = new JLabel[name.size()];
		valueL = new JLabel[name.size()];
		nameP = new JPanel();
		valueP = new JPanel();
		nameP.setLayout(new BoxLayout(nameP,BoxLayout.Y_AXIS));
		valueP.setLayout(new BoxLayout(valueP,BoxLayout.Y_AXIS));
		nameP.setBackground(Color.white);
		valueP.setBackground(Color.white);
		button = new JButton("Tornar");
		
		for(int i = 0; i < name.size(); ++i){
			nameL[i] = new JLabel(Integer.toString(i+1)+". "+name.get(i));
			valueL[i] = new JLabel(setToTime(value.get(i)));
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
		JPanel aux = new JPanel();
		aux.setBackground(Color.WHITE);
		aux.add(new JLabel(Integer.toString(pos)+". "+name));
		aux.add(new JLabel(setToTime(value)));
		verticalBox.add(aux);
		verticalBox.add(Box.createVerticalGlue());

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
	 * Funcio que s'encarrega de retorna l'instancia d'aquesta vista
	 * per tal existeixi un sola instancia(singleton)
	 * 
	 * @return instancia de ViewRanking
	 * 
	 */
	public static ViewRanking getInstance() {
		if (instance == null)
			instance = new ViewRanking();
		return instance;
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

