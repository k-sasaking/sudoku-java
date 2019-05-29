package s5;

import java.util.Arrays;

public class CommonClass {

	public boolean[][] rowflg;
	public boolean[][] columnflg;
	public boolean[][] blockflg;

	
	CommonClass(){
		init();
	}
	
	private void init() {
		//初期化
		for(boolean[] tmp:rowflg) 
			Arrays.fill(tmp, false);
		for(boolean[] tmp:columnflg)
			Arrays.fill(tmp, false);
		for(boolean[]tmp:blockflg)
				Arrays.fill(tmp, false);
	}

	public boolean Allcheck(Su su) {
		for(int i=0;i<9;i++)
			for(int j=0;j<9;j++)
				if(su.get(i, j)==0)return false;
		return true;
	}

	
	//行番を取得
	public int getRow(int blocknum,int blockRow) {
		int plusRow=0;
		if(3<=blocknum&&blocknum<6) plusRow=3;
		else if(6<=blocknum&&blocknum<9) plusRow=6;
		return blockRow+plusRow;
	}
	
	//列番を取得
	public int getColumn(int blocknum,int blockColumn) {
		int plusColumn=blocknum%3;
		return blockColumn+plusColumn*3;
	}
	//ブロック列番を取得
	public int getBlockRow(int row) {
		return row%3;
	}
	//ブロック行番を取得
	public  int getBlockColumn(int column) {
		return column%3;
	}
	//ブロッック番号を取得
	public  int getBlockNum(int row,int column) {
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

	
}
