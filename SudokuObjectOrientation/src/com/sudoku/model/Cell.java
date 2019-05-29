package com.sudoku.model;

import java.util.HashSet;

public class Cell {

	private boolean isRock;
	private int number;
	private HashSet<Integer> predictNumbers;

	Cell() {
		predictNumbers = new HashSet<Integer>();
	}
	
	protected void init() {
		this.setAllPredictNumbers();
		number = 0;
	}


	protected boolean isEmpty() {
		return number == 0;
	}

	protected boolean isRock() {
		return isRock;
	}

	protected void doRock() {
		isRock = true;
	}

	protected void removeRock() {
		isRock = false;
	}

	protected int getNumber() {
		return number;
	}

	protected void setNumber(int number) {

		if (number == 0)
			return;

		if (!isRock) {
			this.number = number;
			predictNumbers = new HashSet<Integer>();
			isRock = true;
		}
	}

	protected void setAllPredictNumbers() {
		for (int i = 1; i <= 9; i++) {
			addPredictNumber(i);
		}
	}

	protected HashSet<Integer> getPredict() {
		return predictNumbers;
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

	protected boolean hasPredictNumber(int number) {
		return predictNumbers.contains(number);
	}

	public Cell deepCopy(Cell temp) {
		this.isRock = temp.isRock;
		this.number = temp.number;
		temp.predictNumbers.forEach(x -> this.predictNumbers.add(x));
		return this;
	}

	public int pollPredictNumber() {

		for (int number : this.predictNumbers) {
			this.predictNumbers.remove(number);
			return number;
		}
		return -1;
	}

}
