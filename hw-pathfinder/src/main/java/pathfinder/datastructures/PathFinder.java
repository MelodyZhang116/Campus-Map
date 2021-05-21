package pathfinder.datastructures;

import graph.Graph;

import java.util.*;


/**
 * This class is used to find the shorted path using Dijkstra's Algorithm.
 * @param <A> the type of node(undecided, can be number, string...)
 * @param <Number> the type that is stored as label of edge in graph
 */
public class PathFinder<A,Number> {
    public static final boolean DEBUG = false;

    // the graph containing all the info of location and distance
    Graph<A,Double> g;
    //AF: find the path that start from a node of this.g and end with a node from this.g
    //    and has the shortest distance(the sum of distance of edges in this.g is smallest)
    //RepInv: g is not null and for each term in this.g, there is no null.
    //        for each edge, its label is not negative.

    /**
     * construct a new PathFinder
     * @param graph that searched for path
     */
    public PathFinder(Graph<A,Double> graph){
        g = graph;
    }

    private void checkRep(){
        if(DEBUG){
            assert(!g.equals(null)):"graph is null";
            assert(!g.containsNode(null)): "The graph has null node(s)";
            assert (!g.allEdges().contains(null)) :"The graph has null edge(s)";

            for(List<Graph.Edge<A, Double>> list:g.allEdges()){
                for(Graph.Edge<A,Double> ed:list){
                    assert(ed != null):"The graph has null edges";
                    assert(ed.getName()>=0.0):"The cost of edge is negative";
                }

            }
        }
    }

    /**
     * find the shortest path ( the smallest sum of cost of segment)
     * @param starting path start from
     * @param destination path end with
     * @return shortest path from starting to destination
     * @spec.requires starting != null and destination != null and
     * starting exist and destination exist
     */
    public Path<Graph.Node<A>> findPath(A starting, A destination){
        if(!g.containsNode(starting) || !g.containsNode(destination)){
            throw new IllegalArgumentException();
        }
        Graph.Node<A> start = new Graph.Node<A>(starting);
        Graph.Node<A> dest = new Graph.Node<A>(destination);

        Comparator<Path<Graph.Node<A>>> comparator= new CostComparator();
        Queue<Path<Graph.Node<A>>> active = new PriorityQueue<Path<Graph.Node<A>>>(comparator);
        // Each element is a path from start to a given node
        // A path's priority in the queue is the total cost of the path
        List<Graph.Node<A>> finished = new ArrayList<Graph.Node<A>>();
        // set of A for which we know the minimum-cost path from start
        active.add(new Path<Graph.Node<A>>(start));

        while(!active.isEmpty()){
            Path<Graph.Node<A>> minPath = active.poll();
            Graph.Node<A> minDest = minPath.getEnd();
            if(minDest.equals(dest)){
                return minPath; //find you!
            }
            if(!finished.contains(minDest)){
                for(Graph.Edge<A,Double> ed:g.listChildren(minDest.getName())){ // go through all edges that start from minDest
                    if(!finished.contains(ed.getChild())){
                        Path<Graph.Node<A>> newPath = minPath.extend(ed.getChild(),ed.getName());
                        active.add(newPath);
                    }

                }
                finished.add(minDest);


            }

        }
        throw new RuntimeException("No path exist from "+starting.toString()+" to "+destination.toString());

    }


    /**
     * a comparator that compare the total cost of path
     */
    public class CostComparator implements Comparator<Path<Graph.Node<A>>>{
        @Override
        public int compare(Path<Graph.Node<A>> x,Path<Graph.Node<A>> y ){
            if(x.getCost()<y.getCost()){
                return -1;
            }
            if(x.getCost()>y.getCost()){
                return 1;
            }
            return 0;
        }
    }
}

