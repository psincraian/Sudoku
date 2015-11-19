package propias.Driver;
 
import java.util.Scanner;
import propias.dominio.clases.SudokuVerification;
import propias.dominio.clases.Board;
 
 /*@Author Daniel Sanchez Martinez*/
public class Driver_SudokuVerification {
 
     
    public static void main(String[] args){
    	Scanner scn = new Scanner(System.in);
        Driver_SudokuVerification ds = new Driver_SudokuVerification();
        SudokuVerification sv = new SudokuVerification();
        
        System.out.println("Introduir tamany:");
        int size = scn.nextInt();
        Board sb = new Board(size);
        //sb.initializeBoard();
        System.out.println(sv.resolve(sb));   
    }
}
