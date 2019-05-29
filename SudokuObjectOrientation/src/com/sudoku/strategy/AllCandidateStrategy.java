package com.sudoku.strategy;

import com.sudoku.main.Const;
import com.sudoku.model.Grid;

public class AllCandidateStrategy implements Strategy {

	private boolean isFinish;

	@Override
	public boolean isFinish() {
		return isFinish;
	}

	@Override
	public Grid doStrategy(Grid grid) {
		isFinish = false;

		for (int i = 0; i < Const.NUM_MAX; i++) {
			for (int j = 0; j < Const.NUM_MAX; j++) {
				if (grid.getCell(i, j).getPredictCnt() == 1) {
					int number = grid.getCell(i, j).pollPredictNumber();
					grid.setNumberResult(i, j, number);
					isFinish = true;
				}
			}
		}

		return grid;
	}

}
