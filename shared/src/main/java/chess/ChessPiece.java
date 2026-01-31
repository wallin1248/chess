package chess;

import java.util.*;

import static java.lang.Math.abs;

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
    private Set<ChessMove> adjacentSquares(ChessBoard board, ChessPosition pos) {
        ChessGame.TeamColor color = board.getPiece(pos).getTeamColor();
        int row = pos.getRow();
        int col = pos.getColumn();
        ArrayList<ChessPosition> possibleDirections = new ArrayList<>();
        // Find all the possible adjacent directions
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++){
                if (row + i >= 1 && row + i <= 8 && col + j >= 1 && col + j <= 8 && (i != 0 || j != 0)) {
                    possibleDirections.add(new ChessPosition(row + i, col + j));
                }
            }
        }
        Set<ChessMove> legalMoves = new HashSet<ChessMove>();
        // Move up-right
        for (ChessPosition dir : possibleDirections) {
            if (board.getPiece(dir) == null) {
                legalMoves.add(new ChessMove(pos, dir, null));
            } else if (board.getPiece(dir).getTeamColor() != color) {
                legalMoves.add(new ChessMove(pos, dir, null));
            }
        }

        return legalMoves;
    }

    /**
     * Helper function that calculates horizontal moves of a piece
     * */
    private Set<ChessMove> horizontalSquares(ChessBoard board, ChessPosition pos) {
        ChessGame.TeamColor color = board.getPiece(pos).getTeamColor();
        int row = pos.getRow();
        int col = pos.getColumn();
        Set<ChessMove> legalMoves = new HashSet<ChessMove>();
        // Moves to the left
        int[] leftSquares = new int[col - 1];
        int temp = col - 1;
        int iter = 0;
        while (temp >= 1) {
            leftSquares[iter] = temp;
            temp -= 1;
            iter += 1;
        }
        for (int i : leftSquares) {
            ChessPosition nextLeft = new ChessPosition(row, i);
            if (board.getPiece(nextLeft) == null) {
                legalMoves.add(new ChessMove(pos, nextLeft, null));
            } else if (board.getPiece(nextLeft).getTeamColor() != color) {
                legalMoves.add(new ChessMove(pos, nextLeft, null));
                break;
            } else { break; }
        }
        // Moves to the right
        int[] rightSquares = new int[8 - col];
        temp = col + 1;
        iter = 0;
        while (temp <= 8) {
            rightSquares[iter] = temp;
            temp += 1;
            iter += 1;
        }
        for (int j : rightSquares) {
            ChessPosition nextRight = new ChessPosition(row, j);
            if (board.getPiece(nextRight) == null) {
                legalMoves.add(new ChessMove(pos, nextRight, null));
            } else if (board.getPiece(nextRight).getTeamColor() != color) {
                legalMoves.add(new ChessMove(pos, nextRight, null));
                break;
            } else { break; }
        }

        return legalMoves;
    }

    /**
     * Helper function that calculates horizontal moves of a piece
     * */
    private Set<ChessMove> verticalSquares(ChessBoard board, ChessPosition pos) {
        ChessGame.TeamColor color = board.getPiece(pos).getTeamColor();
        int row = pos.getRow();
        int col = pos.getColumn();
        Set<ChessMove> legalMoves = new HashSet<ChessMove>();
        // Moves downward
        int[] downSquares = new int[row - 1];
        int temp = row - 1;
        int iter = 0;
        while (temp >= 1) {
            downSquares[iter] = temp;
            temp -= 1;
            iter += 1;
        }
        for (int i : downSquares) {
            ChessPosition nextDown = new ChessPosition(i, col);
            if (board.getPiece(nextDown) == null) {
                legalMoves.add(new ChessMove(pos, nextDown, null));
            } else if (board.getPiece(nextDown).getTeamColor() != color) {
                legalMoves.add(new ChessMove(pos, nextDown, null));
                break;
            } else { break; }
        }
        // Moves upward
        int[] upSquares = new int[8 - row];
        temp = row + 1;
        iter = 0;
        while (temp <= 8) {
            upSquares[iter] = temp;
            temp += 1;
            iter += 1;
        }
        for (int j : upSquares) {
            ChessPosition nextUp = new ChessPosition(j, col);
            if (board.getPiece(nextUp) == null) {
                legalMoves.add(new ChessMove(pos, nextUp, null));
            } else if (board.getPiece(nextUp).getTeamColor() != color) {
                legalMoves.add(new ChessMove(pos, nextUp, null));
                break;
            } else { break; }
        }

        return legalMoves;
    }

    /**
     * Helper function that calculates horizontal moves of a piece
     * */
    private Set<ChessMove> diagonalSquares(ChessBoard board, ChessPosition pos) {
        ChessGame.TeamColor color = board.getPiece(pos).getTeamColor();
        int row = pos.getRow();
        int col = pos.getColumn();
        Set<ChessMove> legalMoves = new HashSet<ChessMove>();
        // Moves to the up-left
        for (int i = 1; i <= 7; i ++) { // At most there are 7 diagonal squares in any one direction
            if (row + i > 8 || col - i < 1 ) { break; }  // Ensure we stay on the board
            ChessPosition newPos = new ChessPosition(row + i, col - i);
            if (board.getPiece(newPos) == null) {
                legalMoves.add(new ChessMove(pos, newPos, null));
            } else if (board.getPiece(newPos).getTeamColor() != color) {
                legalMoves.add(new ChessMove(pos, newPos, null));
                break;
            } else { break; }
        }
        // Moves to the down-left
        for (int i = 1; i <= 7; i ++) { // At most there are 7 diagonal squares in any one direction
            if (row - i < 1 || col - i < 1) { break; }  // Ensure we stay on the board
            ChessPosition newPos = new ChessPosition(row - i, col - i);
            if (board.getPiece(newPos) == null) {
                legalMoves.add(new ChessMove(pos, newPos, null));
            } else if (board.getPiece(newPos).getTeamColor() != color) {
                legalMoves.add(new ChessMove(pos, newPos, null));
                break;
            } else { break; }
        }
        // Moves to the down-right
        for (int i = 1; i <= 7; i ++) { // At most there are 7 diagonal squares in any one direction
            if (row - i < 1 || col + i > 8) { break; }  // Ensure we stay on the board
            ChessPosition newPos = new ChessPosition(row - i, col + i);
            if (board.getPiece(newPos) == null) {
                legalMoves.add(new ChessMove(pos, newPos, null));
            } else if (board.getPiece(newPos).getTeamColor() != color) {
                legalMoves.add(new ChessMove(pos, newPos, null));
                break;
            } else { break; }
        }
        // Moves to the up-right
        for (int i = 1; i <= 7; i ++) { // At most there are 7 diagonal squares in any one direction
            if (row + i > 8 || col + i > 8) { break; }  // Ensure we stay on the board
            ChessPosition newPos = new ChessPosition(row + i, col + i);
            if (board.getPiece(newPos) == null) {
                legalMoves.add(new ChessMove(pos, newPos, null));
            } else if (board.getPiece(newPos).getTeamColor() != color) {
                legalMoves.add(new ChessMove(pos, newPos, null));
                break;
            } else { break; }
        }

        return legalMoves;
    }

    /**
     * Helper function that calculates horizontal moves of a piece
     * */
    private Set<ChessMove> LShapeSquares(ChessBoard board, ChessPosition pos) {
        ChessGame.TeamColor color = board.getPiece(pos).getTeamColor();
        int row = pos.getRow();
        int col = pos.getColumn();
        int[] knightDistances = new int[4];
        knightDistances[0] = -2;
        knightDistances[1] = -1;
        knightDistances[2] = 1;
        knightDistances[3] = 2;
        ArrayList<ChessPosition> possibleDirections = new ArrayList<>();
        // Find all the possible adjacent directions
        for (int i : knightDistances) {
            for (int j : knightDistances){
                if (row + i >= 1 && row + i <= 8 && col + j >= 1 && col + j <= 8 && abs(i) != abs(j)) {
                    possibleDirections.add(new ChessPosition(row + i, col + j));
                }
            }
        }
        Set<ChessMove> legalMoves = new HashSet<ChessMove>();
        // Move up-right
        for (ChessPosition dir : possibleDirections) {
            if (board.getPiece(dir) == null) {
                legalMoves.add(new ChessMove(pos, dir, null));
            } else if (board.getPiece(dir).getTeamColor() != color) {
                legalMoves.add(new ChessMove(pos, dir, null));
            }
        }

        return legalMoves;
    }

    /**
     * Helper function that calculates horizontal moves of a piece
     * */
    private Set<ChessMove> forwardSquare(ChessBoard board, ChessPosition pos) {
        ChessGame.TeamColor color = board.getPiece(pos).getTeamColor();
        int row = pos.getRow();
        int col = pos.getColumn();
        int direction = 0;
        Set<ChessPiece.PieceType> promotion = new HashSet<>();
        Set<ChessMove> legalMoves = new HashSet<ChessMove>();

        // White moves up the board and black moves down the board
        if (color == ChessGame.TeamColor.WHITE) {
            direction = 1;
        } else {
            direction = -1;
        }

        // Find the possible promotions
        if ((row + direction != 8 && color == ChessGame.TeamColor.WHITE) ||
                (row + direction != 1 && color == ChessGame.TeamColor.BLACK)) {
            promotion.add(null);
        } else {
            promotion.add(PieceType.QUEEN);
            promotion.add(PieceType.ROOK);
            promotion.add(PieceType.KNIGHT);
            promotion.add(PieceType.BISHOP);
        }

        // Check non-capturing forward one square
        ChessPosition forwardPos = new ChessPosition(row + direction, col);
        if (board.getPiece(forwardPos) == null) {
            for (ChessPiece.PieceType p : promotion) {
                legalMoves.add(new ChessMove(pos, forwardPos, p));
            }
            // Check forward 2 squares if on homerow
            if (color == ChessGame.TeamColor.WHITE && row == 2 ||
                    color == ChessGame.TeamColor.BLACK && row == 7) {
                ChessPosition forwardPos2 = new ChessPosition(row + 2 * direction, col);
                if (board.getPiece(forwardPos2) == null) {
                    for (ChessPiece.PieceType p : promotion) {
                        legalMoves.add(new ChessMove(pos, forwardPos2, p));
                    }
                }
            }
        }
        // Check capturing
        ChessPosition leftCapture = new ChessPosition(row + direction, col - 1);
        if (board.getPiece(leftCapture).getTeamColor() != color) {
            for (ChessPiece.PieceType p : promotion) {
                legalMoves.add(new ChessMove(pos, leftCapture, p));
            }
        }
        ChessPosition rightCapture = new ChessPosition(row + direction, col + 1);
        if (board.getPiece(rightCapture).getTeamColor() != color) {
            for (ChessPiece.PieceType p : promotion) {
                legalMoves.add(new ChessMove(pos, rightCapture, p));
            }
        }

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
        Collection<ChessMove> legalMoves = new HashSet<ChessMove>();
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

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        ChessPiece that = (ChessPiece) object;
        return teamColor == that.teamColor && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamColor, type);
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
