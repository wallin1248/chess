package chess;

import java.util.Collection;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    private final ChessGame.TeamColor pieceColor;
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
     * Helper function that calculates the adjacent squares of a piece
     * */
    private Collection<ChessMove> adjacentSquares(ChessPosition position) {
        Collection<ChessMove> legalMoves = null;
        // Check if we are on the top of the board
        if (position.getRow() >= 7) {
            // Add the square above
            ChessMove chessMove = new ChessMove(position, null, null);
            legalMoves.add(chessMove);
        }

        return legalMoves;
    }

    /**
     * Helper function that calculates horizontal moves of a piece
     * */
    private Collection<ChessMove> horizontalSquares(ChessPosition position) {
        Collection<ChessMove> legalMoves = null;
        return legalMoves;
    }

    /**
     * Helper function that calculates horizontal moves of a piece
     * */
    private Collection<ChessMove> verticalSuares(ChessPosition position) {
        Collection<ChessMove> legalMoves = null;
        return legalMoves;
    }

    /**
     * Helper function that calculates horizontal moves of a piece
     * */
    private Collection<ChessMove> diagonalSquares(ChessPosition position) {
        Collection<ChessMove> legalMoves = null;
        return legalMoves;
    }

    /**
     * Helper function that calculates horizontal moves of a piece
     * */
    private Collection<ChessMove> LShape(ChessPosition position) {
        Collection<ChessMove> legalMoves = null;
        return legalMoves;
    }

    /**
     * Helper function that calculates horizontal moves of a piece
     * */
    private Collection<ChessMove> forwardSquare(ChessPosition position) {
        Collection<ChessMove> legalMoves = null;
        return legalMoves;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        // Make the variable that will contain the chess moves
        Collection<ChessMove> legalMoves;
        // Check what piece is at myPosition
        if (board.getPiece(myPosition).getPieceType() == PieceType.KING) {
            legalMoves = (adjacentSquares(myPosition));
        } else if (board.getPiece(myPosition).getPieceType() == PieceType.QUEEN) {
            legalMoves = (horizontalSquares(myPosition));
            legalMoves.addAll(verticalSuares(myPosition));
            legalMoves.addAll(diagonalSquares(myPosition));
        } else if (board.getPiece(myPosition).getPieceType() == PieceType.ROOK) {
            legalMoves = (horizontalSquares(myPosition));
            legalMoves.addAll(verticalSuares(myPosition));
        } else if (board.getPiece(myPosition).getPieceType() == PieceType.BISHOP) {
            legalMoves = (diagonalSquares(myPosition));
        } else if (board.getPiece(myPosition).getPieceType() == PieceType.KNIGHT) {
            legalMoves = (LShape(myPosition));
        } else if (board.getPiece(myPosition).getPieceType() == PieceType.PAWN) {
            legalMoves = forwardSquare(myPosition);
        } else {
            legalMoves = null;
            throw new RuntimeException("Piece doesn't have a type");
        }
        return legalMoves;
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
        return "ChessPiece{Type:" + this.getPieceType() +
                ", Color:" + this.getTeamColor() +
                "}";
    }
}
