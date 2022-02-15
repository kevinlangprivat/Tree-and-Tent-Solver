package tools;

import nodes.Node;
import nodes.Tent;
import nodes.Tree;
import objects.Puzzle;

public class PuzzlePrinter {
    public void print(Node[][] map) {
        for (int x = 0; map.length > x; x++) {
            for (int y = 0; map[0].length > y; y++) {
                try {
                    System.out.print(map[x][y].getName() + " ");
                } catch (NullPointerException e) {
                    System.out.print("  ");
                }
            }
            System.out.println();
        }
    }
    public static void printArrayMap(Puzzle puzzle) {
        Node[][] map = new Node[puzzle.getX_amount().size()][puzzle.getY_amount().size()];
        for (Node[] nodes : map) {
            for (Node node : nodes) {
                node = null;
            }
        }
        for (Tree tree : puzzle.getTree_list()) {
            try {
                map[tree.getX()][tree.getY()] = tree;
                int x = tree.getTent().getX();
                int y = tree.getTent().getY();
                Tent tent = tree.getTent();
                map[x][y] = tent;
            } catch (ArrayIndexOutOfBoundsException e) {
                e.printStackTrace();
                System.out.println("Print_15: " + tree.getTent().getX() + " " + tree.getTent().getY());
                System.out.println("Print_16: " + map.length + " " + map[0].length);
            }
        }
        printLine(map);
        System.out.print("     |");
        for (int i = 0; i < map[0].length; i++) {

            if (i < 10) {

                System.out.print("0" + i + " ");
            } else {

                System.out.print(i + " ");
            }
        }
        System.out.println();
        System.out.print("     |");
        for (int i = 0; i < map[0].length; i++) {
            System.out.print("0"+puzzle.getY_amount().get(i)+" ");
        }
        System.out.println();
        printLine(map);
        int j = 0;
        for (Node[] nodes : map) {
            j++;
            if (j < 10) {
                if(puzzle.getX_amount().get(j-1) >= 10){
                    System.out.print("0" + j + "|" + puzzle.getX_amount().get(j-1) + "| ");
                }
                else{
                    System.out.print("0" + j + "|0" + puzzle.getX_amount().get(j-1) + "| ");
                }

            }
            else {
                if(puzzle.getX_amount().get(j-1) >= 10){
                    System.out.print(j + "|" + puzzle.getX_amount().get(j-1) + "| ");
                }
                else{
                    System.out.print(j + "|0" + puzzle.getX_amount().get(j-1) + "| ");
                }

            }
            for (Node node : nodes) {
                if (node == null) {
                    System.out.print("   ");
                } else {
                    System.out.print(node.getName() + "  ");
                }
            }
            System.out.println("");
        }
        printLine(map);
    }

    private static void printLine(Node[][] map){
        for (int i = -2; i < map[0].length; i++) {
            System.out.print("___");
        }
        System.out.println();
    }
}
