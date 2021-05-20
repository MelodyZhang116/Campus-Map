package graph.junitTests;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.Timeout;
import graph.Graph;
import graph.Graph.Node;
import graph.Graph.Edge;

import static org.junit.Assert.assertEquals;

/**
 * This class contains a set of tests that can test implementation of Node class
 */
public class NodeTest {
    @Rule public Timeout glocalTimeout = Timeout.seconds(10);//10 seconds max per method tested

    /** Tests whether a node can be created
     *
     */
    @Test
    public void createNode() {
        new Node<String>("n1");
        new Node<String>("n2");
    }

    /** Tests whether the name of a node can be returned
     *
     */
    @Test
    public void returnName() {
        Node<String> n = new Node<String>("n1");
        Node<String> m = new Node<String>("m1");
        assertEquals("n1",n.getName());
        assertEquals("m1",m.getName());
    }
}
