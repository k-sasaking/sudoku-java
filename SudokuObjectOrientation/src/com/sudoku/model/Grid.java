package com.sudoku.model;

import com.sudoku.main.Const;

public class Grid {

	private Cell[][] self;

	public Grid() {

		init();
	}

	public Grid(int[][] initGrid) {

		init();
		for (int i = 0; i < Const.NUM_MAX; i++) {
			for (int j = 0; j < Const.NUM_MAX; j++) {
				setNumberResult(i, j, initGrid[i][j]);
			}
		}
	}

	public void init() {

		self = new Cell[Const.NUM_MAX][Const.NUM_MAX];
		for (int i = 0; i < Const.NUM_MAX; i++) {
			for (int j = 0; j < Const.NUM_MAX; j++) {
				self[i][j] = new Cell();
				self[i][j].init();
			}
		}
	}

	/*
	 * getter
	 */
	public Cell getCell(int row, int col) {
		return self[row][col];
	}

	public Cell[] getRow(int row) {

		Cell[] result = new Cell[Const.NUM_MAX];
		for (int i = 0; i < Const.NUM_MAX; i++) {
			result[i] = self[row][i];
		}

		return result;
	}

	public Cell[] getCol(int col) {

		Cell[] result = new Cell[Const.NUM_MAX];
		for (int i = 0; i < Const.NUM_MAX; i++) {
			result[i] = self[i][col];
		}

		return result;
	}

	public Cell[][] getBlock(int block) {

		Cell[][] result = new Cell[Const.BLOCK_NUM_MAX][Const.BLOCK_NUM_MAX];
		for (int i = 0; i < Const.BLOCK_NUM_MAX; i++) {
			for (int j = 0; j < Const.BLOCK_NUM_MAX; j++) {
				result[i][j] = getBlockCell(block, i, j);
			}
		}

		return result;
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
		case 1:
		case 2:
			row = blockRow;
			break;
		case 3:
		case 4:
		case 5:
			row = blockRow + Const.BLOCK_NUM_MAX;
			break;
		case 6:
		case 7:
		case 8:
			row = blockRow + Const.BLOCK_NUM_MAX * 2;
			break;
		}
		return row;

	}

	public int getToColNumberFromBlock(int block, int blockCol) {

		int col = -1;
		switch (block) {
		case 0:
		case 3:
		case 6:
			col = blockCol;
			break;
		case 1:
		case 4:
		case 7:
			col = blockCol + Const.BLOCK_NUM_MAX;
			break;
		case 2:
		case 5:
		case 8:
			col = blockCol + Const.BLOCK_NUM_MAX * 2;
			break;
		}
		return col;
	}

	/*
	 * setter
	 */
	public boolean setNumberResult(int row, int col, int number) {

		if (self[row][col].isRock() || number == 0)
			return false;

		System.out.print("==set== row: " + row + ",col: " + col + ",num: " + number);
		self[row][col].setNumber(number);
		removePredict(row, col, number);
		this.printPredict();
		return true;
	}

	public boolean setNumberBlockResult(int block, int blockRow, int blockCol, int number) {

		return setNumberResult(getToRowNumberFromBlock(block, blockRow), getToColNumberFromBlock(block, blockCol), number);
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

		for (int i = 0; i < Const.NUM_MAX; i++) {
			getRow(row)[i].removePredictNumer(number);
		}

	}

	public void removePredictCol(int col, int number) {

		if (number == 0)
			return;

		for (int i = 0; i < Const.NUM_MAX; i++) {
			getCol(col)[i].removePredictNumer(number);
		}
	}

	public void removePredictBlock(int block, int number) {

		if (number == 0)
			return;

		for (int i = 0; i < Const.BLOCK_NUM_MAX; i++) {
			for (int j = 0; j < Const.BLOCK_NUM_MAX; j++) {
				getBlockCell(block, i, j).removePredictNumer(number);
			}
		}
	}

	/*
	 * check logic
	 */

	public boolean isAllRock() {
		boolean result = true;
		for (int i = 0; i < Const.NUM_MAX; i++) {
			for (int j = 0; j < Const.NUM_MAX; j++) {
				if (!self[i][j].isRock())
					result = false;
			}
		}
		return result;
	}

	public boolean isRowRock(int row) {
		boolean result = true;
		for (int i = 0; i < Const.NUM_MAX; i++) {
			if (!getRow(row)[i].isRock())
				result = false;

		}
		return result;
	}

	public boolean isColRock(int col) {
		boolean result = true;
		for (int i = 0; i < Const.NUM_MAX; i++) {
			if (!getCol(col)[i].isRock())
				result = false;

		}
		return result;
	}

	public boolean isBolckRock(int block) {
		boolean result = true;
		for (int i = 0; i < Const.BLOCK_NUM_MAX; i++) {
			for (int j = 0; j < Const.BLOCK_NUM_MAX; j++) {
				if (!getBlock(block)[i][j].isRock())
					result = false;
			}
		}
		return result;
	}

	public void print() {

		for (int i = 0; i < Const.NUM_MAX; i++) {
			for (int j = 0; j < Const.NUM_MAX; j++) {
				System.out.print(self[i][j].getNumber() + " ");
			}
			System.out.println();
		}
	}

	public void printPredict() {
		System.out.println();

		for (int i = 0; i < Const.NUM_MAX; i++) {
			for (int j = 0; j < Const.NUM_MAX; j++) {
				System.out.print("{");
				getCell(i, j).getPredict().forEach(x -> System.out.print(x + " "));
				System.out.print("} ");
			}
			System.out.println();
		}
	}

	/*
	 * deep copy
	 */
	public void deepCopy(Grid temp) {

		for (int i = 0; i < Const.NUM_MAX; i++) {
			for (int j = 0; j < Const.NUM_MAX; j++) {
				self[i][j] = new Cell();
				self[i][j].deepCopy(temp.self[i][j]);

			}
		}
	}
}
