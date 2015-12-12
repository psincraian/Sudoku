package propias.presentacion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
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
	List<String> id;
	private selectSudoku ss;
	private JScrollPane sp;
	
	public interface selectSudoku{
		public void selectSudoku(String id);
		public void getBack();
	}
	/**
	 * 
	 * Constructor de la vista
	 * 
	 * @param id : llista que representa els id's dels 
	 * sudokus a ser mostrats
	 * 
	 */
	public ViewSelectSudoku(List<String> id,Object container) {
		super();
		this.ss = (selectSudoku) container;
		this.id = id;
		createView();
	}
	
	/**
	 * 
	 * Funci� que s'encarrega de configura la vista
	 * 
	 */
	private void createView(){
		iniNames();
		
		setTitle("Seleccio d'un sudoku de la base de dades");
		Box vertical = Box.createVerticalBox();
		vertical.add(Box.createVerticalGlue());
		panel.setBackground(Color.WHITE);
		Box horizontal = Box.createHorizontalBox();
		for(int i = 0; i < buttons.length; ++i){
			if(i % 3 == 0){
				panel.add(horizontal);
				panel.add(Box.createRigidArea(new Dimension(15,15)));
				horizontal = Box.createHorizontalBox();
			}
			horizontal.add(buttons[i]);
			horizontal.add(Box.createRigidArea(new Dimension(15,15)));
		}
		panel.add(horizontal);
		sp.setMaximumSize(new Dimension(300,200));
		sp.setPreferredSize(new Dimension(300,200));
		sp.setMinimumSize(new Dimension(300,200));
		sp.getViewport().add(panel);
		vertical.add(sp);
		vertical.add(Box.createVerticalGlue());
		add(vertical, BorderLayout.CENTER);
	}
	/**
	 * 
	 * Funci� que s'encarrega de inicialitzar els components 
	 * que s'utilitzan a la vista
	 * 
	 */
	private void iniNames() {
		panel = new JPanel();
		sp = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		buttons = new JButton[id.size()];
		for(int i = 0; i < id.size(); ++i){
			buttons[i] = new JButton(id.get(i));
		}
		buttonListener();
	}
	
	/**
	 * 
	 * Afegeix els listeners als botons que 
	 * s'utilitza a la vista
	 * 
	 * @param mm : Clase MouseAdapter que s'utilizar�
	 * per tal de gestionar el listener
	 * 
	 */
	protected void buttonListener(){
		for(int i  = 0; i < buttons.length; ++i)
			buttons[i].addMouseListener(new MouseManage());
	}
	
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