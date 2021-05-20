package graph.junitTests;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.Timeout;
import graph.Graph.Node;
import graph.Graph.Edge;
import graph.Graph;


import static org.junit.Assert.assertEquals;

/**
 * This class contains a set of tests that can test implementation of Edge class
 */
public class EdgeTest  {
    @Rule public Timeout glocalTimeout = Timeout.seconds(10);//10 seconds max per method tested
    /** Tests whether an edge can be created
     *
     */
    @Test
    public void createEdge() {
        new Edge<String,String>("n1","n2","e12");
        new Edge<String,String>("n3","n4","n34");
    }

    /** Tests whether the name of parent of an edge can be returned
     *
     */
    @Test
    public void returnParentName() {
        Edge<String,String> n = new Edge<String,String>("n1","n2","n12");
        Edge<String,String> m = new Edge<String,String>("m1","m2","m12");
        assertEquals("n1",n.getParent().getName());
        assertEquals("m1",m.getParent().getName());
    }
    /** Tests whether the name of child of an edge can be returned
     *
     */
    @Test
    public void returnChildName() {
        Edge<String,String> n = new Edge<String,String>("n1","n2","n12");
        Edge<String,String> m = new Edge<String,String>("m1","m2","m12");
        assertEquals("n2",n.getChild().getName());
        assertEquals("m2",m.getChild().getName());
    }
    /** Tests whether the name of an edge can be returned
     *
     */
    @Test
    public void returnName() {
        Edge<String,String> n = new Edge<String,String>("n1","n2","n12");
        Edge<String,String> m = new Edge<String,String>("m1","m2","m12");
        assertEquals("n12",n.getName());
        assertEquals("m12",m.getName());
    }
}
