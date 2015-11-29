package tests;

import javax.swing.*;

import propias.dominio.clases.CaracteristiquesPartida;
import propias.presentacion.*;

class SelectCharacteristicsTest {

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				SelectCharacteristicsGUI gui = new SelectCharacteristicsGUI();
				gui.createGUI();
			}
		});
	}
}

class SelectCharacteristicsGUI implements SelectCharacteristics.GetParametersListener {
	
	public void createGUI() {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel newContentPane = new SelectCharacteristics(this);
		newContentPane.setOpaque(true);
        frame.getContentPane().setLayout(new java.awt.GridBagLayout());
		frame.getContentPane().add(newContentPane, new java.awt.GridBagConstraints());
        frame.pack();
		frame.setVisible(true);
	}
	
	@Override
	public void getParameters(CaracteristiquesPartida caracteristiques) {
		System.out.println(caracteristiques.getDificultat() + " " + caracteristiques.getGivenNumbers() + " "
				+ caracteristiques.getMida() + " " + caracteristiques.getTipusPartida() );		
	}
}