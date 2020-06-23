package com.chess.engine.board;

import java.util.HashMap;
import java.util.Map;
import com.chess.engine.pieces.*;
import com.google.common.collect.ImmutableMap;

/**
 * Class used to define a chess board tile
 * @author antho
 *
 */
public abstract class Tile {
	
	/** The coordinate of the tile : it goes from 0 to 63 */
	protected final int tileCoordinate;
	
	/** All the empty tiles that will ever exist are described in this map
	 *  so that, if we ever want to use one of them we don't need to create
	 *  them.
	 *  This way we implement immutability.
	 */
	private static final Map<Integer, EmptyTile> EMPTY_TILES_CACHE = createAllPossibleEmotyTiles();
	
	/**
	 * Constructor
	 * @param coord The coordinate of the tile
	 */
	protected Tile(final int coord) {
		this.tileCoordinate = coord;
	}
	
	/**
	 * Function that creates a map containing all the ever existing empty tiles
	 * @return An immutable map containing all the ever existing empty tiles
	 */
	private static Map<Integer, EmptyTile> createAllPossibleEmotyTiles() {
		final Map <Integer, EmptyTile> emptyTileMap = new HashMap<>();
		
		for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
			emptyTileMap.put(i, new EmptyTile(i));
		}
		
		return ImmutableMap.copyOf(emptyTileMap);
	}

	/**
	 * Function that tells you if the tile is occupied
	 * @return A boolean telling whether the tile is occupied or not
	 */
	public abstract boolean isTileOccupied();
	
	/**
	 * Gets the current piece in the tile
	 * @return The current piece in the tile
	 */
	public abstract Piece getPiece();
}
