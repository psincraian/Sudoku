package propias.presentacion;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.KeyStroke;

import propias.presentacion.ControllerViewBoard.viewBoard;


/**
 * Controla la clase UserEntry. Configura els listeners sobre els botons i obre la vista.
 * 
 * @author Daniel Sanchez Martinez
 * 
 */
public class ControllerUserEntry {
	
	public static final boolean LOGIN_VIEW = true;
	public static final boolean REGISTRATION_VIEW = false;
	
	private static ControllerUserEntry instance;

	UserEntry eu;
	private userEntry ue;
	
	protected interface userEntry{
		public boolean checkInfoUser(List<String> info);
		public void showMainMenu();
		public void start();
	}
	
	/**
	 * 
	 * Constructor de ControllerUserEntry tal de gestionar la vista seleccionada.
	 * 
	 * @param cp: Instancia de controlador de presentaci� per tal de poder comunicar-se
	 * i tracta les dades inserides per l'usuari.
	 * @param typeUser: Si typeUser(1) es generar� la vista Login, en el cas contrari, es generar� 
	 * la vista de creaci� d'un nou usuari. 
	 * @param frame : El fram del joc. Es configura amb el panell de la vista.
	 * 
	 */
	public ControllerUserEntry( boolean typeUser, JFrame frame, Object container){
		if(typeUser == LOGIN_VIEW)
			eu = new ViewLogin();
		else 
			eu = new ViewCreateUser();
		this.ue = (userEntry) container;
		instance = this;
		frame.getContentPane().add(eu);
		eu.buttonListener(new MouseManage(), eu.accept);
		eu.buttonListener(new MouseManage(), eu.cancel);
		addKeyBinding();
	}
	
	/**
	 * 
	 * Genera un missatge per tal de ser mostrat a l'usuari.
	 * 
	 * @param message El missatge en questi�.
	 * 
	 */
	public void sendMessage(String message){
		eu.sendMessage(message);
	}
	
	public static ControllerUserEntry getInstance() {
		return instance;
	}
	
	/**
	 * 
	 * Clase que gestiona els clicks per part de l'usuari. Hi han dos butons amb listeners, 
	 * 'Accept' i 'Cancel'.
	 * 
	 */
	public class MouseManage extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			JButton b = (JButton)e.getSource();
			if(b.getText() == "Aceptar"){
				if(ue.checkInfoUser(eu.getInfoUser()))
					ue.showMainMenu();
				else
					ue.start();		
			}
			else
				ue.start();
		}
	}
	
	/**
	 * 
	 * S'encarrega que quan l'usuari pitji la tecla 'INTRO', es comprovaran les dades
	 * ficades per l'usuari.
	 * 
	 */
	public void addKeyBinding(){
		eu.panelButtons.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), "ENTER pressed");
	    eu.panelButtons.getActionMap().put("ENTER pressed", new AbstractAction() {
	        public void actionPerformed(ActionEvent ae) {
	        	if(ue.checkInfoUser(eu.getInfoUser())){
					ue.showMainMenu();
				}
	        }
	    });
	}
}
