import nodes.Possibility;
import nodes.Tent;
import nodes.Tree;
import objects.Constraint;
import objects.Puzzle;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TentAndTreeConstraint extends Constraint<Tree, Possibility> {
    private Puzzle puzzle;

    public TentAndTreeConstraint(List<Tree> treeList, Puzzle puzzle) {
        super(treeList);
        this.puzzle = puzzle;
    }

    /**
     * Method to check if the current map is satisfied
     *
     * @param assignment the map to check
     * @return True if the map is satisfied
     */
    @Override
    public boolean satisfied(LinkedHashMap<Tree, Possibility> assignment) {
        int[] x_counter = new int[puzzle.getX_amount().size()];
        int[] y_counter = new int[puzzle.getY_amount().size()];
        List<Tent> local_tents = new ArrayList<>();
        for (Map.Entry<Tree, Possibility> item : assignment.entrySet()) {
            if (item.getKey().getTent() != null) {
                return false;
            }
            // Constraint #1: Each tent needs to be directly next to a tree y-axis <- This one should never happen!
            else if (Math.abs(item.getKey().getY() - item.getValue().getY()) > 1) {
                return false;
            }
            // Constraint #2: Each tent needs to be directly next to a tree x-axis <- This one should never happen!
            else if (Math.abs(item.getKey().getX() - item.getValue().getX()) > 1) {
                return false;
            }
            // Constraint #3: The column can't be full - vertical!
            else if (puzzle.getX_amount().get(item.getValue().getX()) <= x_counter[item.getValue().getX()]) {
                return false;
            }
            // Constraint #4: The row can't be full - horizontal!
            else if (puzzle.getY_amount().get(item.getValue().getY()) <= y_counter[item.getValue().getY()]) {
                return false;
            }
            // Constraint #5: Each tent must be attached to exactly ONE tree!
            else if (isAlreadyTent(local_tents, item.getValue())) {
                return false;
            }
            // Constraint #6: A tent can't be next to a tent: vertical, horizontal & diagonal!
            else if (isNextToTent(local_tents, item.getValue())) {
                return false;
            }
            x_counter[item.getValue().getX()]++;
            y_counter[item.getValue().getY()]++;
            local_tents.add(new Tent(item.getValue().getX(), item.getValue().getY()));
        }
        return true;
    }

    @Override
    public void setPlaceholderSolution(Possibility possibility, boolean bool){
        possibility.setIsTent(bool);
    }

    @Override
    public boolean isSolution(Possibility possibility){
        //TODO(@KLang) implement this alternative for casting in the CSP-Class
        return possibility.getIsTent();
    }

    /**
     * Checks if a possibility is already a tent
     *
     * @param tents       A list of tens
     * @param possibility The possibility to check
     * @return True if the possibility is already a tent
     */
    private boolean isAlreadyTent(List<Tent> tents, Possibility possibility) {
        for (Tent tent : tents) {
            if (tent.getX() == possibility.getX() && tent.getY() == possibility.getY()) {
                return true;
            }
        }
        return false;
    }

    /**
     * A method to check if the possibility is next to a trend
     *
     * @param tents       A list of tens
     * @param possibility The possibility to check
     * @return True if the possibility is next to a tent
     */
    private boolean isNextToTent(List<Tent> tents, Possibility possibility) {
        for (Tent tent : tents) {
            // left
            if (tent.getX() == possibility.getX() - 1 && tent.getY() == possibility.getY()) {
                return true;
            }
            // right
            else if (tent.getX() == possibility.getX() + 1 && tent.getY() == possibility.getY()) {
                return true;
            }
            // up
            else if (tent.getX() == possibility.getX() && tent.getY() == possibility.getY() + 1) {
                return true;
            }
            // down
            else if (tent.getX() == possibility.getX() && tent.getY() == possibility.getY() - 1) {
                return true;
            }
            // up - left
            else if (tent.getX() == possibility.getX() + 1 && tent.getY() == possibility.getY() - 1) {
                return true;
            }
            // up - right
            else if (tent.getX() == possibility.getX() + 1 && tent.getY() == possibility.getY() + 1) {
                return true;
            }
            // down - left
            else if (tent.getX() == possibility.getX() - 1 && tent.getY() == possibility.getY() - 1) {
                return true;
            }
            // down - right
            else if (tent.getX() == possibility.getX() - 1 && tent.getY() == possibility.getY() + 1) {
                return true;
            }
        }
        return false;
    }
}

