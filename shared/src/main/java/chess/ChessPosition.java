package chess;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Represents a single square position on a chess board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPosition {
    int row;
    int col;

    public ChessPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * @return which row this position is in
     * 1 codes for the bottom row
     */
    public int getRow() {
        return this.row;
    }

    /**
     * @return which column this position is in
     * 1 codes for the left column
     */
    public int getColumn() {
        return this.col;
    }

    @Override
    public String toString() {
        Map<Integer, String> rowNames = HashMap.newHashMap(8);
        rowNames.put(1, "A");
        rowNames.put(2, "B");
        rowNames.put(3, "C");
        rowNames.put(4, "D");
        rowNames.put(5, "E");
        rowNames.put(6, "F");
        rowNames.put(7, "G");
        rowNames.put(8, "H");

        return rowNames.get(row) + col;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        ChessPosition that = (ChessPosition) object;
        return row == that.row && col == that.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }
}
