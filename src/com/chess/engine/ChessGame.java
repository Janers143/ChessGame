package com.chess.engine;

import com.chess.engine.board.Board;

/**
 * Little class used to test some functionalities
 * @author antsalin
 *
 */
public class ChessGame {

	public static void main(String[] args) {
		
		Board board = Board.createStandardBoard();
		
		System.out.println(board);
	}

}
