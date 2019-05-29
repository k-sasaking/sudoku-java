package com.sudoku.main;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		System.out.println(Const.INPUT);
		Scanner sc = new Scanner(System.in);

		int[][] input = new int[9][9];

		/*数字を読み込む　それぞれのflgを立てる*/
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				int num = sc.nextInt();
				input[i][j] = num;
			}
		}

		// 問題を設定
		SolveManager sm = new SolveManager();
		sm.setGrid(input);
		sm.setMode();
		
		// 解決
		sm.solve(false);

	}

}
