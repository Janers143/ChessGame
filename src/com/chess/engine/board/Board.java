package com.chess.engine.board;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chess.engine.Alliance;
import com.chess.engine.pieces.Bishop;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Knight;
import com.chess.engine.pieces.Pawn;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Queen;
import com.chess.engine.pieces.Rook;
import com.chess.engine.player.BlackPlayer;
import com.chess.engine.player.Player;
import com.chess.engine.player.WhitePlayer;
import com.google.common.collect.ImmutableList;

public class Board {
	
	/**
	 * A tile list that represents the game board at a moment of the game
	 */
	private final List<Tile> gameBoard;
	
	/**
	 * A collection of all the active white pieces
	 */
	private final Collection<Piece> whitePieces;
	
	/**
	 * A collection of all the active black pieces
	 */
	private final Collection<Piece> blackPieces;
	
	/**
	 * The white player
	 */
	private final WhitePlayer whitePlayer;
	
	/**
	 * The black player
	 */
	private final BlackPlayer blackPlayer;
	
	/**
	 * The current player
	 */
	private final Player currentPlayer;

	/**
	 * Constructor
	 * @param builder The builder used to create the game board
	 */
	private Board(final Builder builder) {
		this.gameBoard = createGameBoard(builder);
		this.whitePieces = calculateActivePieces(this.gameBoard, Alliance.WHITE);
		this.blackPieces = calculateActivePieces(this.gameBoard, Alliance.BLACK);
		
		final Collection<Move> whiteStandardLegalMoves = calculateLegalMoves(this.whitePieces);
		final Collection<Move> blackStandardLegalMoves = calculateLegalMoves(this.blackPieces);
		
		this.whitePlayer = new WhitePlayer(this, whiteStandardLegalMoves, blackStandardLegalMoves);
		this.blackPlayer = new BlackPlayer(this, blackStandardLegalMoves, whiteStandardLegalMoves);
		
		this.currentPlayer = builder.nextMoveMaker.choosePlayer(this.whitePlayer, this.blackPlayer);
	}

	/**
	 * Method that gets all the legal moves for a set of pieces
	 * @param activePieces The set of pieces
	 * @return All the legal moves
	 */
	private Collection<Move> calculateLegalMoves(Collection<Piece> activePieces) {
		List<Move> legalMoves = new ArrayList<>();
		for (Piece p: activePieces) {
			Collection<Move> currentLegalMoves = p.calculateLegalMoves(this);
			legalMoves.addAll(currentLegalMoves);
		}
		return ImmutableList.copyOf(legalMoves);
	}

	/**
	 * Gets all the active pieces of a player in the current board
	 * @param gameBoard The current board
	 * @param alliance The player
	 * @return All the current active pieces (not captured) of a player
	 */
	private static Collection<Piece> calculateActivePieces(List<Tile> gameBoard, Alliance alliance) {
		final List<Piece> activePieces = new ArrayList<>();
		for (Tile t: gameBoard) {
			if (t.isTileOccupied()) {
				final Piece current = t.getPiece();
				if (current.getAlliance().equals(alliance)) {
					activePieces.add(current);
				}
			}
		}
		return ImmutableList.copyOf(activePieces);
	}

