package com.sudoku.model;

import java.util.HashSet;

public class Cell {

	private boolean isRock;
	private int number;
	private HashSet<Integer> predictNumbers;

	Cell() {
		predictNumbers = new HashSet<Integer>();
	}

	public boolean isEmpty() {
		return number == 0;
	}

	public boolean isRock() {
		return isRock;
	}

	public void doRock() {
		isRock = true;
	}

	public void removeRock() {
		isRock = false;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {

		if(number == 0)
			return;

		if (!isRock)
			this.number = number;
	}

	public void setAllPredictNumbers() {
		for (int i = 1; i <= 9; i++) {
			addPredictNumber(i);
		}
	}

	public int getPredictCnt() {
		return predictNumbers.size();
	}

	public boolean addPredictNumber(int number) {

		if (predictNumbers.contains(number))
			return false;

		predictNumbers.add(number);

		return true;
	}

	public boolean removePredictNumer(int number) {
		if (!predictNumbers.contains(number))
			return false;

		predictNumbers.remove(number);

		return true;
	}

	public boolean hasPredictNumber(int number) {
		return predictNumbers.contains(number);
	}

	public Cell deepCopy(Cell temp) {
		this.isRock = temp.isRock;
		this.number = temp.number;
		temp.predictNumbers.forEach(x->
			this.predictNumbers.add(x)
		);
		return this;
	}


}
