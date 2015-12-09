package propias.presentacion;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

import propias.presentacion.ControllerUserEntry.MouseManage;
/**
 * 
 * @author Brian Martinez Alvarez
 *
 */
public class ControllerStart {
	
	ControllerPresentation cp;
	ViewStart vi;
	/**
	 * 
	 * @param cp Instance of controllerPresentation
	 */
	public ControllerStart(ControllerPresentation cp, JFrame frame){
		this.cp = cp; 
		vi = new ViewStart();
		frame.getContentPane().add(vi);
		vi.listeners(new MouseManage(), vi.button1);
		vi.listeners(new MouseManage(), vi.button2);
		vi.listeners(new MouseManage(), vi.button3);
		vi.listeners(new MouseManage(), vi.button4);
		
	}
	/**
	 * 
	 * Comproba quin boto es premut
	 *
	 */
	public class MouseManage extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			JButton button = (JButton)e.getSource();
			if(button.getText() == "Iniciar Sessio"){
					vi.disableView();
					cp.startUser();
			}
			if(button.getText() == "Iniciar Convidat"){
				vi.disableView();
				cp.startGuest();
			}
			if(button.getText() == "Registrar Usuari"){
				vi.disableView();
				cp.startNewUser();
			}
			if(button.getText() == "Sortir"){
				vi.disableView();
				cp.exitApplication();
			}
		}
							
	}
}
