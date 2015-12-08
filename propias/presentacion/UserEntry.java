package propias.presentacion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;


/**
 * Configure createUser view.The types can be: login and createUser.
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
	 * Create the application.
	 */
	public UserEntry() {
		super();
		setFocusable(true);
		requestFocusInWindow();
		setMaximumSize(1000,750);
		setPreferredSize(976,728);
		setMinimumSize(380,285);
		initialize();
		pack();
		setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		createView();
	}
	
	/**
	 * Create the view.
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
		
		getContentPane().add(Box.createHorizontalStrut(100), BorderLayout.EAST);

		panelButtons = new JPanel();
		panelButtons.add(accept);
		panelButtons.add(cancel);
		panelButtons.add(Box.createVerticalStrut(80), BorderLayout.SOUTH);
		panelButtons.setBackground(Color.white);
		getContentPane().add(panelButtons, BorderLayout.SOUTH);
	}
	/**
	 * Initialize component's view
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
	 * @return user's name
	 */
	public String getName(){
		return name.getText();
	}
	
	/**
	 * Set the message to show to user
	 * @param message
	 */
	public void sendMessage(String message){
		JOptionPane.showMessageDialog(null, message);
	}
	
	/**
	 * Add listeners to the button
	 * @param mm
	 * @param b
	 */
	protected void buttonListener(MouseAdapter mm, JButton b){
		b.addMouseListener(mm);
	}
	
	/**
	 * Set not visible the view
	 */
	protected void disableView(){
		setVisible(false);
	}
	
	protected void setPanelSize(int width, int nElem){
		panel.setPreferredSize(new Dimension(width,nElem*40));
		panel.setMinimumSize(new Dimension(width,nElem*40));
		panel.setMaximumSize(new Dimension(width,nElem*40));
	}
	/**
	 * @return Passwords to check
	 */
	public abstract List<String> getInfoUser();
}
