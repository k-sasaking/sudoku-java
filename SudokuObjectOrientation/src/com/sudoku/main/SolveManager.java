package com.sudoku.main;

import java.util.Scanner;

import com.sudoku.main.Const.CheckType;
import com.sudoku.main.Const.RunMode;
import com.sudoku.model.Grid;
import com.sudoku.strategy.AllCandidateStrategy;
import com.sudoku.strategy.Strategy;

public class SolveManager {

	private Grid grid;
	private RunMode mode;

	public void setGrid(int[][] input) {

		grid = new Grid(input);

	}

	public void setMode() {

		int command = inputSelectModeCommand();
		
	}

	public void solve(boolean auto) {

		while (true) {

			// input user command
			int command = inputSelectSolveCommand();

			// solve
			doSolve(command);
			printGrid();

			// is solved
			if (grid.isAllRock())
				break;
		}

	}

	public void doSolve(int command) {
		Strategy st = new AllCandidateStrategy();
		Solve solve = new Solve(grid, st);
		grid = solve.doAutoSolve();
	}

	public void printGrid() {
		System.out.println(Const.RESULT);
		grid.print();
		System.out.println();
		grid.printPredict();
		System.out.println();

	}

	public void printFinish() {
		System.out.println(Const.SUCCESS);
	}

	public void printError() {
		System.out.println(Const.INPUT_ERROR);
		System.out.println();
	}

	public void printSelectStrategyMenu() {
		System.out.println(Const.DO_SOLVE);
		System.out.println(Const.STRATEGY_MENU);
	}

	public void printSelectModeMenu() {
		System.out.println(Const.DO_MODE);
		System.out.println(Const.MODE_MENU);
	}
	
	private int inputSelectSolveCommand() {

		int result = -1;
		while (true) {
			// print discribe
			printSelectStrategyMenu();

			result = checkCommand(Const.CheckType.STRATEGY);
			if (result != -1)
				break;

			printError();
		}

		return result;
	}
	
	private int inputSelectModeCommand() {

		int result = -1;
		while (true) {
			// print discribe
			printSelectModeMenu();

			result = checkCommand(Const.CheckType.MODE);
			if (result != -1)
				break;

			printError();
		}

		return result;
	}
	
	boolean check(CheckType type, int command){
			
		switch(type) {
			case MODE:
				return (command == 99 || command == 0 || command == 1);
			case STRATEGY:
				return (command == 99 || (command >= 0 && command <= 3));
		}
		return false;
	}


	private int checkCommand(CheckType type) {
		Scanner sc = new Scanner(System.in);
		String command = sc.nextLine();
		int result = -1;
		try {
			result = Integer.parseInt(command);
		} catch (NumberFormatException e) {
			return -1;
		}

		if (check(type, result)) {
			return result;
		}

		return -1;
	}
}
