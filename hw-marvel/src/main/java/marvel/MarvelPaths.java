package marvel;
import graph.Graph;
import graph.Graph.Node;
import graph.Graph.Edge;


import java.io.IOException;
import java.util.*;

import static marvel.MarvelParser.parseData;
/**<b>MarvelPaths</b> represents a social network among characters in Marvel comic books.
 * Each node represent a character and an edge<char1,char2> indicates that char1 appeared in a comic
 * book that char2 also appeared in. There is also a seperate edge for every comic book any two characters
 * appear in, labeled with the name of the book.
 */
public class MarvelPaths {
    public static final boolean DEBUG = false;


    //rep invariant: there is no edge in Graph g whose parent and child are the same.
    //AF(this) = if a and b both appear in marvel comic book BOOK, then store a and b as nodes and BOOK from
    //           a to b and BOOK from b to a as edges into this.g

    //a graph that store characters of comic book as nodes and the name of that comic book as edge(its parent and
    // child are the characters both appear in that book)
    private Graph g;

    /**
     * throw exception if rep invariant is violated.
     */
    private void checkRep(){
        if(DEBUG){
            for(List<Edge> list:g.allEdges()){
                for(Edge ed:list){
                    assert (!ed.getParent().equals(ed.getChild())):"there is edge whose parent and child are the same";
                }
            }
        }
    }
    /**
     * constructor that construct a new Graph
     * @param fileName convert the lines in fileName into a MarvelPaths
     * @spec.requires the content in fileName is assumed to be correct. Each line is name of character + "," + name of comic book
     * @spec.effects constructs MarvelPaths.
     */
    public MarvelPaths(String fileName) throws IOException {
        Map<String, List<String>> data = parseData(fileName);//convert the content in csv file into a map
                                            // whose key is name of comic book, and value are characters contained in that book
        g = new Graph();
        for(String comic : data.keySet()){
            for(String character1:data.get(comic)){
                for(String character2:data.get(comic)){
                    if(!character1.equals(character2)){
                        this.insertEdge(character1,character2,comic);//insert edge from one character to another character
                                                                    // with name of comic book(two characters appear in that book
                                                                    // and they cannot be the same)
                    }
                }
            }
        }

    }
    /**
     * constructor that construct a new MarvelPaths
     * @param g a graph to store in this.g
     * @spec.effects construct a new MarvelPaths
     */
    public MarvelPaths(Graph g){
        this.g = g;
    }

    /**
     * check whether character is contained in this. Return <code>true</code> if he/she
     * is contained in the graph, <code>false</code> otherwise.
     * @param chara to be checked
     * @return True if character is contained in this graph, False otherwise
     */
    public boolean containsNode(String chara) {
        return g.containsNode(chara);
    }

    /**
     * return a list of string[] consisting of names of characters who appear in the same book.
     * @param parent character that is parent
     * @return a list of string[] consisting of names of characters who appear in the same book.
     * each string[] has two elements: name of character name in index0, name of book in index 1
     * @throws IllegalArgumentException if parent is not contained
     */
    public List<String[]> listChildren(String parent){
        return g.listChildren(parent);
    }

    /**
     * Add a character to the MarvelPaths.
     * @param character with name character to be added
     * @throws IllegalArgumentException if character is already in the graph
     * @spec.modifies this
     * @spec.effects add character to this
     */
    public void insertNode(String character){
        g.insertNode(character);
    }

    /**
     * Add an edge from character1 to character2, named book, to the graph
     * if the char1 or char2 node does not exist, then add a new character to graph and then add edge
     * @param char1 character that the edge starts from
     * @param char2 character that the edge ends with
     * @param book the name of the book
     * @throws IllegalArgumentException if the edge already exist
     * @spec.modifies this
     * @spec.effects insert edge from char1 to char2 to this
     */
    public void insertEdge(String char1,String char2,String book){
        g.insertEdge(char1,char2,book);
    }

    /**
     * find paths between two characters in the graph. Given names of two characters, this method search for
     * and return a path through the graph connecting them. And it return the shortest path found by BFS.
     * @param starting the path that starts from
     * @param destination the path that end with
     * @return a path through the graph connecting them. And it return the shortest path found by BFS.
     */
    public List<Edge> findPaths(String starting, String destination){
        Node start = new Node(starting);
        Node dest = new Node(destination);
        Queue<Node> nodesToVisit = new LinkedList<>();   //the worklist
        Map<Node,List<Edge>> map = new HashMap<Node,List<Edge>>();  //map from node to path
        //Each key in map is a visited node, each value is a path from start to that node

        nodesToVisit.add(start);
        map.put(start,new ArrayList<Edge>());

        while(!nodesToVisit.isEmpty()){
            Node nodeVisiting = nodesToVisit.remove();
            if(nodeVisiting.equals(dest)){
                return map.get(nodeVisiting); // find the path!
            }

            List<String[]> sortedEdge= g.listChildren(nodeVisiting.getName()); //get the children of that node which is visited in sorted order
            for(String[] childAndEdge :sortedEdge){
                Node child = new Node(childAndEdge[0]);
                Edge edge = new Edge(nodeVisiting.getName(),childAndEdge[0],childAndEdge[1]);
                if(!map.containsKey(child)){ //that child has not been visited
                    List<Edge> path = map.get(nodeVisiting);
                    path.add(edge);
                    map.put(child,path);
                    nodesToVisit.add(child);
                }
            }
        }



        throw new RuntimeException("no path exist from "+starting+" to "+destination);
    }


}
