package propias.presentacion;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;



//import propias.Driver.ControllerPresentation; /*test*/

/**
 * 
 * S'encarrega de controlar la clase GenerateBoard, configura els listeners
 * de tots els botons i sobre el taulell.
 * 
 * @Author: Daniel Sanchez Martinez
 * 
 */
public class ControllerViewBoard {
	
	public final static int VIEW_CREATE_SUDOKU = 0;
	public final static int VIEW_PLAY_SUDOKU = 1;
	public final static boolean USER_GUEST = true;
	public final static boolean COMPETITION = false;
	public final static boolean TRAINING = true;

	
	private static ControllerViewBoard instance;
	
	MouseEvent lastCell = null;
	GenerateBoard vm;
	List<Integer> candidates;
	int size;
	private viewBoard vb;
	
	/**
	 * 
	 * Interface que s'encarregara de la comunicacio
	 * de la comunicacio amb el ControllerPresentation
	 *
	 */
	protected interface viewBoard{
		public boolean checkBoard();
		public void saveBoard();
		public void showMainMenu();
		public void setBoardFast(String board);
		public void updateCell(String cell, int value);
		public int getCellResolved(String position);
		public List<Integer> getCandidates(String name);
		public List<String> getDifferentCells();
	}
	/**
	 * S'encarrga de llançar la vista seleccionada.
	 * 
	 * @param board : Es tracta del sudoku sense resoldre amb forats per tal
	 * de que l'usuari el resolgui.
	 * @param typeBoard : Representa el tipus de vista. Si typeBoard(1) la vista ser� 
	 * la de creaci� d'un nou suoku per part de l'usuari
	 * @param guest : boolea que ens diu si l'usuari es convidat
	 * @param competition : boolea que ens diu si el tipus de partida es de competicio
	 * @param frame : El fram del joc. Es configura amb el panell de la vista.
	 * @param container : instancia de l'interface que s'ha dimplementar en 
	 * ControllerPresentation per tal de poder comunicar-se amb la vista
	 * 
	 */
	public void launchView(int[][] board, int typeBoard, boolean guest,boolean competition, JFrame frame, Object container){
		this.size = board[0].length;
		instance = this;
		if(typeBoard == VIEW_PLAY_SUDOKU){
			this.vm = new ViewMatch(board,size);
			if(competition == COMPETITION){
				vm.enableCustomProperties();
				vm.buttonListener(new MouseManage(), vm.extraButton[1]);
				vm.buttonListener(new MouseManage(), vm.extraButton[2]);
			}
			else
				vm.extraButton[0].setEnabled(false);
		}
		else{
			this.vm = new ViewCreateBoard(board,size);
			vm.buttonListener(new MouseManage(), vm.actEntry);
		}
		vm.buttonListener(new MouseManage(), vm.extraButton[0]);
		if(guest == USER_GUEST)
			vm.extraButton[0].setEnabled(false);
		vb = (viewBoard)container;
		frame.getContentPane().add(vm);
		vm.buttonListener(new MouseManage(), vm.extraButton[3]);
		vm.buttonListener(new MouseManage(), vm.button[size]);

		for(int i = 0; i < size; ++i){
			this.vm.buttonListener(new MouseManage(), this.vm.button[i]);
			for(int j = 0; j < size; ++j){
				this.vm.panelListener(new MouseManage(), i, j);
			}
		}
	}
	
	/**
	 * 
	 * En el cas que estiguem jugant una partida ja començada per l'usuari
	 * cal actualitzar el taulell amb les modificacions fetes per l'usuari
	 * tal com va acabar(sense contar les caselles ocupades al començar
	 * inicialment la partida)
	 * 
	 * @param position : Casella a modificar
	 * @param value : Valor a mostrar
	 * 
	 */
	public void updateBoard(String position, String value){
		vm.setCell(findCell(position),value);
	}
	
	/**
	 * 
	 * Troba una casella en el taulell donada una posicio
	 * 
	 * @param position : La posicio a ser trobada.
	 * @return  cell : Retorna la casella.
	 * 
	 */
	public JPanel findCell(String position){
		boolean found = false;
		JPanel p = new JPanel();
		for(int i = 0; i < size && !found; ++i){
			for(int j = 0; j < size && !found; ++j){
				if(vm.square[i][j].getName().equals(position)){
					p = vm.square[i][j];
					found = true;
				}
			}
		}
		return p;
	}
	
