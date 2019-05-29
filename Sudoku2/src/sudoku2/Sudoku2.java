package sudoku2;

import java.util.Arrays;
import java.util.Scanner;

/****************
 *   レーザー砲
 **************/

public class Sudoku2 {

	static int[][]su;
	static int[][]backupsu;
	static int[][][] block;
	static boolean[][] rowflg;
	static boolean[][] columnflg;
	static boolean[][] blockflg;
	
/*	--input--z
 *--入門---*
0 6 9 0 1 0 7 2 0
7 0 5 2 0 6 9 0 0
0 2 8 0 0 4 5 0 0
5 7 0 0 2 0 3 0 0
0 4 0 0 5 9 0 0 2
9 0 0 4 3 7 6 0 0
2 0 0 1 0 0 0 0 7
6 0 0 9 7 0 4 0 0
0 0 0 5 4 8 2 6 1
 *--初級--*
0 0 3 0 0 7 0 0 0
0 0 0 0 0 0 8 9 4
4 0 0 8 0 5 0 0 6
0 0 9 3 7 0 1 0 0
5 4 0 0 6 1 3 0 7
1 0 0 0 2 4 0 0 0
0 0 6 0 8 0 0 0 3
7 1 0 0 0 0 0 0 0
0 8 4 0 0 9 0 6 1
 *--中級--*
0 0 0 3 0 0 0 4 7
0 0 3 0 0 5 0 0 0
0 0 0 2 0 0 8 5 0
8 1 2 0 7 0 0 0 0
0 0 9 0 5 0 6 0 0
5 0 0 0 0 0 0 0 0
0 0 0 1 0 4 0 0 2
9 0 0 0 0 0 0 0 6
0 0 5 7 0 6 1 0 0

#####RESULT#####
0 5 0 3 0 0 0 4 7 
0 0 3 0 0 5 0 6 1 
0 0 0 2 0 7 8 5 3 
8 1 2 0 7 0 0 0 5 
0 0 9 0 5 0 6 0 0 
5 0 0 0 0 0 0 0 0 
0 0 0 1 0 4 5 0 2 
9 0 1 5 0 0 0 0 6 
0 0 5 7 0 6 1 0 0 

 */
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Scanner sc = new Scanner(System.in);
		su = new int[9][9];
		backupsu = new int[9][9];
		block = new int[9][3][3];
		rowflg = new boolean[9][9];
		columnflg = new boolean[9][9];
		blockflg = new boolean[9][9];
		
		//初期化
		for(boolean[] tmp:rowflg) 
			Arrays.fill(tmp, false);
		for(boolean[] tmp:columnflg)
			Arrays.fill(tmp, false);
		for(boolean[]tmp:blockflg)
				Arrays.fill(tmp, false);
		
		
		/*数字を読み込む　それぞれのflgを立てる*/
		for(int i=0;i<9;i++) {
			for(int j=0;j<9;j++) {
				int num = sc.nextInt();
				su[i][j] = num;
				block[getBlockNum(i,j)][getBlockRow(i)][getBlockColumn(j)]=num;
				if(num != 0) {
					rowflg[i][num-1]=true;
					columnflg[j][num-1]=true;
					blockflg[getBlockNum(i,j)][num-1]=true;
				}
			}
		}
		
