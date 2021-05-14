package graph;

import java.util.*;


/**<b>Graph</b> represents an immutable graph.
 * This class represents a directed labeled graph. It is a collection of nodes and edges.
 * Each edge connects two nodes and it is directed. e.g.edge e (A,B) indicates en edge from A
 * to B. Then B is the child node of A, and A is the parent node of B. A path refers to a sequence
 * of edges traveling from A to B. Assume that no 2 edges with the same parent and child nodes will
 * have the same edge label.
 */
public class Graph {
    public static final boolean DEBUG = false;
    //rep invariant: for each term in map, there is no null.
    //AF(this) = a graph with nodes in key elements of this.graph and with edges in mapped values of this.graph

    //a map that store the nodes in key and store its edge starting from that node into value of that node
    private Map<Node,List<Edge>> graph;

    /**
     * throw exception if rep invariant is violated.
     */
    private void checkRep(){
        if(DEBUG){
            assert(!graph.containsKey(null)): "The graph has null node(s)";
            assert (!graph.containsValue(null)) :"The graph has null edge(s)";

            for(List<Edge> list:graph.values()){
                for(int i = 0 ; i < list.size()-1 ; i++){
                    assert(list.get(i) != null):"The graph has null edges";
                    for(int j = i +1; j < list.size(); j++){
                        assert(!list.get(i).equals(list.get(j))):"The parent node " + list.get(i).getParent()+
                                " and the child node " + list.get(i).getChild() + " have duplicate edges "+list.get(i).getName();
                    }
                }
                if(list.size()>0) {
                    assert (list.get(list.size() - 1) != null) : "The graph has null edges";
                }
            }
        }
    }

    /**
     * constructor that construct a new Graph
     * @spec.effects constructs an empty queue
     */
    public Graph(){
        this.graph = new HashMap<Node,List<Edge>>();
        checkRep();
    }

    public Collection<List<Edge>> allEdges(){
        return graph.values();
    }
    /**
     * Add a node to the graph.
     * @param node with name node to be added
     * @throws IllegalArgumentException if node is already in the graph
     * @spec.modifies this
     * @spec.effects add node to this
     */
    public void insertNode(String node){
        if(this.containsNode(node)){
            throw new IllegalArgumentException();
        }
        graph.put(new Node(node),new ArrayList<Edge>());
        checkRep();
    }

    /**
     * Add an edge from parent to child, named label, to the graph
     * if the child or parent node does not exist, then add a new node to graph and then add edge
     * @param parent node that the edge starts from
     * @param child node that the edge ends with
     * @param label the name of the edge
     * @throws IllegalArgumentException if the edge already exist
     * @spec.modifies this
     * @spec.effects insert edge from parent to child to this
     */
    public void insertEdge(String parent,String child,String label){
        Edge ed = new Edge(parent,child,label);
        if(!graph.containsKey(new Node(parent))){
            this.insertNode(parent);
        }else if(!graph.containsKey(new Node(child))){
            this.insertNode(child);
        }
        if(!graph.get(ed.getParent()).contains(ed)){
            graph.get(ed.getParent()).add(ed);

        }
        checkRep();
    }

