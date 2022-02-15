import nodes.Possibility;
import nodes.Tree;
import objects.Puzzle;
import tools.PuzzleCreator;
import tools.PuzzlePrinter;

import tools.Timer;


import java.util.*;


public class Helper {
//|--------------------------------------------------------------------------------------------------------------------|
//|                                                    Helper                                                          |
//|--------------------------------------------------------------------------------------------------------------------|
    private static Puzzle puzzle;
    private static Timer timer = new Timer();

    /**
     * A method to execute the csp solving process with the backTrackingSearch
     */
    public static void runBacktrackingSearchFC(String fileName, int puzzle_id, boolean forwardChecking, boolean shuffle) {
        puzzle = new PuzzleCreator().build_puzzle(fileName);
        timer.start_timer(); // Start of the solving process
        ConstraintSatisfactionProblem csp = generate_csp(shuffle);
        puzzle.setSolution(csp.backtrackingSearchFC(forwardChecking));
        String name;
        if (!forwardChecking) {
            name = "BacktrackingSearch";
        } else {
            name = "BacktrackingSearchFC";
        }
        timer.stop_timer(name, puzzle_id, csp.getCount()); // End of the solving process
    }

    /**
     * A method to execute the csp solving process with the TODO
     */
    public static void runMiRVFC(String fileName, int puzzle_id, boolean forwardChecking, boolean shuffle) {
        puzzle = new PuzzleCreator().build_puzzle(fileName);
        timer.start_timer(); // Start of the solving process
        ConstraintSatisfactionProblem csp = generate_csp(shuffle);
        puzzle.setSolution(csp.miRVFC(forwardChecking));
        String name;
        if (!forwardChecking) {
            name = "miRV";
        } else {
            name = "miRVFC";
        }
        timer.stop_timer(name, puzzle_id, csp.getCount()); // End of the solving process
    }

    /**
     * A method to execute the csp solving process with the TODO
     */
    public static void runMoRVFC(String fileName, int puzzle_id, boolean forwardChecking, boolean shuffle) {
        puzzle = new PuzzleCreator().build_puzzle(fileName);
        timer.start_timer(); // Start of the solving process
        ConstraintSatisfactionProblem csp = generate_csp(shuffle);
        puzzle.setSolution(csp.moRVFC(forwardChecking));
        String name;
        if (!forwardChecking) {
            name = "moRV";
        } else {
            name = "moRVFC";
        }
        timer.stop_timer(name, puzzle_id, csp.getCount()); // End of the solving process
    }

    /**
     *
     */
    public static void generate_solution() {
        if (puzzle.getSolution() == null) {
            System.out.println("MAIN: No solution found!");
        } else {
            for (Map.Entry<Tree, Possibility> item : puzzle.getSolution().entrySet()) {
                try {
                    item.getKey().makeTent(item.getValue());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            new PuzzlePrinter().printArrayMap(puzzle); // Printing the Puzzle-Solution
        }
    }

    /**
     * @return
     */
    public static ConstraintSatisfactionProblem<Tree, Possibility> generate_csp(boolean shuffle) {
        LinkedHashMap<Tree, List<Possibility>> csp_possibility = new LinkedHashMap<>();
        for (Tree tree : puzzle.getTmp_tree_list()) {
            csp_possibility.put(tree, tree.getPossibilities());
        }

        List<Tree> list = new ArrayList<>(puzzle.getTmp_tree_list());
        if(shuffle){
            Collections.shuffle(list);
        }
        ConstraintSatisfactionProblem<Tree, Possibility> csp = new ConstraintSatisfactionProblem<>(list, csp_possibility);
        TentAndTreeConstraint tentAndTreeConstraint = new TentAndTreeConstraint(puzzle.getTree_list(), puzzle);
        csp.addConstraint(tentAndTreeConstraint);
        return csp;
    }
}
