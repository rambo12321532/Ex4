import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Ex2UtilsTest {
    private Ex2Sheet sheet;

    @BeforeEach
    void setUp() {
        sheet = new Ex2Sheet();
        // Set up test values in the sheet
        sheet.set(0, 0, "10");  // A0 = 10
        sheet.set(0, 1, "12");  // A1 = 12
        sheet.set(0, 2, "67");  // A2 = 67
        sheet.set(0, 3, "-8040");  // A3 = -8040
        sheet.set(1, 0, "20");  // B0 = 20
        sheet.set(2, 0, "30");  // C0 = 30
    }

    @Test
    void complexTest(){
        assertEquals("13.0", Ex2Utils.evaluateIf("a1*a2 != a3/(2-a1)", "a2+2", "a1+1", sheet));
    }

    @Test
    void testValidConditions() {
        assertEquals("YES", Ex2Utils.evaluateIf("10 > 5", "YES", "NO", sheet));
        assertEquals("NO", Ex2Utils.evaluateIf("10 < 5", "YES", "NO", sheet));
        assertEquals("PASS", Ex2Utils.evaluateIf("10 == 10", "PASS", "FAIL", sheet));
        assertEquals("FAIL", Ex2Utils.evaluateIf("10 != 10", "PASS", "FAIL", sheet));
        assertEquals("OK", Ex2Utils.evaluateIf("5 <= 5", "OK", "NOT OK", sheet));
        assertEquals("NOT OK", Ex2Utils.evaluateIf("6 <= 5", "OK", "NOT OK", sheet));
        assertEquals("CORRECT", Ex2Utils.evaluateIf("30 >= 30", "CORRECT", "INCORRECT", sheet));
        assertEquals("INCORRECT", Ex2Utils.evaluateIf("29 >= 30", "CORRECT", "INCORRECT", sheet));

    }

    @Test
    void testCellReferences() {
        assertEquals("YES", Ex2Utils.evaluateIf("a0 < b0", "YES", "NO", sheet)); // 10 < 20 -> YES
        assertEquals("NO", Ex2Utils.evaluateIf("C0 < B0", "YES", "NO", sheet)); // 30 < 20 -> NO
    }

    @Test
    void testInvalidConditions() {
        assertEquals(Ex2Utils.ERR_IF, Ex2Utils.evaluateIf("10", "YES", "NO", sheet)); // Missing operator and second operand
        assertEquals(Ex2Utils.ERR_IF, Ex2Utils.evaluateIf("A0 + B0", "YES", "NO", sheet)); // Invalid format
        assertEquals(Ex2Utils.ERR_IF, Ex2Utils.evaluateIf("Invalid > 5", "YES", "NO", sheet)); // Invalid number
        assertEquals(Ex2Utils.ERR_IF, Ex2Utils.evaluateIf("10 >> 5", "YES", "NO", sheet)); // Invalid operator
    }

    @Test
    void testEdgeCases() {
        assertEquals("YES", Ex2Utils.evaluateIf("0 >= 0", "YES", "NO", sheet)); // Zero comparison
        assertEquals("YES", Ex2Utils.evaluateIf("-5 < 0", "YES", "NO", sheet)); // Negative numbers
        assertEquals("YES", Ex2Utils.evaluateIf("A0 == 10", "YES", "NO", sheet)); // Cell reference equality
    }
}
