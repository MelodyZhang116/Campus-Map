package pathfinder.datastructures;

import graph.Graph;

import java.util.*;



public class PathFinder<A,Number> {
    Graph<A,Double> g;

    public PathFinder(Graph<A,Double> graph){
        g = graph;
    }
    public Path<Graph.Node<A>> findPath(A starting, A destination){
        Graph.Node<A> start = new Graph.Node<A>(starting);
        Graph.Node<A> dest = new Graph.Node<A>(destination);

        Comparator<Path<Graph.Node<A>>> comparator= new CostComparator();
        Queue<Path<Graph.Node<A>>> active = new PriorityQueue<Path<Graph.Node<A>>>(comparator);// Each element is a path from start to a given node
        // A path's priority in the queue is the total cost of the path
        List<Graph.Node<A>> finished = new ArrayList<Graph.Node<A>>(); // set of A for which we know the minimum-cost path from start
        active.add(new Path<Graph.Node<A>>(start));

        while(!active.isEmpty()){
            Path<Graph.Node<A>> minPath = active.poll();
            Graph.Node<A> minDest = minPath.getEnd();
            if(minDest.equals(dest)){
                return minPath;
            }
            if(!finished.contains(minDest)){
                for(Graph.Edge<A,Double> ed:g.listChildren(minDest.getName())){
                    if(finished.contains(ed.getChild())){
                        Path<Graph.Node<A>> newPath = minPath.extend(ed.getChild(),ed.getName());
                        active.add(newPath);
                    }

                }
                finished.add(minDest);


            }

        }
        throw new RuntimeException("No path exist from "+starting.toString()+" to "+destination.toString());

    }



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
