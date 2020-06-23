package com.chess.engine.pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.MajorMove;
import com.google.common.collect.ImmutableList;

/**
 * Class used to describe the pawn pieces
 * @author antho
 */
public class Pawn extends Piece {

	/**
	 * An array of all the candidate moves that a pawn is allowed to do
	 * /!\ A Pawn can't always do all of these moves
	 */
	private final static int[] CANDIDATE_MOVE_COORDINATES = {7, 8, 9, 16};
	
	/**
	 * Constructor
	 * @param piecePos The position of the pawn in the board
	 * @param pieceAll The alliance of the rook : black or white
	 */
	public Pawn(int piecePos, Alliance pieceAll) {
		super(piecePos, pieceAll);
	}

	@Override
	public Collection<Move> calculateLegalMoves(Board board) {
		final List<Move> legalMoves = new ArrayList<>();
		
		for (final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATES) {
			final int candidateDestinationCoordinate = this.piecePosition + (this.pieceAlliance.getDirection() * currentCandidateOffset);

			if (!BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
				continue;
			}
			
			if (currentCandidateOffset == 8 && !board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
				
				// Management of the normal pawn move
				//TODO There's more work to do here (deal with promotions)...
				legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
			} else if (currentCandidateOffset == 16 && this.isFirstMove() && 
					((BoardUtils.SECOND_ROW[this.piecePosition] && this.getAlliance().isBlack()) || 
					(BoardUtils.SEVENTH_ROW[this.piecePosition] && this.getAlliance().isWhite()))) {
				
				// Management of the 2 tile jump for the pawns in their first move
				final int behindCandidateDestinationCoordinate = this.piecePosition + (this.getAlliance().getDirection() * 8);
				if (!board.getTile(behindCandidateDestinationCoordinate).isTileOccupied() && 
						!board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
					legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
				}
			} else if (currentCandidateOffset == 7 &&
					!((BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite()) ||
					(BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack()))) {
				
				if (board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
					final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
					if (this.pieceAlliance != pieceOnCandidate.pieceAlliance) {
						
						// There's an enemy piece that can be attacked with the pawn to its left
						//TODO There's more work to do here
						legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
					}
				}
				
			} else if (currentCandidateOffset == 9 &&
					!((BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite()) ||
					(BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack()))) {
				
				if (board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
					final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
					if (this.pieceAlliance != pieceOnCandidate.pieceAlliance) {
						// There's an enemy piece that can be attacked with the pawn to its right
						//TODO There's more work to do here
						legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
					}
				}
			}
			
		}
		
		return ImmutableList.copyOf(legalMoves);
	}

}
