package com.chess.engine.player;

/**
 * Describes the move status: if it's done,...
 * @author antsalin
 * 
 */
public enum MoveStatus {

	DONE{
		@Override
		public boolean isDone() {
			return true;
		}
	}, ILLEGAL_MOVE {
		@Override
		public boolean isDone() {
			return false;
		}
	}, LEAVES_PLAYER_IN_CHECK {
		@Override
		public boolean isDone() {
			return false;
		}
	};
	
	/**
	 * Tells if the move is done or not
	 * @return A boolean telling whether the move is done or not
	 */
	public abstract boolean isDone();
}
