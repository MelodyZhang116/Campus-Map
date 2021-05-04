package graph.junitTests;
import graph.Node;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * This class contains a set of tests that can test implementation of Node class
 */
public class NodeTest {
    /** Tests whether a node can be created
     *
     */
    @Test
    public void createNode() {
        new Node("n1");
        new Node("n2");
    }

    /** Tests whether the name of a node can be returned
     *
     */
    @Test
    public void returnName() {
        Node n = new Node("n1");
        Node m = new Node("m1");
        assertEquals("n1",n.getName());
        assertEquals("m1",m.getName());
    }
}
