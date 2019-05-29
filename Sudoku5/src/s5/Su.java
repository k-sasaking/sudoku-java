package s5;

public class Su extends CommonClass{

	private int[][] su;
	private int[][][] block;

	//コンストラクタ
	Su(){
		su = new int[9][9];
		block = new int[9][3][3];
	}
	
	
	public int get(int row ,int column) {
		return su[row][column];
	}
	public int get(int blocknum,int blockrow ,int blockcolumn) {
		return block[blocknum][blockrow][blockcolumn];
	}
	
	public void set(int row,int column ,int num) {
		su[row][column] = num;
		syncBlockSu(row,column);
	}
	public void set(int blocknum ,int blockrow,int blockcolumn ,int num) {
		block[blocknum][blockrow][blockcolumn] = num;
		syncSu(blocknum,blockrow,blockcolumn);
	}

	 void syncSu(int blocknum,int blockrow, int blockcolumn) {
		su[getRow(blocknum,blockrow)][getColumn(blocknum,blockcolumn)]=block[blocknum][blockrow][blockcolumn];
	}
	 void syncBlockSu(int row, int column) {
		block[getBlockNum(row,column)][getBlockRow(row)][getBlockColumn(column)]=su[row][column];
	}
	
	
}
