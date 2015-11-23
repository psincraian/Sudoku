package propias.Driver;
 
import java.util.Scanner;

import propias.dominio.clases.Board;
import propias.dominio.controladores.generator.CntrlSudokuVerification;
 
/**
 * 
 * @author daniel sanchez martinez
 *
 */
public class Driver_SudokuVerification {
 
     
    public static void main(String[] args){
    	Scanner scn = new Scanner(System.in);
        Driver_SudokuVerification ds = new Driver_SudokuVerification();
        CntrlSudokuVerification sv = new CntrlSudokuVerification();
        
        System.out.println("Introduir tamany:");
        int size = scn.nextInt();
        Board sb = new Board(size);
        //sb.initializeBoard();
        System.out.println(sv.resolve(sb));   
    }
}
