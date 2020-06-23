package com.chess.engine;

/**
 * Enum used to create two types of pieces : black and white
 * @author antho
 *
 */
public enum Alliance {
	BLACK {
		@Override
		public
		int getDirection() {
			return 1;
		}

		@Override
		public boolean isBlack() {
			return true;
		}

		@Override
		public boolean isWhite() {
			return false;
		}
		
	},
	WHITE {
		@Override
		public
		int getDirection() {
			return -1;
		}

		@Override
		public boolean isBlack() {
			return false;
		}

		@Override
		public boolean isWhite() {
			return true;
		}
	};
	
	/**
	 * Function that tells you in which direction move the pawns
	 * @return The direction of the pawns
	 */
	public abstract int getDirection();
	
	/**
	 * Function that tells if the piece is black or not
	 * @return A boolean telling if the piece is black
	 */
	public abstract boolean isBlack();
	
	/**
	 * Function that tells if the piece is white or not
	 * @return A boolean telling if the piece is white
	 */
	public abstract boolean isWhite();
}
