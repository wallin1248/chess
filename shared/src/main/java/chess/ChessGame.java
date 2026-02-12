package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    private TeamColor teamTurn;
    private ChessBoard board;
    private boolean[] couldCastle;
    private boolean couldEnPassant;

    // This is supposed to make the class able to be initialized without giving a teamTurn
    public ChessGame() {
        new ChessGame(TeamColor.WHITE);
    }

    public ChessGame(TeamColor teamTurn) {
        // Store the given teamTurn
        this.teamTurn = teamTurn;
        // Initialize the board
        this.board = new ChessBoard();
        this.board.resetBoard();
        // Initialize castling (kingside white, queenside white, kingside black, queenside black)
        this.couldCastle = new boolean[]{true, true, true, true};
        // Initialize enPassant tracker
        this.couldEnPassant = false;
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return this.teamTurn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        this.teamTurn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets valid move(s) for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        // TODO: Implement this
        // Use startPosition and chessBoard to find which piece moves
        // For each of that piece's moves, check if the opponent is able to capture the king after
        // If the opponent can't, add this move to the collection
        // Return the collection of moves this piece can make
        throw new RuntimeException("Not implemented");
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to perform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        // TODO: Implement this
        chess.ChessPosition startPos = move.getStartPosition();
        TeamColor movingTeam = board.getPiece(move.getStartPosition()).getTeamColor();
        // Check if this move is inside of validMoves and if the right team is moving
        if (validMoves(startPos).contains(move) && movingTeam == teamTurn) {
            // If it is, do move
        } else if (movingTeam != teamTurn) {
            throw new InvalidMoveException("Wrong team moving");
        } else if (!validMoves(startPos).contains(move)) {
            throw new InvalidMoveException("Illegal move");
        }
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the team sees the given square(s)
     *
     * @param teamColor which team is looking
     * @param
     * @return True if the specified team is in check
     */
    private boolean canSeeSquare(TeamColor teamColor, Collection<ChessPosition> squares) {
        // TODO
        // Make a collection of every piece on the teamColor's side
        ArrayList<ChessPosition> teamPositions = new ArrayList<>();
        ChessPosition tempPos;
        ChessPiece tempPiece;
        for (int i = 1; i < 8; i++) {
            for (int j = 1; j < 8; j++) {
                tempPos = new ChessPosition(i, j);
                tempPiece = board.getPiece(tempPos);
                if (tempPiece.getTeamColor() == teamColor) {
                    teamPositions.add(tempPos);
                }
            }
        }
        // Use ChessPiece.pieceMoves() to find all squares the teamColor can move to
        ArrayList<ChessPosition> possibleEndPositions = new ArrayList<>();
        for (ChessPosition currPos : teamPositions) {
            ChessPiece currPiece = board.getPiece(currPos);
            Collection<ChessMove> currMoves = currPiece.pieceMoves(board, currPos);
            for (ChessMove move : currMoves) {
                ChessPosition endPos = move.endPosition;
                possibleEndPositions.add(endPos);
            }
        }
        // If all squares are hit at least once return true. Else return false
        for (ChessPosition target : squares) {
            if (!possibleEndPositions.contains(target)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        // TODO
        // Find the king
        // Use canSeeSquare on the king's location
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        // TODO
        // Find the king + the king's possible moves
        // Use canSeeSquare on the king's location + possible moves
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves while not in check.
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        // TODO
        // Find the king's possible moves
        // Use canSeeSquare on the king's possible moves
        throw new RuntimeException("Not implemented");
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return this.board;
    }
}
