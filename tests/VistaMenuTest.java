package tests;

import javax.swing.JFrame;
import javax.swing.JPanel;

import propias.dominio.clases.OptionsMenu;
import propias.presentacion.VistaMenu;

public class VistaMenuTest {

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				VistaMenuGUI gui = new VistaMenuGUI();
				gui.createGUI();
			}
		});
	}
	
}

class VistaMenuGUI implements VistaMenu.MenuButtonClicked {
	
	public void createGUI() {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel newContentPane = new VistaMenu(this);
		newContentPane.setOpaque(true);
        frame.getContentPane().setLayout(new java.awt.GridBagLayout());
		frame.getContentPane().add(newContentPane, new java.awt.GridBagConstraints());
        frame.pack();
		frame.setVisible(true);
	}

	@Override
	public void getOption(OptionsMenu option) {
		System.out.println(option.toString());
	}
}
