package commons;

import java.util.ArrayList;

public class Board {

    public static final String SEPARATOR_1 = "-";
    public static final String SEPARATOR_2 = "\t";

    private Stone[][] stones;
    private ArrayList<Chain> chains;

    private final boolean debug;

    public Board(final boolean debug) {
        stones = new Stone[19][19];
        for (int i = 0; i < stones.length; i++)
            for (int j = 0; j < stones.length; j++)
                stones[j][i] = new Stone(new Coordonates(j, i));
        this.debug = debug;
    }

    public String toString() {
        String board = "";

        for (int i = 0; i < stones.length; i++) {
            for (int j = 0; j < stones.length; j++)
                if (j < stones.length - 1)
                    board += stones[j][i].toString() + SEPARATOR_1;
                else
                    board += stones[j][i].toString();
            board += SEPARATOR_2;
        }

        return board;
    }

    public void print() {
        String board = "";
        if (debug)
            board += "    A   B   C   D   E   F   G   H   I   J   K   L   M   N   O   P   Q   R   S\n";
        else
            board += "   A   B   C   D   E   F   G   H   I   J   K   L   M   N   O   P   Q   R   S\n";
        for (int i = 0; i < stones.length; i++) {
            if (i < 9)
                if (debug)
                    board += "0" + (i + 1) + " ";
                else
                    board += "0" + (i + 1) + " ";
            else if (debug)
                board += (i + 1) + " ";
            else
                board += (i + 1) + " ";
            for (int j = 0; j < stones.length; j++)
                if (j < stones.length - 1) {
                    if (debug)
                        board += stones[j][i].getNbNeighbour() + stones[j][i].toString()
                                + stones[j][i].getNbfreedomDegrees() + "-";
                    else
                        board += stones[j][i].toString() + "---";
                } else if (debug)
                    board += stones[j][i].getNbNeighbour() + stones[j][i].toString()
                            + stones[j][i].getNbfreedomDegrees();
                else
                    board += stones[j][i].toString();
            board += "\n";
            if (i < stones.length - 1) {
                if (debug)
                    board += " ";
                for (int j = 0; j < stones.length; j++)
                    board += "   |";
            }
            board += "\n";
        }
        System.out.println(board);
    }

    public void updateBoard(String command) {
        String[] lines = command.split(SEPARATOR_2);
        for (int i = 1; i < lines.length; i++) {
            String[] values = lines[i].split(SEPARATOR_1);
            for (int j = 0; j < values.length; j++) {
                if (values[j].equals(Stone.black))
                    stones[j][i - 1] = new Stone(Stone.black, new Coordonates(j, i - 1));
                else if (values[j].equals(Stone.white))
                    stones[j][i - 1] = new Stone(Stone.white, new Coordonates(j, i - 1));
                else if (values[j].equals(Stone.free))
                    stones[j][i - 1] = new Stone(Stone.free, new Coordonates(j, i - 1));
                else
                    System.out.println("Error : invalid value was send as board.");
            }
        }
    }

    public void setStone(Coordonates coord, int turn) {
        if (turn % 2 == 0)
            stones[coord.getIColumn() - 1][coord.getILine() - 1] = new Stone(Stone.white,
                    new Coordonates(coord.getIColumn() - 1, coord.getILine() - 1));
        else
            stones[coord.getIColumn() - 1][coord.getILine() - 1] = new Stone(Stone.black,
                    new Coordonates(coord.getIColumn() - 1, coord.getILine() - 1));
    }

    public Stone getStone(Coordonates coord) {
        return stones[coord.getIColumn() - 1][coord.getILine() - 1];
    }

    public void parseBoard() {
        for (int i = 0; i < stones.length; i++)
            for (int j = 0; j < stones.length; j++)
                stones[j][i].resetStats();
        setNeighbours();
        setFreedoms();
        setChains();
        for (int i = 0; i < chains.size() - 1; i++)
            if (chains.get(i).getNbfreedoms() == 0) {
                System.out.println("Chain " + i + " will be removed.");
                for (int j = 0; j < chains.get(i).getNbStone(); j++)
                    stones[chains.get(i).getStone(j).getCoordonates().getX()][chains.get(i).getStone(j).getCoordonates()
                            .getY()].resetStone();
                chains.remove(i);
            }
        System.out.println("nb chain : " + chains.size());
        for (int i = 0; i < chains.size(); i++) {
            System.out.print("chain " + i + " : " + chains.get(i).getNbStone() + " s. | ");
            for (int j = 0; j < chains.get(i).getNbStone(); j++)
                System.out.print("(" + (chains.get(i).getStone(j).getCoordonates().getX() + 1) + ";"
                        + (chains.get(i).getStone(j).getCoordonates().getY() + 1) + ") ");
            System.out.println();
        }
    }

    private void setNeighbours() {
        for (int i = 0; i < stones.length; i++) {
            for (int j = 0; j < stones.length; j++) {
                if (i < stones.length - 1 && stones[j][i].getColor() != Stone.free
                        && stones[j][i].getColor() == stones[j][i + 1].getColor())
                    stones[j][i].setNeighbour(Stone.DOWN, true);
                if (i > 0 && stones[j][i].getColor() != Stone.free
                        && stones[j][i].getColor() == stones[j][i - 1].getColor())
                    stones[j][i].setNeighbour(Stone.UP, true);
                if (j < stones.length - 1 && stones[j][i].getColor() != Stone.free
                        && stones[j][i].getColor() == stones[j + 1][i].getColor())
                    stones[j][i].setNeighbour(Stone.RIGHT, true);
                if (j > 0 && stones[j][i].getColor() != Stone.free
                        && stones[j][i].getColor() == stones[j - 1][i].getColor())
                    stones[j][i].setNeighbour(Stone.LEFT, true);
            }
        }
    }

