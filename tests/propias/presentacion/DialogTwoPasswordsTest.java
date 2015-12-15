package tests.propias.presentacion;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import propias.dominio.clases.CaracteristiquesPartida;
import propias.presentacion.DialogTwoPasswords;
import propias.presentacion.SelectCharacteristics;

public class DialogTwoPasswordsTest implements DialogTwoPasswords.twoPasswordsInterface {

	DialogTwoPasswords dialog;
	private void createGUI() {
		JFrame frame = new JFrame();
	    dialog = DialogTwoPasswords.getInstance(this);
	    dialog.setVisible(true);
	}
	

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				DialogTwoPasswordsTest test = new DialogTwoPasswordsTest();
				test.createGUI();
			}
		});
	}


	@Override
	public void save(String password1, String password2) {
		System.out.println(password1 + " " + password2);
		
	}


	@Override
	public void cancel() {
		dialog.dispose();
	}
}
