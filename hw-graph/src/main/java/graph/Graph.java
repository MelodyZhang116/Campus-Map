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
    //rep invariant: for each term in map, there is no null.
    //AF(this) = a graph with nodes in key elements of this.graph and with edges in mapped values of this.graph
    private Map<Node,List<Edge>> graph;

    /**
     * throw exception if rep invariant is violated.
     */
    private void checkRep(){
        assert(!graph.containsKey(null));
        assert (!graph.containsValue(null));
        for(List<Edge> list:graph.values()){
            for(Edge ed:list){
                assert (ed!=null);
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
    }

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
    public void insertEdge(String parent,String child,String label){
        Edge ed = new Edge(parent,child,label);
        if(!graph.containsKey(new Node(parent))||!graph.containsKey(new Node(child))||graph.get(ed.getParent()).contains(ed)){
            throw new IllegalArgumentException();
        }
        graph.get(ed.getParent()).add(ed);
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
        graph.remove(new Node(node));
        for(List<Edge> arr:graph.values()){
            arr.removeIf(ed -> ed.getChild().equals(new Node(node)));
        }
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
        for(Node node: graph.keySet()){
            sortedNodes.add(node.getName());
        }

        String result = "";
        Iterator<String> itr = sortedNodes.iterator();
        while(itr.hasNext()){
            result = result + " "+itr.next();
        }
        return result;


    }

    /**
     * return a string consisting of names of children nodes of the given parent.
     * @param parent node that is parent
     * @return a string consisting of names of children of parent. The syntax is
     * specified in listNode method.
     * @throws IllegalArgumentException if parent is not contained in the graph
     */
    public String listChildren(String parent){
        if(!this.containsNode(parent)){
            throw new IllegalArgumentException();
        }
        Set<String> children = new TreeSet<>();
        Node node = new Node(parent);
        if(graph.get(node).isEmpty()){
            return "";
        }
        for(Edge ed: graph.get(node)){
            children.add(ed.getChild().getName()+"("+ed.getName()+")");
        }
        String result = "";
        Iterator<String> itr = children.iterator();
        while(itr.hasNext()){
            result = result + " "+itr.next();
        }
        return result;

    }



    /**
     * <b>Node</b> represent an <b>immutable</b> location. It takes a name of
     * location and store that location as Node object.
     */
    public static class Node {
        // Rep invariant: name != null
        //AF(this) = a node with name this.name

        // the name of the node
        final String name;

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

        @Override
        public boolean equals(Object o){
            if(!(o instanceof graph.Node)){
                return false;
            }
            graph.Node n = (graph.Node) o;
            return this.name.equals(n.name);
        }

        @Override
        public int hashCode(){
            return name.length();
        }


    }


}
