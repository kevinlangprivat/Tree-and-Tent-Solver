package objects;

import nodes.Possibility;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Abstract constraint class
 * Source: https://livebook.manning.com/book/classic-computer-science-problems-in-java/chapter-3/v-3/32
 *
 * @param <V> V is the variable type
 * @param <D> D is the domain type
 */
public abstract class Constraint<V, D> {

    /**
     *
     */
    public List<V> variables;

    /**
     *
     * @param variables
     */
    public Constraint(List<V> variables) {
        this.variables = variables;
    }

    /**
     * Must be overridden by subclasses
     * @param assignment
     * @return
     */
    public abstract boolean satisfied(LinkedHashMap<V, D> assignment);

    /**
     * Note: This methode is a lazy solution
     * Is method is used to add a domain into variable
     */
    public abstract void setPlaceholderSolution(D domain, boolean bool);

    /**
     * Note: This methode is a lazy solution
     * @param domain The domain to check
     * @return True if the domain is a solution, False is the domain isn't a solution
     */
    public abstract boolean isSolution(D domain);
}
