package s5;

import java.util.Scanner;

public class Sudoku5 extends CommonClass{

	//static int[][]su;
	static int[][][] block;
	static boolean[][] rowflg;
	static boolean[][] columnflg;
	static boolean[][] blockflg;
	static boolean setNumflg;
	static boolean[][][]ablesu;
	static boolean [][]checkablesu;
	
	static final String[] type = {"block","row","column"};
	static final int BLOCKTYPE =0;
	static final int ROWTYPE =1;
	static final int COLUMNTYPE =2;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Scanner sc = new Scanner(System.in);
		block = new int[9][3][3];
		rowflg = new boolean[9][9];
		columnflg = new boolean[9][9];
		blockflg = new boolean[9][9];
		ablesu = new boolean[9][9][9];
		checkablesu = new boolean[9][9];
		
		Su s = new Su();
		for(int i=0;i<9;i++) {
			for(int j=0;j<9;j++) {
				int num = sc.nextInt();
				s.set(i,j,num);
				if(num != 0) {
					rowflg[i][num-1]=true;
					columnflg[j][num-1]=true;
					blockflg[getBlockNum(i,j)][num-1]=true;
				}
			}
		}
		
	}

}
