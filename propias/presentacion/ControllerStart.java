package propias.presentacion;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;

import propias.dominio.clases.Options;
import propias.presentacion.ControllerUserEntry.MouseManage;
/**
 * 
 * @author Brian Martinez Alvarez
 *
 */
public class ControllerStart {
	
	ControllerPresentation cp;
	ViewStart vi;
	private GetOptionsListInterface listener;
	
	protected interface GetOptionsListInterface {
		public void getOption(Options option);
	}
	
	/**
	 * 
	 * @param cp Instance of controllerPresentation
	 */
	public ControllerStart(Object object, JFrame frame){
		listener = (GetOptionsListInterface) object;

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
					listener.getOption(Options.IniciarSessio);
			}
			if(button.getText() == "Iniciar Convidat"){
				vi.disableView();
				listener.getOption(Options.IniciarConvidat);
			}
			if(button.getText() == "Registrar Usuari"){
				vi.disableView();
				listener.getOption(Options.RegistrarUsuari);
			}
			if(button.getText() == "Sortir"){
				vi.disableView();
				listener.getOption(Options.Sortir);
			}
		}
							
	}
}
