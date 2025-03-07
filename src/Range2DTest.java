import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Range2DTest {

    @Test
    public void testRangeCreation() {
        CellEntry cell1 = new CellEntry("A1");
        CellEntry cell2 = new CellEntry("C3");
        Range2D range = new Range2D(cell1, cell2);

        assertEquals("A1:C3", range.toString(), "Range string representation should match");
    }
    @Test
    public void testContains() {
        CellEntry cell1 = new CellEntry("A1");
        CellEntry cell2 = new CellEntry("C3");
        Range2D range = new Range2D(cell1, cell2);

        assertTrue(range.contains(new CellEntry("B2")), "B2 should be inside the range");
        assertTrue(range.contains(new CellEntry("A1")), "A1 should be inside the range");
        assertTrue(range.contains(new CellEntry("C3")), "C3 should be inside the range");
        assertFalse(range.contains(new CellEntry("D4")), "D4 should be outside the range");
    }

    @Test
    public void testGetCells() {
        CellEntry cell1 = new CellEntry("A1");
        CellEntry cell2 = new CellEntry("B2");
        Range2D range = new Range2D(cell1, cell2);

        CellEntry[] cells = range.getCells();
        assertEquals(4, cells.length, "Range A1:B2 should contain 4 cells");
        assertEquals("A1", cells[0].toCell(), "First cell should be A1");
        assertEquals("B2", cells[3].toCell(), "Last cell should be B2");
    }
}