		//問題を解く
//		while(!clearCheck()) {
		blockLaserSolve();
		blockLaserSolve();
		blockLaserSolve();
		allSearchSolve();
		allSearchSolve();
		blockLaserSolve();
		blockLaserSolve();
		allSearchSolve();
//		}
		
		
//		System.out.println("");
//		System.out.println("blockflg");
//		for(boolean[]tmp2:blockflg) {
//			for(boolean tmp:tmp2)
//				System.out.print(tmp+" ");
//			System.out.println("");
//		}

//		for(int[][] b1:block) {
//			System.out.println("======Block=====");
//			for(int[] b2:b1) {
//				for(int value:b2) {
//					System.out.print(value+" ");
//				}
//				System.out.println();
//			}
//			System.out.println();
//		}
		
		
		System.out.println("#####RESULT#####");
		//結果出力
		for(int[] rsu:su) {
			for(int value:rsu) {
				System.out.print(value+" ");
			}
			System.out.println();
		}
		
	}
	/*解決ロジック１(入門編)*/
	private static void allSearchSolve() {
		// TODO Auto-generated method stub
		for(int i=0;i<9;i++) {
			for(int j=0;j<9;j++) {
				if(su[i][j]==0) {
					boolean[] check = new boolean[9];
					Arrays.fill(check, false);
					for(int k=0;k<9;k++) {
						
						if(rowflg[i][k])check[k]=true;
						if(columnflg[j][k])check[k]=true;
						if(blockflg[getBlockNum(i,j)][k])check[k]=true;
					}
					int num = 0;
					int count =0;
					for(int k=0;k<9;k++) {
							if(!check[k]) {
								count++;
								num = k;
							}
					}
					if(count==1) {
						System.out.println("Num"+(num+1)+" i:"+i+" j:"+j);
						setNum(num+1,i,j);
						for(int k=0;k<9;k++) {
						}
					}
				}
			}
		}
	}
	
	/*解決ロジック2(初級編)*/
	private static void blockLaserSolve() {
		// TODO Auto-generated method stub
		for(int b=0;b<9;b++) {
			for(int k=0;k<9;k++) {
				if(!blockflg[b][k]) {
					boolean[][] check = new boolean[3][3];
					for(boolean[] tmp:check) 
						Arrays.fill(tmp, false);
					for(int brow=0;brow<3;brow++) {
						for(int bcolumn=0;bcolumn<3;bcolumn++) {
							if(rowflg[getRow(b,brow)][k])check[brow][bcolumn]=true;
							if(columnflg[getColumn(b,bcolumn)][k])check[brow][bcolumn]=true;
							if(block[b][brow][bcolumn]!=0)check[brow][bcolumn]=true;
						}
					}
					int count=0;
					int tmprow=-1;
					int tmpcolumn=-1;
					for(int brow=0;brow<3;brow++) {
						for(int bcolumn=0;bcolumn<3;bcolumn++) {
							if(!check[brow][bcolumn]) {
								count++;
								tmprow = brow;
								tmpcolumn = bcolumn;
							}
						}
					}
					if(count==1) {
						setNum(k+1,b,tmprow,tmpcolumn);
						System.out.println("Num"+(k+1)+" i:"+getRow(b,tmprow)+" j:"+getColumn(b,tmpcolumn));
					}
				}
			}
		}
	}

	public static int getBlockRow(int row) {
		return row%3;
	}
	public static int getBlockColumn(int column) {
		return column%3;
	}
	public static int getBlockNum(int row,int column) {
		if(0<=row&&row<3) {
			if(0<=column&&column<3) return 0;
			else if(3<=column&&column<6) return 1;
			else return 2;
		}else if(3<=row&&row<6) {
			if(0<=column&&column<3) return 3;
			else if(3<=column&&column<6) return 4;
			else return 5;
		}else {
			if(0<=column&&column<3) return 6;
			else if(3<=column&&column<6) return 7;
			else return 8;
		}
	}
	static int getRow(int blocknum,int blockRow) {
		int plusRow=0;
		if(3<=blocknum&&blocknum<6) plusRow=3;
		else if(6<=blocknum&&blocknum<9) plusRow=6;
		return blockRow+plusRow;
	}
	static int getColumn(int blocknum,int blockColumn) {
		int plusColumn=blocknum%3;
		
		return blockColumn+plusColumn*3;
	}
	static void setNum(int num,int row,int column) {
		su[row][column] = num;
		block[getBlockNum(row,column)][getBlockRow(row)][getBlockColumn(column)]=num;
		rowflg[row][num-1]= true;
		columnflg[column][num-1]= true;
		blockflg[getBlockNum(row,column)][num-1]= true;
	}
	static void setNum(int num,int blocknum,int blockrow , int blockcolumn) {
		
		su[getRow(blocknum,blockrow)][getColumn(blocknum,blockcolumn)]=num;
		block[blocknum][blockrow][blockcolumn]=num;
		rowflg[getRow(blocknum,blockrow)][num-1]= true;
		columnflg[getColumn(blocknum,blockcolumn)][num-1]= true;
		blockflg[blocknum][num-1]= true;
	}
	static int returnSu(int blocknum,int blockrow, int blockcolumn){
		return su[getRow(blocknum,blockrow)][getColumn(blocknum,blockcolumn)];
	}
	
	static void syncSu(int blocknum,int blockrow, int blockcolumn) {
		su[getRow(blocknum,blockrow)][getColumn(blocknum,blockcolumn)]=block[blocknum][blockrow][blockcolumn];
	}
	static void syncBlockSu(int row, int column) {
		block[getBlockNum(row,column)][getBlockRow(row)][getBlockColumn(column)]=su[row][column];
	}
	
	
	
	//データをバックアップ および flgの再設定
	public void backup() {
		for(int i=0;i<9;i++) {
			for(int j=0;j<9;j++) {
				backupsu[i][j] = su[i][j];
				block[getBlockNum(i,j)][getBlockRow(i)][getBlockColumn(j)]=su[i][j];
				if(su[i][j] != 0) {
					rowflg[i][su[i][j]-1]=true;
					columnflg[j][su[i][j]-1]=true;
					blockflg[getBlockNum(i,j)][su[i][j]-1]=true;
				}
			}
		}
	}

	public static boolean clearCheck() {
		
		for(boolean[] tmp2:rowflg)
			for(boolean tmp:tmp2)
				if(!tmp)return false;
		for(boolean[] tmp2:columnflg)
			for(boolean tmp:tmp2)
				if(!tmp)return false;
		for(boolean[]tmp2:blockflg)
			for(boolean tmp:tmp2)
				if(!tmp)return false;
		return true;
	}
	
	
	
}
