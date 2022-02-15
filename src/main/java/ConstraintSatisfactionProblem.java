import nodes.Possibility;
import nodes.Tree;
import objects.Constraint;

import java.util.*;

/**
 * Constraint Satisfaction Problem
 * Source: https://livebook.manning.com/book/classic-computer-science-problems-in-java/chapter-3/v-3/32
 *
 * @param <V> Variables - All possible Trees
 * @param <D> Domains - All possible Nodes
 */
public class ConstraintSatisfactionProblem<V, D> {
    private List<V> variables;
    private LinkedHashMap<V, List<D>> domains;
    private LinkedHashMap<V, List<Constraint<V, D>>> constraints;

    int count = 0;
    int not_consistent_counter = 0;

    /**
     * @param variables
     * @param domains
     */
    public ConstraintSatisfactionProblem(List<V> variables, LinkedHashMap<V, List<D>> domains) {
        this.variables = variables;
        this.domains = domains;
        constraints = new LinkedHashMap<>();
        for (V variable : variables) {
            constraints.put(variable, new ArrayList<>());
            if (!domains.containsKey(variable)) {
                throw new IllegalArgumentException("Every variable should have  domain assigned to it.");
            }
        }
    }

    /**
     * @param constraint
     */
    public void addConstraint(Constraint<V, D> constraint) {
        for (V variable : constraint.variables) {
            if (!variables.contains(variable)) {
                throw new IllegalArgumentException("Variable in constraint not in CSP");
            } else {
                constraints.get(variable).add(constraint);
            }
        }
    }

    /**
     * Check if the value assignment is consistent by checking all constraints for the given variable against it
     *
     * @param variable
     * @param assignment
     * @return
     */
    public boolean consistent(V variable, LinkedHashMap<V, D> assignment) {
        for (Constraint<V, D> constraint : constraints.get(variable)) {
            if (!constraint.satisfied(assignment)) {
                return false;
            }
        }
        return true;
    }

//|--------------------------------------------------------------------------------------------------------------------|
//|                                                    Backtracking + Forward Checking (via boolean)                   |
//|--------------------------------------------------------------------------------------------------------------------|

    /**
     * This method is used to TODO
     *
     * @param assignment
     * @return
     */
    private LinkedHashMap<V, D> backtrackingSearchFC(LinkedHashMap<V, D> assignment, LinkedHashMap<V, List<D>> local_domains, boolean forwardChecking) {
        count++;
        if (assignment.size() == variables.size()) {
            return assignment;
        }
        V unassigned = variables.stream().filter(v -> (local_domains.containsKey(v))).findFirst().get();
        for (D value : local_domains.get(unassigned)) {
            LinkedHashMap<V, D> localAssignment = new LinkedHashMap<>(assignment);
            localAssignment.put(unassigned, value);
            if (consistent(unassigned, localAssignment)) {
                LinkedHashMap<V, List<D>> local_domains_tmp = new LinkedHashMap<V, List<D>>(local_domains);
                local_domains_tmp.remove(unassigned);
                LinkedHashMap<V, D> result;
                try {
                    // handing over a null here will trigger a nullpointer exception, before going into the next recursion
                    if(forwardChecking){ //<- with forward checking
                        LinkedHashMap<V, List<D>> test = new LinkedHashMap<V, List<D>>(forwardCheck(localAssignment, local_domains_tmp));
                        result = backtrackingSearchFC(localAssignment, test, forwardChecking);
                    }
                    else{ //<- without forward checking
                        result = backtrackingSearchFC(localAssignment, local_domains_tmp, forwardChecking);
                    }
                    if (result != null) {
                        return result;
                    }
                } catch (NullPointerException e) {
                    continue;
                }
            }
        }
        return null;
    }

    /**
     * Helper for backtrackingSearch when nothing known yet
     *
     * @return A map containing the solution.
     */
    public LinkedHashMap<V, D> backtrackingSearchFC(boolean forwardChecking) {
        LinkedHashMap<V, D> backtrackingSearch = backtrackingSearchFC(new LinkedHashMap<>(), domains, forwardChecking);
        System.out.println("Backtracks: " + count + " | Not consistent: " + not_consistent_counter);
        return backtrackingSearch;
    }

//|--------------------------------------------------------------------------------------------------------------------|
//|                             Minimum Remaining Values [miRV] + Forward Checking (via boolean)                       |
//|--------------------------------------------------------------------------------------------------------------------|

