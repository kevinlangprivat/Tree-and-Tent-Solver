package nodes;

public class Tent extends Node {
    private int y;
    private int x;
    private String name = "X";

    /**
     * Constructor
     *
     * @param x X-Coordinate of the tree
     * @param y Y-Coordinate of the tree
     */
    public Tent(int x, int y) {
        this.x = x;
        this.y = y;
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

}