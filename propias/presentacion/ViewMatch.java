package propias.presentacion;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * 
 * Configura la vista de partida d'un sudoku.
 * 
 * @author Daniel Sanchez Martinez
 * 
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
	 * 
	 * Constructor. Configura el titol a ser mostrat. 
	 * Configura el temps que es mostrara per pantalla
	 * a l'usuari per a que sapiga quan de temps li porta
	 * resolde el sudoku
	 * 
	 * @param board : Correspon al sudoku(amb forats)
	 * @param size : mida del sudoku(9 o 16)
	 * 
	 */
	public ViewMatch(int[][] board, int size){
		super(board,size);
		setTitle("Partida Sudoku");
		timeL = new JLabel();
		JPanel timeP = new JPanel();
		timeP.setBorder(BorderFactory.createRaisedBevelBorder());
		timeP.add(timeL);
		panelN.add(timeP);
		initializeTime();
		setVisible(true);
	}
	
	/**
	 * 
	 * Configura propietats personalitzades de la vista partida.
	 * Botons com les ajudes que nomes s'utilitza en una partida.
	 * 
	 */
	public void enableCustomProperties(){
		extraButton[1] = new JButton("Hint1");
		extraButton[2] = new JButton("Hint2");
		extraButton[1].setToolTipText("Solucionar casella seleccionada");
		extraButton[2].setToolTipText("Revisar progrés actual del sudoku");
		verticalButton.add(extraButton[1]);
		verticalButton.add(extraButton[2]);
	}
	
	/**
	 * 
	 * Afegeix listeners als botons.
	 * 
	 * @param mm : Clase MouseAdaptar que s'encarrega de gestionar el listener
	 * @param b : Boto a ser observat
	 * 
	 */
	protected void buttonListener(MouseAdapter mm, JButton b){
	b.addMouseListener(mm);
	}
	
	/**
	 * 
	 * Inicialitza el timer i configura cada quan s'actualitzara(1 segon)
	 * 
	 */
	public void initializeTime(){
		time = new Timer(1000, new timerListener());
		time.start();		
	}
	
	/**
	 * 
	 * Listener que s'encarrega de anar modificant el temps que es mostra per pantalla
	 *
	 */
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