    /**
     * This method is used to TODO
     *
     * @param assignment
     * @return
     */
    private LinkedHashMap<V, D> miRVFC(LinkedHashMap<V, D> assignment, LinkedHashMap<V, List<D>> localDomains, boolean forwardChecking) {
        count++;
        // assignment is complete if every variable is assigned (our base case)
        if (assignment.size() == variables.size()) {
            return assignment;
        }
        // get the tree with the least amount of possibilities
        V unassigned = getSmallest(localDomains);
        // get the every possible domain value of the first unassigned variable
        try {
            for (D value : localDomains.get(unassigned)) {
                // shallow copy of assignment that we can change
                LinkedHashMap<V, D> localAssignment = new LinkedHashMap<>(assignment);
                localAssignment.put(unassigned, value);
                // if we're still consistent, we recurse (continue)
                if (consistent(unassigned, localAssignment)) {
                    constraints.get(unassigned).get(0).setPlaceholderSolution(value, true);
                    LinkedHashMap<V, List<D>> local_domains_tmp = new LinkedHashMap<V, List<D>>(localDomains);
                    local_domains_tmp.remove(unassigned);
                    LinkedHashMap<V, D> result;
                    try {
                        // handing over a null here will trigger a nullpointer exception, before going into the next recursion
                        if(forwardChecking){ //<- with forward checking
                            LinkedHashMap<V, List<D>> test = new LinkedHashMap<V, List<D>>(forwardCheck(localAssignment, local_domains_tmp));
                            result = miRVFC(localAssignment, test, forwardChecking);
                        }
                        else{ //<- without forward checking
                             result = miRVFC(localAssignment, local_domains_tmp, forwardChecking);
                        }
                        if (result != null) {
                            return result;
                        }
                    } catch (NullPointerException e) {
                        constraints.get(unassigned).get(0).setPlaceholderSolution(value, false);
                        continue;
                    }
                    constraints.get(unassigned).get(0).setPlaceholderSolution(value, false);
                }
            }
        } catch (NullPointerException e) {
            System.out.println("No unassigned Value found!");
            return null;
        }
        return null;
    }

    /**
     * Helper for localSearch when nothing known yet
     *
     * @return A map containing the solution.
     */
    public LinkedHashMap<V, D> miRVFC(boolean forwardChecking) {
        LinkedHashMap<V, D> minimumRemainingValues = miRVFC(new LinkedHashMap<>(), domains, forwardChecking);
        System.out.println("Backtracks: " + count + " | Not consistent: " + not_consistent_counter);
        return minimumRemainingValues;
    }

//|--------------------------------------------------------------------------------------------------------------------|
//|                                            Most Remaining Values [moRV]                                            |
//|--------------------------------------------------------------------------------------------------------------------|

    /**
     * This method is used to TODO
     *
     * @param assignment
     * @return
     */
    private LinkedHashMap<V, D> moRVFC(LinkedHashMap<V, D> assignment, LinkedHashMap<V, List<D>> localDomains, boolean forwardChecking) {
        count++;
        // assignment is complete if every variable is assigned (our base case)
        if (assignment.size() == variables.size()) {
            return assignment;
        }
        // get the tree with the least amount of possibilities
        V unassigned = getBiggest(localDomains);
        // get the every possible domain value of the first unassigned variable
        try {
            for (D value : localDomains.get(unassigned)) {
                // shallow copy of assignment that we can change
                LinkedHashMap<V, D> localAssignment = new LinkedHashMap<>(assignment);
                localAssignment.put(unassigned, value);
                // if we're still consistent, we recurse (continue)
                if (consistent(unassigned, localAssignment)) {
                    constraints.get(unassigned).get(0).setPlaceholderSolution(value, true);
                    LinkedHashMap<V, List<D>> local_domains_tmp = new LinkedHashMap<V, List<D>>(localDomains);
                    local_domains_tmp.remove(unassigned);
                    LinkedHashMap<V, D> result;
                    try {
                        // handing over a null here will trigger a nullpointer exception, before going into the next recursion
                        if(forwardChecking){ //<- with forward checking
                            LinkedHashMap<V, List<D>> test = new LinkedHashMap<V, List<D>>(forwardCheck(localAssignment, local_domains_tmp));
                            result = moRVFC(localAssignment, test, forwardChecking);
                        }
                        else{ //<- without forward checking
                            result = moRVFC(localAssignment, local_domains_tmp, forwardChecking);
                        }
                        if (result != null) {
                            return result;
                        }
                    } catch (NullPointerException e) {
                        constraints.get(unassigned).get(0).setPlaceholderSolution(value, false);
                        continue;
                    }
                    constraints.get(unassigned).get(0).setPlaceholderSolution(value, false);
                }
            }
        } catch (NullPointerException e) {
            System.out.println("No unassigned Value found!");
            return null;
        }
        return null;
    }

