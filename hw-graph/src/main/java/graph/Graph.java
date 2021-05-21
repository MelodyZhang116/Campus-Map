package graph;

import java.util.*;


/**<b>Graph</b> represents an immutable graph.
 * This class represents a directed labeled graph. It is a collection of nodes and edges.
 * Each edge connects two nodes and it is directed. e.g.edge e (A,B) indicates en edge from A
 * to B. Then B is the child node of A, and A is the parent node of B. A path refers to a sequence
 * of edges traveling from A to B. Assume that no 2 edges with the same parent and child nodes will
 * have the same edge label.
 */
public class Graph<A,B> {
    public static final boolean DEBUG = false;
    //rep invariant: for each term in map, there is no null.
    //AF(this) = a graph with nodes in key elements of this.graph and with edges in mapped values of this.graph

    //a map that store the nodes in key and store its edge starting from that node into value of that node
    private Map<Node<A>,List<Edge<A,B>>> graph;

    /**
     * throw exception if rep invariant is violated.
     */
    private void checkRep(){
        if(DEBUG){
            assert(!graph.containsKey(null)): "The graph has null node(s)";
            assert (!graph.containsValue(null)) :"The graph has null edge(s)";

            for(List<Edge<A,B>> list:graph.values()){
                for(Edge<A,B> ed:list){
                    assert(ed != null):"The graph has null edges";

                }

            }
        }
    }

    /**
     * constructor that construct a new Graph
     * @spec.effects constructs an empty queue
     */
    public Graph(){
        this.graph = new HashMap<Node<A>,List<Edge<A,B>>>();
        checkRep();
    }

    /**
     *
     * @return return the collection of all values in graph
     */
    public Collection<List<Edge<A,B>>> allEdges(){
        return graph.values();
    }
    /**
     * Add a node to the graph. If the node already exist, then do nothing.
     * @param node with name node to be added
     * @spec.modifies this
     * @spec.effects add node to this
     */
    public void insertNode(A node){
        if(!this.containsNode(node)) {

            graph.put(new Node<A>(node), new ArrayList<Edge<A, B>>());
            checkRep();
        }
    }

    /**
     * Add an edge from parent to child, named label, to the graph
     * if the child or parent node does not exist, then add a new node to graph and then add edge
     * @param parent node that the edge starts from
     * @param child node that the edge ends with
     * @param label the name of the edge
     * @spec.modifies this
     * @spec.requires the inserted edge does not exist before inserted
     * @spec.effects insert edge from parent to child to this
     */
    public void insertEdge(A parent,A child,B label){
        Edge<A,B> ed = new Edge<A,B>(parent,child,label);
        if(!this.containsNode(parent)){
            this.insertNode(parent);
        }
        if(!this.containsNode(child)){
            this.insertNode(child);
        }
            graph.get(ed.getParent()).add(ed);


        checkRep();
    }

    /**
     * remove node from the graph, and remove all edges from the node or to the node
     * @param node the node to be removed
     * @throws IllegalArgumentException if node is not in the graph
     * @spec.modifies this
     * @spec.effects remove the node from this
     */
    public void removeNode(A node){
        if(!this.containsNode(node)){
            throw new IllegalArgumentException();
        }
        graph.remove(new Node<A>(node)); // remove the node
        for(List<Edge<A,B>> arr:graph.values()){
            arr.removeIf(ed -> ed.getChild().equals(new Node<A>(node)));
        } //remove any edge that have child of node
        checkRep();
    }

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
    public void removeEdge(A parent,A child,B label){
        Edge<A,B> ed = new Edge<A,B>(parent, child, label);
        if(!this.containsNode(parent) || !this.containsNode(child) ||!graph.get(new Node<A>(parent)).contains(ed)){
            throw new IllegalArgumentException();
        }
        graph.get(new Node<A>(parent)).remove(ed);
        checkRep();
    }


    /**
     * check whether node is contained in this. Return <code>true</code> if node
     * is contained in the graph, <code>false</code> otherwise.
     * @param node to be checked
     * @return True if node is contained in this graph, False otherwise
     */
    public boolean containsNode(A node){
        return graph.containsKey(new Node<A>(node));
    }

    /**
     * return a list consisting of nodes
     * @return a list consisting of nodes. The names
     * should appear in alphabetical order.
     */
    public List<Node<A>> listNodes(){
        Set<A> sortedNodes = new TreeSet<A>();
        for(Node<A> node: graph.keySet()){ //add all nodes in keyset of graph to TreeSet
            sortedNodes.add(node.getName());
        }

        List<Node<A>> result = new ArrayList<Node<A>>();
        Iterator<A> itr = sortedNodes.iterator();
        while(itr.hasNext()){//add the name of node into string result
            result.add(new Node<A>(itr.next()));
        }
        return result;


    }

