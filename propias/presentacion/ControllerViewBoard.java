package propias.presentacion;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;



//import propias.Driver.ControllerPresentation; /*test*/

/**
 * Its duty is to controll board view.
 * 
 * @Author: Daniel Sanchez Martinez
 */
public class ControllerViewBoard {
	
	MouseEvent lastCell = null;
	ControllerPresentation cp;
	GenerateBoard vm;
	List<Integer> candidates;
	int size;
	/**
	 * Constructor. Initialize a view, depending of typeBoard. If typeBoard is 0, 
	 * initialize a ViewMatch to play. If typeBoard is 1, initializes a ViewCreateBoard
	 * to create a sudoku with holes given by user 
	 * @param board
	 * @param size
	 * @param typeBoard
	 */
	public ControllerViewBoard(int[][] board, int size, int typeBoard, ControllerPresentation scp, boolean guest){
		this.cp = scp;
		this.size = size;
		if(typeBoard == 0){
			this.vm = new ViewMatch(board,size);
			vm.buttonListener(new MouseManage(), vm.extraButton[1]);
			vm.buttonListener(new MouseManage(), vm.extraButton[2]);
			vm.extraButton[0].setEnabled(false);
		}
		else{
			this.vm = new ViewCreateBoard(board,size);
			vm.buttonListener(new MouseManage(), vm.actEntry);
		}
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
	 * In case the sudoku is started, it's necessary to update it
	 * such as it was saved
	 * @param position
	 * @param value
	 */
	public void updateBoard(String position, String value){
		vm.setCell(findCell(position),value);
	}
	/**
	 * Find a cell on board given a position
	 * @param position
	 * @return  cell
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
	 * Set enable the buttons depending on the candidates
	 * @param cond
	 */
	public void setCandidates(boolean cond){
		for(int i = 0; i < candidates.size();++i)
			vm.setEnableButton(cond,candidates.get(i));
	}
	
	public void sendMessage(String message){
		vm.sendMessage(message);
	}
	
	/**
	 * Implements mouseClicked function of MouseAdapter 
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
    				vm.disableView();
    				cp.getBack();
	        	}
				else if(bPressed.getText() == "Guardar"){
	        		if(vm instanceof ViewCreateBoard){
	        			if(cp.checkBoard()){
	        				cp.saveBoard();
	        				vm.sendMessage("S'ha guardat el sudoku");
	        				vm.disableView();
	        				cp.getBack();
	        			}
	        			else{
	        				vm.sendMessage("No s'ha pogut guardar, no te solución única");
	        			}
	        		}
	        		else{
	        			cp.saveBoard();
        				vm.sendMessage("S'ha guardat el sudoku");
        				vm.disableView();
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
