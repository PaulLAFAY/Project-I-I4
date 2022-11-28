package commons;

import java.util.ArrayList;

public class Chain {

    private ArrayList<Stone> stones;
    private int nbFreedoms;
    private boolean isComplete;

    public Chain() {
        stones = new ArrayList<Stone>();
    }

    public void addStone(Stone stone) {
        stones.add(stone);
        stone.setChained(true);
        nbFreedoms += stones.get(stones.size() - 1).getNbfreedomDegrees();
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete() {
        isComplete = true;
    }

    public int getNbStone() {
        return stones.size();
    }

    public int getNbfreedoms() {
        return nbFreedoms;
    }

    public Stone getStone(int index) {
        return stones.get(index);
    }
}
