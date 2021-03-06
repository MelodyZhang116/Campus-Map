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
    private Graph<String,String> g;

    public static void main(String[] args){
        try {
            Scanner input = new Scanner(System.in);
            System.out.println("Do you want to start?Yes/No");
            while (input.nextLine().equalsIgnoreCase("yes")) {
                System.out.println("Give a name to your MarvelPaths:");
                String name = input.nextLine();
                System.out.println("Which file you want to read from?");
                String file = input.nextLine();
                MarvelPaths graph = new MarvelPaths(file);
                System.out.println("Do you want to find a path? Yes/No");
                while(input.nextLine().equalsIgnoreCase("yes")){
                    System.out.println("a character name to start:");
                    String starting = input.nextLine();
                    while(!graph.containsNode(starting)){
                        System.out.println("start does not exist.");
                        System.out.println("Another name to start(or q to quit): ");

                        starting = input.nextLine();
                        if(starting.equalsIgnoreCase("q")){
                            break;
                        }
                    }
                    System.out.println("a character name to end: ");
                    String dest = input.nextLine();
                    while(!graph.containsNode(dest)){
                        System.out.println("destination does not exist");
                        System.out.println("Another name to end (or q to quit): ");
                        dest = input.nextLine();
                        if(dest.equalsIgnoreCase("q")){
                            break;
                        }
                    }
                    try{
                        List<Edge<String,String>> paths = graph.findPaths(starting,dest);
                        for(Edge ed:paths){
                            System.out.println(ed.getParent().getName()+" to "+ed.getChild().getName()+" via "+ed.getName());
                        }
                    }catch(RuntimeException e){
                        System.out.println("no path found");
                    }


                }
                System.out.println("Do you want to find a path? Yes/No");



            }
            System.out.println("Do you want to start?Yes/No");

        } catch (IOException e){
            System.out.println("the file doesn't exist, has an invalid name, or can't be read");
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
        g = new Graph<String,String>();
        for(String comic : data.keySet()){
            List<String> characters = data.get(comic);
            for(int i = 0 ; i <characters.size();i++){
                for(int j =i+1; j<characters.size();j++){
                    if(!characters.get(i).equals(characters.get(j))){
                        this.insertEdge(characters.get(i),characters.get(j),comic);//insert edge from one character to another character
                                                                    // with name of comic book(two characters appear in that book
                                                                    // and they cannot be the same)
                        this.insertEdge(characters.get(j),characters.get(i),comic);
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
    public MarvelPaths(Graph<String,String> g){
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
    public List<Edge<String,String>> listChildren(String parent){
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
     * @return a path through the graph connecting them. And it return the shortest path found by BFS
     * @spec.requires starting and destination is in g(main checks that)
     */
    public List<Edge<String,String>> findPaths(String starting, String destination){
        Node<String> start = new Node<String>(starting);
        Node<String> dest = new Node<String>(destination);
        if(!g.containsNode(starting) || !g.containsNode(destination)){
            throw new IllegalArgumentException();
        }
        Queue<Node<String>> nodesToVisit = new LinkedList<>();   //the worklist
        Map<Node<String>,List<Edge<String,String>>> map = new HashMap<Node<String>,List<Edge<String,String>>>();  //map from node to path
        //Each key in map is a visited node, each value is a path from start to that node

        nodesToVisit.add(start);
        map.put(start,new ArrayList<Edge<String,String>>());

        while(!nodesToVisit.isEmpty()){
            Node<String> nodeVisiting = nodesToVisit.remove();
            if(nodeVisiting.equals(dest)){
                return map.get(nodeVisiting); // find the path!
            }

            List<Edge<String,String>> sortedEdge= g.listChildren(nodeVisiting.getName()); //get the children of that node which is visited in sorted order
            for(Edge<String,String> childAndEdge :sortedEdge){
                Node<String> child = childAndEdge.getChild();
                Edge<String,String> edge = childAndEdge;
                if(!map.containsKey(child)){ //that child has not been visited
                    List<Edge<String,String>> path = new ArrayList<>(map.get(nodeVisiting));
                    path.add(edge);

                    map.put(child,path);
                    nodesToVisit.add(child);
                }
            }
        }



        throw new RuntimeException("no path exist from "+starting+" to "+destination);
    }


}
