package chess;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    ChessGame.TeamColor pieceColor;
    ChessPiece.PieceType pieceType;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.pieceType = type;
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
        return this.pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return this.pieceType;
    }

    /**
     * Generate a collection of all repeated moves in a direction
     */
    private Collection<ChessMove> genericMove(ChessBoard board, ChessPosition pos, int moveUp, int moveRight, ChessGame.TeamColor color, int numRepeat, PieceType promotion) {
        Collection<ChessMove> foundMoves = new HashSet<>();
        // Recursion exit condition
        if (numRepeat < 0) {
            return foundMoves;
        }

        // Calculate where the piece lands
        int row = pos.getRow();
        int col = pos.getColumn();
        int newRow = row + moveUp;
        int newCol = col + moveRight;

        // Check it is still on the board
        if (newRow > 8 || newRow < 1) {
            return foundMoves;
        }
        if (newCol > 8 || newCol < 1) {
            return foundMoves;
        }

        // Find if we can add the new moves
        ChessPosition newPos = new ChessPosition(newRow, newCol);
        if (board.getPiece(newPos) != null) {
            ChessGame.TeamColor colorAtNew = board.getPiece(newPos).getTeamColor();
            if (colorAtNew != color) {
                foundMoves.add(new ChessMove(pos, newPos, promotion));
                foundMoves.addAll(genericMove(board, newPos, moveUp, moveRight, color, numRepeat - 1, promotion));
            }
        }
        return foundMoves;
    }

    /**
     * Find the moves a King can make
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    private Collection<ChessMove> kingMoves(ChessBoard board, ChessPosition pos, ChessGame.TeamColor color) {

        Collection<ChessMove> totalMoves = new HashSet<>();
        /*
        totalMoves.addAll(genericMove(board, pos, 1, 1, color, 0, null));
        totalMoves.addAll(genericMove(board, pos, 1, 0, color, 0, null));
        totalMoves.addAll(genericMove(board, pos, 1, -1, color, 0, null));
        totalMoves.addAll(genericMove(board, pos, 0, 1, color, 0, null));
        totalMoves.addAll(genericMove(board, pos, 0, -1, color, 0, null));
        totalMoves.addAll(genericMove(board, pos, -1, 1, color, 0, null));
        totalMoves.addAll(genericMove(board, pos, -1, 0, color, 0, null));
        totalMoves.addAll(genericMove(board, pos, -1, -1, color, 0, null));
*/

        int[] moveVector = {-1, 0, 1};
        for (int i : moveVector) {
            for (int j : moveVector) {
                if (i != 0 || j != 0) {
                    totalMoves.addAll(genericMove(board, pos, i, j, color, 0, null));
                }
            }
        }


        return totalMoves;
    }

    /**
     * Find the moves a Queen can make
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    private Collection<ChessMove> queenMoves(ChessBoard board, ChessPosition pos, ChessGame.TeamColor color) {
        Collection<ChessMove> totalMoves = new HashSet<>();
        int[] moveVector = {-1, 0, 1};
        for (int i : moveVector) {
            for (int j : moveVector) {
                if (i != 0 || j != 0) {
                    totalMoves.addAll(genericMove(board, pos, i, j, color, 8, null));
                }
            }
        }
        return totalMoves;
    }

    /**
     * Find the moves a Rook can make
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    private Collection<ChessMove> rookMoves(ChessBoard board, ChessPosition pos, ChessGame.TeamColor color) {
        Collection<ChessMove> totalMoves = new HashSet<>();
        int[] moveVector = {-1, 1};
        for (int i : moveVector) {
            totalMoves.addAll(genericMove(board, pos, i, 0, color, 8, null));
            totalMoves.addAll(genericMove(board, pos, 0, i, color, 8, null));
        }
        return totalMoves;
    }

    /**
     * Find the moves a Bishop can make
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    private Collection<ChessMove> bishopMoves(ChessBoard board, ChessPosition pos, ChessGame.TeamColor color) {
        Collection<ChessMove> totalMoves = new HashSet<>();
        int[] moveVector = {-1, 1};
        for (int i : moveVector) {
            totalMoves.addAll(genericMove(board, pos, i, i, color, 8, null));
        }
        return totalMoves;
    }

    /**
     * Find the moves a Knight can make
     */
    private Collection<ChessMove> knightMoves(ChessBoard board, ChessPosition pos, ChessGame.TeamColor color) {
        Collection<ChessMove> totalMoves = new HashSet<>();
        int[] smallMove = {-1, 1};
        int[] bigMove = {-2, 2};
        for (int i : smallMove) {
            for (int j : bigMove) {
                totalMoves.addAll(genericMove(board, pos, i, j, color, 0, null));
                totalMoves.addAll(genericMove(board, pos, j, i, color, 0, null));
            }
        }
        return totalMoves;
    }

    /**
     * Find the moves a Pawn can make
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    private Collection<ChessMove> pawnMoves(ChessBoard board, ChessPosition pos, ChessGame.TeamColor color) {
        Collection<ChessMove> totalMoves = new HashSet<>();
        boolean promote;
        if (color == ChessGame.TeamColor.WHITE) {
            if (pos.getRow() == 7) {
                promote = true;
            } else {
                promote = false;
            }

            ChessPosition upPos = new ChessPosition(pos.getRow() + 1, pos.getColumn());
            if (board.getPiece(upPos) == null) {
                if (promote) {
                    totalMoves.add(new ChessMove(pos, upPos, PieceType.QUEEN));
                    totalMoves.add(new ChessMove(pos, upPos, PieceType.ROOK));
                    totalMoves.add(new ChessMove(pos, upPos, PieceType.BISHOP));
                    totalMoves.add(new ChessMove(pos, upPos, PieceType.KNIGHT));
                } else {
                    totalMoves.add(new ChessMove(pos, upPos, null));
                }
            }

            ChessPosition leftPos = new ChessPosition(pos.getRow() + 1, pos.getColumn() - 1);
            if (board.getPiece(leftPos).getTeamColor() == ChessGame.TeamColor.BLACK) {
                if (promote) {
                    totalMoves.add(new ChessMove(pos, leftPos, PieceType.QUEEN));
                    totalMoves.add(new ChessMove(pos, leftPos, PieceType.ROOK));
                    totalMoves.add(new ChessMove(pos, leftPos, PieceType.BISHOP));
                    totalMoves.add(new ChessMove(pos, leftPos, PieceType.KNIGHT));
                } else {
                    totalMoves.add(new ChessMove(pos, leftPos, null));
                }
            }

            ChessPosition rightPos = new ChessPosition(pos.getRow() + 1, pos.getColumn() + 1);
            if (board.getPiece(rightPos).getTeamColor() == ChessGame.TeamColor.BLACK) {
                if (promote) {
                    totalMoves.add(new ChessMove(pos, rightPos, PieceType.QUEEN));
                    totalMoves.add(new ChessMove(pos, rightPos, PieceType.ROOK));
                    totalMoves.add(new ChessMove(pos, rightPos, PieceType.BISHOP));
                    totalMoves.add(new ChessMove(pos, rightPos, PieceType.KNIGHT));
                } else {
                    totalMoves.add(new ChessMove(pos, rightPos, null));
                }
            }
        } else {
            if (pos.getRow() == 2) {
                promote = true;
            } else {
                promote = false;
            }

            ChessPosition upPos = new ChessPosition(pos.getRow() - 1, pos.getColumn());
            if (board.getPiece(upPos) == null) {
                if (promote) {
                    totalMoves.add(new ChessMove(pos, upPos, PieceType.QUEEN));
                    totalMoves.add(new ChessMove(pos, upPos, PieceType.ROOK));
                    totalMoves.add(new ChessMove(pos, upPos, PieceType.BISHOP));
                    totalMoves.add(new ChessMove(pos, upPos, PieceType.KNIGHT));
                } else {
                    totalMoves.add(new ChessMove(pos, upPos, null));
                }
            }

            ChessPosition leftPos = new ChessPosition(pos.getRow() - 1, pos.getColumn() - 1);
            if (board.getPiece(leftPos).getTeamColor() == ChessGame.TeamColor.WHITE) {
                if (promote) {
                    totalMoves.add(new ChessMove(pos, leftPos, PieceType.QUEEN));
                    totalMoves.add(new ChessMove(pos, leftPos, PieceType.ROOK));
                    totalMoves.add(new ChessMove(pos, leftPos, PieceType.BISHOP));
                    totalMoves.add(new ChessMove(pos, leftPos, PieceType.KNIGHT));
                } else {
                    totalMoves.add(new ChessMove(pos, leftPos, null));
                }
            }

            ChessPosition rightPos = new ChessPosition(pos.getRow() - 1, pos.getColumn() + 1);
            if (board.getPiece(rightPos).getTeamColor() == ChessGame.TeamColor.WHITE) {
                if (promote) {
                    totalMoves.add(new ChessMove(pos, rightPos, PieceType.QUEEN));
                    totalMoves.add(new ChessMove(pos, rightPos, PieceType.ROOK));
                    totalMoves.add(new ChessMove(pos, rightPos, PieceType.BISHOP));
                    totalMoves.add(new ChessMove(pos, rightPos, PieceType.KNIGHT));
                } else {
                    totalMoves.add(new ChessMove(pos, rightPos, null));
                }
            }
        }
        return totalMoves;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition pos) {
        Collection<ChessMove> allMoves = new HashSet<ChessMove>();
        // Find what piece is moving
        ChessPiece piece = board.getPiece(pos);
        ChessGame.TeamColor color = piece.getTeamColor();

        if (piece.getPieceType() == null) {
            return allMoves;
        } else if (piece.getPieceType() == PieceType.KING) {
            allMoves.addAll(kingMoves(board, pos, color));
        } else if (piece.getPieceType() == PieceType.QUEEN) {
            allMoves.addAll(queenMoves(board, pos, color));
        } else if (piece.getPieceType() == PieceType.ROOK) {
            allMoves.addAll(rookMoves(board, pos, color));
        } else if (piece.getPieceType() == PieceType.BISHOP) {
            allMoves.addAll(bishopMoves(board, pos, color));
        } else if (piece.getPieceType() == PieceType.KNIGHT) {
            allMoves.addAll(knightMoves(board, pos, color));
        } else if (piece.getPieceType() == PieceType.PAWN) {
            allMoves.addAll(pawnMoves(board, pos, color));
        }
        return allMoves;
    }

    @Override
    public String toString() {
        return "ChessPiece{" +
                "color=" + pieceColor +
                ", type=" + pieceType +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        ChessPiece that = (ChessPiece) object;
        return pieceColor == that.pieceColor && pieceType == that.pieceType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, pieceType);
    }
}
