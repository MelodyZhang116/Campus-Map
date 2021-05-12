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
        Node start = new Node(starting);
        Node dest = new Node(destination);
        Queue<Node> nodesToVisit = new LinkedList<>();
        Map<Node,List<Edge>> map = new HashMap<Node,List<Edge>>();
        nodesToVisit.add(start);
        map.put(start,new ArrayList<Edge>());
        while(!nodesToVisit.isEmpty()){
            Node nodeVisiting = nodesToVisit.remove();
            if(nodeVisiting.equals(dest)){
                return //TODO
            }
            for(Edge edgeOfChildren:g.get(nodeVisiting)){
                if(!map.containsKey(edgeOfChildren.getChild())){
                    List<Edge> path = map.get(nodeVisiting);
                    Boolean add = path.add(edgeOfChildren);
                    if(add) {
                        map.put(edgeOfChildren.getChild(), path);
                        nodesToVisit.add(edgeOfChildren.getChild());
                    }
                }
            }
        }




    }
}
