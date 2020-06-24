package com.chess.engine.pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.chess.engine.board.Move.AttackMove;
import com.chess.engine.board.Move.MajorMove;
import com.google.common.collect.ImmutableList;

/**
 * Class used to describe the king pieces
 * @author antho
 */
public class King extends Piece {

	/**
	 * An array of all the candidate moves that a king is allowed to do
	 * /!\ A king can't always do all of these moves
	 */
	private final static int[] CANDIDATE_MOVES = {-9, -8, -7, -1, 1, 7, 8, 9};
	
	/**
	 * Constructor
	 * @param piecePos The position of the king in the board
	 * @param pieceAll The alliance of the king : black or white
	 */
	public King(int piecePos, Alliance pieceAll) {
		super(piecePos, pieceAll);
	}

	@Override
	public Collection<Move> calculateLegalMoves(Board board) {
		final List<Move> legalMoves = new ArrayList<>();
				
		for (final int currentCandidateOffset : CANDIDATE_MOVES) {
			final int candidateDestinationCoordinate = this.piecePosition + currentCandidateOffset;
			
			if (isFirstColumnExclusion(this.piecePosition, currentCandidateOffset) ||
					isEighthColumnExclusion(this.piecePosition, currentCandidateOffset)) {
				continue;
			}
			
			if (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
				final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
				
				// Check if the tile is occupied
				if (!candidateDestinationTile.isTileOccupied()) {
					// We are moving the piece to an empty tile
					legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
				} else {
					final Piece pieceAtLocation = candidateDestinationTile.getPiece();
					final Alliance pieceAlliance = pieceAtLocation.getAlliance();
					// Check if it is an enemry piece
					if (pieceAlliance != this.pieceAlliance) {
						// We are attacking another piece (capturing)
						legalMoves.add(new AttackMove(board, this, candidateDestinationCoordinate, pieceAtLocation));
					}
				}
			}
		}
		
		return ImmutableList.copyOf(legalMoves);
	}

	/**
	 * Function that tells if you are in the first column and in this case
	 * if you are going to the left (you can't go to the left or you will be out of the board)
	 * @param currentPos The current position of the king
	 * @param candidateOffset The current offset being tested for legal moves
	 * @return A boolean telling if the destination is a legal move or not
	 */
	private static boolean isFirstColumnExclusion(final int currentPos, final int candidateOffset) {
		return BoardUtils.FIRST_COLUMN[currentPos] && (candidateOffset == -9 ||
				candidateOffset == -1 || candidateOffset == 7) ;
	}
	
	/**
	 * Function that tells if you are in the eighth column and in this case
	 * if you are going to the right (you can't go to the right or you will be out of the board)
	 * @param currentPos The current position of the knight
	 * @param candidateOffset The current offset being tested for legal moves
	 * @return A boolean telling if the destination is a legal move or not
	 */
	private static boolean isEighthColumnExclusion(final int currentPos, final int candidateOffset) {
		return BoardUtils.EIGHTH_COLUMN[currentPos] && (candidateOffset == -7 ||
				candidateOffset == 1 || candidateOffset == 9) ;
	}
	
	@Override
	public String toString() {
		return Piece.PieceType.KING.toString();
	}
}
