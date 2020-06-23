package com.chess.engine.board;

/**
 * Class used to describe some utils for the board
 * @author antho
 */
public class BoardUtils {

	/** Array that tells you if the position is in the first column */
	public static final boolean[] FIRST_COLUMN = initColumn(0);
	
	/** Array that tells you if the position is in the second column */
	public static final boolean[] SECOND_COLUMN = initColumn(1);

	/** Array that tells you if the position is in the seventh column */
	public static final boolean[] SEVENTH_COLUMN = initColumn(6);

	/** Array that tells you if the position is in the seventh column */
	public static final boolean[] EIGHTH_COLUMN = initColumn(7);
	
	/** Array that tells you if the position is in the second row */
	public static final boolean[] SECOND_ROW = null;
	
	/** Array that tells you if the position is in the seventh row */
	public static final boolean[] SEVENTH_ROW = null;
	
	/** Number of tiles on the board */
	public static final int NUM_TILES = 64;
	
	/** Number of tiles per row */
	public static final int NUM_TILES_PER_ROW = 8;

	/**
	 * Useless constructor
	 */
	private BoardUtils() {
		throw new RuntimeException("You can't instanciate this class");
	}

	/**
	 * Function used to generate the array that tells you 
	 * whether the coordinate is in the n column
	 * @param columnNb The number of the column
	 * @return An array with the tiles that are in the columnNb column
	 */
	private static boolean[] initColumn(int columnNb) {
		final boolean[] column = new boolean[NUM_TILES];
		
		do {
			column[columnNb] = true;
			columnNb += NUM_TILES_PER_ROW;
		} while (columnNb < NUM_TILES);
		
		return column;
	}

	/**
	 * Function that tells if a destination coordinate is valid
	 * (if it doesn't go out of bounds)
	 * @param coord The destination coordinate
	 * @return A boolean telling if the destination coordinate is valid
	 */
	public static boolean isValidTileCoordinate(final int coord) {
		boolean isValid = (coord >= 0 && coord < NUM_TILES);
		return isValid;
	}

}
