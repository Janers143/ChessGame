package com.chess.engine.player;

import java.util.Collection;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.Piece;

/**
 * Describes the black pieces player
 * @author antsalin
 *
 */
public class BlackPlayer extends Player {

	/**
	 * Constructor
	 * @param board The current state of the board of this game
	 * @param blackStandardLegalMoves Most of the moves the black player can make (excluding castling,...)
	 * @param whiteStandardLegalMoves Most of the moves the white player can make (excluding castling,...)
	 */
	public BlackPlayer(Board board, Collection<Move> blackStandardLegalMoves,
			Collection<Move> whiteStandardLegalMoves) {
		super(board, blackStandardLegalMoves, whiteStandardLegalMoves);
	}

	@Override
	public Collection<Piece> getActivePieces() {
		return this.board.getBlackPieces();
	}

	@Override
	public Alliance getAlliance() {
		return Alliance.BLACK;
	}

	@Override
	public Player getOpponent() {
		return this.board.getWhitePlayer();
	}

}
