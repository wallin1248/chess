package chess;

import static java.util.Objects.hash;

/**
 * Represents moving a chess piece on a chessboard
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessMove {

    private final ChessPosition startPosition;
    private final ChessPosition endPosition;
    private final ChessPiece.PieceType promotionPiece;

    public ChessMove(ChessPosition startPosition, ChessPosition endPosition,
                     ChessPiece.PieceType promotionPiece) {
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.promotionPiece = promotionPiece;
    }

    /**
     * @return ChessPosition of starting location
     */
    public ChessPosition getStartPosition() { return this.startPosition; }

    /**
     * @return ChessPosition of ending location
     */
    public ChessPosition getEndPosition() { return this.endPosition; }

    /**
     * Gets the type of piece to promote a pawn to if pawn promotion is part of this
     * chess move
     *
     * @return Type of piece to promote a pawn to, or null if no promotion
     */
    public ChessPiece.PieceType getPromotionPiece() { return this.promotionPiece; }

    /**
     * Override the equals command for this function specifically.
     *
     * @return true if both moves start and end in the same positions
     * and promote to the same piece.
     */
    @Override
    public boolean equals(Object otherMove) {
        // If both are the same reference, return true
        if (this == (ChessMove) otherMove) { return true; }

        // Check if both have the same start, end, and promotion
        ChessMove chessMove = (ChessMove) otherMove;
        return (this.getStartPosition() == chessMove.getStartPosition())
                && (this.getEndPosition() == chessMove.getEndPosition())
                && (this.getPromotionPiece() == chessMove.getPromotionPiece());
    }

    /**
     * Override the hashcode command
     * */
    @Override
    public int hashCode() {
        return hash(this.startPosition, this.endPosition, this.promotionPiece);
    }

    /**
     * Create the print method for this object.
     */
    @Override
    public String toString() {
        return "ChessMove{Start:" + this.startPosition
                + ", End:" + this.endPosition
                + ", Promote:" + this.promotionPiece
                + "}";
    }
}