	/**
	 * A method that creates a game board using a builder
	 * @param builder The builder that will be used
	 * @return The game board generated by the builder
	 */
	private List<Tile> createGameBoard(Builder builder) {
		final Tile[] tiles = new Tile[BoardUtils.NUM_TILES];
		for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
			tiles[i] = Tile.createTile(i, builder.boardConfig.get(i));
		}
		return ImmutableList.copyOf(tiles);
	}
	
	/**
	 * Creates the initial board with all the pieces at their initial position
	 * @return The initial board
	 */
	public static Board createStandardBoard() {
		// Creating the new builder
		final Builder builder = new Builder();
		
		// Remember that coord 0 is the top left corner (black corner)
		// Adding all black pieces 
		builder.setPiece(new Rook  (0,  Alliance.BLACK));
		builder.setPiece(new Knight(1,  Alliance.BLACK));
		builder.setPiece(new Bishop(2,  Alliance.BLACK));
		builder.setPiece(new Queen (3,  Alliance.BLACK));
		builder.setPiece(new King  (4,  Alliance.BLACK));
		builder.setPiece(new Bishop(5,  Alliance.BLACK));
		builder.setPiece(new Knight(6,  Alliance.BLACK));
		builder.setPiece(new Rook  (7,  Alliance.BLACK));
		// Adding all black pawns
		builder.setPiece(new Pawn  (8,  Alliance.BLACK));
		builder.setPiece(new Pawn  (9,  Alliance.BLACK));
		builder.setPiece(new Pawn  (10, Alliance.BLACK));
		builder.setPiece(new Pawn  (11, Alliance.BLACK));
		builder.setPiece(new Pawn  (12, Alliance.BLACK));
		builder.setPiece(new Pawn  (13, Alliance.BLACK));
		builder.setPiece(new Pawn  (14, Alliance.BLACK));
		builder.setPiece(new Pawn  (15, Alliance.BLACK));
		
		// Adding all white pawns
		builder.setPiece(new Pawn  (48, Alliance.WHITE));
		builder.setPiece(new Pawn  (49, Alliance.WHITE));
		builder.setPiece(new Pawn  (50, Alliance.WHITE));
		builder.setPiece(new Pawn  (51, Alliance.WHITE));
		builder.setPiece(new Pawn  (52, Alliance.WHITE));
		builder.setPiece(new Pawn  (53, Alliance.WHITE));
		builder.setPiece(new Pawn  (54, Alliance.WHITE));
		builder.setPiece(new Pawn  (55, Alliance.WHITE));
		// Adding all white pieces
		builder.setPiece(new Rook  (56, Alliance.WHITE));
		builder.setPiece(new Knight(57, Alliance.WHITE));
		builder.setPiece(new Bishop(58, Alliance.WHITE));
		builder.setPiece(new Queen (59, Alliance.WHITE));
		builder.setPiece(new King  (60, Alliance.WHITE));
		builder.setPiece(new Bishop(61, Alliance.WHITE));
		builder.setPiece(new Knight(62, Alliance.WHITE));
		builder.setPiece(new Rook  (63, Alliance.WHITE));
		
		// The white player is the first player to move
		builder.setMoveMaker(Alliance.WHITE);
		
		return builder.build();
	}

	/**
	 * Get a tile from its coordinate
	 * @param tileCoord The tile coordinate
	 * @return The tile
	 */
	public Tile getTile(final int tileCoord) {
		return null;
	}
	
	/**
	 * Gets the current player (the one that has to move)
	 * @return The player that has to move
	 */
	public Player currentPlayer() {
		return this.currentPlayer;
	}
	
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
			final String tileText =  this.gameBoard.get(i).toString();
			builder.append(String.format("%3s", tileText));
			if (i % BoardUtils.NUM_TILES_PER_ROW == 7) {
				// If we are at the end of the row
				builder.append("\n");
			}
		}
		return builder.toString();
	}

	/**
	 * Describes a board builder
	 * @author antsalin
	 *
	 */
	public static class Builder{
		
		/**
		 * Associates each piece to a tile id
		 */
		Map<Integer, Piece> boardConfig;
		
		/**
		 * Next player to move
		 */
		Alliance nextMoveMaker;
		
		/**
		 * Basic constructor
		 */
		public Builder() {
			this.boardConfig = new HashMap<>();
		}
		
		/**
		 * Puts a piece at its position in the board
		 * @param piece The piece we want to place on the board
		 * @return The new board builder
		 */
		public Builder setPiece(final Piece piece) {
			this.boardConfig.put(piece.getPiecePosition(), piece);
			return this;
		}
		
		/**
		 * Sets the next player to move
		 * @param nextPlayer The next player to move (white or black)
		 * @return The new board builder
		 */
		public Builder setMoveMaker(final Alliance nextPlayer) {
			this.nextMoveMaker = nextPlayer;
			return this;
		}
		
		/**
		 * Builds a new board
		 * @return The new board
		 */
		public Board build() {
			return new Board(this);
		}
	}

	/**
	 * Gets the white pieces
	 * @return All the active white pieces
	 */
	public Collection<Piece> getWhitePieces() {
		return this.whitePieces;
	}
	
	/**
	 * Gets the black pieces
	 * @return All the active black pieces
	 */
	public Collection<Piece> getBlackPieces() {
		return this.blackPieces;
	}

	/**
	 * Gets the black player
	 * @return The black player
	 */
	public Player getBlackPlayer() {
		return this.blackPlayer;
	}

	/**
	 * Gets the white player
	 * @return The white player
	 */
	public Player getWhitePlayer() {
		return this.whitePlayer;
	}
}
