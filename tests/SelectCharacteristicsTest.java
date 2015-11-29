package tests;

import javax.swing.*;

import propias.dominio.clases.CaracteristiquesPartida;
import propias.presentacion.*;

class SelectCharacteristicsTest {

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				GUI gui = new GUI();
				gui.createGUI();
			}
		});
	}
}

class GUI implements SelectCharacteristics.GetParametersListener {
	
	public void createGUI() {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JComponent newContentPane = new SelectCharacteristics(this);
		newContentPane.setOpaque(true);
		frame.setContentPane(newContentPane);

		frame.setVisible(true);
	}
	
	@Override
	public void getParameters(CaracteristiquesPartida caracteristiques) {
		System.out.println(caracteristiques.getDificultat() + " " + caracteristiques.getGivenNumbers() + " "
				+ caracteristiques.getMida() + " " + caracteristiques.getTipusPartida() );		
	}
}