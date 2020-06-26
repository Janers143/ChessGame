package com.chess.engine.player;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

/**
 * Describes the transition from one move to the next one
 * @author antsalin
 *
 */
public class MoveTransition {
	
	/** The transition board from before the move to after the move */
	private final Board transitionBoard;
	
	/** The move we want to make */
	private final Move move;
	
	/** The move status : whether we managed to make the move or not */
	private final MoveStatus moveStatus;

	/**
	 * Constructor
	 * @param transitionBoard The transition board
	 * @param move The move the player wants to make
	 * @param moveStatus The move status : whether we managed to make the move or not
	 */
	public MoveTransition(Board transitionBoard, Move move, MoveStatus moveStatus) {
		this.transitionBoard = transitionBoard;
		this.move = move;
		this.moveStatus = moveStatus;
	}

	/**
	 * Gets the move status
	 * @return The move status
	 */
	public MoveStatus getMoveStatus() {
		return this.moveStatus;
	}
	
	
}
