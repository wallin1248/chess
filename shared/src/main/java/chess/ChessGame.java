package chess;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

/**
 * A class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    ChessBoard chessBoard;
    TeamColor whichTeamTurn;

    public ChessGame() {
        chessBoard.resetBoard();
    }
    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return this.whichTeamTurn;
    }

    /**
     * Sets which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        this.whichTeamTurn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets all valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        // Find out which piece is in that spot
        ChessPiece piece = chessBoard.getPiece(startPosition);
        // Return null if there is no piece there
        if (piece == null) {
            return null;
        }
        // Find the moves that are possible but not necessarily legal
        Collection<ChessMove> allMoves = piece.pieceMoves(chessBoard, startPosition);
        // Remove the illegal moves ie the ones that put you in check
        for (ChessMove move : allMoves) {
            // Create a fake board to simulate this move being made
            ChessBoard newBoard = this.getBoard();
            newBoard.addPiece(move.getStartPosition(), null);
            newBoard.addPiece(move.getEndPosition(), chessBoard.getPiece(move.getStartPosition()));
            // Check if the king is in check

        }
        // Return all legal moves
        return piece.pieceMoves(chessBoard, startPosition);
    }

    /**
     * Forces a move in the chess game, regardless of legality
     *
     * @param move chess move to perform
     */
    private void forceMove(ChessMove move) {
        // Find the piece at the location the move starts at
        ChessPiece movingPiece = chessBoard.getPiece(move.getStartPosition());
        // Make the move
        chessBoard.addPiece(move.getStartPosition(), null);
        chessBoard.addPiece(move.getEndPosition(), movingPiece);
        // Set the team to the other side
        TeamColor setTurn;
        if (movingPiece.getTeamColor() == TeamColor.WHITE) {
            setTurn = TeamColor.BLACK;
        } else {
            setTurn = TeamColor.WHITE;
        }
        setTeamTurn(setTurn);
    }

    /**
     * Makes a move in the chess game
     *
     * @param move chess move to perform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        // Find the piece at the location the move starts at
        ChessPiece movingPiece = chessBoard.getPiece(move.getStartPosition());
        // Check there actually is a piece there
        if (movingPiece == null) {
            throw new InvalidMoveException("There is no piece there to move");
        }
        // Check it is on the right team
        if (movingPiece.getTeamColor() != whichTeamTurn) {
            throw new InvalidMoveException("Piece of wrong team tried to move");
        }
        // Ensure the move is included in the validMoves function
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        // Find every piece on the other team
        // Simulate every single move the other side can make
        // If any of those moves capture opponent's king, break and return True.
        // If none meet that condition, it is not checkmate. Return false.
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        // Make sure it is the given team's turn
        // Make sure the king is in check
        // Generate all validMoves for that team's pieces
        // If none, it is checkmate
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
        // Make sure it is the given team's turn
        // Make sure the king is NOT in check
        // Generate all validMoves for that team's pieces
        // If none, it is checkmate
        throw new RuntimeException("Not implemented");
    }

    /**
     * Sets this game's chessboard to a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.chessBoard = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return this.chessBoard;
    }

    @Override
    public String toString() {
        return "ChessGame{" +
                "chessBoard=" + chessBoard +
                ", whichTeamTurn=" + whichTeamTurn +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        ChessGame chessGame = (ChessGame) object;
        return Objects.equals(chessBoard, chessGame.chessBoard) && whichTeamTurn == chessGame.whichTeamTurn;
    }

    @Override
    public int hashCode() {
        return Objects.hash(chessBoard, whichTeamTurn);
    }
}
