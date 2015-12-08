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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class ViewSelectSudoku extends SetView{
	private JPanel panel;
	protected JButton[] buttons;
	List<String> id;

	/**
	 * Create the application.
	 */
	public ViewSelectSudoku(List<String> id) {
		super();
		this.id = id;
		createView();
		setVisible(true);
	}
	
	/**
	 * Create the view.
	 */
	private void createView(){
		iniNames();
		
		Box vertical = Box.createVerticalBox();
		vertical.add(Box.createVerticalGlue());
		panel.setBackground(Color.WHITE);
		//panel.add();
		Box horizontal = Box.createHorizontalBox();
		for(int i = 0; i < buttons.length; ++i){
			if(i % 3 != 0)
				horizontal.add(buttons[i]);
			else{
				panel.add(horizontal);
				horizontal = Box.createHorizontalBox();
			}
		}
		panel.setBorder(BorderFactory.createRaisedBevelBorder());
		vertical.add(panel);
		vertical.add(Box.createVerticalGlue());
		add(vertical, BorderLayout.CENTER);
		
		add(Box.createHorizontalStrut(100), BorderLayout.EAST);

	}
	/**
	 * Initialize component's view
	 */
	private void iniNames() {
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		buttons = new JButton[id.size()];
		for(int i = 0; i < id.size(); ++i){
			buttons[i] = new JButton(id.get(i));
		}
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
}