	/**
	 * 
	 * Habilita els botons(de l'1 al 9) depenen si son candidats o no.
	 * 
	 * @param cond : Depenen de cond, el boto estara habilidat o no.
	 * 
	 */
	public void setCandidates(boolean cond){
		for(int i = 0; i < candidates.size();++i)
			vm.setEnableButton(cond,candidates.get(i));
	}
	
	/**
	 * 
	 * Genera un missatge per tal de ser mostrat a l'usuari.
	 * 
	 * @param message El missatge en questio.
	 * 
	 */
	public void sendMessage(String message){
		vm.sendMessage(message);
	}
	
	/**
	 * 
	 * Implementa la funcio mouseClicked de la clase MouseAdapter, per tal de 
	 * poder gestionar els listeners de la vista.
	 *  
	 */
	public class MouseManage extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			if(e.getSource() instanceof JPanel){
				JPanel cell = (JPanel)e.getSource();
				if(cell.isEnabled()){
					if(lastCell != null && lastCell.getSource() instanceof JPanel){
						JPanel aux = (JPanel)lastCell.getSource();
						String[] pos = aux.getName().split(" ");
			        	vm.drawSquare(Integer.parseInt(pos[0]),Integer.parseInt(pos[1]));
			        	setCandidates(false);
					}
					lastCell = e;
					candidates = vb.getCandidates(cell.getName());
					setCandidates(true);
		        	cell.setBackground(new Color(120,220,220));
				}
			}
			else if(e.getSource() instanceof JButton){
				JButton bPressed = (JButton) e.getSource();
				if(bPressed.getText() == "Hint2"){
	        		List<String> currentProgress = vb.getDifferentCells();
	        		for(String wrongCell : currentProgress){
	        			JPanel p = new JPanel();
	        			p = findCell(wrongCell);
	        			p.setBackground(Color.RED);
	        		}
	        	}
				else if(bPressed.getText() == "Tornar"){
    				vb.showMainMenu();
	        	}
				else if(bPressed.getText() == "Guardar" && vm.extraButton[0].isEnabled()){
					if(vm instanceof ViewCreateBoard){
	        			if(vb.checkBoard()){
	        				vb.saveBoard();
	        				vm.sendMessage("S'ha guardat el sudoku");
	        				vb.showMainMenu();
	        			}
	        			else{
	        				vm.sendMessage("No s'ha pogut guardar, no te solucion unica");
	        			}
	        		}
	        		else{
	        			vb.saveBoard();
        				vm.sendMessage("S'ha guardat el sudoku");
        				vb.showMainMenu();
	        		}
	        	}
				else if(bPressed.getText() == "Actualitzar"){
					vb.setBoardFast(vm.nums.getText());
				}
				else if(lastCell != null && !(lastCell.getSource() instanceof JButton))
		        {
		        	JPanel cell = (JPanel)lastCell.getSource();
		        	JLabel label = (JLabel) cell.getComponent(0);
			        if(bPressed.getText() != "Hint1"){
			        	if(bPressed.getName() == "0"){
			        		label.setText("");
							vb.updateCell(cell.getName(),0);
				        	setCandidates(false);
				        	String[] pos = cell.getName().split(" ");
				        	vm.drawSquare(Integer.parseInt(pos[0]),Integer.parseInt(pos[1]));
				        	lastCell = e;
			        	}
			        	else if(candidates.contains(Integer.parseInt(bPressed.getText()))) {
				        	label.setText(bPressed.getText());
							vb.updateCell(cell.getName(),Integer.parseInt(bPressed.getText()));
				        	setCandidates(false);
				        	String[] pos = cell.getName().split(" ");
				        	vm.drawSquare(Integer.parseInt(pos[0]),Integer.parseInt(pos[1]));
				        	lastCell = e;
			        	}
		        	}
			        else if(bPressed.getText() == "Hint1" & bPressed.isEnabled()){
			       		int value = vb.getCellResolved(cell.getName());
			       		if(value != 0){
					       	label.setText(Integer.toString(value));
					       	String[] pos = cell.getName().split(" ");
				        	vm.drawSquare(Integer.parseInt(pos[0]),Integer.parseInt(pos[1]));
			       		}
			       		else{
			       			bPressed.setEnabled(false);
			       		}
			        }
		        }
			}
		}
	}
	
	/**
	 * 
	 * Funcio que s'encarrega de retorna l'instancia d'aquest controlador
	 * per tal existeixi un sola instancia(singleton)
	 * 
	 * @return instancia de ControllerViewBoard
	 * 
	 */
	public static ControllerViewBoard getInstance() {
		if (instance == null)
			instance = new ControllerViewBoard();
		return instance;
	}
}
