package graph;

/**
 * <b>Node</b> represent an <b>immutable</b> location. It takes a name of
 * location and store that location as Node object.
 */
public final class Node {
    /**
     * construct a new Node with name str
     * @param str take str as name of location
     */
    public Node(String str){}

    /**
     * return the name of the node
     * @return name of the node
     */
    public String getName(){throw new IllegalArgumentException();}
}
