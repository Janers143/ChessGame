package com.chess.engine.board;

import com.chess.engine.pieces.*;

/**
 * Class used to describe an occupied tile
 * @author antho
 *
 */
public class OccupiedTile extends Tile {
	
	/**
	 * The piece that is currently in this tile
	 */
	private final Piece pieceOnTile;
	
	/**
	 * Constructor
	 * @param coord The coordinate of the tile
	 * @param p The piece that is occupying the tile
	 */
	protected OccupiedTile(final int coord, final Piece p) {
		super(coord);
		this.pieceOnTile = p;
	}

	@Override
	public boolean isTileOccupied() {
		return false;
	}

	@Override
	public Piece getPiece() {
		return this.pieceOnTile;
	}

	@Override
	public String toString() {
		// If the tile is occupied, the result will depend on 
		// the piece that occupies the tile
		return (getPiece().getAlliance().isBlack() ?
				getPiece().toString().toLowerCase() :
				getPiece().toString());
	}
}
