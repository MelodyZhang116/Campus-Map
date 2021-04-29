package graph.junitTests;
import graph.Edge;
import graph.Node;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * This class contains a set of tests that can test implementation of Edge class
 */
public class EdgeTest {
    /** Tests whether an edge can be created
     *
     */
    @Test
    public void createEdge() {
        new Edge("n1","n2","e12");
        new Edge("n3","n4","n34");
    }

    /** Tests whether the name of parent of an edge can be returned
     *
     */
    @Test
    public void returnParentName() {
        Edge n = new Edge("n1","n2","n12");
        Edge m = new Edge("m1","m2","m12");
        assertEquals("n1",n.getParent());
        assertEquals("m1",m.getParent());
    }
    /** Tests whether the name of child of an edge can be returned
     *
     */
    @Test
    public void returnChildName() {
        Edge n = new Edge("n1","n2","n12");
        Edge m = new Edge("m1","m2","m12");
        assertEquals("n2",n.getChild());
        assertEquals("m2",m.getChild());
    }
    /** Tests whether the name of an edge can be returned
     *
     */
    @Test
    public void returnName() {
        Edge n = new Edge("n1","n2","n12");
        Edge m = new Edge("m1","m2","m12");
        assertEquals("n12",n.getName());
        assertEquals("m12",m.getName());
    }
}
