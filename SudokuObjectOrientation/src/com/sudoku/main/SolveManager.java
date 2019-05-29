package com.sudoku.main;

import java.util.Scanner;

import com.sudoku.model.Grid;
import com.sudoku.strategy.AllCandidateStrategy;
import com.sudoku.strategy.Strategy;

public class SolveManager {

	private Grid grid;

	public void setGrid(int[][] input) {

		grid = new Grid(input);

	}

	public void solve(boolean auto) {

		while (true) {

			// input user command
			int command = inputCommand();

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

	}

	public void printFinish() {
		System.out.println(Const.SUCCESS);
	}

	public void printError() {
		System.out.println(Const.INPUT_ERROR);
		System.out.println();
	}

	public void printMenu() {
		System.out.println(Const.DO_SOLVE);
		System.out.println(Const.MENU);
	}

	private int inputCommand() {

		int result = -1;
		while (true) {
			// print discribe
			printMenu();

			result = checkCommand();
			if (result != -1)
				break;

			printError();
		}

		return result;
	}

	private int checkCommand() {
		Scanner sc = new Scanner(System.in);
		String command = sc.nextLine();
		int result = -1;
		try {
			result = Integer.parseInt(command);
		} catch (NumberFormatException e) {
			return -1;
		}

		if (result == 99 || (result >= 0 && result <= 2)) {
			return result;
		}

		return -1;
	}
}
