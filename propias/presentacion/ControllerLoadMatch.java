package propias.presentacion;

import java.util.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
/**
 * 
 * @author Brian Martinez Alvarez
 *
 */
public class ControllerLoadMatch {
	
	ControllerPresentation cp;
	ViewLoadMatch vl;
	List<String> id;
	/**
	 * 
	 * @param cp Instance of controllerPresentation
	 */
	public ControllerLoadMatch(ControllerPresentation cp, List<String> id,JFrame frame){
		this.cp = cp;
		this.id = id;
		
		if (id.size() == 0) {
			JOptionPane.showMessageDialog(null, "No hi ha partides guardades");
			cp.Menu(false);
		}
		else{
			vl = new ViewLoadMatch(id);
			frame.getContentPane().add(vl);
			for(int i=0; i<id.size(); ++i){
				vl.listeners(new MouseManage(), vl.buttonList.get(i));
			}
			vl.listener(new MouseManage());
		}
	}
	/**
	 * 
	 * Comproba quin boto es premut
	 *
	 */
	public class MouseManage extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			JButton button = (JButton)e.getSource();
			if (button.getText() == "Tornar") {
				vl.disableView();
				cp.getBack();
			}
			else {
				for(int i=0; i< id.size(); ++i){
					if(button.getText() == id.get(i)){
						vl.disableView();
						cp.loadMatch(id.get(i));
					}
				}
			}
		}
							
	}
}