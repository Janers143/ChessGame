package com.chess.engine.player;

import java.util.Collection;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.Piece;

/**
 * Describes the white pieces player
 * @author antsalin
 *
 */
public class WhitePlayer extends Player {

	/**
	 * Constructor
	 * @param board The current state of the board of this game
	 * @param blackStandardLegalMoves Most of the moves the black player can make (excluding castling,...)
	 * @param whiteStandardLegalMoves Most of the moves the white player can make (excluding castling,...)
	 */
	public WhitePlayer(Board board, Collection<Move> whiteStandardLegalMoves,
			Collection<Move> blackStandardLegalMoves) {
		super(board, whiteStandardLegalMoves, blackStandardLegalMoves);
	}

	@Override
	public Collection<Piece> getActivePieces() {
		return this.board.getWhitePieces();
	}

	@Override
	public Alliance getAlliance() {
		return Alliance.WHITE;
	}

	@Override
	public Player getOpponent() {
		return this.board.getBlackPlayer();
	}

}
