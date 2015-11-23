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
public class GenerateBoard extends SetView{
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
	/**
	 * Create the application.
	 */
	public GenerateBoard(int[][] board, int size) {
		super();
			this.size = size;
			if(size % 3 == 0)
				subsize = 3;
			else
				subsize = 4;
			initialize(board);
			pack();
			setVisible(true);
	}

	/**
	 * Create the view
	 */
	protected void initialize(int[][] board) {
		getContentPane().setSize(1000,800);
		setResizable(false);
		getContentPane().setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panelC = new JPanel();
		panelS = new JPanel();
		JPanel panelN = new JPanel();
		panelC.setBackground(Color.white);
		panelN.setBackground(Color.white);
		panelS.setBackground(Color.white);

		/*North*/
		getContentPane().add(panelN, BorderLayout.NORTH);
		panelN.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		JLabel title = new JLabel("Partida Sudoku");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		panelN.add(title);
		prueba = new JLabel();
		panelN.add(prueba);
		
		Component verticalStrut = Box.createVerticalStrut(80);
		Component horizontalStrut = Box.createHorizontalStrut(100);
		panelN.add(verticalStrut);
		panelN.add(horizontalStrut);
		
		/*Center*/
		getContentPane().add(panelC, BorderLayout.CENTER);
		panelC.setLayout(new BoxLayout(panelC,BoxLayout.Y_AXIS));

		iniBoard(board);
		
		extraButton[3].setToolTipText("Vuelve a la pagina principal, sin guardar tablero actual");
		panelN.add(extraButton[3]);
		
		/*South*/
		Component verticalStrut_ = Box.createVerticalStrut(130);
		panelS.add(verticalStrut_);
		getContentPane().add(panelS, BorderLayout.SOUTH);
		
		/*East*/
		extraButton[0].setToolTipText("Guardar board actual");
		extraButton[1].setToolTipText("Solucionar casilla seleccionada");
		extraButton[2].setToolTipText("Revisar progreso actual board");
		verticalButton.add(extraButton[0]);
		verticalButton.add(extraButton[1]);
		verticalButton.add(extraButton[2]);
		verticalButton.add(button[size]);
		getContentPane().add(verticalButton, BorderLayout.EAST);
	}
	/**
	 * Initialize the view
	 * @param board
	 */
	private void iniBoard(int[][] board){
		verticalBox = Box.createVerticalBox();
		square = new JPanel[size][size];
		label = new JLabel[size][size];
		button = new JButton[size+1];
		verticalButton = Box.createVerticalBox();
		extraButton = new JButton[4];
		extraButton[0] = new JButton("Guardar");
		extraButton[1] = new JButton("Hint1");
		extraButton[2] = new JButton("Hint2");
		extraButton[3] = new JButton("Volver");
		button[size] = new JButton("Borrar");
		button[size].setName("0");
		for(int i = 0; i < size; ++i){
			verticalBox.add(iniRow(i,board[i]));
			button[i] = new JButton(Integer.toString(i+1));
			//button[i].setName(Integer.toString(i+1));
			button[i].setEnabled(false);
			button[i].setPreferredSize(new Dimension(50,50));
			button[i].setMinimumSize(new Dimension(50,50));
			button[i].setMaximumSize(new Dimension(50,50));
			panelS.add(button[i]);
		}
		panelC.add(Box.createVerticalGlue());
		panelC.add(verticalBox);
		panelC.add(Box.createVerticalGlue());
	}
	/**
	 * Initialize some components
	 * @param i
	 * @param row
	 * @return
	 */
	private Box iniRow(int i,int[] row){
		horizontalBox = Box.createHorizontalBox();
		horizontalBox.add(Box.createHorizontalStrut(200));
		for(int j = 0; j < size; ++j){
			square[i][j] = new JPanel(new FlowLayout(FlowLayout.CENTER));
			square[i][j].setPreferredSize(new Dimension(30,30));
			square[i][j].setMinimumSize(new Dimension(30,30));
			square[i][j].setMaximumSize(new Dimension(55,55));
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
		horizontalBox.add(Box.createHorizontalStrut(200));
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
	protected void buttonListener(MouseAdapter mm, JButton b){
		b.addMouseListener(mm);
	}
	
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
	}
	/**
	 * Set frame to visible or not visible
	 */
	protected void disableView(){
		setVisible(false);
	}
	/**
	 * Close view
	 */
	protected void closeView(){
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		dispose();
	}
}
