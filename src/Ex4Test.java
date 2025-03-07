import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class Ex4Test {
    private Ex2Sheet sheet;
    private Range2D range;

    @BeforeEach
    void setUp() {
        sheet = new Ex2Sheet(5, 5); // Create a 5x5 sheet

        // Fill the sheet with some test data
        sheet.set(0, 0, "10");  // A1
        sheet.set(1, 0, "20");  // B1
        sheet.set(2, 0, "30");  // C1
        sheet.set(0, 1, "5");   // A2
        sheet.set(1, 1, "15");  // B2
        sheet.set(2, 1, "invalid");  // C2 (Non-numeric value)

        range = new Range2D(new CellEntry(0, 0), new CellEntry(2, 1));;
    }

    @Test
    void testMin() {
        assertEquals(5.0, Ex2Functions.min(range, sheet), 0.0001);
    }

    @Test
    void testMax() {
        assertEquals(30.0, Ex2Functions.max(range, sheet), 0.0001);
    }

    @Test
    void testSum() {
        assertEquals(10 + 20 + 30 + 5 + 15, Ex2Functions.sum(range, sheet), 0.0001);
    }

    @Test
    void testAverage() {
        double expectedAvg = (10 + 20 + 30 + 5 + 15) / 5.0; // Only 5 valid numbers
        assertEquals(expectedAvg, Ex2Functions.average(range, sheet), 0.0001);
    }

}
