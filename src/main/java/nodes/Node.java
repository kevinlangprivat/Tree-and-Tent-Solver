package nodes;

public abstract class Node {
    private int y;
    private int x;
    private String name;

    Node() {
    }

    /* Getter */
    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public String getName() {
        return name;
    }
}
