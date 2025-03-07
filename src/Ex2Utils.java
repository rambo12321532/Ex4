import java.util.Arrays;
import java.util.Stack;

/**
 * This class contains a set of constants for Ex2 (I2CS, ArielU 2025A),
 * As defined in: https://docs.google.com/document/d/1-18T-dj00apE4k1qmpXGOaqttxLn-Kwi/edit?usp=sharing&ouid=113711744349547563645&rtpof=true&sd=true
 * Do NOT change this class!
 *
 */
public class Ex2Utils {
    public static final int TEXT=1, NUMBER=2, FORM=3, ERR_FORM_FORMAT=-2, ERR_CYCLE_FORM=-1, ERR=-1;
    public static final String ERR_CYCLE = "ERR_CYCLE!", ERR_FORM = "ERR_FORM!";
    public static final int WIDTH = 9, HEIGHT=17, MAX_CHARS=8, WINDOW_WIDTH=1200, WINDOW_HEIGHT=600;
    public static final int WAIT_TIME_MS = 10, MAX_X=20;
    public static final double EPS1 = 0.001, EPS2=EPS1*EPS1, EPS=EPS2, PEN_RADIUS = 0.001;
    public static final double GUI_X_SPACE = 2, GUI_X_START = 3, GUI_Y_TEXT_START = 0.4;
    public static boolean Debug = false;
    public static final String[] M_OPS = {"+", "-", "*", "/"};
    public static  final String[] ABC= {"A","B","C","D","E","F","G","H","I","J","K","L","O","M","N","P","Q","R","S","T","U","V","W","X","Y","Z"};

    // ****** not implemented in Ex2!! ******
    public static final String[] FUNCTIONS = {"if", "sin", "cos", "pow", "min", "max", "sum", "average"};
    // public static String[] B_OPS = {"<", ">", "==","!=", "<=", ">="};

    public static final int IF_ERR = -3, FUNC_ERR = -4;
    public static final String ERR_IF = "IF_ERR!", ERR_FUNC = "FUNC_ERR!";

    public static String evaluateIf(String condition, String ifTrue, String ifFalse, Ex2Sheet sheet) {
        try {
            String[] parts = condition.split(" ");
            if (parts.length < 3) {
                return ERR_IF;
            }

            double leftValue = evaluateExpression(parts[0], sheet);
            double rightValue = evaluateExpression(parts[2], sheet);
            String operator = parts[1];

            boolean result;
            switch (operator) {
                case "<":
                    result = leftValue < rightValue;
                    break;
                case ">":
                    result = leftValue > rightValue;
                    break;
                case "==":
                    result = leftValue == rightValue;
                    break;
                case "!=":
                    result = leftValue != rightValue;
                    break;
                case "<=":
                    result = leftValue <= rightValue;
                    break;
                case ">=":
                    result = leftValue >= rightValue;
                    break;
                default:
                    return ERR_IF;
            }

            return getCellValue(result ? ifTrue : ifFalse, sheet);
        } catch (Exception e) {
            return ERR_IF;
        }
    }

    public static String evaluateFunction(String function, Range2D range, Ex2Sheet sheet) {
        try {
            switch (function.toLowerCase()) {
                case "sum":
                    return String.valueOf(Ex2Functions.sum(range, sheet));
                case "min":
                    return String.valueOf(Ex2Functions.min(range, sheet));
                case "max":
                    return String.valueOf(Ex2Functions.max(range, sheet));
                case "average":
                    return String.valueOf(Ex2Functions.average(range, sheet));
                default:
                    return ERR_FUNC;
            }
        } catch (Exception e) {
            return ERR_FUNC;
        }
    }

    private static double evaluateExpression(String expression, Ex2Sheet sheet) {
        expression = expression.trim(); // Trim spaces

        // Check if the expression is a simple cell reference or numeric value
        if (isSimpleExpression(expression)) {
            return parseValue(expression, sheet);
        }

        // Otherwise, evaluate the complex expression
        return evaluateComplexExpression(expression, sheet);
    }

