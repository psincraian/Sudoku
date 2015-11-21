package propias.presentacion;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


/**
 * Configure createUser view.The types can be: login and createUser.
 * 
 * @author Daniel Sanchez Martinez
 */

public abstract class UserEntry extends setView{
	protected JFrame frame;
	private String[] names = {"Nom: ","Contrasenya: "};
	private JLabel[] label;
	private JTextField name;
	protected JPasswordField pf;
	protected JButton accept;
	protected JButton cancel;
	private JPanel[] panel;
	protected JPanel labelPanel;
	protected JPanel textPanel;
	protected JLabel title;

	/**
	 * Create the application.
	 */
	public UserEntry() {
		super();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		getContentPane().setSize(750,750);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		pack();
		createView();
	}
	
	/**
	 * Create the view.
	 */
	private void createView(){
		iniNames();
		
		labelPanel.add(label[0]);
		labelPanel.add(Box.createVerticalStrut(10));
		labelPanel.add(label[1]);
		panel[0].add(labelPanel);
		textPanel.add(name);
		textPanel.add(pf);
		panel[0].add(textPanel);
		add(panel[0], BorderLayout.CENTER);
		
		getContentPane().add(Box.createHorizontalStrut(100), BorderLayout.EAST);
		
		panel[1].add(title);
		panel[1].add(Box.createVerticalStrut(80));
		getContentPane().add(panel[1], BorderLayout.NORTH);

		JPanel panelButtons = new JPanel();
		panelButtons.add(accept);
		panelButtons.add(cancel);
		panelButtons.add(Box.createVerticalStrut(80), BorderLayout.SOUTH);
		getContentPane().add(panelButtons, BorderLayout.SOUTH);
	}
	/**
	 * Initialize component's view
	 */
	private void iniNames() {
		label = new JLabel[2];
		pf = new JPasswordField();
		panel = new JPanel[2];
		title = new JLabel("");
		labelPanel = new JPanel();
		textPanel = new JPanel();
		labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
		textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));

		for(int i = 0; i < names.length; ++i){
			label[i] = new JLabel(names[i]);
			panel[i] = new JPanel();
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
	 * Set the view's title
	 * @param text
	 */
	public void setTitle(String text){
		title.setText(text);
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
	 * Close view
	 */
	protected void closeView(){
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		dispose();
	}
	
	/**
	 * Set not visible the view
	 */
	protected void disableView(){
		setVisible(false);
	}
	/**
	 * @return Passwords to check
	 */
	public abstract List<String> getInfoUser();
}
