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
        Map<Integer, String> colNames = HashMap.newHashMap(8);
        colNames.put(1, "A");
        colNames.put(2, "B");
        colNames.put(3, "C");
        colNames.put(4, "D");
        colNames.put(5, "E");
        colNames.put(6, "F");
        colNames.put(7, "G");
        colNames.put(8, "H");

        return colNames.get(col) + row;
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