    private static boolean isSimpleExpression(String expression) {
        // Check if the expression is a valid cell reference or a numeric value
        return isSimpleCellReference(expression) || isNumeric(expression);
    }

    private static boolean isSimpleCellReference(String expression) {
        // Check if the expression is a valid cell reference
        return parseCellReference(expression) != null;
    }

    private static boolean isNumeric(String expression) {
        try {
            Double.parseDouble(expression);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static double evaluateComplexExpression(String expression, Ex2Sheet sheet) {
        Stack<Double> values = new Stack<>();
        Stack<Character> operators = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (Character.isDigit(c)) {
                StringBuilder sb = new StringBuilder();
                while (i < expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                    sb.append(expression.charAt(i++));
                }
                values.push(Double.parseDouble(sb.toString()));
                i--; // Adjust for the extra increment
            } else if (c == '(') {
                operators.push(c);
            } else if (c == ')') {
                while (!operators.isEmpty() && operators.peek() != '(') {
                    values.push(applyOp(operators.pop(), values.pop(), values.pop()));
                }
                operators.pop(); // Pop '('
            } else if (isOperator(c)) {
                if (!operators.isEmpty() && operators.peek() == '(') {
                    operators.push(c);
                } else {
                    while (!operators.isEmpty() && hasPrecedence(c, operators.peek())) {
                        values.push(applyOp(operators.pop(), values.pop(), values.pop()));
                    }
                    operators.push(c);
                }
            } else if (Character.isLetter(c)) {
                StringBuilder sb = new StringBuilder();
                while (i < expression.length() && Character.isLetterOrDigit(expression.charAt(i))) {
                    sb.append(expression.charAt(i++));
                }
                int[] cell = parseCellReference(sb.toString());
                if (cell != null) {
                    String evalResult = sheet.eval(cell[0], cell[1]); // Get cell value
                    if (evalResult == null) throw new IllegalArgumentException("Invalid cell reference: " + sb.toString());
                    values.push(Double.parseDouble(evalResult));
                } else {
                    throw new IllegalArgumentException("Invalid cell reference: " + sb.toString());
                }
                i--; // Adjust for the extra increment
            }
        }

        while (!operators.isEmpty()) {
            values.push(applyOp(operators.pop(), values.pop(), values.pop()));
        }

        return values.pop();
    }

    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    private static boolean hasPrecedence(char op1, char op2) {
        return (op1 != '*' && op1 != '/') || (op2 != '+' && op2 != '-');
    }

    private static double applyOp(char op, double b, double a) {
        switch (op) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0) throw new UnsupportedOperationException("Cannot divide by zero");
                return a / b;
        }
        return 0;
    }

    /**
     * Parses a value: returns a numeric value if possible, else evaluates a cell reference.
     */
    private static double parseValue(String input, Ex2Sheet sheet) {
        try {
            return Double.parseDouble(input); // Direct number
        } catch (NumberFormatException e) {
            int[] cell = parseCellReference(input);
            if (cell != null) {
                String evalResult = sheet.eval(cell[0], cell[1]); // Get cell value
                if (evalResult == null) throw new IllegalArgumentException("Invalid cell reference: " + input);
                return Double.parseDouble(evalResult);
            }
            throw new IllegalArgumentException("Invalid input: " + input);
        }
    }

    /**
     * Parses a cell reference (e.g., "A1" -> {row, col}).
     */
    private static int[] parseCellReference(String ref) {
        if (ref.length() < 2) return null;

        String colStr = ref.substring(0, 1);
        String rowStr = ref.substring(1);

        try {
            int col = Arrays.asList(Ex2Utils.ABC).indexOf(colStr.toUpperCase());
            int row = Integer.parseInt(rowStr);

            if (col >= 0 && row >= 0) {
                return new int[]{col, row};
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    /**
     * Retrieves the value from a cell reference or returns the direct value.
     */
    private static String getCellValue(String expr, Ex2Sheet sheet) {
        try {
            double evaluatedValue = evaluateExpression(expr, sheet);
            return String.valueOf(evaluatedValue);
        } catch (Exception e) {
            return expr; // Return the original expression if evaluation fails
        }
    }
}
