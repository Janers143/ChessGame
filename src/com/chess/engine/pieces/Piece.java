package com.chess.engine.pieces;

import java.util.Collection;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

/**
 * Class used to describe on of the chess pieces
 * @author antho
 *
 */
public abstract class Piece {

	/** The piece has a position (tile) on the board */
	protected final int piecePosition;
	
	/** The piece has an alliance : it can be black or white */
	protected final Alliance pieceAlliance;
	
	/**	Boolean value telling whether the piece has already moved or not */
	protected final Boolean isFirstMove;
	
	/**
	 * Constructor
	 * @param piecePos The position of the piece in the board (a Tile)
	 * @param pieceAll The alliance of the piece : black or white
	 */
	Piece(final int piecePos, final Alliance pieceAll) {
		this.pieceAlliance = pieceAll;
		this.piecePosition = piecePos;
		//TODO More work here...
		this.isFirstMove = false;
	}
	
	/**
	 * Function that gets all the legal moves the piece can currently make
	 * @param board The board in which the piece is
	 * @return A list containing all the legal moves the piece can make
	 */
	public abstract Collection<Move> calculateLegalMoves(final Board board);

	/**
	 * Function that returns the piece alliance
	 * @return The piece alliance
	 */
	public Alliance getAlliance() {
		return this.pieceAlliance;
	}
	
	/**
	 * Function that tells if the piece has already moved or not
	 * @return A boolean telling whether the piece has already moved or not
	 */
	public Boolean isFirstMove() {
		return this.isFirstMove;
	}

	/**
	 * Gets the piece position
	 * @return The piece position
	 */
	public Integer getPiecePosition() {
		return this.piecePosition;
	}
	
	/**
	 * Enum type to describe different pieces
	 * @author antsalin
	 *
	 */
	public enum PieceType {
		
		PAWN("P"),
		KNIGHT("N"),
		BISHOP("B"),
		ROOK("R"),
		QUEEN("Q"),
		KING("K");
		
		/**
		 * The type of piece: bishop, rook, ...
		 */
		private String pieceName;
		
		/**
		 * Constructor
		 * @param pieceName The type of piece
		 */
		PieceType(String pieceName) {
			this.pieceName = pieceName;
		}

		@Override
		public String toString() {
			return this.pieceName;
		}
		
	}
}
