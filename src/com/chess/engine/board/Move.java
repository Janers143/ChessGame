package com.chess.engine.board;

import com.chess.engine.board.Board.Builder;
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
	final Piece movedPiece;
	
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
		this.movedPiece = movedPiece;
		this.board = board;
	}
	
	/**
	 * Gets the destination tile of the current move
	 * @return The destination tile of the current move
	 */
	public Integer getDestinationCoordinate() {
		return this.destinationCoord;
	}
	
	/**
	 * Gets the piece that is being moved
	 * @return The piece to move
	 */
	public Piece getMovedPiece() {
		return this.movedPiece;
	}

	/**
	 * Executes the move : creates a new board in which the move is done
	 * @return A new board in which the move has been made
	 */
	public abstract Board execute();
	
	/**
	 * Class used to describe a major move
	 * A major move is 
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

		@Override
		public Board execute() {
			final Builder builder = new Builder();
			//TODO : create a hashcode and equals methods for the Piece class
			// Setting up all the current player's pieces except the one he has moved
			for (final Piece piece : this.board.currentPlayer().getActivePieces()) {
				if (!this.movedPiece.equals(piece)) {
					builder.setPiece(piece);
				}
			}
			
			// Setting up all the opponent player's pieces
			for (final Piece piece : this.board.currentPlayer().getOpponent().getActivePieces()) {
				builder.setPiece(piece);
			}
			
			// Setting up the piece moved by the current player and changing the current player
			builder.setPiece(this.movedPiece.movePiece(this));
			builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());
			
			return builder.build();
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

		@Override
		public Board execute() {
			return null;
		}
		
	}
	
}
