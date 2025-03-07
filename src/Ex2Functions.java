/**

 Ex2Functions provides basic spreadsheet functions such as min, max, sum, and average.

 It processes numeric data within a specified range of cells while ignoring non-numeric values.
 */

public class Ex2Functions {
    /**

    Parses a string to extract a numeric value.



    @param data The string data to parse.

            @return A Double representing the numeric value, or null if parsing fails.
            */

    private static Double parseNumericData(String data) {
        try {
            double value = Double.parseDouble(data.trim());
            return Double.isNaN(value) ? null : value; // Ignore NaN values
        } catch (NumberFormatException e) {
            return null; // Return null for non-numeric data
        }
    }
    /**

     Computes the minimum numeric value within the given range of cells.



     @param range The range of cells to evaluate.

     @param sheet The sheet containing the cell data.

     @return The minimum numeric value found, or 0 if no valid numbers exist.
     */


    public static double min(Range2D range, Ex2Sheet sheet) {
        Double minValue = null;

        for (CellEntry cell : range.getCells()) {
            String cellData = sheet.get(cell.getX(), cell.getY()).getData();
            System.out.println("Sheet Data: " + cellData);

            Double value = parseNumericData(cellData);
            if (value != null) {
                if (minValue == null || value < minValue) {
                    minValue = value;
                }
            }
        }

        return minValue != null ? minValue : 0; // Return 0 if all values are non-numeric
    }
    /**

     Computes the maximum numeric value within the given range of cells.



     @param range The range of cells to evaluate.

     @param sheet The sheet containing the cell data.

     @return The maximum numeric value found, or 0 if no valid numbers exist.
     */

    public static double max(Range2D range, Ex2Sheet sheet) {
        Double maxValue = null;

        for (CellEntry cell : range.getCells()) {
            String cellData = sheet.get(cell.getX(), cell.getY()).getData();
            System.out.println("Sheet Data: " + cellData);

            Double value = parseNumericData(cellData);
            if (value != null) {
                if (maxValue == null || value > maxValue) {
                    maxValue = value;
                }
            }
        }

        return maxValue != null ? maxValue : 0; // Return 0 if all values are non-numeric
    }
    /**

     Computes the sum of numeric values within the given range of cells.



     @param range The range of cells to evaluate.

     @param sheet The sheet containing the cell data.

     @return The sum of all valid numeric values.
     */


    public static double sum(Range2D range, Ex2Sheet sheet) {
        double sum = 0;

        for (CellEntry cell : range.getCells()) {
            String cellData = sheet.get(cell.getX(), cell.getY()).getData();
            System.out.println("Sheet Data: " + cellData);

            Double value = parseNumericData(cellData);
            if (value != null) {
                sum += value;
            }
        }

        return sum;
    }
    /**

     Computes the average of numeric values within the given range of cells.



     @param range The range of cells to evaluate.

     @param sheet The sheet containing the cell data.

     @return The average of valid numeric values, or 0 if no valid numbers exist.
     */
    public static double average(Range2D range, Ex2Sheet sheet) {
        double sum = 0;
        int count = 0;

        for (CellEntry cell : range.getCells()) {
            String cellData = sheet.get(cell.getX(), cell.getY()).getData();
            System.out.println("Sheet Data: " + cellData);

            Double value = parseNumericData(cellData);
            if (value != null) {
                sum += value;
                count++;
            }
        }

        return count > 0 ? sum / count : 0; // Avoid division by zero
    }
}
