package propias.presentacion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import javafx.scene.shape.Line;

/**
 * Generates a new view of board. the type's view is:
 * ViewMatch to create a new match
 * ViewCreateBoard to create a new sudoku by the user
 * 
 * @author Daniel Sanchez Martinez
 */
public abstract class GenerateBoard extends SetView{
	private JPanel panelS;
	private JPanel panelC;
	protected JButton[] button;
	protected JButton[] extraButton;
	protected JPanel[][] square;
	private JLabel[][] label;
	private Box verticalBox;
	Box horizontalBox;
	Box verticalButton;
	private int size;
	protected JLabel prueba;
	private int subsize;
	JLabel entryFast;
	JTextField nums;
	JButton actEntry;
	
	/**
	 * Create the application.
	 */
	public GenerateBoard(int[][] board, int size) {
		super();
		//setMaximumSize(1000,750);
		//setPreferredSize(976,728);
		//setMinimumSize(976,728);
		this.size = size;
		if(size % 3 == 0)
			subsize = 3;
		else
			subsize = 4;
		initialize(board);
	}

	/**
	 * Create the view
	 */
	protected void initialize(int[][] board) {
		panelC = new JPanel();
		panelS = new JPanel();
		panelC.setBackground(Color.white);
		panelS.setBackground(Color.white);

		/*Center*/
		getContentPane().add(panelC, BorderLayout.CENTER);
		panelC.setLayout(new BoxLayout(panelC,BoxLayout.Y_AXIS));

		iniBoard(board);
				
		/*South*/
		Component verticalStrut_ = Box.createVerticalStrut(130);
		panelS.add(verticalStrut_);
		getContentPane().add(panelS, BorderLayout.SOUTH);
		
		/*East*/
		extraButton[0].setToolTipText("Guardar board actual");
		extraButton[3].setToolTipText("Torna a la pàgina principal, sense guardar taulell actual");
		button[size].setToolTipText("Desmarca o esborra casella seleccionada");
		verticalButton.add(extraButton[3]);
		verticalButton.add(extraButton[0]);
		verticalButton.add(button[size]);
		getContentPane().add(verticalButton, BorderLayout.EAST);
	}
	/**
	 * Initialize the view
	 * @param board
	 */
	private void iniBoard(int[][] board){
		square = new JPanel[size][size];
		label = new JLabel[size][size];
		button = new JButton[size+1];
		verticalButton = Box.createVerticalBox();
		extraButton = new JButton[4];
		extraButton[0] = new JButton("Guardar");
		extraButton[3] = new JButton("Tornar");
		button[size] = new JButton("Borrar");
		button[size].setName("0");
		for(int i = 0; i < size; ++i){
			panelC.add(iniRow(i,board[i]));
			button[i] = new JButton(Integer.toString(i+1));
			button[i].setEnabled(false);
			button[i].setPreferredSize(new Dimension(50,50));
			button[i].setMinimumSize(new Dimension(50,50));
			button[i].setMaximumSize(new Dimension(50,50));
			panelS.add(button[i]);
		}
		panelC.setMinimumSize(new Dimension(size*30,size*30));
		panelC.setPreferredSize(new Dimension(size*30,size*30));
		panelC.setMaximumSize(new Dimension(size*30,size*30));
	}
	/**
	 * Initialize some components
	 * @param i
	 * @param row
	 * @return
	 */
	private Box iniRow(int i,int[] row){
		horizontalBox = Box.createHorizontalBox();
		for(int j = 0; j < size; ++j){
			if(subsize == 3){
				square[i][j] = new JPanel(new FlowLayout(FlowLayout.CENTER,15,15));
				square[i][j].setPreferredSize(new Dimension(50,50));
				square[i][j].setMinimumSize(new Dimension(50,50));
				square[i][j].setMaximumSize(new Dimension(50,50));
			}
			else{
				square[i][j] = new JPanel(new FlowLayout());
				square[i][j].setPreferredSize(new Dimension(30,30));
				square[i][j].setMinimumSize(new Dimension(30,30));
				square[i][j].setMaximumSize(new Dimension(30,30));
			}
			drawSquare(i,j);
			if(row[j] == 0)
					label[i][j] = new JLabel("");
			else{
				label[i][j] = new JLabel(Integer.toString(row[j]));
				label[i][j].setForeground(new Color(204,102,0));
				square[i][j].setEnabled(false);
			}			
			square[i][j].setBorder(new LineBorder(new Color(0, 0, 0),2));
			square[i][j].add(label[i][j]);
			String name = Integer.toString(i) + " " + Integer.toString(j);
			square[i][j].setName(name);
			horizontalBox.add(square[i][j]);
		}
		return horizontalBox;
	}	
	/**
	 * Paint the background of a panel. Depending on i and j positions.
	 * @param i
	 * @param j
	 */
	protected void drawSquare(int i, int j){
		if(subsize == 3){
			if(i < 3 || i > 5){
				if(j < 3 || j > 5)
					square[i][j].setBackground(new Color(200,200,200));
				else 
					square[i][j].setBackground(Color.WHITE);
			}
			else{
				if(j < 3 || j > 5)
					square[i][j].setBackground(Color.WHITE);
				else 
					square[i][j].setBackground(new Color(200,200,200));
			}
		}
		else{
			if(i < 4 || (i > 7 && i < 12)){
				if(j < 4 || (j > 7 && j < 12))
					square[i][j].setBackground(new Color(200,200,200));
				else 
					square[i][j].setBackground(Color.WHITE);
			}
			else{
				if(j < 4 || (j > 7 && j < 12))
					square[i][j].setBackground(Color.WHITE);
				else 
					square[i][j].setBackground(new Color(200,200,200));
			}
		}
	}
	
	/**
	 * Add listeners to the button
	 * @param mm
	 * @param b
	 */
	protected abstract void buttonListener(MouseAdapter mm, JButton b);
	
	/**
	 * Add listeners to the panel
	 * @param mm
	 * @param index
	 * @param subindex
	 */
	protected void panelListener(MouseAdapter mm, int index, int subindex){
		square[index][subindex].addMouseListener(mm);
	}
	/**
	 * Enable or disable the button to interact between user and board
	 * @param cond
	 */
	protected void setEnableButton(boolean cond, int index){
			button[index-1].setEnabled(cond);
	}
	
	/**
	 * Send message to the user
	 * @param message
	 */
	protected void sendMessage(String message){
		JOptionPane.showMessageDialog(null, message);
	}
	
	/**
	 * Change value of a cell
	 * @param l
	 * @param text
	 */
	protected void setCell(JPanel p, String text){
		JLabel l = new JLabel();
		l = (JLabel)p.getComponent(0);
		l.setText(text);
		p.setEnabled(true);
	}
	/**
	 * Set frame to visible or not visible
	 */
	protected void disableView(){
		setVisible(false);
	}
	
	protected abstract void enableCustomProperties();
}
