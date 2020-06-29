package com.chess.engine.pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.google.common.collect.ImmutableList;

import static com.chess.engine.board.Move.*;

/**
 * Class used to describe the knight pieces
 * @author antho
 */
public class Knight extends Piece {
	
	/**
	 * An array of all the candidate moves that a knight is allowed to do
	 * /!\ A Knight can't always do all of these moves
	 */
	private final static int[] CANDIDATE_MOVES = {-17, -15, -10, -6, 6, 10, 15, 17};

	/**
	 * Constructor
	 * @param piecePos The position of the knight in the board (a Tile)
	 * @param pieceAll The alliance of the knight : black or white
	 */
	public Knight(final int piecePos, final Alliance pieceAll) {
		super(piecePos, pieceAll, PieceType.KNIGHT);
	}

	@Override
	public Collection<Move> calculateLegalMoves(Board board) {
		
		final List<Move> legalMoves = new ArrayList<>();
		
		for(final int currentCandidate : CANDIDATE_MOVES) {
			final int candidateDestinationCoord = this.piecePosition + currentCandidate;
			// Check if it is a valid coordinate (not out of bounds)
			if (BoardUtils.isValidTileCoordinate(candidateDestinationCoord)) {
				
				// Check if the piece is going out of bounds from the sides
				if (isFirstColumnExclusion(this.piecePosition, currentCandidate) ||
						isSecondColumnExclusion(this.piecePosition, currentCandidate) ||
						isSeventhColumnExclusion(this.piecePosition, currentCandidate) ||
						isEighthColumnExclusion(this.piecePosition, currentCandidate)) {
					continue;
				}
				
				final Tile candidateDestinationTile = board.getTile(candidateDestinationCoord);
				// Check if the tile is occupied
				if (!candidateDestinationTile.isTileOccupied()) {
					// We are moving the piece to an empty tile
					legalMoves.add(new MajorMove(board, this, candidateDestinationCoord));
				} else {
					final Piece pieceAtLocation = candidateDestinationTile.getPiece();
					final Alliance pieceAlliance = pieceAtLocation.getAlliance();
					// Check if it is an enemry piece
					if (pieceAlliance != this.pieceAlliance) {
						// We are attacking another piece (capturing)
						legalMoves.add(new AttackMove(board, this, candidateDestinationCoord, pieceAtLocation));
					}
				}
			}
		}
		
		return ImmutableList.copyOf(legalMoves);
	}
	
	/**
	 * Function that tells if you are in the first column and in this case
	 * if you are going to the left (you can't go to the left or you will be out of the board)
	 * @param currentPos The current position of the knight
	 * @param candidateOffset The current offset being tested for legal moves
	 * @return A boolean telling if the destination is a legal move or not
	 */
	private static boolean isFirstColumnExclusion(final int currentPos, final int candidateOffset) {
		return BoardUtils.FIRST_COLUMN[currentPos] && (candidateOffset == -17 ||
				candidateOffset == -10 || candidateOffset == 6 || candidateOffset == 15) ;
	}

	/**
	 * Function that tells if you are in the second column and in this case
	 * if you are going to an allowed tile (you can only go one column to the left)
	 * @param currentPos The current position of the knight
	 * @param candidateOffset The current offset being tested for legal moves
	 * @return A boolean telling if the destination is a legal move or not
	 */
	private static boolean isSecondColumnExclusion(final int currentPos, final int candidateOffset) {
		return BoardUtils.SECOND_COLUMN[currentPos] && (candidateOffset == -10 || candidateOffset == 6) ;
	}
	
	/**
	 * Function that tells if you are in the seventh column and in this case
	 * if you are going to an allowed tile (you can only go one column to the right)
	 * @param currentPos The current position of the knight
	 * @param candidateOffset The current offset being tested for legal moves
	 * @return A boolean telling if the destination is a legal move or not
	 */
	private static boolean isSeventhColumnExclusion(final int currentPos, final int candidateOffset) {
		return BoardUtils.SEVENTH_COLUMN[currentPos] && (candidateOffset == 10 || candidateOffset == -6) ;
	}
	
	/**
	 * Function that tells if you are in the eighth column and in this case
	 * if you are going to the right (you can't go to the right or you will be out of the board)
	 * @param currentPos The current position of the knight
	 * @param candidateOffset The current offset being tested for legal moves
	 * @return A boolean telling if the destination is a legal move or not
	 */
	private static boolean isEighthColumnExclusion(final int currentPos, final int candidateOffset) {
		return BoardUtils.EIGHTH_COLUMN[currentPos] && (candidateOffset == 17 ||
				candidateOffset == 10 || candidateOffset == -6 || candidateOffset == -15) ;
	}
	
	@Override
	public String toString() {
		return Piece.PieceType.KNIGHT.toString();
	}

	@Override
	public Knight movePiece(Move move) {
		return new Knight(move.getDestinationCoordinate(), move.getMovedPiece().getAlliance());
	}
}
