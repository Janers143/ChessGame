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
 * Class used to describe the rook pieces
 * @author antho
 */
public class Rook extends Piece {

	/**
	 * A move vector for the rook : as it uses the columns and lines,
	 * the rook can move in +1, +2, +3; +8, +16, +24;
	 * -1, -2, -3; -8, -16, -24, ...
	 */
	private final static int[] CANDIDATE_MOVE_VECTOR_COORDINATES = {-8, -1, 1, 8};

	/**
	 * Constructor
	 * @param piecePos The position of the rook in the board
	 * @param pieceAll The alliance of the rook : black or white
	 */
	public Rook(int piecePos, Alliance pieceAll) {
		super(piecePos, pieceAll, PieceType.ROOK);
	}

	@Override
	public Collection<Move> calculateLegalMoves(Board board) {
		final List<Move> legalMoves = new ArrayList<>();
		
		for (final int candidateCoordinateOffset : CANDIDATE_MOVE_VECTOR_COORDINATES) {
			int candidateDestinationCoord = this.piecePosition;
			
			while (BoardUtils.isValidTileCoordinate(candidateDestinationCoord)) {
				// Check if the piece is going out of bounds from the sides
				if (isFirstColumnExclusion(candidateDestinationCoord, candidateCoordinateOffset) ||
						isEighthColumnExclusion(candidateDestinationCoord, candidateCoordinateOffset)) {
					break;
				}
				
				candidateDestinationCoord += candidateCoordinateOffset;
				
				// Check if it is a valid coordinate (not out of bounds)
				if (BoardUtils.isValidTileCoordinate(candidateDestinationCoord)) {
					final Tile candidateDestinationTile = board.getTile(candidateDestinationCoord);
					
					// Check if the tile is occupied
					if (!candidateDestinationTile.isTileOccupied()) {
						// We are moving the piece to an empty tile
						legalMoves.add(new MajorMove(board, this, candidateDestinationCoord));
					} else {
						final Piece pieceAtLocation = candidateDestinationTile.getPiece();
						final Alliance pieceAlliance = pieceAtLocation.pieceAlliance;
						// Check if it is an enemry piece
						if (this.pieceAlliance != pieceAlliance) {
							// We are attacking another piece (capturing)
							legalMoves.add(new AttackMove(board, this, candidateDestinationCoord, pieceAtLocation));
						}
						// We get out of the while loop because if we are here, it means
						// that there is a piece in this diagonal and the rook cannot
						// jump over other pieces
						break;
					}
				}
			}
		}
		return ImmutableList.copyOf(legalMoves);
	}
	
	/**
	 * Function that tells if you are in the first column and in this case
	 * if you are going to the left (you can't go to the left or you will be out of the board)
	 * @param currentPosition The current position of the rook
	 * @param candidateOffset The cuurent offset being tested for legal moves
	 * @return A boolean telling if the destination is a legal move or not
	 */
	private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -1);
	}
	
	/**
	 * Function that tells if you are in the eighth column and in this case
	 * if you are going to the right (you can't go to the right or you will be out of the board)
	 * @param currentPosition The current position of the rook
	 * @param candidateOffset The cuurent offset being tested for legal moves
	 * @return A boolean telling if the destination is a legal move or not
	 */
	private static boolean isEighthColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset == 1);
	}

	@Override
	public String toString() {
		return Piece.PieceType.ROOK.toString();
	}
}
