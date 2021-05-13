package marvel;
import graph.Graph;
import graph.Graph.Node;
import graph.Graph.Edge;


import java.io.IOException;
import java.util.*;

import static marvel.MarvelParser.parseData;

public class MarvelPaths {
    private Graph g;

    public MarvelPaths() throws IOException {
        Map<String, List<String>> data = parseData("marvel.csv");
        for(String comic : data.keySet()){
            for(String character1:data.get(comic)){
                for(String character2:data.get(comic)){
                    if(!character1.equals(character2)){
                        g.insertEdge(character1,character2,comic);
                    }
                }
            }
        }

    }

    public String findPaths(String starting, String destination){
        if(!g.containsNode(starting) && !g.containsNode(destination)){
            return "unknown: "+starting+"\nunknown: "+destination;
        }
        if(!g.containsNode(starting)){
            return "unknown: " + starting;
        }
        if(!g.containsNode(destination)){
            return "unknown: "+destination;
        }

        Node start = new Node(starting);
        Node dest = new Node(destination);
        Queue<Node> nodesToVisit = new LinkedList<>();   //the worklist
        Map<Node,List<Edge>> map = new HashMap<Node,List<Edge>>();  //map from node to path
        //Each ket in map is a visited node, each value is a path from start to that node

        nodesToVisit.add(start);
        map.put(start,new ArrayList<Edge>());

        while(!nodesToVisit.isEmpty()){
            Node nodeVisiting = nodesToVisit.remove();
            if(nodeVisiting.equals(dest)){
                //return a string TODO
                return turnToString(map.get(nodeVisiting),starting,destination);
            }

            //before foreach loop, turn all the children(with its edge) of the node to be visited
            // to an alphabetically sorted arraylist.
            List<String[]> sortedEdge= g.listChildren(nodeVisiting.getName());//get a alphabetically sorted string of children

            // at this stage, sortedEdge should be a arraylist of edge that is sorted alphabetically

            for(String[] childAndEdge :sortedEdge){
                Node child = new
                if(!map.containsKey()){ //that child has not been visited
                    List<Edge> path = map.get(nodeVisiting);
                    path.add(edgeOfChildren);

                    map.put(edgeOfChildren.getChild(), path);
                    nodesToVisit.add(edgeOfChildren.getChild());

                }
            }
        }



        throw new RuntimeException();
    }

    private String turnToString(List<Edge> edge,String start,String dest){
        String result = "";
        result += "path from "+start+" to "+dest+":";
        if(edge.isEmpty()){
            result += "/nno path found";
            return result;
        }
        for(Edge ed:edge){
            result += "/n"+ed.getParent().getName()+" to "+ed.getChild().getName()+" via "+ed.getName();
        }

    }
}
