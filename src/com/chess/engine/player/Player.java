package com.chess.engine.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Piece;
import com.google.common.collect.ImmutableList;

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
	
	/** A boolean that tells if the player is in check or not */
	private final boolean isInCheck;
	
	/**
	 * Constructor
	 * @param board The current state of the board
	 * @param legalMoves Most legal moves the player can make (excluding en passant, castle,...)
	 * @param opponentLegalMoves Most legal moves the opponent can make (excluding en passant, castle,...)
	 */
	protected Player(final Board board, final Collection<Move> legalMoves, final Collection<Move> opponentLegalMoves) {
		this.board = board;
		this.playerKing = getKing();
		this.legalMoves = legalMoves;
		this.isInCheck = !Player.calculateAttacksOnTile(this.getPlayerKing().getPiecePosition(), opponentLegalMoves)
				.isEmpty();
	}

	/**
	 * Calculates all the moves of the opponent that attack a tile
	 * @param tileCoordinate The tile coordinate
	 * @param opponentLegalMoves All the legal moves the opponent can make
	 * @return A list of the moves the of the opponent that attack a tile
	 */
	private static Collection<Move> calculateAttacksOnTile(final Integer tileCoordinate,
			final Collection<Move> opponentLegalMoves) {
		
		final List<Move> attackMoves = new ArrayList<>();
		for (final Move move : opponentLegalMoves) {
			if (tileCoordinate == move.getDestinationCoordinate()) {
				attackMoves.add(move);
			}
		}
		return ImmutableList.copyOf(attackMoves);
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
		return this.isInCheck;
	}

	/**
	 * Tells if the player is in a check mate position
	 * @return A boolean telling whether the player is in check mate or not
	 */
	public boolean isInCheckMate() {
		return this.isInCheck && !hasEscapeMoves();
	}

	/**
	 * Tells if the player has some legal move that can be done
	 * @return A boolean telling if the player has some legal move that can be done
	 */
	protected boolean hasEscapeMoves() {
		boolean res = false;
		for (final Move move : this.legalMoves) {
			final MoveTransition transition = makeMove(move);
			if (transition.getMoveStatus().isDone()) {
				res = true;
			}
		}
		return res;
	}

	/**
	 * Tells if the player is in a stale mate position
	 * @return A boolean telling whether the player is in stale mate or not
	 */
	public boolean isInStaleMate() {
		return !this.isInCheck && !hasEscapeMoves();
	}
	
	/**
	 * Tells if the player has already castled or not
	 * @return A boolean that tells if the player is castled or not
	 */
	public boolean isCastled() {
		//TODO Implement this method
		return false;
	}
	
	/**
	 * Executes the move the player want to make
	 * @param move The move the players want to make
	 * @return A move transition to execute the move
	 */
	public MoveTransition makeMove(final Move move) {
		MoveTransition res = null;
		if (!isMoveLegal(move)) {
			res = new MoveTransition(this.board, move, MoveStatus.ILLEGAL_MOVE);
		} else {
			final Board transitionBoard = move.execute();
			final int kingsPosition = transitionBoard.currentPlayer().getOpponent().getPlayerKing().getPiecePosition();
			final Collection<Move> opponentLegalMoves = transitionBoard.currentPlayer().getLegalMoves();
			final Collection<Move> kingAttacks = calculateAttacksOnTile(kingsPosition, opponentLegalMoves);
			
			if (!kingAttacks.isEmpty()) {
				res = new MoveTransition(this.board, move, MoveStatus.LEAVES_PLAYER_IN_CHECK);
			} else {
				res = new MoveTransition(this.board, move, MoveStatus.DONE);
			}
		}
		return res;
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
	
	/**
	 * Gets the player's king piece
	 * @return The player's king piece
	 */
	public Piece getPlayerKing() {
		return this.playerKing;
	}
	
	/**
	 * Gets all the player's legal moves
	 * @return All the player's legal moves
	 */
	public Collection<Move> getLegalMoves(){
		return this.legalMoves;
	}
}
