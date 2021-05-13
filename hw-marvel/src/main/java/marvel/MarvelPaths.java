package marvel;
import graph.Graph;
import graph.Graph.Node;
import graph.Graph.Edge;


import java.io.IOException;
import java.util.*;

import static marvel.MarvelParser.parseData;

public class MarvelPaths {
    private Graph g;

    public MarvelPaths(String fileName) throws IOException {
        Map<String, List<String>> data = parseData(fileName);
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


    public boolean containsNode(String node) {
        return g.containsNode(node);
    }
    public List<String[]> listChildren(String parent){
        return g.listChildren(parent);
    }

    public MarvelPaths(Graph g){
        this.g = g;
    }
    public void insertNode(String node){
        g.insertNode(node);
    }

    public void insertEdge(String parent,String child,String label){
        g.insertEdge(parent,child,label);
    }

    public List<Edge> findPaths(String starting, String destination){


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
                return map.get(nodeVisiting);
            }


            List<String[]> sortedEdge= g.listChildren(nodeVisiting.getName());

            // at this stage, sortedEdge should be a arraylist of edge that is sorted alphabetically

            for(String[] childAndEdge :sortedEdge){
                Node child = new Node(childAndEdge[0]);
                Edge edge = new Edge(nodeVisiting.getName(),childAndEdge[0],childAndEdge[1]);
                if(!map.containsKey(child)){ //that child has not been visited

                    map.put(child,new ArrayList<Edge>());
                    for(Edge ed:map.get(nodeVisiting)){
                        map.get(child).add(ed);
                    }
                    map.get(child).add(edge);
                    nodesToVisit.add(child);

                }
            }
        }



        throw new RuntimeException();
    }


}
