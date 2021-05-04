package graph;

/**
 * <b>Edge</b> represents <b>immutable</b> directed edge that starts from one location
 * to another location, with a name with it.
 * The location that the edge starts from is parent, and the location that the edge
 * end with is child.
 */

public final class Edge {
    //rep invariant: parent != null && child != null && label!= null
    //AF(this) = an edge starts from this.parent to this.child with name this.label
    private final Node parent;
    private final Node child;
    private final String label;

    /**
     * throw exception if rep variant is violated.
     */
    private void checkRep(){
        assert(parent != null && child!= null && label!= null);
    }
    /**
     * construct a new Edge from parent to child with a name
     * @param parent the edge starts from
     * @param child the edge ends with
     * @param label the edge's name
     */
    public Edge(String parent,String child,String label){
        this.parent = new Node(parent);
        this.child = new Node(child);
        this.label = label;
        checkRep();
    }

    /**
     * return the name of the edge
     * @return the name of given edge
     */
    public String getName(){throw new IllegalArgumentException();}

    /**
     * return the parent node's name of the edge
     * @return the name of parent node of the edge
     */
    public String getParent(){throw new IllegalArgumentException();}

    /**
     * return the child node's name of the edge
     * @return the name of the child node of the edge
     */
    public String getChild(){throw new IllegalArgumentException();}
}
