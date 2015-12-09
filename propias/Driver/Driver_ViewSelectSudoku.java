package propias.Driver;

import java.util.ArrayList;
import java.util.List;

import propias.presentacion.*;

public class Driver_ViewSelectSudoku {

	public static void main(String[] args){
		List<String> id = new ArrayList<String>();
		id.add("e001");
		id.add("e002");
		id.add("e003");
		id.add("e004");
		id.add("e005");
		id.add("e006");
		id.add("e007");
		id.add("e008");
		id.add("e011");
		id.add("e012");
		id.add("e013");
		id.add("e014");
		id.add("e015");
		id.add("e016");
		id.add("e017");
		id.add("e018");
		
		ViewSelectSudoku ss = new ViewSelectSudoku(id);
	}
}