    /**
     * return a list of Edge consisting of names of children nodes of the given parent.
     * @param parent node that is parent
     * @return a list of edge consisting of names of children nodes of the given parent.
     * @throws IllegalArgumentException if parent is not contained in the graph
     */
    public List<Edge<A,B>> listChildren(A parent){
        if(!this.containsNode(parent)){
            throw new IllegalArgumentException();
        }
        Node<A> node = new Node<A>(parent);
        List<Edge<A,B>> result = new ArrayList<Edge<A,B>>();
        for(Edge<A,B> ed:graph.get(node)){
            result.add(ed);
        }
        return result;



    }



    /**
     * return a list of edge that starts from parent node
     * @param parent the name of parent node that the edge start from
     * @return a list of edge that starts from parent node
     */
    public List<Edge<A,B>> get(A parent){
        return graph.get(new Node<A>(parent));
    }

    /**
     * return a list of edge that starts from parent node
     * @param parent the node that edge starts from
     * @return a list of edge that starts from parent node
     */
    public List<Edge<A,B>> get(Node parent){
        return graph.get(parent);
    }



    /**
     * <b>Node</b> represent an <b>immutable</b> location. It takes a name of
     * location and store that location as Node object.
     */
    public static class Node<A>{
        // Rep invariant: name != null
        //AF(this) = a node with name this.name

        // the name of the node
        public final A name;

        /**
         * throw exception if rep invariant is violated.
         */
        private void checkRep(){

            assert(name != null):"The name of node is invalid";
        }

        /**
         * construct a new Node with name
         * @param name name of location
         */
        public Node(A name){
            this.name = name;
            checkRep();
        }

        /**
         * return the name of the node
         * @return name of the node
         */
        public A getName(){
            return name;
        }

        /**
         * compare two nodes. Return true if they have the same name, false otherwise
         * @param o the node that need to be compared with
         * @return true if two nodes are equal, false otherwise
         */
        @Override
        public boolean equals(Object o){
            if(!(o instanceof Node)){
                return false;
            }
            Node<?> n = (Node<?>) o;
            return this.name.equals(n.name);
        }

        /**
         * @return the hashcode of name as hashcode of node
         */
        @Override
        public int hashCode(){
            return name.hashCode();
        }


    }

    /**
     * <b>Edge</b> represents <b>immutable</b> directed edge that starts from one location
     * to another location, with a name with it.
     * The location that the edge starts from is parent, and the location that the edge
     * end with is child.
     */
    public static class Edge<X,Y> {
        //rep invariant: parent != null && child != null && label!= null
        //AF(this) = an edge starts from this.parent to this.child with name this.label

        // the parent node
        private final Node<X> parent;
        // the child node
        private final Node<X> child;
        //the name of the edge
        private final Y label;

        /**
         * throw exception if rep variant is violated.
         */
        private void checkRep(){
            assert(parent != null ):"The parent is null";
            assert (child != null):"The child is null";
            assert (label != null):"The name is null";
        }
        /**
         * construct a new Edge from parent to child with a name
         * @param parent the edge starts from
         * @param child the edge ends with
         * @param label the edge's name
         */
        public Edge(X parent,X child,Y label){
            this.parent = new Node<X>(parent);
            this.child = new Node<X>(child);
            this.label = label;
            checkRep();
        }

        /**
         * return the name of the edge
         * @return the name of given edge
         */
        public Y getName(){
            return label;
        }

        /**
         * return the parent node the edge
         * @return the parent node of the edge
         */
        public Node<X> getParent(){
        return parent;
    }

    /**
     * return the child node of the edge
     * @return the child node of the edge
     */
    public Node<X> getChild(){
        return child;
    }

    /**
     *
     * @return the sum of hashcode of parent, child, and label
     *          as hashCode of edge
     */
    @Override
    public int hashCode(){
        return parent.hashCode()+child.hashCode()+label.hashCode();
    }

    /**
     * check if two edges are considered equal.
     * @param o the other object to be compared
     * @return true if they are considered equal, e.g.have the same parent node and child node
     * and have the same name. False otherwise.
     */
    @Override
    public boolean equals(Object o){
        if(!(o instanceof Edge)){
            return false;
        }
        Edge<?,?> n = (Edge<?,?>) o;
        return parent.equals(n.parent) && child.equals(n.child) && label.equals(n.label);
    }
}



}
