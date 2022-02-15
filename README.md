# Ausführen:

1. Default package öffnen 
2. "Main.java" öffnen.
3. Einstellungen Anpassen: 
- "shuffle"  & "forward check" können durch einen bool ein-(true) und aus-(false)geschaltet.
- "csv amount" gibt an wie viele CSVs durchlaufen werden sollen.
4. Ausführen
- Ergebnis: In der Console sollte eine Map ausgegeben werden. 
- (X) - Zelt | (T) - Baum
5. Das Ergebnis ist dann unter: \src\main\resources\evaluation.txt
- Beispiel Ausgabe: 4, 2988, 1031, BacktrackingSearchFC
- Name der Werte: CSV-ID, Schritte, Zeit, Name der Heuristik + mit ForwardChecking(FC) oder ohne nFC)

** Aufgabe: **
The assignments are independent of one another, but the lecture will address the issues of the assignments in order, so it is advisable to complete the assignments in the given order. A more detailed overview of the individual lectures required for the assignments will be given in the first lecture. Basis for your grade will be the documentation of the assignments but you must also hand in the source code of the software you developed for the assignments. The assignments are rather openly worded to leave room for your own ideas and creativity. If you identify any interesting questions you want to examine and evaluate feel free to do so.

**Information on the Documentation at a Glance**
- Make and hand in separate documentations for each of the three assignments
- Indicate who worked on which part, try to rotate responsibilities between assignments
- Do not explain fundamentals on a general level, instead focus on your particular application of the fundamentals
- Limit of five pages of actual written content per assignment, excluding figures and tables
- Do not forget your names and group number on the documentation
- Language must be English or German
- See individual assignments for specifics on the content


**Information on the Software at a Glance**
- Make and hand in separate projects for each of the three assignments
- Use Java exclusively
- Projects must be importable into eclipse IDE (use default Java projects or Maven projects)
- Include all dependencies and libraries, unless they will be imported automatically by Maven
- Try to write readable, self-explanatory code
- Any GUI or other visualization component of the software is not required (statistics, graphs, etc. in the documentation however should be shown through appropriate figures)
- Do not over-engineer the software, grading is based what/why you did and the results (the documentation) and not how you did it (the software)

# Assignment 2: objects.Constraint-based Solution of the Tents and Trees Logic puzzles

Tents and Trees is a logic puzzle and is played on a rectangular grid. On some of the squares, trees are drawn in, and there are numbers at the beginning of the rows and columns. The goal is to attach a tent to each tree, so that the following rules are fulfilled:

1. Each tent must be attached to exactly one tree.
2. Each tree must be attached to exactly one tent.
3. Each tent must stand horizontally or vertically next to its corresponding tree.
4. Each tent must stand directly adjacent to its corresponding tree.
5. There are exactly as many tents in each row or column as the number on the side indicates.
6. No two tents may stand immediately next to each other, not even diagonally.

Your task is to model Tents and Trees as a constraint satisfaction problem (Csp) and solve it using suitable heuristics to evaluate the performance (e.g. run time/iterations, number of backtracking steps, ratio between pruning and searching operations) of your heuristics. Ten different Tents and
Trees puzzles are provided through StudIP which you must use for the evaluation of your heuristics to generate somewhat reliable data and generalize your findings. The puzzles are provided as CSV files with the first row and column indicating the number of tents per column/row, all other fields being either empty or containing ‘t’, indicating a tree.

You may not use any dedicated libraries to model and solve CSPs, you may however use libraries to import the CSV files! You must model and implement all required data structures and algorithms yourselves. Moreover, you must determine suitable heuristics that can be used to search for solutions. You may also use pruning algorithms such as arc consistency to preemptively reduce the size of the search tree.

You should get familiar with the puzzle and its core approaches before you start with the assignment. Use online resources to generate and solve some puzzles (e.g. https://brainbashers.com/tents.asp).

**Contents of the Documentation for Assignment 2 at a Glance**
- Explain your data model, assumptions and limitations
- Explain the different pruning and search heuristics you used to solve the puzzle
- Explain your evaluation setup, show and discuss the results