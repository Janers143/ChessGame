package com.chess.engine.player;

import java.util.Collection;
import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Piece;

/**
 * Describes a player
 * @author antsalin
 *
 */
public abstract class Player {
	
	/** The current board of the game */
	protected final Board board;
	
	/** The king of the player */
	protected final King playerKing;
	
	/** A list of all the legal moves the player can make */
	protected final Collection<Move> legalMoves;
	
	/**
	 * Constructor
	 * @param board The current state of the board
	 * @param legalMoves Most legal moves the player can make (excluding en passant, castle,...)
	 * @param opponentLegalMoves Most legal moves the opponent can make (excluding en passant, castle,...)
	 */
	protected Player(Board board, Collection<Move> legalMoves, Collection<Move> opponentLegalMoves) {
		this.board = board;
		this.playerKing = getKing();
		this.legalMoves = legalMoves;
	}

	/**
	 * Gets the king of this player
	 * @return The king of the current player
	 */
	private King getKing() {
		List<Piece> activePieces = (List<Piece>)getActivePieces();
		King king = null;
		for (Piece p : activePieces) {
			if (p.getPieceType().isKing()) {
				king = (King) p;
			}
		}
		if (king == null) {
			throw new RuntimeException("This is not a valid board! There is no king!");
		}
		return king;
	}
	
	/**
	 * Tells if a move is legal or not
	 * @param move The move we want to test
	 * @return A boolean telling if the move is legal or not
	 */
	public boolean isMoveLegal(final Move move) {
		return this.legalMoves.contains(move);
	}
	
	/**
	 * Tells if the player is in a check position
	 * @return A boolean telling whether the player is in check or not
	 */
	public boolean isInCheck() {
		//TODO Implement this method
		return false;
	}

	/**
	 * Tells if the player is in a check mate position
	 * @return A boolean telling whether the player is in check mate or not
	 */
	public boolean isInCheckMate() {
		//TODO Implement this method
		return false;
	}

	/**
	 * Tells if the player is in a stale mate position
	 * @return A boolean telling whether the player is in stale mate or not
	 */
	public boolean isInStaleMate() {
		//TODO Implement this method
		return false;
	}
	
	public boolean isCastled() {
		//TODO Implement this method
		return false;
	}
	
	public MoveTransition makeMove(final Move move) {
		return null;
	}
	
	/**
	 * Gets all the active pieces of the player
	 * @return The active pieces of the player
	 */
	public abstract Collection<Piece> getActivePieces();
	
	/**
	 * Gets the alliance of the player
	 * @return The alliance (black or white) of the player
	 */
	public abstract Alliance getAlliance();
	
	/**
	 * Gets the opponent player
	 * @return The opponent player
	 */
	public abstract Player getOpponent();
}
