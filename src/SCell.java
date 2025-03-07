import java.util.Arrays;

/**
 * The documentation of this class was removed as of Ex4...
 */
public class SCell implements Cell {
    private String _line;
    private int order =0;
    int type = Ex2Utils.TEXT;
    public SCell() {this("");}
    public SCell(String s) {setData(s);}

    @Override
    public int getOrder() {
        return order;
    }

    //@Override
    @Override
    public String toString() {
        return getData();
    }

    @Override
public void setData(String s) {
        _line = s;
        if (s.startsWith("=")) {
            String functionName = s.substring(1, s.indexOf('(')).toLowerCase();
            System.out.println(functionName);
            if (Arrays.asList(Ex2Utils.FUNCTIONS).contains(functionName)) {
                type = Ex2Utils.FORM;
            } else {
                type = Ex2Utils.ERR_FORM_FORMAT;
            }
        } else if (isNumber(s)) {
            type = Ex2Utils.NUMBER;
        } else {
            type = Ex2Utils.TEXT;
        }
    }
    @Override
    public String getData() {
        return _line;
    }

    @Override
    public int getType() {
        return type;
    }

    @Override
    public void setType(int t) {
        type = t;
    }

    @Override
    public void setOrder(int t) {
        this.order = t;
    }
    public static boolean isNumber(String line) {
        boolean ans = false;
        try {
            Double.parseDouble(line);
            ans = true;
        }
        catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
        }
        return ans;
    }
}
