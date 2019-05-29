import java.util.ArrayList;
import java.util.Scanner;

public class SudokuTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		ArrayList<String[]> sudoku = new ArrayList<String[]>();
		for(int i=0;i<9;i++) {
				String[]  s = String.valueOf(sc.nextLine()).split("");
				sudoku.add(s);
		}
		
		for(String[] s:sudoku) { 
			for(String str:s) {
				System.out.print(" "+str);
			}
			System.out.println();
		}
				
		
		
	
	
	}

}
