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
        colNames.put(1, "a");
        colNames.put(2, "b");
        colNames.put(3, "c");
        colNames.put(4, "d");
        colNames.put(5, "e");
        colNames.put(6, "f");
        colNames.put(7, "g");
        colNames.put(8, "h");

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
