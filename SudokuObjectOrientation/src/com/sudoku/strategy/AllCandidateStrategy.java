package com.sudoku.strategy;

import com.sudoku.model.Grid;

public class AllCandidateStrategy implements Strategy{


	@Override
	public boolean isFinish() {
		return false;
	}

	@Override
	public Grid doStrategy(Grid grid) {
		return grid;
	}

}
