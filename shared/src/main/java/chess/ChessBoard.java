package chess;

import java.util.Arrays;
import java.util.Objects;
import java.util.HashMap;
import java.util.Map;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
    ChessPiece[][] chessBoard = new ChessPiece[8][8];

    public ChessBoard() {
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        this.chessBoard[position.getRow() - 1][position.getColumn() - 1] = piece;
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        return this.chessBoard[position.getRow() - 1][position.getColumn() - 1];
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        // Clear the board entirely
        chessBoard = new ChessPiece[8][8];

        // Add the pawns
        for (int col = 1; col <= 8; col++) {
            addPiece(new ChessPosition(2, col), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN));
            addPiece(new ChessPosition(7, col), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN));
        }

        // Add the pieces for white
        addPiece(new ChessPosition(1, 1), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK));
        addPiece(new ChessPosition(1, 2), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT));
        addPiece(new ChessPosition(1, 3), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP));
        addPiece(new ChessPosition(1, 4), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.QUEEN));
        addPiece(new ChessPosition(1, 5), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KING));
        addPiece(new ChessPosition(1, 6), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP));
        addPiece(new ChessPosition(1, 7), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT));
        addPiece(new ChessPosition(1, 8), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK));

        // Add the pieces for black
        addPiece(new ChessPosition(8, 1), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK));
        addPiece(new ChessPosition(8, 2), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT));
        addPiece(new ChessPosition(8, 3), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP));
        addPiece(new ChessPosition(8, 4), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.QUEEN));
        addPiece(new ChessPosition(8, 5), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING));
        addPiece(new ChessPosition(8, 6), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP));
        addPiece(new ChessPosition(8, 7), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT));
        addPiece(new ChessPosition(8, 8), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK));
    }

    @Override
    public String toString() {
        Map<ChessPiece,String> namesOfPieces = new HashMap<>();
        namesOfPieces.put(new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KING), "K");
        namesOfPieces.put(new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.QUEEN), "Q");
        namesOfPieces.put(new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK), "R");
        namesOfPieces.put(new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP), "B");
        namesOfPieces.put(new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT), "N");
        namesOfPieces.put(new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN), "P");
        namesOfPieces.put(new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING), "k");
        namesOfPieces.put(new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.QUEEN), "q");
        namesOfPieces.put(new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK), "r");
        namesOfPieces.put(new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP), "b");
        namesOfPieces.put(new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT), "n");
        namesOfPieces.put(new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN), "p");

        String string = "";
        for (int row = 1; row < 9; row++) {
            for (int col = 1; col < 9; col++) {
                ChessPosition pos = new ChessPosition(row, col);
                ChessPiece piece = this.getPiece(pos);
                if (piece == null) {
                    string = string.concat(".");
                } else {
                    string = string.concat(namesOfPieces.get(piece));
                }
            }
            string = string.concat("/");
        }

        return "ChessBoard{" +
                "chessBoard=" + string +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        ChessBoard that = (ChessBoard) object;
        return Objects.deepEquals(chessBoard, that.chessBoard);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(chessBoard);
    }
}
