package nodes;

import exceptions.TentException;

import java.util.ArrayList;
import java.util.List;

public class Tree extends Node {

    private int y;
    private int x;
    private String name = "T";
    private List<Possibility> possibilities = new ArrayList<>();
    private Tent tent;

    /**
     * Constructor
     *
     * @param x X-Coordinate of the tree
     * @param y Y-Coordinate of the tree
     */
    public Tree(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Method to add a possibility to this tree
     *
     * @param possibility The possibility to add
     */
    public void addPossibility(Possibility possibility) {
        possibilities.add(possibility);
        possibility.addTree(this);
    }

    /**
     * Method to remove a possibility from this tree
     *
     * @param impossibility The possibility to remove
     */
    public void removePossibility(Possibility impossibility) {
        possibilities.remove(impossibility);
    }

    /**
     * Method to transform a possibility into a tent
     *
     * @param possibility The possibility to transform
     * @throws TentException if a tent already exists
     */
    public void makeTent(Possibility possibility) throws TentException {
        if (tent == null) {
            tent = new Tent(possibility.getX(), possibility.getY());
            possibility.removeFromTree();
        } else {
            throw new TentException();
        }
    }

    /* Getter */
    public String getName() {
        return name;
    }

    public int getY() {
        return this.y;
    }

    public int getX() {
        return this.x;
    }

    public Tent getTent() {
        return tent;
    }

    public List<Possibility> getPossibilities() {
        return possibilities;
    }

}
