package propias.presentacion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 
 * 
 * @author Brian Martinez Alvarez
 */
public class ViewProfile extends SetView implements DialogTwoPasswords.twoPasswordsInterface, DialogChangeUsername.changeUsername{
	JPanel title;
	JPanel data;
	JPanel buttons;
	JLabel em;
	JLabel mm;
	JLabel hm;
	JLabel et;
	JLabel mt;
	JLabel ht;
	JLabel bet;
	JLabel bmt;
	JLabel bht;
	JLabel space;
	JLabel[] dataMatches;
	JLabel[] dataTime;
	JLabel[] dataBestTime;
	long[] matches;
	long[] time;
	long[] bestTime;
	String name;
	String pass1;
	String pass2;
	public JButton buttonReturn;
	public JButton changeName;
	public JButton changePass;
	private ProfileReturnListener listener;
	
	protected interface ProfileReturnListener {
		public void getBack();
		public void changeUserName(String name);
		public void changeUserPass(String pass1, String pass2);
	}
	
	/**
	 * Constructora
	 * @param matches Partides jugades
	 * @param time Temps total jugat
	 * @param bestTime Millor temps del jugador
	 */
	public ViewProfile(long[] matches, long[] time, long[] bestTime, Object object){
		super();
		listener = (ProfileReturnListener) object;
		this.matches = matches;
		this.time = time;
		this.bestTime = bestTime;
		setTitle("Perfil d'usuari");
		start_GUI();
		setVisible(true);
	}
	/**
	 * Inicia la vista
	 */
	public void start_GUI(){
		initVariables();
		Box b = Box.createVerticalBox();
		b.add(Box.createGlue());
		JPanel profile = new JPanel();
		profile.add(title);
		profile.add(Box.createHorizontalStrut(20));
		profile.add(data);
		profile.setBackground(Color.white);
		b.add(profile);
		add(b, BorderLayout.CENTER);
		add(Box.createGlue(),BorderLayout.SOUTH);
		add(Box.createGlue(),BorderLayout.EAST);
		add(Box.createGlue(),BorderLayout.WEST);
		buttons.add(Box.createVerticalStrut(80), BorderLayout.SOUTH);
		buttons.setBackground(Color.white);
		add(buttons, BorderLayout.SOUTH);
		listener(new MouseManage());
	}
	/**
	 * Inicia les variables necessaries
	 */
	public void initVariables(){
		title = new JPanel();
		data = new JPanel();
		dataMatches = new JLabel[matches.length];
		dataTime = new JLabel[time.length];
		dataBestTime = new JLabel[bestTime.length];
		title.setLayout(new BoxLayout(title,BoxLayout.Y_AXIS));
		data.setLayout(new BoxLayout(data,BoxLayout.Y_AXIS));
		title.setBackground(Color.white);
		data.setBackground(Color.white);
		initData();
		em = new JLabel("Partides Facils: ");
		title.add(em);
		data.add(dataMatches[0]);
	    mm = new JLabel("Partides Mitjanes: ");
	    title.add(mm);
		data.add(dataMatches[1]);
	    hm = new JLabel("Partides Dificils: ");
	    title.add(hm);
		data.add(dataMatches[2]);
	    et = new JLabel("Temps Partides Facils: ");
	    title.add(et);
		data.add(dataTime[0]);
	    mt = new JLabel("Temps Partides Mitjanes: ");
	    title.add(mt);
		data.add(dataTime[1]);
	    ht = new JLabel("Temps Partides Dificils: ");
	    title.add(ht);
		data.add(dataTime[2]);
	    bet = new JLabel("Millor Temps Partides Facils: ");
	    title.add(bet);
		data.add(dataBestTime[0]);
	    bmt = new JLabel("Millor Temps Partides Mitjanes: ");
	    title.add(bmt);
		data.add(dataBestTime[1]);
	    bht = new JLabel("Millor Temps Partides Dificils: ");
	    title.add(bht);
		data.add(dataBestTime[2]);
		buttonReturn = new JButton("Tornar");
		changeName = new JButton("Cambiar nom Usuari");
		changePass = new JButton("Cambiar Contrasenya");
		buttonReturn.setToolTipText("Tornar al Menu Principal");
		buttons = new JPanel();
		buttons.add(buttonReturn);
		buttons.add(changeName);
		buttons.add(changePass);
	}
	/**
	 * Inicia les dades del jugador(partides, temps i millor temps)
	 */
	public void initData(){
		for(int i=0; i<3; ++i){
			dataMatches[i] = new JLabel(String.valueOf(matches[i]));
			dataTime[i] = new JLabel(String.valueOf(time[i]));
			dataBestTime[i] = new JLabel(String.valueOf(bestTime[i]));
		}
	}
	
	public class MouseManage extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			JButton b = (JButton)e.getSource();
			if(b.getText().equals("Tornar")) {
				listener.getBack();
			}
			else if (b.getText().equals("Cambiar nom Usuari")){
				new DialogChangeUsername(this);
				save(name);
			}
			else{
				new DialogTwoPasswords(this);
				save(pass1,pass2);
			}
		}
	}
	public void save(String username){
		listener.changeUserName(username);
	}
	public void save(String pass1, String pass2){
		listener.changeUserPass(pass1, pass2);
	}
	/**
	 * Controla el boto
	 * @param ma
	 * @param button
	 */
	public void listener(MouseAdapter ma){
		buttonReturn.addMouseListener(ma);
		changeName.addMouseListener(ma);
		changePass.addMouseListener(ma);
	}
	/**
	 * Amaga la vista
	 */
	public void disableView(){
		setVisible(false);
	}
	@Override
	public void cancel() {
		
	}
	
}

