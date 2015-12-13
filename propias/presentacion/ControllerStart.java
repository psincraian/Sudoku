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

		vi = new ViewStart();
		frame.getContentPane().add(vi, BorderLayout.CENTER);
		vi.listeners(new MouseManage(), vi.button[0]);
		vi.listeners(new MouseManage(), vi.button[1]);
		vi.listeners(new MouseManage(), vi.button[2]);
		vi.listeners(new MouseManage(), vi.button[3]);
		
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
					listener.getOption(Options.IniciarSessio);
			}
			if(button.getText() == "Iniciar Convidat"){
				listener.getOption(Options.IniciarConvidat);
			}
			if(button.getText() == "Registrar Usuari"){
				listener.getOption(Options.RegistrarUsuari);
			}
			if(button.getText() == "Sortir"){
				listener.getOption(Options.Sortir);
			}
		}
							
	}
}
