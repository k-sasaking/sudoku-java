package com.sudoku.strategy;

import com.sudoku.model.Grid;

public interface Strategy {

	boolean isFinish();

	Grid doStrategy(Grid grid);


}
