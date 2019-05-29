package s5;

public class AbleSu extends CommonClass{
	boolean[][][]ablesu;

	/*ableSu 可能性のある数を追加する*/
	public  void addAbleSu(int num,int blocknum ,int brow ,int bcolumn) {
		addAbleSu(num,getRow(blocknum,brow),getColumn(blocknum,bcolumn));
	}
	public  void addAbleSu(int num ,int row,int column ) {
		ablesu[row][column][num-1] = false;
	}
	
	/*全部trueにする*/
	private  void setAllTrueAbleSu(int num,int row,int column ) {
		//セルを全部true
		for(int i=0;i<9;i++)ablesu[row][column][i]=true;
		//列を全部true
		for(int i=0;i<9;i++)ablesu[i][column][num-1]=true;
		//行を全部true
		for(int i=0;i<9;i++)ablesu[row][i][num-1]=true;
		//ブロックを全部true
		int block = getBlockNum(row,column);
		switch(block) {
			case 0:for(int i=0;i<3;i++)for(int j=0;j<3;j++)ablesu[i][j][num-1]=true;break;
			case 1:for(int i=0;i<3;i++)for(int j=3;j<6;j++)ablesu[i][j][num-1]=true;break;
			case 2:for(int i=0;i<3;i++)for(int j=6;j<9;j++)ablesu[i][j][num-1]=true;break;
			case 3:for(int i=3;i<6;i++)for(int j=0;j<3;j++)ablesu[i][j][num-1]=true;break;
			case 4:for(int i=3;i<6;i++)for(int j=3;j<6;j++)ablesu[i][j][num-1]=true;break;
			case 5:for(int i=3;i<6;i++)for(int j=6;j<9;j++)ablesu[i][j][num-1]=true;break;
			case 6:for(int i=6;i<9;i++)for(int j=0;j<3;j++)ablesu[i][j][num-1]=true;break;
			case 7:for(int i=6;i<9;i++)for(int j=3;j<6;j++)ablesu[i][j][num-1]=true;break;
			case 8:for(int i=6;i<9;i++)for(int j=6;j<9;j++)ablesu[i][j][num-1]=true;break;				
		}
		
	} 
}
