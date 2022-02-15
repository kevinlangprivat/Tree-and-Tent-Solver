package nodes;

import java.util.ArrayList;
import java.util.List;

public class Possibility extends Node {

    private int y;
    private int x;
    private String name = "P";
    private boolean isTent = false;

    private List<Tree> trees = new ArrayList<>();

    /**
     * Constructor
     *
     * @param x X-Coordinate of the tree
     * @param y Y-Coordinate of the tree
     */
    public Possibility(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Method to remove this possibility from all connected trees
     */
    public void removeFromTree() {
        for (Tree tree : trees) {
            tree.removePossibility(this);
        }
    }

    /**
     * Method to add a connected tree to this possibility
     *
     * @param tree The tree to be added to this possibility
     */
    protected void addTree(Tree tree) {
        trees.add(tree);
    }

    /* Getter */
    public List<Tree> getTree() {
        return trees;
    }

    public String getName() {
        return name;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public boolean getIsTent(){
        return this.isTent;
    };

    public void setIsTent(boolean isTent){
        this.isTent = isTent;
    }
}
