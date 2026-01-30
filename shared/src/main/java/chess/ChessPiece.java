package chess;

import java.util.Collection;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    private final ChessGame.TeamColor teamColor;
    private final PieceType type;

    public ChessPiece(ChessGame.TeamColor teamColor, PieceType type) {
        this.teamColor = teamColor;
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
    public ChessGame.TeamColor getTeamColor() { return this.teamColor; }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() { return this.type; }

    /**
     * Helper function that calculates the adjacent squares of a piece
     * */
    private Collection<ChessMove> adjacentSquares(ChessBoard board, ChessPosition pos) {
        ChessGame.TeamColor color = board.getPiece(pos).getTeamColor();
        int row = pos.getRow();
        int col = pos.getColumn();
        int row_up = row + 1;
        int row_down = row - 1;
        int col_right = col + 1;
        int col_left = col - 1;
        Collection<ChessMove> legalMoves = null;
        // Move directly up
        if (row != 7) { // Broken into 2 if statements to not lookup outside of the chessboard
            if (board.getPiece(new ChessPosition(row_up, col)).getTeamColor() != color) {
                // Add the square above
                ChessMove chessMove = new ChessMove(pos, new ChessPosition(row_up, col), null);
                legalMoves.add(chessMove);
            }
        }
        // Move directly left
        if (col != 0) { // Broken into 2 if statements to not lookup outside of the chessboard
            if (board.getPiece(new ChessPosition(row, col_left)).getTeamColor() != color) {
                // Add the square above
                ChessMove chessMove = new ChessMove(pos, new ChessPosition(row, col_left), null);
                legalMoves.add(chessMove);
            }
        }
        // Move directly down
        if (row != 0) { // Broken into 2 if statements to not lookup outside of the chessboard
            if (board.getPiece(new ChessPosition(row_down, col)).getTeamColor() != color) {
                // Add the square above
                ChessMove chessMove = new ChessMove(pos, new ChessPosition(row_down, col), null);
                legalMoves.add(chessMove);
            }
        }
        // Move directly right
        if (col != 7) { // Broken into 2 if statements to not lookup outside of the chessboard
            if (board.getPiece(new ChessPosition(row,col_right)).getTeamColor() != color) {
                // Add the square above
                ChessMove chessMove = new ChessMove(pos, new ChessPosition(row, col_right), null);
                legalMoves.add(chessMove);
            }
        }
        // Move up-left
        if (row != 7 && col != 0) { // Broken into 2 if statements to not lookup outside of the chessboard
            if (board.getPiece(new ChessPosition(row_up, col_left)).getTeamColor() != color) {
                // Add the square above
                ChessMove chessMove = new ChessMove(pos, new ChessPosition(row_up, col_left), null);
                legalMoves.add(chessMove);
            }
        }
        // Move down-left
        if (row != 0 && col != 0) { // Broken into 2 if statements to not lookup outside of the chessboard
            if (board.getPiece(new ChessPosition(row_down, col_left)).getTeamColor() != color) {
                // Add the square above
                ChessMove chessMove = new ChessMove(pos, new ChessPosition(row_down, col_left), null);
                legalMoves.add(chessMove);
            }
        }
        // Move down-right
        if (row != 0 && col != 7) { // Broken into 2 if statements to not lookup outside of the chessboard
            if (board.getPiece(new ChessPosition(row_down, col_right)).getTeamColor() != color) {
                // Add the square above
                ChessMove chessMove = new ChessMove(pos, new ChessPosition(row_down, col_right), null);
                legalMoves.add(chessMove);
            }
        }
        // Move up-right
        if (row != 7 && col != 7) { // Broken into 2 if statements to not lookup outside of the chessboard
            if (board.getPiece(new ChessPosition(row_up,col_right)).getTeamColor() != color) {
                // Add the square above
                ChessMove chessMove = new ChessMove(pos, new ChessPosition(row_up, col_right), null);
                legalMoves.add(chessMove);
            }
        }

        return legalMoves;
    }

    /**
     * Helper function that calculates horizontal moves of a piece
     * */
    private Collection<ChessMove> horizontalSquares(ChessBoard board, ChessPosition pos) {
        ChessGame.TeamColor color = board.getPiece(pos).getTeamColor();
        int row = pos.getRow();
        int col = pos.getColumn();
        Collection<ChessMove> legalMoves = null;
        // Moves to the left
        for (int i = row - 1; i >= 0; i --) {
            ChessPosition newPos = new ChessPosition(i, col);
            if (board.getPiece(newPos).getTeamColor() != color) {
                legalMoves.add(new ChessMove(pos, newPos, null));
            } else { break; }
        }
        // Moves to the right
        for (int i = row + 1; i <= 7; i ++) {
            ChessPosition newPos = new ChessPosition(i, col);
            if (board.getPiece(newPos).getTeamColor() != color) {
                legalMoves.add(new ChessMove(pos, newPos, null));
            } else { break; }
        }

        return legalMoves;
    }

    /**
     * Helper function that calculates horizontal moves of a piece
     * */
    private Collection<ChessMove> verticalSquares(ChessBoard board, ChessPosition pos) {
        ChessGame.TeamColor color = board.getPiece(pos).getTeamColor();
        int row = pos.getRow();
        int col = pos.getColumn();
        Collection<ChessMove> legalMoves = null;
        // Moves downward
        for (int i = col - 1; i >= 0; i --) {
            ChessPosition newPos = new ChessPosition(row, i);
            if (board.getPiece(newPos).getTeamColor() != color) {
                legalMoves.add(new ChessMove(pos, newPos, null));
            } else { break; }
        }
        // Moves upward
        for (int i = col + 1; i <= 7; i ++) {
            ChessPosition newPos = new ChessPosition(row, i);
            if (board.getPiece(newPos).getTeamColor() != color) {
                legalMoves.add(new ChessMove(pos, newPos, null));
            } else { break; }
        }

        return legalMoves;
    }

    /**
     * Helper function that calculates horizontal moves of a piece
     * */
    private Collection<ChessMove> diagonalSquares(ChessBoard board, ChessPosition pos) {
        ChessGame.TeamColor color = board.getPiece(pos).getTeamColor();
        int row = pos.getRow();
        int col = pos.getColumn();
        Collection<ChessMove> legalMoves = null;
        // Moves to the up-left
        for (int i = 1; i <= 7; i ++) {
            if (col + i > 7 || row - i < 0) { break; }  // Ensure we stay on the board
            ChessPosition newPos = new ChessPosition(row + i, col - i);
            if (board.getPiece(newPos).getTeamColor() != color) {
                legalMoves.add(new ChessMove(pos, newPos, null));
            } else { break; }
        }
        // Moves to the down-left
        for (int i = 1; i <= 7; i ++) {
            if (col - i < 0 || row - i < 0) { break; }  // Ensure we stay on the board
            ChessPosition newPos = new ChessPosition(row - i, col - i);
            if (board.getPiece(newPos).getTeamColor() != color) {
                legalMoves.add(new ChessMove(pos, newPos, null));
            } else { break; }
        }
        // Moves to the down-right
        for (int i = 1; i <= 7; i ++) {
            if (col - i < 0 || row + i > 7) { break; }  // Ensure we stay on the board
            ChessPosition newPos = new ChessPosition(row - i, col + i);
            if (board.getPiece(newPos).getTeamColor() != color) {
                legalMoves.add(new ChessMove(pos, newPos, null));
            } else { break; }
        }
        // Moves to the up-right
        for (int i = 1; i <= 7; i ++) {
            if (col + i > 7 || row + i > 7) { break; }  // Ensure we stay on the board
            ChessPosition newPos = new ChessPosition(row + i, col + i);
            if (board.getPiece(newPos).getTeamColor() != color) {
                legalMoves.add(new ChessMove(pos, newPos, null));
            } else { break; }
        }

        return legalMoves;
    }

    /**
     * Helper function that calculates horizontal moves of a piece
     * */
    private Collection<ChessMove> LShapeSquares(ChessBoard board, ChessPosition pos) {
        ChessGame.TeamColor color = board.getPiece(pos).getTeamColor();
        int row = pos.getRow();
        int col = pos.getColumn();
        Collection<ChessMove> legalMoves = null;

        // Upward knight jumps
        if (col < 6) {  // Ensure we stay on the board
            if (row > 0) {
                ChessPosition newPos = new ChessPosition(row - 1, col + 2);
                legalMoves.add(new ChessMove(pos, newPos, null));
            }
            if (row < 7) {
                ChessPosition newPos = new ChessPosition(row + 1, col + 2);
                legalMoves.add(new ChessMove(pos, newPos, null));
            }
        }
        // Leftward knight jumps
        if (row > 1) {  // Ensure we stay on the board
            if (col > 0) {
                ChessPosition newPos = new ChessPosition(row - 2, col - 1);
                legalMoves.add(new ChessMove(pos, newPos, null));
            }
            if (col < 7) {
                ChessPosition newPos = new ChessPosition(row - 2, col - 1);
                legalMoves.add(new ChessMove(pos, newPos, null));
            }
        }
        // Downward knight jumps
        if (col > 1) {  // Ensure we stay on the board
            if (row > 0) {
                ChessPosition newPos = new ChessPosition(row - 1, col - 2);
                legalMoves.add(new ChessMove(pos, newPos, null));
            }
            if (row < 7) {
                ChessPosition newPos = new ChessPosition(row + 1, col - 2);
                legalMoves.add(new ChessMove(pos, newPos, null));
            }
        }
        // Rightward knight jumps
        if (row < 6) {  // Ensure we stay on the board
            if (col > 0) {
                ChessPosition newPos = new ChessPosition(row + 2, col - 1);
                legalMoves.add(new ChessMove(pos, newPos, null));
            }
            if (col < 7) {
                ChessPosition newPos = new ChessPosition(row + 2, col - 1);
                legalMoves.add(new ChessMove(pos, newPos, null));
            }
        }
        return legalMoves;
    }

    /**
     * Helper function that calculates horizontal moves of a piece
     * */
    private Collection<ChessMove> forwardSquare(ChessBoard board, ChessPosition position) {
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
            legalMoves = (adjacentSquares(board, myPosition));
        } else if (board.getPiece(myPosition).getPieceType() == PieceType.QUEEN) {
            legalMoves = (horizontalSquares(board, myPosition));
            legalMoves.addAll(verticalSquares(board, myPosition));
            legalMoves.addAll(diagonalSquares(board, myPosition));
        } else if (board.getPiece(myPosition).getPieceType() == PieceType.ROOK) {
            legalMoves = (horizontalSquares(board, myPosition));
            legalMoves.addAll(verticalSquares(board, myPosition));
        } else if (board.getPiece(myPosition).getPieceType() == PieceType.BISHOP) {
            legalMoves = (diagonalSquares(board, myPosition));
        } else if (board.getPiece(myPosition).getPieceType() == PieceType.KNIGHT) {
            legalMoves = (LShapeSquares(board, myPosition));
        } else if (board.getPiece(myPosition).getPieceType() == PieceType.PAWN) {
            legalMoves = forwardSquare(board, myPosition);
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
