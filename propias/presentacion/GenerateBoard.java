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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;


/**
 * 
 * Configura una nova vista amb un taulell. Segons 
 * el tipus de vista que es vulgui, es carregarà 
 * la creació d'un nou sudoku o jugar una partida. 
 * 
 * @author Daniel Sanchez Martinez
 * 
 */
public abstract class GenerateBoard extends SetView{
	private JPanel panelS;
	private JPanel panelC;
	protected JButton[] button;
	protected JButton[] extraButton;
	protected JPanel[][] square;
	private JLabel[][] label;
	Box horizontalBox;
	Box verticalButton;
	private int size;
	protected JLabel prueba;
	private int subsize;
	JLabel entryFast;
	JTextField nums;
	JButton actEntry;
	
	/**
	 * 
	 * Constructor del GenerateBoard. Configura la submida 
	 * del taulell i comença a configurar la vista
	 * 
	 * @param board : Pertany al sudoku, si la vista que es vol mostrar
	 * es ViewMatch, board contindrà el sudoku sense resoldre(amb forats), 
	 * en el cas de que es vulgui la vista per crear un nou sudoku, el board
	 * serà buit.
	 * @param size : la mida del sudoku
	 * 
	 */
	public GenerateBoard(int[][] board, int size) {
		super();
		this.size = size;
		if(size == 9)
			subsize = 3;
		else
			subsize = 4;
		initialize(board);
	}

	/**
	 * 
	 * Configura la vista.
	 * 
	 * @param board : El sudoku
	 */
	protected void initialize(int[][] board) {
		panelC = new JPanel();
		panelS = new JPanel();
		panelC.setBackground(Color.white);
		panelS.setBackground(Color.white);

		/*Center*/
		add(panelC, BorderLayout.CENTER);
		panelC.setLayout(new BoxLayout(panelC,BoxLayout.Y_AXIS));

		iniBoard(board);
				
		/*South*/
		Component verticalStrut_ = Box.createVerticalStrut(130);
		panelS.add(verticalStrut_);
		add(panelS, BorderLayout.SOUTH);
		
		/*East*/
		extraButton[0].setToolTipText("Guardar board actual");
		extraButton[3].setToolTipText("Torna a la pÃ gina principal, sense guardar taulell actual");
		button[size].setToolTipText("Desmarca o esborra casella seleccionada");
		verticalButton.add(extraButton[3]);
		verticalButton.add(extraButton[0]);
		verticalButton.add(button[size]);
		add(verticalButton, BorderLayout.EAST);
	}
	
	/**
	 * 
	 * Inicialitza els paràmetres de la vista.
	 * 
	 * @param board : En el cas de que la vista sigui de jugar una partida, 
	 * es posaràn els numeros a la vista.
	 * 
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
	 * 
	 * Inicialitza el propi taulell per poder interactuar amb l'usuari.
	 * 
	 * @param i : fila dins del taulell
	 * @param row : fila dins del sudoku(board)
	 * @return Es retorna tota una fila, amb quadrats pintats, que pot estar buit
	 * o amb numeros
	 * 
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
	 * 
	 * Per tal de poder diferencia les lines dins del taulell, 
	 * es pinta de diferent color(monocrom) segons la submida congigurada
	 * 
	 * @param i : posició dins d'una fila
	 * @param j : posició dins d'un columna
	 * 
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
	 * 
	 * Afegeix listeners als botons.
	 * 
	 * @param mm : Clase MouseAdaptar que s'encarrega de gestionar el listener
	 * @param b : Botó a ser observat
	 * 
	 */
	protected abstract void buttonListener(MouseAdapter mm, JButton b);
	
	/**
	 * Afegeix listeners als panells. Es a dir, afegeix els listeners
	 * al taulell del joc.
	 * 
	 * @param mm : Clase MouseAdaptar que s'encarrega de gestionar el listener
	 * @param index : posició dins d'una fila
	 * @param subindex : posició dins d'una columna
	 */
	protected void panelListener(MouseAdapter mm, int index, int subindex){
		square[index][subindex].addMouseListener(mm);
	}
	
	/**
	 * 
	 * Habilida o deshabilita els botons per interactuar entre usuari
	 * i taulell. Està lligat amb els candidats.
	 * 
	 * @param cond : Condició que fa que un botó sigui habilitat o
	 * deshabilitat
	 * 
	 */
	protected void setEnableButton(boolean cond, int index){
			button[index-1].setEnabled(cond);
	}
	
	/**
	 * 
	 * Envia un missatge a l'usuari.
	 * 
	 * @param message : El missatge en questió
	 * 
	 */
	protected void sendMessage(String message){
		JOptionPane.showMessageDialog(null, message);
	}
	
	/**
	 * 
	 * Cambia el valor d'una casella. Relacionat quan un usuari vol
	 * carregar una partida guardada.
	 * 
	 * @param l : panell en questió(casella)
	 * @param text : valor a posar
	 * 
	 */
	protected void setCell(JPanel p, String text){
		JLabel l = new JLabel();
		l = (JLabel)p.getComponent(0);
		l.setText(text);
		p.setEnabled(true);
	}
	
	/**
	 * 
	 * Segons la vista que s'ha seleccionat, una vista tindrà unes botons o uns altres.
	 * Aquesta funció s'encarregarà de mostrar tot allo que necessita la vista.
	 * 
	 */
	protected abstract void enableCustomProperties();
}
