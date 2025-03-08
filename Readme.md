# Spreadsheet Function Usage Guide

## Data

Let's assume the following data is in the spreadsheet:

|   | A  | B  | C  | D  |
|---|----|----|----|----|
| 1 | 10 | 20 | 30 | 40 |
| 2 | 5  | 15 | 25 | 35 |
| 3 | 2  | 4  | 6  | 8  |
| 4 | 1  | 3  | 5  | 7  |

## Functions

### `if` Function

`=if(A1 > B1, "A1 is greater", "B1 is greater")`

This formula checks if the value in cell `A1` is greater than the value in cell `B1`. If true, it returns "A1 is greater"; otherwise, it returns "B1 is greater".

### `sum` Function

`=sum(A1:A4)`

This formula calculates the sum of the values in the range `A1` to `A4`.

### `min` Function

`=min(A1:A4)`

This formula finds the minimum value in the range `A1` to `A4`.

### `max` Function

`=max(A1:A4)`

 This formula finds the maximum value in the range `A1` to `A4`.

### `average` Function

**Formula**: `=average(A1:A4)`

**Explanation**: This formula calculates the average of the values in the range `A1` to `A4`.

## How to Enter and Use Functions

1. **Open the Spreadsheet**: Open the spreadsheet application.

2. **Enter Data**: Enter the example data into the spreadsheet cells.

3. **Enter Formulas**: Enter the formulas into the desired cells. For example:
    - To enter the `if` function, type `=if(A1 > B1, "A1 is greater", "B1 is greater")` into cell `E1`.
    - To enter the `sum` function, type `=sum(A1:A4)` into cell `E2`.
    - To enter the `min` function, type `=min(A1:A4)` into cell `E3`.
    - To enter the `max` function, type `=max(A1:A4)` into cell `E4`.
    - To enter the `average` function, type `=average(A1:A4)` into cell `E5`.