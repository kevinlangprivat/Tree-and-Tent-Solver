package tools;

import nodes.*;
import objects.Puzzle;

import java.util.ArrayList;
import java.util.List;

public class PuzzleCreator {
    Puzzle puzzle = new Puzzle();

    /**
     * Method to update the different modelling parts of the puzzle and filter obvious tents
     */
    public Puzzle build_puzzle(String fileName) {
        puzzle.setMap(raw_build_puzzle(fileName));
        model_puzzle();
        return puzzle;
    }

    /**
     * Method to update the different modelling parts of the puzzle
     */
    public Node[][] raw_build_puzzle(String fileName) {
        CsvReader csvReader = new CsvReader();
        Node[][] raw_map = null;
        List<List<String>> records_list = csvReader.read_csv(fileName);
        for (int x = 0; records_list.size() > x; x++) {
            for (int y = 0; records_list.get(0).size() > y; y++) {
                if (x > 0 && y > 0) {
                    if (records_list.get(x).get(y).equals("t")) {
                        Tree new_tree = new Tree(x - 1, y - 1);
                        raw_map[x - 1][y - 1] = new_tree;
                        puzzle.getTmp_tree_list().add(new_tree);
                    } else {
                        raw_map[x - 1][y - 1] = null;
                    }
                } else if (x == 0 && y > 0) {
                    puzzle.getY_amount().add(Integer.parseInt(records_list.get(x).get(y)));
                } else if (x > 0 && y == 0) {
                    puzzle.getX_amount().add(Integer.parseInt(records_list.get(x).get(y)));
                } else if (x == 0 && y == 0) {
                    raw_map = new Node[records_list.size() - 1][records_list.get(0).size() - 1];
                }
            }
        }
        List<Tree> copy = new ArrayList<>();
        copy.addAll(puzzle.getTmp_tree_list());
        puzzle.setTree_list(copy);
        return raw_map;
    }

    /**
     * Method to create generate the possibilities of all trees.
     */
    private void model_puzzle() {
        for (Tree tree : puzzle.getTmp_tree_list()) {
            generate_possibility(-1, 0, tree);   // checking underneath
            generate_possibility(1, 0, tree);    // checking above
            generate_possibility(0, -1, tree);   // checking left
            generate_possibility(0, 1, tree);    // checking right
        }
    }

    /**
     * Checks the given neighbor and adds a possibility to its coordinates
     *
     * @param x_modifier - x-Modifier to check the neighbor
     * @param y_modifier - y-Modifier to check the neighbor
     */
    private void generate_possibility(int x_modifier, int y_modifier, Tree tree) {
        try {
            x_modifier = tree.getX() + x_modifier;
            y_modifier = tree.getY() + y_modifier;
            if (puzzle.getMap()[x_modifier][y_modifier] == null) {
                // Creating a new Possibility
                // System.out.println("Print_01: " + x_modifier + "|" +y_modifier );
                Possibility possibility = new Possibility(x_modifier, y_modifier);
                puzzle.getMap()[x_modifier][y_modifier] = possibility;
                tree.addPossibility(possibility);
            } else if (puzzle.getMap()[x_modifier][y_modifier].getClass() == Possibility.class) {
                tree.addPossibility((Possibility) puzzle.getMap()[x_modifier][y_modifier]);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            // do nothing
        }
    }

}