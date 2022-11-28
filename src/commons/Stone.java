package commons;

public class Stone {

    public static final String black = "X";
    public static final String white = "O";
    public static final String free = " ";

    public static final int UP = 0;
    public static final int RIGHT = 1;
    public static final int DOWN = 2;
    public static final int LEFT = 3;

    private Coordonates coordonates;
    private String color;
    private int nbNeighbour;
    private boolean neighbours[];
    private int freedomDegrees;
    private boolean freedoms[];
    private boolean isChained;

    public Stone(Coordonates coordonates) {
        this.coordonates = new Coordonates(coordonates.getX(), coordonates.getY());
        color = free;
        nbNeighbour = 0;
        neighbours = new boolean[4];
        for (int i = 0; i < neighbours.length; i++)
            neighbours[i] = false;
        freedomDegrees = 0;
        freedoms = new boolean[4];
        for (int i = 0; i < freedoms.length; i++)
            freedoms[i] = false;
        isChained = false;
    }

    public Stone(String color, Coordonates coordonates) {
        this.color = color;
        this.coordonates = new Coordonates(coordonates.getX(), coordonates.getY());
        nbNeighbour = 0;
        neighbours = new boolean[4];
        for (int i = 0; i < neighbours.length; i++)
            neighbours[i] = false;
        freedomDegrees = 0;
        freedoms = new boolean[4];
        for (int i = 0; i < freedoms.length; i++)
            freedoms[i] = false;
        isChained = false;
    }

    public String toString() {
        if (color.equals(black))
            return black;
        else if (color.equals(white))
            return white;
        else if (color.equals(free))
            return free;
        else {
            System.out.println("Error : NULL");
            return null;
        }
    }

    public Coordonates getCoordonates() {
        return coordonates;
    }

    public String getColor() {
        return color;
    }

    public void resetStats() {
        for (int i = 0; i < 4; i++) {
            neighbours[i] = false;
            freedoms[i] = false;
        }
        nbNeighbour = 0;
        freedomDegrees = 0;
        isChained = false;
    }

    public void resetStone() {
        color = free;
        for (int i = 0; i < 4; i++) {
            neighbours[i] = false;
            freedoms[i] = false;
        }
        nbNeighbour = 0;
        freedomDegrees = 0;
        isChained = false;
        System.out.println("stone remove (" + coordonates.getX() + ";" + coordonates.getY() + ")");
    }

    public void setNeighbour(int direction, boolean bool) {
        neighbours[direction] = bool;
        if (bool)
            nbNeighbour++;
        else
            nbNeighbour--;
    }

    public int getNbNeighbour() {
        return nbNeighbour;
    }

    public void setFreedom(int direction) {
        freedoms[direction] = true;
        freedomDegrees++;
    }

    public int getNbfreedomDegrees() {
        return freedomDegrees;
    }

    public boolean isChained() {
        return isChained;
    }

    public void setChained(boolean bool) {
        isChained = bool;
    }

    public boolean hasNeighbour(int direction) {
        return neighbours[direction];
    }

    public boolean getNeighbours(int index) {
        return neighbours[index];
    }

    public boolean getFreedoms(int index) {
        return freedoms[index];
    }

    public void copy(Stone stone) {
        this.color = stone.getColor();
        this.nbNeighbour = stone.getNbNeighbour();
        this.freedomDegrees = stone.getNbfreedomDegrees();
        for (int i = 0; i < 4; i++) {
            this.neighbours[i] = stone.getNeighbours(i);
            this.freedoms[i] = stone.getFreedoms(i);
        }
        this.isChained = stone.isChained();
    }

}