    /**
     * remove node from the graph, and remove all edges from the node or to the node
     * @param node the node to be removed
     * @throws IllegalArgumentException if node is not in the graph
     * @spec.modifies this
     * @spec.effects remove the node from this
     */
    public void removeNode(String node){
        if(!this.containsNode(node)){
            throw new IllegalArgumentException();
        }
        graph.remove(new Node(node)); // remove the node
        for(List<Edge> arr:graph.values()){
            arr.removeIf(ed -> ed.getChild().equals(new Node(node)));
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
    public void removeEdge(String parent,String child,String label){
        Edge ed = new Edge(parent, child, label);
        if(!this.containsNode(parent) || !this.containsNode(child) ||!graph.get(new Node(parent)).contains(ed)){
            throw new IllegalArgumentException();
        }
        graph.get(new Node(parent)).remove(ed);
        checkRep();
    }


    /**
     * check whether node is contained in this. Return <code>true</code> if node
     * is contained in the graph, <code>false</code> otherwise.
     * @param node to be checked
     * @return True if node is contained in this graph, False otherwise
     */
    public boolean containsNode(String node){
        return graph.containsKey(new Node(node));
    }

    /**
     * return a string consisting of names of nodes
     * @return a string consisting of names of nodes. Names are listed on the same line, by
     * a space-separated list of the node data contained in each node of the graph. The names
     * should appear in alphabetical order.
     */
    public String listNodes(){
        Set<String> sortedNodes = new TreeSet<String>();
        for(Node node: graph.keySet()){ //add all nodes in keyset of graph to TreeSet
            sortedNodes.add(node.getName());
        }

        String result = "";
        Iterator<String> itr = sortedNodes.iterator();
        while(itr.hasNext()){//add the name of node into string result
            result = result + " "+itr.next();
        }
        return result;


    }

    /**
     * return a list of string[] consisting of names of children nodes of the given parent.
     * @param parent node that is parent
     * @return a list of string[] consisting of names of children nodes of the given parent.
     * each string[] has two elements: name of child node in index0, name of edge in index 1
     * @throws IllegalArgumentException if parent is not contained in the graph
     */
    public List<String[]> listChildren(String parent){
        if(!this.containsNode(parent)){
            throw new IllegalArgumentException();
        }
        Set<String> children = new TreeSet<>();
        Node node = new Node(parent);

        for(Edge ed: graph.get(node)){ // add the name of children associated with its edge
                                       //to a TreeSet
            children.add(ed.getChild().getName()+" "+ed.getName());
        }
        List<String[]> result = new ArrayList<String[]>();
        Iterator<String> itr = children.iterator();
        while(itr.hasNext()){ // add the names into string result
            String nextChild = itr.next();
            int index = nextChild.indexOf(" ");
            String[] childToAdd = new String[]{nextChild.substring(0,index),nextChild.substring(index+1)};
            result.add(childToAdd);

        }
        return result;

    }

    /**
     * return a list of edge that starts from parent node
     * @param parent the name of parent node that the edge start from
     * @return a list of edge that starts from parent node
     */
    public List<Edge> get(String parent){
        return graph.get(new Node(parent));
    }

    /**
     * return a list of edge that starts from parent node
     * @param parent the node that edge starts from
     * @return a list of edge that starts from parent node
     */
    public List<Edge> get(Node parent){
        return graph.get(parent);
    }



    /**
     * <b>Node</b> represent an <b>immutable</b> location. It takes a name of
     * location and store that location as Node object.
     */
    public static class Node {
        // Rep invariant: name != null
        //AF(this) = a node with name this.name

        // the name of the node
        public final String name;

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
            Node n = (Node) o;
            return this.name.equals(n.name);
        }

        /**
         * return the length of the name as hashcode of node.
         * @return the length of the name as hashcode of node.
         */
        @Override
        public int hashCode(){
            return name.length();
        }


    }

    /**
     * <b>Edge</b> represents <b>immutable</b> directed edge that starts from one location
     * to another location, with a name with it.
     * The location that the edge starts from is parent, and the location that the edge
     * end with is child.
     */

    public static class Edge {
        //rep invariant: parent != null && child != null && label!= null
        //AF(this) = an edge starts from this.parent to this.child with name this.label

        // the parent node
        private final Node parent;
        // the child node
        private final Node child;
        //the name of the edge
        private final String label;

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
        public String getName(){
            return label;
        }

        /**
         * return the parent node the edge
         * @return the parent node of the edge
         */
        public Node getParent(){
        return parent;
    }

    /**
     * return the child node of the edge
     * @return the child node of the edge
     */
    public Node getChild(){
        return child;
    }

    /**
     *
     * @return the sum of length of name of parent, child, and name of edge
     *          as hashCode of edge
     */
    @Override
    public int hashCode(){
        return parent.hashCode()+child.hashCode()+label.length();
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
        Edge n = (Edge) o;
        return parent.equals(n.parent) && child.equals(n.child) && label.equals(n.label);
    }
}


}
