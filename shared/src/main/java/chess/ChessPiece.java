package chess;

import java.util.Collection;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    private ChessGame.TeamColor pieceColor;
    private PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() { return this.pieceColor; }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() { return this.type; }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        // TODO: FIX THIS
        throw new RuntimeException("Not implemented");
    }

    /**
     * Override the equals command for this function specifically.
     *
     * @return true if both pieces are the same type and color, false otherwise
     *
     */
    @Override
    public boolean equals(Object otherPiece) {
        // If both are the same reference, return true
        if (this == otherPiece) { return true; }

        // Check if both have the same color and piece type
        ChessPiece chessPiece = (ChessPiece) otherPiece;
        return this.getTeamColor() == chessPiece.getTeamColor() && this.getPieceType() == chessPiece.getPieceType();
    }

    /**
     * Override the hashcode command
     * */
    @Override
    public int hashCode() {
        int hash = 0;
        if (this.pieceColor == ChessGame.TeamColor.WHITE) {
            hash += 6;
        }

        if (this.type == PieceType.PAWN) {
            hash += 1;
        } else if (this.type == PieceType.KNIGHT) {
            hash += 2;
        } else if (this.type == PieceType.BISHOP) {
            hash += 3;
        } else if (this.type == PieceType.ROOK) {
            hash += 4;
        } else if (this.type == PieceType.QUEEN) {
            hash += 5;
        }

        return hash;
    }
        /**
         * Create the print method for this object.
         */
    @Override
    public String toString() {
        return "ChessPiece{" + this.getPieceType() + ", " + this.getTeamColor() + "}";
    }
}
