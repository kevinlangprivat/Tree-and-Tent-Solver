package objects;

import nodes.Node;
import nodes.Possibility;
import nodes.Tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Puzzle {
    private Node[][] map;
    private List<Integer> x_amount = new ArrayList<>();
    private List<Integer> y_amount = new ArrayList<>();
    private List<Tree> tmp_tree_list = new ArrayList<>();   // A list of trees without a tent
    private List<Tree> tree_list = new ArrayList<>();       // The complete list of trees
    private Map<Tree, Possibility> solution;                // The solution for this puzzle

    /* Setter */
    public void setMap(Node[][] map) {
        this.map = map;
    }

    public void setSolution(Map<Tree, Possibility> solution) {
        this.solution = solution;
    }

    public void setTree_list(List<Tree> tree_list) {
        this.tree_list = tree_list;
    }

    /* Getter */
    public Node[][] getMap() {
        return map;
    }

    public List<Integer> getX_amount() {
        return x_amount;
    }

    public List<Integer> getY_amount() {
        return y_amount;
    }

    public List<Tree> getTmp_tree_list() {
        return tmp_tree_list;
    }

    public Map<Tree, Possibility> getSolution() {
        return solution;
    }

    public List<Tree> getTree_list() {
        return tree_list;
    }
}

