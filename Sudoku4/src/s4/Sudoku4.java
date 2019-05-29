package s4;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


/*********************************
 * 			隠しレーザー砲
 ********************************/
public class Sudoku4 {

	static int[][]su;
	static int[][]backupsu;
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

	
	/*
	 * サンプルデータ
	 */
	
/*	--input--z

1 5 0 0 0 0 0 0 0
6 0 9 0 0 0 0 0 2
0 0 0 8 1 6 5 0 4
0 0 2 0 0 0 0 0 0
0 0 4 0 3 0 7 0 0
0 0 0 0 0 7 0 1 5
0 0 0 4 0 0 0 0 0
0 8 0 2 0 9 0 0 0
0 0 0 0 0 3 0 0 0


//蔵レーザーほう
1 0 8 0 0 0 0 2 0
0 0 2 8 0 0 7 6 0
0 0 0 0 7 0 0 5 0
4 0 0 0 0 1 0 0 3
0 0 0 0 9 0 0 0 0 
0 0 6 0 0 0 0 0 0
0 0 0 6 0 0 0 7 0
0 0 4 3 0 0 0 0 0 
0 0 1 2 0 8 0 0 0



//超難問ノルウェー
8 0 0 0 0 0 0 0 0
0 0 3 6 0 0 0 0 0
0 7 0 0 9 0 2 0 0 
0 5 0 0 0 7 0 0 0
0 0 0 0 4 5 7 0 0
0 0 0 1 0 0 0 3 0
0 0 1 0 0 0 0 6 8
0 0 8 5 0 0 0 1 0
0 9 0 0 0 0 4 0 0

8 0 0 0 0 0 0 0 0
0 0 3 6 0 0 0 0 0
0 7 0 0 9 0 2 0 0 
0 5 0 0 0 7 0 0 0
0 0 0 0 4 5 7 0 0
0 0 0 1 0 0 0 3 0
0 0 1 0 0 0 3 6 8
0 0 8 5 0 0 0 1 0
0 9 0 0 0 0 4 0 0


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
		ablesu = new boolean[9][9][9];
		checkablesu = new boolean[9][9];

		
		//初期化
		for(boolean[] tmp:rowflg) 
			Arrays.fill(tmp, false);
		for(boolean[] tmp:columnflg)
			Arrays.fill(tmp, false);
		for(boolean[]tmp:blockflg)
				Arrays.fill(tmp, false);
		for(boolean[][] abs:ablesu)
			for(boolean[]ab :abs)
				Arrays.fill(ab, true);
		for(boolean[]cab :checkablesu)
			Arrays.fill(cab, true);
		
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
		
		/*
		 * 問題を解く
		 */
		while(!clearCheck()) {
			setNumflg=false;
			blockLaserSolve(blockflg,rowflg,columnflg);
			blockLaserSolve(blockflg,rowflg,columnflg);
			blockLaserSolve(blockflg,rowflg,columnflg);
			if(allCandidateCheck())setNumflg=true;
			if(allCandidateCheck())setNumflg=true;
			if(allCandidateCheck())setNumflg=true;
			if(allCandidateCheck())setNumflg=true;
			allSearchSolve();
			allSearchSolve();
			if(!setNumflg) {
				break;
			}
		}
		
