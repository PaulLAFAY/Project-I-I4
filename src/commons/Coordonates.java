package commons;

public class Coordonates {

    public static final String COORD_SEPARATOR = ";";

    private String column;
    private String line;
    private int x;
    private int y;

    public Coordonates(String command) {
        String[] str = command.split(COORD_SEPARATOR);
        column = str[1];
        line = str[2];
    }

    public Coordonates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coordonates(String column, String line) {
        this.column = column;
        this.line = line;
    }

    public String toString() {
        return column + COORD_SEPARATOR + line;
    }

    public String getColumn() {
        return column;
    }

    public String getLine() {
        return line;
    }

    public int getIColumn() {
        return ((int) column.charAt(0)) - 64;
    }

    public int getILine() {
        return Integer.parseInt(line);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

}
