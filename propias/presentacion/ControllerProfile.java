package propias.presentacion;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
/**
 * 
 * @author Brian Martinez Alvarez
 *
 */
public class ControllerProfile {
	ControllerPresentation cp;
	ViewProfile vp;
	/**
	 * 
	 * @param cp ControllerPresentation
	 * @param matches Partides jugades
	 * @param time Temps total empleat
	 * @param bestTime Millor temps del jugador
	 */
	public ControllerProfile(ControllerPresentation cp, long[]matches, long[]time, long[]bestTime){
		this.cp = cp;
		vp = new ViewProfile(matches,time,bestTime);
		vp.listeners(new MouseManage(), vp.buttonReturn);
	}
	/**
	 * 
	 * Comproba quin boto es premut
	 *
	 */
	public class MouseManage extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			JButton button = (JButton)e.getSource();
			if(button.getText() == "Tornar"){
				vp.disableView();
				cp.getBack();
			}
		}
							
	}
}
