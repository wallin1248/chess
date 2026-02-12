package chess;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static java.lang.Math.abs;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    ChessGame.TeamColor pieceColor;
    PieceType type;
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
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
    }

    private Set<ChessMove> adjacentMoves(ChessBoard board, ChessPosition myPosition) {
        Set<ChessMove> possibleMoves = new HashSet<>();
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        // Check all 8 adjacent squares
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                // Make sure we are still on the chessboard
                if (row + i >= 1 && row + i <= 8 && col + j >= 1 && col + j <= 8 && (i != 0 || j != 0)) {
                    ChessPosition newPosition = new ChessPosition(row + i, col + j);
                    ChessMove newMove = new ChessMove(myPosition, newPosition, null);
                    if (board.getPiece(newPosition) == null) {
                        possibleMoves.add(newMove);
                    } else if (board.getPiece(newPosition).pieceColor != board.getPiece(myPosition).pieceColor) {
                        possibleMoves.add(newMove);
                    }
                }
            }
        }

        return possibleMoves;
    }

    private Set<ChessMove> straightLine(ChessBoard board, ChessPosition myPosition, int[] direction) {
        Set<ChessMove> possibleMoves = new HashSet<>();
        int row = myPosition.getRow();
        int col = myPosition.getColumn();

        // Straight lines never go longer than the starting square plus 7 moves
        for (int i = 1; i < 8; i++) {
            int newRow = row + direction[0] * i;
            int newCol = col + direction[1] * i;
            // Check we are still in the board
            if (newRow >= 1
                    && newRow <= 8
                    && newCol >= 1
                    && newCol <= 8) {
                ChessPosition newPosition = new ChessPosition(newRow, newCol);
                ChessMove newMove = new ChessMove(myPosition, newPosition, null);
                if (board.getPiece(newPosition) == null) {
                    // Can move through empty squares
                    possibleMoves.add(newMove);
                } else if (board.getPiece(newPosition).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                    // Can move to but not past an enemy piece
                    possibleMoves.add(newMove);
                    break;
                } else if (board.getPiece(newPosition).getTeamColor() == board.getPiece(myPosition).getTeamColor()) {
                    break;
                } else {
                    throw new RuntimeException("Shouldn't be possible to have piece that isn't the same or different type as own.");
                }
            }
        }

        return possibleMoves;
    }

    private Set<ChessMove> flatMoves(ChessBoard board, ChessPosition myPosition) {
        Set<ChessMove> possibleMoves = new HashSet<>();
        int[] up    = { 1,  0};
        int[] down  = {-1,  0};
        int[] left  = { 0, -1};
        int[] right = { 0,  1};

        possibleMoves.addAll(straightLine(board, myPosition, up));
        possibleMoves.addAll(straightLine(board, myPosition, down));
        possibleMoves.addAll(straightLine(board, myPosition, left));
        possibleMoves.addAll(straightLine(board, myPosition, right));

        return possibleMoves;
    }

    private Set<ChessMove> diagMoves(ChessBoard board, ChessPosition myPosition) {
        Set<ChessMove> possibleMoves = new HashSet<>();
        int[] up_left    = { 1, -1};
        int[] down_left  = {-1, -1};
        int[] up_right   = { 1,  1};
        int[] down_right = {-1,  1};

        possibleMoves.addAll(straightLine(board, myPosition, up_left));
        possibleMoves.addAll(straightLine(board, myPosition, down_left));
        possibleMoves.addAll(straightLine(board, myPosition, up_right));
        possibleMoves.addAll(straightLine(board, myPosition, down_right));

        return possibleMoves;
    }

    private Set<ChessMove> lShapeMoves(ChessBoard board, ChessPosition myPosition) {
        Set<ChessMove> possibleMoves = new HashSet<>();
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        int[] knightDistances = new int[4];
        knightDistances[0] = 1;
        knightDistances[1] = 2;
        knightDistances[2] = -1;
        knightDistances[3] = -2;
        // Check all 8 adjacent squares
        for (int i : knightDistances) {
            for (int j : knightDistances) {
                // Make sure we are still on the chessboard
                if (row + i >= 1 && row + i <= 8 && col + j >= 1 && col + j <= 8 && (abs(i) != abs(j))) {
                    ChessPosition newPosition = new ChessPosition(row + i, col + j);
                    ChessMove newMove = new ChessMove(myPosition, newPosition, null);
                    if (board.getPiece(newPosition) == null) {
                        possibleMoves.add(newMove);
                    } else if (board.getPiece(newPosition).pieceColor != board.getPiece(myPosition).pieceColor) {
                        possibleMoves.add(newMove);
                    }
                }
            }
        }

        return possibleMoves;
    }

    private Set<ChessMove> pawnMoves(ChessBoard board, ChessPosition myPosition) {
        Set<ChessMove> possibleMoves = new HashSet<>();
        ChessPosition newPosition;
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        boolean isOnHomeRow = (row == 2 && board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.WHITE) ||
                (row == 7 && board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.BLACK);
        boolean isReadyToPromote = (row == 7 && board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.WHITE) ||
                (row == 2 && board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.BLACK);
        Set<PieceType> promoteTo = new HashSet<>();
        if (isReadyToPromote) {
            promoteTo.add(PieceType.QUEEN);
            promoteTo.add(PieceType.ROOK);
            promoteTo.add(PieceType.BISHOP);
            promoteTo.add(PieceType.KNIGHT);
        } else {
            promoteTo.add(null);
        }
        int direction;
        if (board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.WHITE) {
            direction = 1;
        } else {
            direction = -1;
        }
        boolean isOnLeft = col <= 1;
        boolean isOnRight = col >= 8;

        // Check if we can move forward one
        if (row + direction  >= 1 && row + direction <= 8) {
            newPosition = new ChessPosition(row + direction, col);
            if (board.getPiece(newPosition) == null) {
                for (PieceType promotion : promoteTo) {
                    ChessMove newMove = new ChessMove(myPosition, newPosition, promotion);
                    possibleMoves.add(newMove);
                }
                // Since the first square is open, check if we can move 2 spaces as well
                if (isOnHomeRow) {
                    newPosition = new ChessPosition(row + 2 * direction, col);
                    if (board.getPiece(newPosition) == null) {
                        for (PieceType promotion : promoteTo) {
                            ChessMove newMove = new ChessMove(myPosition, newPosition, promotion);
                            possibleMoves.add(newMove);
                        }
                    }
                }
            }
        }
        // Check if we can capture to the left
        if (!isOnLeft && row + direction  >= 1 && row + direction <= 8) {
            newPosition = new ChessPosition(row + direction, col - 1);
            if (board.getPiece(newPosition) != null) {
                if (board.getPiece((newPosition)).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                    for (PieceType promotion : promoteTo) {
                        ChessMove newMove = new ChessMove(myPosition, newPosition, promotion);
                        possibleMoves.add(newMove);
                    }
                }
            }
        }
        // Check if we can capture to the right
        if (!isOnRight && row + direction  >= 1 && row + direction <= 8) {
            newPosition = new ChessPosition(row + direction, col + 1);
            if (board.getPiece(newPosition) != null) {
                if (board.getPiece((newPosition)).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                    for (PieceType promotion : promoteTo) {
                        ChessMove newMove = new ChessMove(myPosition, newPosition, promotion);
                        possibleMoves.add(newMove);
                    }
                }
            }
        }

        return possibleMoves;
    }

    /**
     * Overload the pieceMoves method to be able to handle multiple pieces at once.
     * Calculates all the positions a collection of chess piece can move to.
     * Does not take into account moves that are illegal due to leaving the king in
     * danger.
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, Collection<ChessPosition> myPositions) {
        Collection<ChessMove> possibleMoves = new HashSet<>();
        for (ChessPosition currPos : myPositions) {
            possibleMoves.addAll(pieceMoves(board, currPos));
        }
        return possibleMoves;
    }

        /**
         * Calculates all the positions a chess piece can move to.
         * Does not take into account moves that are illegal due to leaving the king in
         * danger.
         *
         * @return Collection of valid moves
         */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> possibleMoves = new HashSet<>();

        ChessPiece myPiece = board.getPiece(myPosition);
        PieceType myType = myPiece.getPieceType();
        // Run everything needed to find each piece's legal moves
        if (myType == PieceType.KING) {
            possibleMoves.addAll(adjacentMoves(board, myPosition));
        } else if (myType == PieceType.QUEEN) {
            possibleMoves.addAll(flatMoves(board, myPosition));
            possibleMoves.addAll(diagMoves(board, myPosition));
        } else if (myType == PieceType.ROOK) {
            possibleMoves.addAll(flatMoves(board, myPosition));
        } else if (myType == PieceType.BISHOP) {
            possibleMoves.addAll(diagMoves(board, myPosition));
        } else if (myType == PieceType.KNIGHT) {
            possibleMoves.addAll(lShapeMoves(board, myPosition));
        } else if (myType == PieceType.PAWN) {
            possibleMoves.addAll(pawnMoves(board, myPosition));
        } else {
            throw new RuntimeException("Unknown piece type");
        }
        return possibleMoves;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        ChessPiece that = (ChessPiece) object;
        return pieceColor == that.pieceColor && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type);
    }

    @Override
    public String toString() {
        return "ChessPiece{" +
                "pieceColor=" + pieceColor +
                ", type=" + type +
                '}';
    }
}
