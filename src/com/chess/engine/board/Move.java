package com.chess.engine.board;

import com.chess.engine.pieces.Piece;

/**
 * Class used to represent a chess move
 * @author antho
 */
public abstract class Move {
	
	/**
	 * The board in which the move is done
	 */
	final Board board;
	
	/**
	 * The piece that was moved
	 */
	final Piece piece;
	
	/**
	 * The destination coordinate
	 */
	final int destinationCoord;

	/**
	 * Constructor
	 * @param board The board in which the move is done
	 * @param movedPiece The piece that was moved
	 * @param destinationaCoord The destination coordinate
	 */
	public Move(final Board board, final Piece movedPiece, final int destinationaCoord) {
		this.destinationCoord = destinationaCoord;
		this.piece = movedPiece;
		this.board = board;
	}
	
	
	/**
	 * Class used to describe a major move
	 * @author antho
	 */
	public static final class MajorMove extends Move{
		
		/**
		 * Constructor
		 * @param board The board in which the move is done
		 * @param movedPiece The piece that was moved
		 * @param destinationaCoord The destination coordinate
		 */
		public MajorMove(final Board board, final Piece movedPiece, final int destinationaCoord) {
			super(board, movedPiece, destinationaCoord);
		}
		
	}
	
	/**
	 * Class used to describe an attacking (capture) move
	 * @author antho
	 */
	public static final class AttackMove extends Move{
		
		/**
		 * The piece that is being attacked
		 */
		final Piece attackedPiece;
		
		/**
		 * Constructor
		 * @param board The board in which the move is done
		 * @param movedPiece The piece that was moved
		 * @param destinationCoord The destination coordinate
		 * @param attackedPiece The piece that is being attacked
		 */
		public AttackMove(final Board board, final Piece movedPiece, final int destinationCoord, final Piece attackedPiece) {
			super(board, movedPiece, destinationCoord);
			this.attackedPiece = attackedPiece;
		}
		
	}
	
}
