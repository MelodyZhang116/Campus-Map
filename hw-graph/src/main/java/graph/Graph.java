package graph;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a directed labeled graph. It is a collection of nodes and edges.
 * Each edge connects two nodes and it is directed. e.g.edge e (A,B) indicates en edge from A
 * to B. Then B is the child node of A, and A is the parent node of B. A path refers to a sequence
 * of edges traveling from A to B. Assume that no 2 edges with the same parent and child nodes will
 * have the same edge label.
 */
public class Graph {


    /**
     * constructor that construct a new Graph
     * @spec.effects constructs an empty queue
     */
    public Graph(){ }

    /**
     * Add a node to the graph.
     * @param node with name node to be added
     * @throws IllegalArgumentException if node is already in the graph
     * @spec.modifies this
     * @spec.effects add node to this
     */
    public void insertNode(String node){}

    /**
     * Add an edge from parent to child, named label, to the graph
     * @param parent node that the edge starts from
     * @param child node that the edge ends with
     * @param label the name of the edge
     * @throws IllegalArgumentException if parent or child is not in the graph or the
     * edge is alreadu in the graph
     * @spec.modifies this
     * @spec.effects insert edge from parent to child to this
     */
    public void insertEdge(String parent,String child,String label){}

    /**
     * remove node from the graph, and remove all edges from the node or to the node
     * @param node the node to be removed
     * @throws IllegalArgumentException if node is not in the graph
     * @spec.modifies this
     * @spec.effects remove the node from this
     */
    public void removeNode(String node){}

    /**
     * remove the edge with name label, from node parent to node child.
     * @param parent remove the edge starts from parent
     * @param child remove the edge end with child
     * @param label remoce the edge named label
     * @throws IllegalArgumentException if !containsNode(parent) or !containsNode(child)
     * or !containsEdge(parent,child,edge)
     * @spec.modifies this
     * @spec.effects remove the edge from this
     */
    public void removeEdge(String parent,String child,String label){}


    /**
     * check whether node is contained in this. Return <code>true</code> if node
     * is contained in the graph, <code>false</code> otherwise.
     * @param node to be checked
     * @return True if node is contained in this graph, False otherwise
     */
    public boolean containsNode(String node){return true;}

    /**
     * return a string consisting of names of nodes
     * @return a string consisting of names of nodes. Names are listed on the same line, by
     * a space-separated list of the node data contained in each node of the graph. The names
     * should appear in alphabetical order. There is a single space between the colon and the
     * first node name, but no space if there are no nodes.
     */
    public String listNodes(){return "";}

    /**
     * return a string consisting of names of children nodes of the given parent.
     * @param parent node that is parent
     * @return a string consisting of names of children of parent. The syntax is
     * specified in listNode method.
     * @throws IllegalArgumentException if parent is not contained in the graph
     */
    public String listChildren(String parent){return "";}

    /**
     * return a string consisting of path starting from parent node to child node.
     * @param parent the path from parent
     * @param child the path to child
     * @throws IllegalArgumentException if parent or child not exist
     * @return a string that consisting of path starting from parent node to child node.
     * e.g.If there is a path from B (starting node) to C(terminal node), that is from B
     * to A to B to C, then it should return "(B,A),(A,B),(B,C)"
     */
    public List<String> listPath(String parent, String child){throw new IllegalArgumentException();}



}