		if(Allcheck()) {
			System.out.println("#####RESULT#####");
			//結果出力
			for(int[] rsu:su) {
				for(int value:rsu) {
					System.out.print(value+" ");
				}
				System.out.println();
			}
		}else {
			printAbleSu();
		}
			
		

	}
	
	
	
	/***************************************
	 * 		解決ロジック１(入門編)
	 ****************************************/
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
						setNum(num+1,i,j);
						for(int k=0;k<9;k++) {
						}
					}
				}
			}
		}
	}
	
	/***************************************
	 * 		解決ロジック2(初級編)
	 ****************************************/
	private static void blockLaserSolve(boolean blockflg[][],boolean rowflg[][] ,boolean columnflg[][]) {
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
					ArrayList<Integer> tmprow = new ArrayList<Integer>();//falseの値を格納する
					ArrayList<Integer> tmpcolumn = new ArrayList<Integer>();//falseの値を格納する
					for(int brow=0;brow<3;brow++) {
						for(int bcolumn=0;bcolumn<3;bcolumn++) {
							if(!check[brow][bcolumn]) {
								count++;
								tmprow.add(brow);
								tmpcolumn.add(bcolumn);
							}
						}
					}
					if(count==1) {
						setNum(k+1,b,tmprow.get(0),tmpcolumn.get(0));
					}
					else {
						if(tmprow.size()==1||tmpcolumn.size()==1) {
							if(tmprow.size()==1) {
								for(int c=0;c<tmpcolumn.size();c++) {
									tmpcolumn.get(c);
								}
							}
							if(tmpcolumn.size()==1) {
								for(int r=0;r<tmprow.size();r++) {
									tmprow.get(r);
								}
							}
						}else {
							for(int c=0;c<tmpcolumn.size();c++) {
								for(int r=0;r<tmprow.size();r++) {
									if(su[getRow(b,tmprow.get(r))][getColumn(b,tmpcolumn.get(c))]==0)
									addAbleSu(k+1,b,tmprow.get(r),tmpcolumn.get(c));
								}
							}							
							
						}
						
					}
				}
			}
		}
	}
	/***************************************
	 * 解決ロジック（中級編）
	 * 可能性のある数を全て出し、比較して行く。
	 ****************************************/
	public static boolean  allCandidateCheck() {
		return (
		  allCandidateCheckRow()//値が1意にきまる
		||allCandidateCheckColumn()//予約マスの確保
		||
		allCandidateCheckBlock()//予約マスの確保
		);
	}
	public static boolean allCandidateCheckRow() {
		boolean changedFlg =false;
		for(int r=0;r<9;r++) {
			changedFlg =allCandidateCheck1(ROWTYPE,r);
			changedFlg = allCandidateCheck2(ROWTYPE,r); 
		}
		return changedFlg;
	}
	public static boolean allCandidateCheckColumn() {
		boolean changedFlg =false;
		for(int c=0;c<9;c++) {
			changedFlg =allCandidateCheck1(COLUMNTYPE,c);
			changedFlg = allCandidateCheck2(COLUMNTYPE,c); 
		}		
		return changedFlg;
	}
	//ブロック：一意的に決まるものを探す。
	public static boolean  allCandidateCheckBlock() {
		boolean changedFlg =false;
		boolean henkan[][] = new boolean[9][9];
		for(int b=0;b<9;b++) {
			for(int k =0;k<9;k++) {
				int num =0;
				switch(b) {
				case 0:for(int i=0;i<3;i++)for(int j=0;j<3;j++) 
					{henkan[num][k] = ablesu[i][j][k];num++;}
					break;
				case 1:for(int i=0;i<3;i++)for(int j=3;j<6;j++)if(ablesu[i][j][k])
					{henkan[num][k] = ablesu[i][j][k];num++;}
					break;
				case 2:for(int i=0;i<3;i++)for(int j=6;j<9;j++)if(ablesu[i][j][k])
					{henkan[num][k] = ablesu[i][j][k];num++;}
					break;
				case 3:for(int i=3;i<6;i++)for(int j=0;j<3;j++)if(ablesu[i][j][k])
					{henkan[num][k] = ablesu[i][j][k];num++;}
					break;
				case 4:for(int i=3;i<6;i++)for(int j=3;j<6;j++)if(ablesu[i][j][k])
					{henkan[num][k] = ablesu[i][j][k];num++;}
					break;
				case 5:for(int i=3;i<6;i++)for(int j=6;j<9;j++)if(ablesu[i][j][k])
					{henkan[num][k] = ablesu[i][j][k];num++;}
					break;
				case 6:for(int i=6;i<9;i++)for(int j=0;j<3;j++)if(ablesu[i][j][k])
					{henkan[num][k] = ablesu[i][j][k];num++;}
					break;
				case 7:for(int i=6;i<9;i++)for(int j=3;j<6;j++)if(ablesu[i][j][k])
					{henkan[num][k] = ablesu[i][j][k];num++;}
					break;
				case 8:for(int i=6;i<9;i++)for(int j=6;j<9;j++)if(ablesu[i][j][k])
					{henkan[num][k] = ablesu[i][j][k];num++;}
					break;				
				}
			}
//				IndivisualLayser(b);
			changedFlg =allCandidateCheck1(BLOCKTYPE,b);
			changedFlg = allCandidateCheck2(BLOCKTYPE,b); 
		}
		
		return changedFlg;
	}
	public static boolean typeflg (int typenum,int i,int k) {
		if(type[typenum].equals("row"))return rowflg[i][k];
		else if(type[typenum].equals("column"))return columnflg[i][k];
		else return blockflg[i][k];
	}
	public static void setTypeFlg (int typenum,int i,int k) {
		if(type[typenum].equals("row")) rowflg[i][k] = true;
		else if(type[typenum].equals("column")) columnflg[i][k]=true;
		else blockflg[i][k]=true;
	}
	public static boolean typeAblesu (int typenum,int i ,int j,int k) {
		if(type[typenum].equals("row"))return ablesu[i][j][k];
		else if(type[typenum].equals("column"))return ablesu[j][i][k];
		else return ablesu[getRowHenkan(i,j)][getColumnHenkan(i,j)][k];
	}
	public static boolean typeAblesu (int typenum,int i ,int j,int k,boolean res) {
		if(type[typenum].equals("row")) { ablesu[i][j][k]=res;return res;}
		else if(type[typenum].equals("column")) {ablesu[j][i][k]=res;return res;}
		else {ablesu[getRowHenkan(i,j)][getColumnHenkan(i,j)][k]=res;return res;}
	}
	public static boolean typeSetNum(int typenum,int i ,int j,int k) {
		if(type[typenum].equals("row"))setNum(k+1,i,j);
		else if(type[typenum].equals("column"))setNum(k+1,j,i);
		else setNum(k+1,i,getBlockRowHenkan(j),getBlockColumnHenkan(j));
		return true;
	}
	public static boolean  allCandidateCheck1(int typenum ,int i) {
		int resSubNum = -1;
		boolean setNum = false;
			for(int k=0;k<9;k++) {
				int cnt =0;
				if(!typeflg(typenum,i,k)) {
					for(int j=0;j<9;j++) {						
						if(!typeAblesu(typenum,i,j,k)) {
							resSubNum = j;
							cnt++;
						};
					}
					if(cnt == 1) {
						setNum =typeSetNum(typenum,i,resSubNum,k);
					}
				}
			}
		return setNum;
	}	
	public static boolean  allCandidateCheck2(int typenum ,int i) {
		boolean setFlg = false;
			ArrayList<ArrayList<Integer>>  checklist = new ArrayList<ArrayList<Integer>>(); 
			for(int j=0;j<9;j++) {
				ArrayList<Integer> checkc = new ArrayList<Integer>();
				for(int k=0;k<9;k++) {
						if(!typeAblesu(typenum,i,j,k)) {
							checkc.add(k);
						};
				}
				checklist.add(checkc);
			}
			
			ArrayList<ArrayList<Integer>> setSubNumFlg = new ArrayList<ArrayList<Integer>>();
			ArrayList<boolean[]> subnumlist = new ArrayList<boolean[]>();
			for(int j=0;j<9;j++) {
				int size = checklist.get(j).size();
				boolean[] subnumflg = new boolean[9];
				Arrays.fill(subnumflg, false);
				int samecnt = 1;
				for(int tmp=j+1;tmp<9;tmp++) {
					if(size == checklist.get(tmp).size()) {
						boolean samecheckflg = true;
						// リストの中身が同じかチェック
						for(int k=0;k<size;k++) {
								if(checklist.get(j).get(k) != checklist.get(tmp).get(k)) {
									samecheckflg = false;
									break;
								}
						}
						//リストの中身が同じだったらsamecnt +1
						if(samecheckflg) {
							samecnt++;
							subnumflg[tmp]=true;
						}
					}
				}
				//限定された予約枠がある場合
				if(samecnt == size) {
					setSubNumFlg.add(checklist.get(j));
					subnumflg[j]=true;
					subnumlist.add(subnumflg);
					setFlg=true;
				}
			}			
			//限定された予約枠をrowflgに代入する。
				if(setSubNumFlg.size()>0) {
					for(int listsize =0;listsize<setSubNumFlg.size();listsize++) {
						for(int size=0 ;size<setSubNumFlg.get(listsize).size(); size++) {
							int num = setSubNumFlg.get(listsize).get(size);
							//ableSUのnumがfalseの値で、subnumflgがtrue以外のものは、全てablesuをtrueにする。
							for(int j=0;j<9;j++) {
								if(!subnumlist.get(listsize)[j]) {
									typeAblesu(typenum,i,j,num,true);
								}
							}
						}
					}
				}
							
		return setFlg;
	}
	public static int getRowHenkan(int bnum,int j) {
		switch(bnum) {
		case 0:case 1:case 2:
			switch(j) {
				case 0:case 1:case 2:return 0;
				case 3:case 4:case 5:return 1;
				case 6:case 7:case 8:return 2;
			}break;
		case 3:case 4:case 5:
			switch(j) {
				case 0:case 1:case 2:return 3;
				case 3:case 4:case 5:return 4;
				case 6:case 7:case 8:return 5;
			}break;
		case 6:case 7:case 8:				
			switch(j) {
				case 0:case 1:case 2:return 6;
				case 3:case 4:case 5:return 7;
				case 6:case 7:case 8:return 8;
			}break;
		}
		return -1;
	}
	public static int getColumnHenkan(int bnum,int j) {
		switch(bnum) {
		case 0:case 3:case 6:
			switch(j) {
				case 0:case 3:case 6:return 0;
				case 1:case 4:case 7:return 1;
				case 2:case 5:case 8:return 2;
			}break;
		case 1:case 4:case 7:
			switch(j) {
			case 0:case 3:case 6:return 3;
			case 1:case 4:case 7:return 4;
			case 2:case 5:case 8:return 5;
			}break;
		case 2:case 5:case 8:				
			switch(j) {
			case 0:case 3:case 6:return 6;
			case 1:case 4:case 7:return 7;
			case 2:case 5:case 8:return 8;
			}break;
		}
		return -1;
	}
	public static int getBlockRowHenkan(int j) {
		switch(j){
			case 0:case 1:case 2:return 0;
			case 3:case 4:case 5:return 1;
			case 6:case 7:case 8:return 2;
		}
		return j;
	}
	public static int getBlockColumnHenkan(int j) {
		switch(j){
		case 0:case 3:case 6:return 0;
		case 1:case 4:case 7:return 1;
		case 2:case 5:case 8:return 2;
		}
		return j;
	}
	
	/*****************************
	 * 　　　隠しレーザー砲
	 *******************************/
	public static boolean IndivisualLayser(int blocknum) {
		boolean result = false;
		for(int k=0;k<9;k++) {
			
			int cnt = 0;
			int layserRow = -1;
			int layserColumn = -1;
			boolean[] checklayser = new boolean[9];
			Arrays.fill(checklayser, false);
			for(int j=0;j<9;j++) {
				if(!ablesu[getRowHenkan(blocknum,j)][getColumnHenkan(blocknum,j)][k]) {
					cnt++;
					checklayser[j]=true;
				}
			}
			if(cnt==1);//一意的に決まる。setNumする。
			if(2<=cnt&&cnt<=3) {
				//RowCheck（他の列が全てtrue担っている→その列で隠しレーザー砲が使える。
				//1列目
				if(!checklayser[3]&&!checklayser[4]&&!checklayser[5]
				 &&!checklayser[6]&&!checklayser[7]&&!checklayser[8])
					layserRow = getRowHenkan(blocknum,0);
				//2列目
				if(!checklayser[0]&&!checklayser[1]&&!checklayser[2]
				 &&!checklayser[6]&&!checklayser[7]&&!checklayser[8])
					 layserRow = getRowHenkan(blocknum,4);
				//3列目
				if(!checklayser[0]&&!checklayser[1]&&!checklayser[2]
				 &&!checklayser[3]&&!checklayser[4]&&!checklayser[5])
					 layserRow = getRowHenkan(blocknum,8);

					
				//ColumnCheck（他の行が全てtrue担っている→その列で隠しレーザー砲が使える。
				//1行目
				if(!checklayser[1]&&!checklayser[2]
				 &&!checklayser[4]&&!checklayser[5]
				 &&!checklayser[7]&&!checklayser[8])
					layserColumn = getColumnHenkan(blocknum,0);
				//2行目
				if(!checklayser[0]&&!checklayser[2]
				 &&!checklayser[3]&&!checklayser[5]
				 &&!checklayser[6]&&!checklayser[8])
					layserColumn = getColumnHenkan(blocknum,4);
					
				//3行目
				if(!checklayser[0]&&!checklayser[1]
				 &&!checklayser[3]&&!checklayser[2]
				 &&!checklayser[6]&&!checklayser[7])
					layserColumn = getColumnHenkan(blocknum,8);
			}
			if(layserRow!=-1) {
				result =true;
				for(int c=0;c<9;c++) {
					if(getColumnHenkan(blocknum,0)!=c&&getColumnHenkan(blocknum,4)!=c&&getColumnHenkan(blocknum,8)!=c)
					ablesu[layserRow][c][k]=true;
					
				}
			}
			if(layserColumn!=-1) {
				result =true;
				for(int r=0;r<9;r++) {
					if(getRowHenkan(blocknum,0)!=r&&getRowHenkan(blocknum,4)!=r&&getRowHenkan(blocknum,8)!=r)
					ablesu[r][layserColumn][k]=true;
				}
			}
			
		}
		return result;
	}
	
	/***********************************
	 * 
	 * 		COMMON METHOD
	 * 
	 ************************************/
	
	
	
	/*ableSu 可能性のある数を追加する*/
	public static void addAbleSu(int num,int blocknum ,int brow ,int bcolumn) {
		addAbleSu(num,getRow(blocknum,brow),getColumn(blocknum,bcolumn));
	}
	public static void addAbleSu(int num ,int row,int column ) {
		ablesu[row][column][num-1] = false;
	}
	//１つの配列のableSuのfalseの数を表示する。
	static public int getAbleSuSize(int row ,int column){
		int cnt = 0;
		for(boolean rst :ablesu[row][column])
				if(!rst)cnt++;
		return cnt;
	}
	//１つの配列のableSuがfalseの値を表示(配列番号:0-8）
	static public int[] getAbleSuValue(int row ,int column){
			int N = getAbleSuSize(row ,column);
			int[] cnt = new int[N];
			int i=0,j=0;
			for(boolean rst :ablesu[row][column])
				if(!rst) {cnt[j]=i;j++;}i++;
			return cnt;
	}
	//二つの配列のサイズが同じかどうか
	static public boolean getSameSizeFlg(int row1,int column1 ,int row2,int column2) {
		boolean rst = true;
		if(getAbleSuSize(row1,column1) != getAbleSuSize(row2,column2))rst = false;
		return rst;
	}
	//二つの配列のサイズの数を返す。（同じじゃない場合は、-1)
	static public int getSameSizeSu(int row1,int column1 ,int row2,int column2) {
		if(!getSameSizeFlg(row1,column1,row2,column2))return -1;
		else return getAbleSuSize(row1,column1);
	}
	//二つの配列の値が同じかどうか
	static public boolean getSameValueFlg(int row1,int column1 ,int row2,int column2) {
			boolean rst = true;
			int size = getSameSizeSu(row1,column1,row2,column2);
			if(size ==-1)return false;
			else {
				int[] a = getAbleSuValue(row1,column1);
				int[] b = getAbleSuValue(row2,column2);
				for(int i=0;i<size;i++) if(a[i]!=b[i])rst=false;
				return rst;
			}
	}
	//ブロック列番を取得
	public static int getBlockRow(int row) {
		return row%3;
	}
	//ブロック行番を取得
	public static int getBlockColumn(int column) {
		return column%3;
	}
	//ブロッック番号を取得
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
	//行番を取得
	static int getRow(int blocknum,int blockRow) {
		int plusRow=0;
		if(3<=blocknum&&blocknum<6) plusRow=3;
		else if(6<=blocknum&&blocknum<9) plusRow=6;
		return blockRow+plusRow;
	}
	//列番を取得
	static int getColumn(int blocknum,int blockColumn) {
		int plusColumn=blocknum%3;
		
		return blockColumn+plusColumn*3;
	}
	//値をセットする
	static void setNum(int num,int blocknum,int blockrow , int blockcolumn) {
		setNum(num,getRow(blocknum,blockrow),getColumn(blocknum,blockcolumn));
	}
	//値をセットする
	static void setNum(int num,int row,int column) {
		su[row][column] = num;
		block[getBlockNum(row,column)][getBlockRow(row)][getBlockColumn(column)]=num;
		rowflg[row][num-1]= true;
		columnflg[column][num-1]= true;
		blockflg[getBlockNum(row,column)][num-1]= true;
		setNumflg=true;
		setAllTrueAbleSu(num,row,column);
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
	
	
	public static void setAllTrueAbleSu(int num,int row,int column ) {
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
	
	//00 01 02 
	public static void printAbleSu() {
		System.out.println("#########");
		//結果出力
		for(int[] rsu:su) {
			for(int value:rsu) {
				System.out.print(value+" ");
			}
			System.out.println();
		}	

		for(int i=0;i<9;i++) {
			for(int j=0;j<9;j++) {
				System.out.print("{");
					for(int k =0;k<9;k++) {
						if(!ablesu[i][j][k])System.out.print((k+1)+"");
					}
				System.out.print("}");
				System.out.print(" ");
				if(j==2||j==5)System.out.print("              "); 

			}
			System.out.println();
		}
	}
	
	
	public static boolean Allcheck() {
		for(int i=0;i<9;i++)
			for(int j=0;j<9;j++)
				if(su[i][j]==0)return false;
		
		return true;
	}
	
	
}
