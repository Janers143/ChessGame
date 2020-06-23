package com.chess.engine.board;

import java.util.List;
import java.util.Map;

import com.chess.engine.Alliance;
import com.chess.engine.pieces.Piece;
import com.google.common.collect.ImmutableList;

public class Board {
	
	private final List<Tile> gameBoard;

	private Board(Builder builder) {
		this.gameBoard = createGameBoard(builder);
	}

	private List<Tile> createGameBoard(Builder builder) {
		final Tile[] tiles = new Tile[BoardUtils.NUM_TILES];
		for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
			tiles[i] = Tile.createTile(i, builder.boardConfig.get(i));
		}
		return ImmutableList.copyOf(tiles);
	}
	
	public static Board createStandardBoard() {
		return null;
	}

	public Tile getTile(final int tileCoord) {
		return null;
	}

	/**
	 * Describes a board builder
	 * @author antsalin
	 *
	 */
	public static class Builder{
		
		/**
		 * Associates each piece to a tile id
		 */
		Map<Integer, Piece> boardConfig;
		
		/**
		 * Next player to move
		 */
		Alliance nextMoveMaker;
		
		/**
		 * Basic constructor
		 */
		public Builder() {}
		
		/**
		 * Puts a piece at its position in the board
		 * @param piece The piece we want to place on the board
		 * @return The new board builder
		 */
		public Builder setPiece(final Piece piece) {
			this.boardConfig.put(piece.getPiecePosition(), piece);
			return this;
		}
		
		/**
		 * Sets the next player to move
		 * @param nextPlayer The next player to move (white or black)
		 * @return The new board builder
		 */
		public Builder setMoveMaker(final Alliance nextPlayer) {
			this.nextMoveMaker = nextPlayer;
			return this;
		}
		
		/**
		 * Builds a new board
		 * @return The new board
		 */
		public Board build() {
			return new Board(this);
		}
	}
}
