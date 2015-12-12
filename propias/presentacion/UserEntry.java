package propias.presentacion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


/**
 * 
 * Configura una nova vista que permet fer login o crear
 * un nou usuari.
 * 
 * @author Daniel Sanchez Martinez
 */

public abstract class UserEntry extends SetView {
	private String[] names = {"Nom: ","Contrasenya: "};
	private JLabel[] label;
	private JTextField name;
	protected JPasswordField pf;
	protected JButton accept;
	protected JButton cancel;
	private JPanel panel;
	protected JPanel labelPanel;
	protected JPanel textPanel;
	protected JPanel panelButtons;

	/**
	 * 
	 * Creadora de la configuraci� de la vista.
	 * 
	 */
	public UserEntry() {
		super();
		setFocusable(true);
		requestFocusInWindow();
		createView();
	}
	
	/**
	 * 
	 * Configura la vista amb la mateixa base per les dues 
	 * vistes posibles.
	 * 
	 */
	private void createView(){
		iniNames();
		
		Box vertical = Box.createVerticalBox();
		vertical.add(Box.createVerticalGlue());
		panel.setBackground(Color.WHITE);
		labelPanel.setBackground(Color.white);
		textPanel.setBackground(Color.white);
		labelPanel.add(label[0]);
		labelPanel.add(Box.createVerticalStrut(10));
		labelPanel.add(label[1]);
		panel.add(labelPanel);
		textPanel.add(name);
		textPanel.add(Box.createVerticalStrut(10));
		textPanel.add(pf);
		panel.add(textPanel);
		panel.setBorder(BorderFactory.createRaisedBevelBorder());
		vertical.add(panel);
		vertical.add(Box.createVerticalGlue());
		add(vertical, BorderLayout.CENTER);
		
		/*getContentPane().*/add(Box.createHorizontalStrut(100), BorderLayout.EAST);

		panelButtons = new JPanel();
		panelButtons.add(accept);
		panelButtons.add(cancel);
		panelButtons.add(Box.createVerticalStrut(80), BorderLayout.SOUTH);
		panelButtons.setBackground(Color.white);
		/*getContentPane().*/add(panelButtons, BorderLayout.SOUTH);
	}
	
	/**
	 * 
	 * Configura els components comuns entre les dues vistes
	 * 
	 */
	private void iniNames() {
		label = new JLabel[2];
		pf = new JPasswordField();
		panel = new JPanel();
		labelPanel = new JPanel();
		textPanel = new JPanel();
		labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
		textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));

		for(int i = 0; i < names.length; ++i){
			label[i] = new JLabel(names[i]);
		}
		name = new JTextField(10);
		pf = new JPasswordField(10);
		
		accept = new JButton("Aceptar");
		cancel = new JButton("Cancel·lar");
	}
	
	/**
	 * Funci� que s'encarrega de retorna el nombre
	 * de l'usuari
	 * 
	 * @return nom de l'usuari
	 * 
	 */
	public String getName(){
		return name.getText();
	}
	
	/**
	 * 
	 * Es configura un missatge per tal de ser mostrat a l'usuari
	 * 
	 * @param message : El missatge en si
	 * 
	 */
	public void sendMessage(String message){
		JOptionPane.showMessageDialog(null, message);
	}
	
	/**
	 * 
	 * Afegeix els listeners als botons que s'especifiqui.
	 * 
	 * @param mm : Clase MouseAdapter que s'encarregar� de
	 * gestionar els listeners dels botons
	 * @param b : El bot� que es vol observar
	 * 
	 */
	protected void buttonListener(MouseAdapter mm, JButton b){
		b.addMouseListener(mm);
	}
	
	/**
	 * 
	 * Aquesta funci� s'encarreg� de configura l'amplada i al�ada
	 * m�xima dels JTextField, JPasswordField i JLabel
	 * per tal de dibuixar un quadrat elevat al seu voltant.
	 * 
	 * @param width : amplada 
	 * @param nElem : nombre de elements, que pot ser 2 en el cas de login
	 * i 3 en el cas de creaci� d'un nou usuari.
	 */
	protected void setPanelSize(int width, int nElem){
		panel.setPreferredSize(new Dimension(width,nElem*40));
		panel.setMinimumSize(new Dimension(width,nElem*40));
		panel.setMaximumSize(new Dimension(width,nElem*40));
	}
	
	/**
	 * 
	 * Funci� que s'encarrega de retorna el password introduit.
	 * Retorna una llista ja que per la vista de creaci� d'un nou
	 * usuari hi han 2 contrasenyes.
	 * 
	 * @return Contrasenya a verificar.
	 * 
	 */
	public abstract List<String> getInfoUser();
}
