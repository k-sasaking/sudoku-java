package com.sudoku.model;

public class Grid {

	private static int NUM_MAX = 9;
	private static int BLOCK_NUM_MAX = 3;

	private Cell[][] self;

	public Grid() {

		init();
	}

	public Grid(int[][] initGrid) {

		init();
		for (int i = 0; i < NUM_MAX; i++) {
			for (int j = 0; j < NUM_MAX; j++) {
				self[i][j] = new Cell();
				self[i][j].setNumber(initGrid[i][j]);
			}
		}
	}

	public void init() {

		self = new Cell[NUM_MAX][NUM_MAX];
		for (int i = 0; i < NUM_MAX; i++) {
			for (int j = 0; j < NUM_MAX; j++) {
				self[i][j] = new Cell();
			}
		}
	}

	/*
	 * getter
	 */
	public Cell[] getRow(int row) {

		Cell[] result = new Cell[NUM_MAX];
		for (int i = 0; i < NUM_MAX; i++) {
			result[i] = self[row][i];
		}

		return result;
	}

	public Cell[] getCol(int col) {

		Cell[] result = new Cell[NUM_MAX];
		for (int i = 0; i < NUM_MAX; i++) {
			result[i] = self[i][col];
		}

		return result;
	}

	public Cell[][] getBlock(int block) {

		Cell[][] result = new Cell[BLOCK_NUM_MAX][BLOCK_NUM_MAX];
		for (int i = 0; i < BLOCK_NUM_MAX; i++) {
			for (int j = 0; j < BLOCK_NUM_MAX; j++) {
				result[i][j] = getBlockCell(block, i, j);
			}
		}

		return result;
	}

	public Cell getRowCell(int row, int index) {
		return getRow(row)[index];

	}

	public Cell getColCell(int col, int index) {
		return getCol(col)[index];

	}

	public Cell getBlockCell(int block, int blockRow, int blockCol) {

		int row = getToRowNumberFromBlock(block, blockRow);
		int col = getToColNumberFromBlock(block, blockCol);

		return self[row][col];
	}

	public int getToBlockNumberFromRowCol(int row, int col) {
		if (row < 3) {
			if (col < 3)
				return 0;
			else if (col < 6)
				return 1;
			else
				return 2;
		} else if (row < 6) {
			if (col < 3)
				return 3;
			else if (col < 6)
				return 4;
			else
				return 5;
		} else {
			if (col < 3)
				return 6;
			else if (col < 6)
				return 7;
			else
				return 8;
		}
	}

	public int getToRowNumberFromBlock(int block, int blockRow) {

		int row = -1;
		switch (block) {
		case 0:
		case 3:
		case 6:
			row = blockRow;
			break;
		case 1:
		case 4:
		case 7:
			row = blockRow + BLOCK_NUM_MAX;
			break;
		case 2:
		case 5:
		case 8:
			row = blockRow + BLOCK_NUM_MAX * 2;
			break;
		}
		return row;

	}

	public int getToColNumberFromBlock(int block, int blockCol) {

		int col = -1;
		switch (block) {
		case 0:
		case 1:
		case 2:
			col = blockCol;
			break;
		case 3:
		case 4:
		case 5:
			col = blockCol + BLOCK_NUM_MAX;
			break;
		case 6:
		case 7:
		case 8:
			col = blockCol + BLOCK_NUM_MAX * 2;
			break;
		}
		return col;
	}

	/*
	 * setter
	 */
	public boolean setNumber(int row, int col, int number) {

		if (self[row][col].isRock())
			return false;

		self[row][col].setNumber(number);
		removePredict(row, col, number);

		return true;
	}

	public boolean setNumberBlock(int block, int blockRow, int blockCol, int number) {

		return setNumber(getToRowNumberFromBlock(block, blockRow), getToColNumberFromBlock(block, blockCol), number);
	}

	/*
	 * remove predict
	 */
	public void removePredict(int row, int col, int number) {

		removePredictRow(row, number);
		removePredictCol(col, number);
		removePredictBlock(getToBlockNumberFromRowCol(row, col), number);

	}

	public void removePredictRow(int row, int number) {

		if (number == 0)
			return;

		for (int i = 0; i < NUM_MAX; i++) {
			getRow(row)[i].removePredictNumer(number);
		}

	}

	public void removePredictCol(int col, int number) {

		if (number == 0)
			return;

		for (int i = 0; i < NUM_MAX; i++) {
			getCol(col)[i].removePredictNumer(number);
		}
	}

	public void removePredictBlock(int block, int number) {

		if (number == 0)
			return;

		for (int i = 0; i < BLOCK_NUM_MAX; i++) {
			for (int j = 0; j < BLOCK_NUM_MAX; j++) {
				getBlock(block)[i][j].removePredictNumer(number);
			}
		}
	}

	/*
	 * check logic
	 */

	public boolean isAllRock() {
		boolean result = true;
		for (int i = 0; i < NUM_MAX; i++) {
			for (int j = 0; j < NUM_MAX; j++) {
				if (!self[i][j].isRock())
					result = false;
			}
		}
		return result;
	}

	public boolean isRowRock(int row) {
		boolean result = true;
		for (int i = 0; i < NUM_MAX; i++) {
			if (!getRow(row)[i].isRock())
				result = false;

		}
		return result;
	}

	public boolean isColRock(int col) {
		boolean result = true;
		for (int i = 0; i < NUM_MAX; i++) {
			if (!getCol(col)[i].isRock())
				result = false;

		}
		return result;
	}

	public boolean isBolckRock(int block) {
		boolean result = true;
		for (int i = 0; i < BLOCK_NUM_MAX; i++) {
			for (int j = 0; j < BLOCK_NUM_MAX; j++) {
				if (!getBlock(block)[i][j].isRock())
					result = false;
			}
		}
		return result;
	}

	public int containRow(int row, int number) {
		int result = -1;

		return result;

	}

	public int containCol(int col, int number) {
		int result = -1;

		return result;

	}

	public int containBlock(int block, int number) {
		int result = -1;

		return result;

	}


	public void print() {

		for (int i = 0; i < NUM_MAX; i++) {
			for (int j = 0; j < NUM_MAX; j++) {
				System.out.print(self[i][j].getNumber()+" ");
			}
			System.out.println();
		}
	}


	/*
	 * deep copy
	 */
	public void deepCopy(Grid temp) {

		for (int i = 0; i < NUM_MAX; i++) {
			for (int j = 0; j < NUM_MAX; j++) {
				self[i][j] = new Cell();
				self[i][j].deepCopy(temp.self[i][j]);

			}
		}
	}
}
