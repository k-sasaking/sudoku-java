package com.sudoku.main;

import com.sudoku.model.Grid;
import com.sudoku.strategy.Strategy;

public class Solve {

	private Strategy st;
	private Grid grid;

	Solve(Grid grid, Strategy st) {
		this.grid = grid;
		this.st = st;
	}

	public Grid doAutoSolve() {

		grid = st.doStrategy(grid);
		
		return grid;
	}

}
