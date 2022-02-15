class Main {
//|--------------------------------------------------------------------------------------------------------------------|
//|                                                    Settings                                                        |
//|--------------------------------------------------------------------------------------------------------------------|

    private static int csv_amount = 9;              // min 0 | max 9
    private static boolean forwardCheck = true;     // true or false
    private static boolean shuffle = false;         // true or false

//|--------------------------------------------------------------------------------------------------------------------|
//|                                                    Main                                                            |
//|--------------------------------------------------------------------------------------------------------------------|


    public static void main(String[] args) {
        Helper helper = new Helper();
        for (int i = 0; i <= csv_amount; i++) {
            String fileName = "src/main/resources/csv/tents_trees_" + i + ".csv";
            /* MIRV */
                helper.runMiRVFC(fileName, i, forwardCheck, shuffle);
                helper.generate_solution();

            /* Backtracking-Search*/
            helper.runBacktrackingSearchFC(fileName, i, forwardCheck, shuffle);
            helper.generate_solution();

            /* MORV */
//            helper.runMoRVFC(fileName, i, forwardCheck, shuffle);
//            helper.generate_solution();
        }
    }
}