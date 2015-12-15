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
	
	/**
	 * 
	 * Interface que s'encarregara de la comunicacio
	 * de la comunicacio amb el ControllerPresentation
	 *
	 */
	protected interface userEntry{
		public boolean checkInfoUser(List<String> info);
		public void showMainMenu();
		public void start();
	}
	
	/**
	 * 
	 * S'encarrega de llançar tal de gestionar la vista seleccionada.
	 * 
	 * @param typeUser: Si typeUser(1) es generara la vista Login, en el cas contrari, es generara 
	 * la vista de creacio d'un nou usuari. 
	 * @param frame : El fram del joc. Es configura amb el panell de la vista.
	 * @param container instancia de l'interface que s'ha dimplementar en 
	 * ControllerPresentation per tal de poder comunicar-se amb la vista
	 * 
	 */
	public void launchView(boolean typeUser, JFrame frame, Object container){
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
	 * @param message El missatge en questio.
	 * 
	 */
	public void sendMessage(String message){
		eu.sendMessage(message);
	}
	
	/**
	 * 
	 * Funcio que s'encarrega de retorna l'instancia d'aquest controlador
	 * per tal existeixi un sola instancia(singleton)
	 * 
	 * @return instancia de ControllerUserEntry
	 * 
	 */
	public static ControllerUserEntry getInstance() {
		if(instance == null)
			instance = new ControllerUserEntry();
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
