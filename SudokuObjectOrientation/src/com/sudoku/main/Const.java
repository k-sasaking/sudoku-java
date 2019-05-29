package com.sudoku.main;

public class Const {

	public static String INPUT = "Please input numbers:";
	public static String DO_SOLVE = "Which solve do you choose?";
	public static String STRATEGY_MENU = "0: All candidate, 1: laser beam, 99: auto";
	public static String DO_MODE = "Which mode do you run?";
	public static String MODE_MENU = "0: auto, 1: debug";
	public static String INPUT_ERROR = ">>>>>>>>>Input error";
	public static String RESULT = "############ RESULT ################";
	public static String SUCCESS = "############ SUCCESS ################";
	public static int NUM_MAX = 9;
	public static int BLOCK_NUM_MAX = 3;
	
	public enum RunMode{
		DEBUG,
		AUTO
	}

	public enum CheckType{
		STRATEGY,
		MODE
	}

}
