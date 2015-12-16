package propias.presentacion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

/**
 * 
 * Vista que s'encarrega de mostrar per pantalla les
 * els sudokus que hi han disponibles a la base de dades
 * 
 * @author Daniel Sanchez Martinez
 *
 */

public class ViewSelectSudoku extends SetView{
	private JPanel panel;
	protected JButton[] buttons;
	List<List<String>> id;
	private selectSudoku ss;
	private JScrollPane sp;
	private static ViewSelectSudoku instance;
	
	/**
	 * 
	 * Interface que s'encarregara de la comunicacio
	 * de la comunicacio amb el ControllerPresentation
	 *
	 */
	public interface selectSudoku{
		public void selectSudoku(String id);
		public void getBack();
	}
	
	/**
	 * 
	 * Constructor de la vista
	 * 
	 */
	public ViewSelectSudoku() {
		super();
	}
	
	/**
	 * 
	 * Funcio que s'encarrega de llençar la vista selectSudoku
	 * 
	 * @param id : llista que conte una llista amb id+creador
	 * del sudoku
	 * @param container : interface que s'encarrega de implementar
	 * els metodes per tal de comunicar-se amb el controlador
	 */
	public void launchView(List<List<String>> id,Object container){
		this.ss = (selectSudoku) container;
		this.id = id;
		removeAll();
		setTitle("Seleccio d'un sudoku de la base de dades");
		createView();
	}
	/**
	 * 
	 * Funcio que s'encarrega de afegir els elements a la vista.
	 * 
	 */
	private void createView(){
		iniNames();
		
		Box horizontal = Box.createHorizontalBox();
		Box vertical = Box.createVerticalBox();
		vertical.add(Box.createVerticalGlue());
		panel.setBackground(Color.WHITE);
		panel.add(Box.createRigidArea(new Dimension(15,15)));
		horizontal.add(new JLabel("ID sudoku"));
		horizontal.add(Box.createHorizontalStrut(125));
		horizontal.add(new JLabel("Creador"));
		setSize(horizontal, new Dimension(250,100));
		vertical.add(horizontal);
		for(int i = 0; i < buttons.length; ++i){
			horizontal = Box.createHorizontalBox();
			horizontal.add(Box.createHorizontalStrut(15));
			horizontal.add(buttons[i]);
			horizontal.add(Box.createHorizontalGlue());
			horizontal.add(new JLabel(id.get(i).get(1)));
			horizontal.add(Box.createRigidArea(new Dimension(15,15)));
			panel.add(horizontal);
		}
		setSize(sp, new Dimension(300,100));
		sp.getViewport().add(panel);
		vertical.add(sp);
		vertical.add(Box.createVerticalGlue());
		add(vertical, BorderLayout.CENTER);
	}
	
	/**
	 * 
	 * Funcio que s'encarrega de inicialitzar els components 
	 * que s'utilitzan a la vista
	 * 
	 */
	private void iniNames() {
		panel = new JPanel();
		sp = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		buttons = new JButton[id.size()];
		for(int i = 0; i < id.size(); ++i)
			buttons[i] = new JButton(id.get(i).get(0));
		buttonListener();
	}
	
	/**
	 * 
	 * Afegeix els listeners als botons que 
	 * s'utilitza a la vista
	 * 
	 * @param mm : Clase MouseAdapter que s'utilizara
	 * per tal de gestionar el listener
	 * 
	 */
	protected void buttonListener(){
		for(int i  = 0; i < buttons.length; ++i)
			buttons[i].addMouseListener(new MouseManage());
	}
	
	/**
	 * 
	 * Funcio que s'encarrega de configura les dimensions donat 
	 * un component qualsevol
	 * 
	 */
	public void setSize(Object container,Dimension d){
		((JComponent) container).setPreferredSize(d);
		((JComponent) container).setMinimumSize(d);
		((JComponent) container).setMaximumSize(d);
	}
	
	/**
	 * 
	 * Funcio que s'encarrega de retorna l'instancia d'aquesta vista
	 * per tal existeixi un sola instancia(singleton)
	 * 
	 * @return instancia de ViewSelectSudoku
	 * 
	 */
	public static ViewSelectSudoku getInstance() {
		if (instance == null)
			instance = new ViewSelectSudoku();
		return instance;
	}
	
	/**
	 * 
	 * Clase que gestiona els clicks per part de l'usuari. Si l'usuari
	 * clica sobre "Tornar", tornarem enrere. En canvi, si l'usuari
	 * clica sobre un boto, que representa un id(sudoku), començara la
	 * vista match
	 * 
	 */
	public class MouseManage extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			JButton b = (JButton)e.getSource();
			if(b.getText().equals("Tornar"))
				ss.getBack();
			else
				ss.selectSudoku(b.getText());
		}
	}
}