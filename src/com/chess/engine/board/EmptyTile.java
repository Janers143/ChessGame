package com.chess.engine.board;

import com.chess.engine.pieces.*;

/**
 * Class used to describe an empty tile
 * @author antho
 *
 */
public final class EmptyTile extends Tile {

	/**
	 * Constructor
	 * @param coord The coordinate of the tile
	 */
	protected EmptyTile(final int coord) {
		super(coord);
	}

	@Override
	public boolean isTileOccupied() {
		return false;
	}

	@Override
	public Piece getPiece() {
		return null;
	}

}
