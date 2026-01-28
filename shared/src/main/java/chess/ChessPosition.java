package chess;

/**
 * Represents a single square position on a chess board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPosition {

    private final int row;
    private final int col;

    public ChessPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * @return which row this position is in
     * 1 codes for the bottom row
     */
    public int getRow() { return this.row; }

    /**
     * @return which column this position is in
     * 1 codes for the left row
     */
    public int getColumn() { return this.col; }

    /**
     * Override the equals command for this function specifically.
     *
     * @return true if both moves start and end in the same positions and have the same promotions
     *
     */
    @Override
    public boolean equals(Object otherPosition) {
        // If both are the same reference, return true
        if (this == otherPosition) { return true; }

        // Check if both have the same color and piece type
        ChessPosition chessPosition = (ChessPosition) otherPosition;
        return this.col == chessPosition.col
                && this.row == chessPosition.row;
    }

    /**
     * Override the hashcode command
     * */
    @Override
    public int hashCode() {
        int hash = 0;

        hash += this.col;
        hash += 8 * this.row;
        return hash;
    }

    /**
     * Create the print method for this object.
     */
    @Override
    public String toString() {
        return "ChessMove{Column:" + this.col
                + ", Row:" + this.row
                + "}";
    }
}
