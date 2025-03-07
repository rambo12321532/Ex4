/**
 * Represents a rectangular range of cells in a spreadsheet, defined by two CellEntry objects.
 */
public class Range2D {
    private final CellEntry topLeft;
    private final CellEntry bottomRight;

    /**
     * Constructs a Range2D given two CellEntry objects representing the diagonal corners.
     */
    public Range2D(CellEntry cell1, CellEntry cell2) {
        int minX = Math.min(cell1.getX(), cell2.getX());
        int minY = Math.min(cell1.getY(), cell2.getY());
        int maxX = Math.max(cell1.getX(), cell2.getX());
        int maxY = Math.max(cell1.getY(), cell2.getY());
        this.topLeft = new CellEntry(minX, minY);
        this.bottomRight = new CellEntry(maxX, maxY);
    }

    /**
     * Returns true if the given CellEntry is within this range.
     */
    public boolean contains(CellEntry cell) {
        return cell.getX() >= topLeft.getX() && cell.getX() <= bottomRight.getX()
                && cell.getY() >= topLeft.getY() && cell.getY() <= bottomRight.getY();
    }

    /**
     * Returns an array of all CellEntry objects within this range.
     */
    public CellEntry[] getCells() {
        int width = bottomRight.getX() - topLeft.getX() + 1;
        int height = bottomRight.getY() - topLeft.getY() + 1;
        CellEntry[] cells = new CellEntry[width * height];
        int index = 0;
        for (int x = topLeft.getX(); x <= bottomRight.getX(); x++) {
            for (int y = topLeft.getY(); y <= bottomRight.getY(); y++) {
                cells[index++] = new CellEntry(x, y);
            }
        }
        return cells;
    }

    /**
     * Returns the top-left corner of this range.
     */
    public CellEntry getTopLeft() {
        return topLeft;
    }

    /**
     * Returns the bottom-right corner of this range.
     */
    public CellEntry getBottomRight() {
        return bottomRight;
    }

    /**
     * Returns the number of rows in this range.
     */
    public int getRowCount() {
        return bottomRight.getY() - topLeft.getY() + 1;
    }

    /**
     * Returns the number of columns in this range.
     */
    public int getColumnCount() {
        return bottomRight.getX() - topLeft.getX() + 1;
    }

    /**
     * Parses a range string (e.g., "A1:C5") and returns a Range2D object.
     *
     * @param rangeString the range string to parse
     * @return a Range2D object representing the parsed range
     * @throws IllegalArgumentException if the range string is invalid
     */
    public static Range2D parse(String rangeString) {
        String[] parts = rangeString.split(":");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid range format: " + rangeString);
        }
        CellEntry topLeft = CellEntry.parse(parts[0]);
        CellEntry bottomRight = CellEntry.parse(parts[1]);
        return new Range2D(topLeft, bottomRight);
    }

    @Override
    public String toString() {
        return topLeft.toCell() + ":" + bottomRight.toCell();
    }
}