    /**
     * Helper for localSearch when nothing known yet
     *
     * @return A map containing the solution.
     */
    public LinkedHashMap<V, D> moRVFC(boolean forwardChecking) {
        LinkedHashMap<V, D> mRV = moRVFC(new LinkedHashMap<>(), domains, forwardChecking);
        System.out.println("Backtracks: " + count + " | Not consistent: " + not_consistent_counter);
        return mRV;
    }

//|--------------------------------------------------------------------------------------------------------------------|
//|                                                    Helper                                                          |
//|--------------------------------------------------------------------------------------------------------------------|

    /**
     *
     * @param assignment
     * @param local_domains
     * @return
     */
    private LinkedHashMap<V, List<D>> forwardCheck(LinkedHashMap<V, D> assignment, LinkedHashMap<V, List<D>> local_domains) {
        for (Map.Entry<V, List<D>> item : local_domains.entrySet()) {
            int c = 0;
            for (D d : item.getValue()) {
                LinkedHashMap<V, D> localAssignment = new LinkedHashMap<>(assignment);
                localAssignment.put(item.getKey(), d);
                if (!consistent(item.getKey(), localAssignment)) {
                    not_consistent_counter++;
                } else {
                    c++;
                }
            }
            if (c == 0) {
                return null;
            }
        }
        LinkedHashMap<V, List<D>> local_domains_tmp = new LinkedHashMap<V, List<D>>(local_domains);
        //printOpenNodes(local_domains_tmp.keySet());
        return local_domains_tmp;
    }

    /**
     * Method to return the variable with the smallest domain
     *
     * @param local_domains
     * @return
     */
    private V getSmallest(LinkedHashMap<V, List<D>> local_domains) {
        for (int i = 1; i <= 4; i++) { // Four is the max amount of possibilities
            for (Map.Entry<V, List<D>> item : local_domains.entrySet()) {
                int counter = 0;
                for (D d : item.getValue()) {
                    //TODO(@KLang): replace by a method without casting
                    if (!((Possibility) d).getIsTent()) {
                        counter++;
                    }
                }
                if (counter == i) {
                    return item.getKey();
                }
            }
        }
        return null;
    }

    /**
     * Method to return the variable with the biggest domain
     *
     * @param local_domains
     * @return
     */
    private V getBiggest(LinkedHashMap<V, List<D>> local_domains) {
        for (int i = 4; i >= 1; i--) {// Four is the max amount of possibilities
            for (Map.Entry<V, List<D>> item : local_domains.entrySet()) {
                int counter = 0;
                for (D d : item.getValue()) {
                    //TODO(@KLang): replace by a method without casting
                    if (!((Possibility) d).getIsTent()) {
                        counter++;
                    }
                }
                if (counter == i) {
                    return item.getKey();
                }
            }
        }
        return null;
    }

    /**
     *
     * @param assignment
     * @param local_domains
     * @return
     */
    private LinkedHashMap<V, List<D>> arcConsistency(LinkedHashMap<V, D> assignment, LinkedHashMap<V, List<D>> local_domains) {
        LinkedHashMap<V, List<D>> local_domains_tmp = new LinkedHashMap<V, List<D>>();
        for (Map.Entry<V, List<D>> item : local_domains.entrySet()) {
            List<D> add_me = new ArrayList<>();
            for (D d : item.getValue()) {
                LinkedHashMap<V, D> localAssignment = new LinkedHashMap<>(assignment);
                localAssignment.put(item.getKey(), d);
                if (!consistent(item.getKey(), localAssignment)) {
                    not_consistent_counter++;
                }
                else{
                    add_me.add(d);
                }
            }
            local_domains_tmp.put(item.getKey(), add_me);
        }
        if(local_domains_tmp.isEmpty()){
            return null;
        }
        //printOpenNodes(local_domains_tmp.keySet());
        return local_domains_tmp;
    }

    public int getCount() {
        return count;
    }

}