    private void setFreedoms() {
        for (int i = 0; i < stones.length; i++) {
            for (int j = 0; j < stones.length; j++) {
                if (i < stones.length - 1 && stones[j][i].getColor() != Stone.free
                        && stones[j][i + 1].getColor() == Stone.free)
                    stones[j][i].setFreedom(Stone.DOWN);
                if (i > 0 && stones[j][i].getColor() != Stone.free && stones[j][i - 1].getColor() == Stone.free)
                    stones[j][i].setFreedom(Stone.UP);
                if (j < stones.length - 1 && stones[j][i].getColor() != Stone.free
                        && stones[j + 1][i].getColor() == Stone.free)
                    stones[j][i].setFreedom(Stone.RIGHT);
                if (j > 0 && stones[j][i].getColor() != Stone.free && stones[j - 1][i].getColor() == Stone.free)
                    stones[j][i].setFreedom(Stone.LEFT);
            }
        }
    }

    private void setChains() {
        chains = new ArrayList<Chain>();
        Stone[][] stoneAlt = new Stone[19][19];
        for (int i = 0; i < stones.length; i++)
            for (int j = 0; j < stones.length; j++) {
                stoneAlt[j][i] = new Stone(new Coordonates(j, i));
                stoneAlt[j][i].copy(stones[j][i]);
            }
        Coordonates cursor = new Coordonates(0, 0);
        ArrayList<Coordonates> cursorPile = new ArrayList<Coordonates>();
        for (int i = 0; i < stones.length; i++) {
            for (int j = 0; j < stones.length; j++) {
                if (stoneAlt[j][i].getColor() != Stone.free && !stoneAlt[j][i].isChained()) {
                    chains.add(new Chain());
                    cursor.setXY(j, i);
                    while (!chains.get(chains.size() - 1).isComplete()) {
                        if (!stoneAlt[cursor.getX()][cursor.getY()].isChained())
                            chains.get(chains.size() - 1).addStone(stoneAlt[cursor.getX()][cursor.getY()]);
                        if (stoneAlt[cursor.getX()][cursor.getY()].getNbNeighbour() == 0)
                            cursor = noNeighbour(stoneAlt, cursor, cursorPile);
                        else if (stoneAlt[cursor.getX()][cursor.getY()].getNbNeighbour() == 1)
                            cursor = oneNeighbour(stoneAlt, cursor);
                        else if (stoneAlt[cursor.getX()][cursor.getY()].getNbNeighbour() > 1)
                            cursor = manyNeighbour(stoneAlt, cursor, cursorPile);
                    }
                }
            }
        }
    }

    private Coordonates noNeighbour(Stone[][] stoneAlt, Coordonates cursor, ArrayList<Coordonates> cursorPile) {
        if (cursorPile.isEmpty())
            chains.get(chains.size() - 1).setComplete();
        else {
            cursor.setX(cursorPile.get(cursorPile.size() - 1).getX());
            cursor.setY(cursorPile.get(cursorPile.size() - 1).getY());
            cursorPile.remove(cursorPile.size() - 1);
            if (stoneAlt[cursor.getX()][cursor.getY()].getNbNeighbour() > 1)
                cursorPile.add(new Coordonates(cursor.getX(), cursor.getY()));
            cursor = moveCursor(stoneAlt, cursor);
        }
        return cursor;
    }

    private Coordonates oneNeighbour(Stone[][] stoneAlt, Coordonates cursor) {
        cursor = moveCursor(stoneAlt, cursor);
        return cursor;
    }

    private Coordonates manyNeighbour(Stone[][] stoneAlt, Coordonates cursor, ArrayList<Coordonates> cursorPile) {
        cursorPile.add(new Coordonates(cursor.getX(), cursor.getY()));
        cursor = moveCursor(stoneAlt, cursor);
        return cursor;
    }

    private Coordonates moveCursor(Stone[][] stoneAlt, Coordonates cursor) {
        if (stoneAlt[cursor.getX()][cursor.getY()].hasNeighbour(Stone.DOWN)) {
            stoneAlt[cursor.getX()][cursor.getY()].setNeighbour(Stone.DOWN, false);
            cursor.setY(cursor.getY() + 1);
            stoneAlt[cursor.getX()][cursor.getY()].setNeighbour(Stone.UP, false);
        } else if (stoneAlt[cursor.getX()][cursor.getY()].hasNeighbour(Stone.UP)) {
            stoneAlt[cursor.getX()][cursor.getY()].setNeighbour(Stone.UP, false);
            cursor.setY(cursor.getY() - 1);
            stoneAlt[cursor.getX()][cursor.getY()].setNeighbour(Stone.DOWN, false);
        } else if (stoneAlt[cursor.getX()][cursor.getY()].hasNeighbour(Stone.RIGHT)) {
            stoneAlt[cursor.getX()][cursor.getY()].setNeighbour(Stone.RIGHT, false);
            cursor.setX(cursor.getX() + 1);
            stoneAlt[cursor.getX()][cursor.getY()].setNeighbour(Stone.LEFT, false);
        } else if (stoneAlt[cursor.getX()][cursor.getY()].hasNeighbour(Stone.LEFT)) {
            stoneAlt[cursor.getX()][cursor.getY()].setNeighbour(Stone.LEFT, false);
            cursor.setX(cursor.getX() - 1);
            stoneAlt[cursor.getX()][cursor.getY()].setNeighbour(Stone.RIGHT, false);
        }
        return cursor;
    }
}
