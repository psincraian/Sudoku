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
	
	MouseEvent lastCell = null;
	ControllerPresentation cp;
	GenerateBoard vm;
	List<Integer> candidates;
	int size;
	
	/**
	 * Constructor que s'encarrga de gestionar la vista seleccionada.
	 * 
	 * @param board : Es tracta del sudoku sense resoldre amb forats per tal
	 * de que l'usuari el resolgui.
	 * @param size : Representa la mida del sudoku. Potser 9 o 16.
	 * @param typeBoard : Representa el tipus de vista. Si typeBoard(1) la vista serà 
	 * la de creació d'un nou suoku per part de l'usuari
	 * @param scp
	 * @param guest : Indica si l'usuari es convidat o no
	 * @param frame : El fram del joc. Es configura amb el panell de la vista.
	 * 
	 */
	public ControllerViewBoard(int[][] board, int size, int typeBoard, ControllerPresentation scp, boolean guest, JFrame frame){
		this.cp = scp;
		this.size = size;
		if(typeBoard == 0){
			this.vm = new ViewMatch(board,size);
			vm.buttonListener(new MouseManage(), vm.extraButton[1]);
			vm.buttonListener(new MouseManage(), vm.extraButton[2]);
			if(guest)
				vm.extraButton[0].setEnabled(false);
		}
		else{
			this.vm = new ViewCreateBoard(board,size);
			vm.buttonListener(new MouseManage(), vm.actEntry);
		}
		frame.getContentPane().add(vm);
		vm.buttonListener(new MouseManage(), vm.extraButton[0]);
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
	 * Troba una casella en el taulell donada una posició
	 * 
	 * @param position : La posició a ser trobada.
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
	 * @param cond : Depenen de cond, el boto estarà habilidat o no.
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
	 * @param message El missatge en questió.
	 * 
	 */
	public void sendMessage(String message){
		vm.sendMessage(message);
	}
	
	/**
	 * 
	 * Implementa la funció mouseClicked de la clase MouseAdapter, per tal de 
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
					candidates = cp.getCandidates(cell.getName());
					setCandidates(true);
		        	cell.setBackground(new Color(120,220,220));
				}
			}
			else if(e.getSource() instanceof JButton){
				JButton bPressed = (JButton) e.getSource();
				if(bPressed.getText() == "Hint2"){
	        		List<String> currentProgress = cp.getDifferentCells();
	        		for(String wrongCell : currentProgress){
	        			JPanel p = new JPanel();
	        			p = findCell(wrongCell);
	        			p.setBackground(Color.RED);
	        		}
	        	}
				else if(bPressed.getText() == "Tornar"){
    				cp.getBack();
	        	}
				else if(bPressed.getText() == "Guardar" && vm.extraButton[0].isEnabled()){
					if(vm instanceof ViewCreateBoard){
	        			if(cp.checkBoard()){
	        				cp.saveBoard();
	        				vm.sendMessage("S'ha guardat el sudoku");
	        				cp.getBack();
	        			}
	        			else{
	        				vm.sendMessage("No s'ha pogut guardar, no te soluciÃ³n Ãºnica");
	        			}
	        		}
	        		else{
	        			cp.saveBoard();
        				vm.sendMessage("S'ha guardat el sudoku");
        				cp.getBack();
	        		}
	        	}
				else if(bPressed.getText() == "Actualitzar"){
					cp.setBoardFast(vm.nums.getText());
				}
				else if(lastCell != null && !(lastCell.getSource() instanceof JButton))
		        {
		        	JPanel cell = (JPanel)lastCell.getSource();
		        	JLabel label = (JLabel) cell.getComponent(0);
			        if(bPressed.getText() != "Hint1"){
			        	if(bPressed.getName() == "0"){
			        		label.setText("");
							cp.updateCell(cell.getName(),0);
				        	setCandidates(false);
				        	String[] pos = cell.getName().split(" ");
				        	vm.drawSquare(Integer.parseInt(pos[0]),Integer.parseInt(pos[1]));
				        	lastCell = e;
			        	}
			        	else if(candidates.contains(Integer.parseInt(bPressed.getText()))) {
				        	label.setText(bPressed.getText());
							cp.updateCell(cell.getName(),Integer.parseInt(bPressed.getText()));
				        	setCandidates(false);
				        	String[] pos = cell.getName().split(" ");
				        	vm.drawSquare(Integer.parseInt(pos[0]),Integer.parseInt(pos[1]));
				        	lastCell = e;
			        	}
		        	}
			        else if(bPressed.getText() == "Hint1"){
			       		int value = cp.getCellResolved(cell.getName());
				       	label.setText(Integer.toString(value));
				       	String[] pos = cell.getName().split(" ");
			        	vm.drawSquare(Integer.parseInt(pos[0]),Integer.parseInt(pos[1]));		
			        }
		        }
			}
		}
	}
}
