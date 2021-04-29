package graph;

/**
 * <b>Edge</b> represents <b>immutable</b> directed edge that starts from one location
 * to another location, with a name with it. Assume that no two locations have two edges
 * with the same name and same direction.
 * The location that the edge starts from is parent, and the location that the edge
 * end with is child.
 */

public final class Edge {

    /**
     * construct a new Edge from parent to child with a name
     * @param parent the edge starts from
     * @param child the edge ends with
     * @param label the edge's name
     */
    public Edge(String parent,String child,String label){}

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
