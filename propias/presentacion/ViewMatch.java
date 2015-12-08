package propias.presentacion;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.Border;

/**
 * Type of the view board: ViewMatch
 * 
 * @author Daniel Sanchez Martinez
 */
public class ViewMatch extends GenerateBoard{
	
	Timer time;
	JLabel timeL;
	int[] hour = {0,0};
	int[] minute = {0,0};
	int[] second = {0,0};
	String[] s;
	String[] m;
	String[] h;
	/**
	 * Constructor
	 * @param board
	 * @param size
	 */
	public ViewMatch(int[][] board, int size){
		super(board,size);
		setTitle("Partida Sudoku");
		enableCustomProperties();
		timeL = new JLabel();
		JPanel timeP = new JPanel();
		timeP.setBorder(BorderFactory.createRaisedBevelBorder());
		timeP.add(timeL);
		panelN.add(timeP);
		initializeTime();
		setVisible(true);
	}
	
	public void enableCustomProperties(){
		extraButton[1] = new JButton("Hint1");
		extraButton[2] = new JButton("Hint2");
		extraButton[1].setToolTipText("Solucionar casella seleccionada");
		extraButton[2].setToolTipText("Revisar progrés actual del sudoku");
		verticalButton.add(extraButton[1]);
		verticalButton.add(extraButton[2]);
	}
	
	protected void buttonListener(MouseAdapter mm, JButton b){
	b.addMouseListener(mm);
	}
	
	public void initializeTime(){
		time = new Timer(1000, new timerListener());
		time.start();		
	}
	
	class timerListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			++second[0];
			if(second[0] == 10){
				second[0] = 0;
				++second[1];
				if(second[1] == 6){
					second[0] = 0;
					second[1] = 0;
					++minute[0];
					if(minute[0] == 10){
						minute[0] = 0;
						++minute[1];
						if(minute[1] == 6){
							minute[0] = 0;
							minute[1] = 0;
							++hour[0];
							if(hour[0] == 10){
								hour[0] = 0;
								++hour[1];
							}
						}
					}
				}
			}			
			timeL.setText(String.valueOf(hour[1])+String.valueOf(hour[0])+":"+String.valueOf(minute[1])+String.valueOf(minute[0])+":"+String.valueOf(second[1])+String.valueOf(second[0]));
		}
	}
}