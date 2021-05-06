package graph;

/**
 * <b>Node</b> represent an <b>immutable</b> location. It takes a name of
 * location and store that location as Node object.
 */
public final class Node {
    // Rep invariant: name != null
    //AF(this) = a node with name this.name
    private final String name;

    /**
     * throw exception if rep invariant is violated.
     */
    private void checkRep(){
        assert(name != null);
    }

    /**
     * construct a new Node with name
     * @param name name of location
     */
    public Node(String name){
        this.name = name;
        checkRep();
    }

    /**
     * return the name of the node
     * @return name of the node
     */
    public String getName(){
        return name;
    }


}
