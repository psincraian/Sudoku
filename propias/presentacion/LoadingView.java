package propias.presentacion;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import propias.presentacion.SetView;

public class LoadingView extends JPanel {

	
	private LoadingView() {
		super();
		ImageIcon loading = new ImageIcon("resources/ajax-loader.gif");
		add(new JLabel("Creant sudoku... ", loading, JLabel.CENTER), BorderLayout.CENTER);
		setMinimumSize(new Dimension(200, 50));
		setPreferredSize(new Dimension(200, 50));
	}
	
	public static LoadingView newInstance() {
		if (instance != null) {
			try {
				instance.finalize();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
		
		instance = new LoadingView();
		return instance;
	}
	
	private static LoadingView instance;
}
