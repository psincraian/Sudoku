package propias.dominio.controladores.generator;

import propias.dominio.clases.Board;

/**
 * 
 * Verificador d'un sudoku. Verifica si un sudoku compleix que
 * un nombre no pot estar repetit dos cops a la mateixa fila, columna
 * quadrant
 * 
 * @author Daniel Sanchez Martinez
 */
public class CntrlSudokuVerification {
	
	/**
	 * 
	 * Comprova si donat un sudoku es correcte. Si no es troba 
	 * un nombre dos cops en la mateix fila, columna i quadrant
	 * llavors el sudoku es correcte.
	 * 
	 * @param b : el sudoku a ser comprovat
	 * @return : si es correcte retorna true, sino retorn false
	 * 
	 */
	public boolean resolve(Board b){
		final int dim = b.getSize();	
		boolean sol1,sol2; 
		int jtmp,itmp,subDim,actual;
		subDim = dim % 3 == 0 ? 3 : 4;
		
		for(int i = 0; i < dim; ++i){
			for(int j = 0; j < dim; ++j){
				actual = b.getCasella(i, j);
				if(actual != 0){
					jtmp = j+1;
					for(;jtmp != dim && b.getCasella(i, jtmp) != actual; ++jtmp);
					sol1 = jtmp == dim? true : false;
					itmp = i+1;
					for(;itmp != dim && b.getCasella(itmp, j) != actual; ++itmp);
					sol2 = itmp == dim? true : false;
					if(sol1 == false || sol2 == false) return false;
					for(jtmp = 0; (jtmp+j) != j && jtmp != subDim; ++jtmp){
						for(itmp = i+1;itmp != subDim && b.getCasella(itmp, jtmp) != actual; ++itmp);		
						sol1 = jtmp == subDim? true : false;							
						if(sol1 != true) return false;
					}
				}
			} 
		}
		return true;
	}
}
