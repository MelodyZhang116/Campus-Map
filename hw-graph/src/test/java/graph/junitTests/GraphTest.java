package graph.junitTests;
import graph.Graph;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


import static org.junit.Assert.assertEquals;

/**
 * This class contains a set of test cases that can be used to test the implementation of the Graph
 * class.
 */
public class GraphTest {
    /**
     * Tests that "add a node that already exist in graph" throws an IllegalArgumentException
     */
    @Test(expected=IllegalArgumentException.class)
    public void addNodeAlreadyExit() {
        Graph g = new Graph();
        g.insertNode("n1");
        g.insertNode("n1");
    }

    /**
     * Tests that "add an edge whose parent does not exist" throws an IllegalArgumentException
     */
    @Test(expected=IllegalArgumentException.class)
    public void addEdgeWithParentNotExist() {
        Graph g = new Graph();
        g.insertNode("n1");
        g.insertEdge("n2","n1","e1");
    }

    /**
     * Tests that "add an edge whose child does not exist" throws an IllegalArgumentException
     */
    @Test(expected=IllegalArgumentException.class)
    public void addEdgeWithChildNotExist() {
        Graph g = new Graph();
        g.insertNode("n1");
        g.insertEdge("n1","n2","e1");
    }

    /**
     * Tests that "add an edge that already exist" throws an IllegalArgumentException
     */
    @Test(expected=IllegalArgumentException.class)
    public void addEdgeAlreadyExist() {
        Graph g = new Graph();
        g.insertNode("n1");
        g.insertNode("n2");
        g.insertEdge("n2","n1","e1");
        g.insertEdge("n2","n1","e1");

    }

    /** Tests whether a node can be correctly removed in one graph
     *
     */
    @Test
    public void removeNodeFromOneGraph() {
        Graph g = new Graph();
        g.insertNode("n1");
        g.insertNode("n2");
        g.insertEdge("n2","n1","e1");
        g.removeNode("n1");
        assertEquals("remove node incorrectly because nodes are incorrect","n2",g.listNodes());

    }
    /** Tests whether removing a node(not in the graph) throw IllegalArgumentException
     *
     */
    @Test(expected=IllegalArgumentException.class)
    public void removeNodeNotExist() {
        Graph g = new Graph();
        g.insertNode("n1");
        g.insertNode("n2");
        g.insertEdge("n2","n1","e1");
        g.removeNode("n3");

    }

    /** Tests whether an edge can be correctly removed in one graph
     *
     */
    @Test
    public void removeEdgeFromOneGraph() {
        Graph g = new Graph();
        g.insertNode("n1");
        g.insertNode("n2");
        g.insertEdge("n2","n1","e1");
        g.removeEdge("n2","n1","e1");
        assertEquals("remove edge incorrectly","",g.listChildren("n2"));

    }

    /**
     * Tests that "remove an edge whose parent does not exist" throws an IllegalArgumentException
     */
    @Test(expected=IllegalArgumentException.class)
    public void removeEdgeWithParentNotExist() {
        Graph g = new Graph();
        g.insertNode("n1");
        g.removeEdge("n2","n1","e1");
    }

    /**
     * Tests that "remove an edge whose child does not exist" throws an IllegalArgumentException
     */
    @Test(expected=IllegalArgumentException.class)
    public void removeEdgeWithChildNotExist() {
        Graph g = new Graph();
        g.insertNode("n1");
        g.removeEdge("n1","n2","e1");
    }

    /**
     * Tests that "remove an edge that already exist" throws an IllegalArgumentException
     */
    @Test(expected=IllegalArgumentException.class)
    public void removeEdgeAlreadyExist() {
        Graph g = new Graph();
        g.insertNode("n1");
        g.insertNode("n2");
        g.removeEdge("n2","n1","e1");

    }

    /** Tests whether containsNode is correct when the node exist
     *
     */
    @Test
    public void ContainsNodeTest() {
        Graph g = new Graph();
        g.insertNode("n1");
        g.insertNode("n2");
        g.insertEdge("n2","n1","e1");
        assertTrue(g.containsNode("n2"));

    }
    /** Tests whether containsNode is correct when the node does not exist in
     * non-empty graph
     *
     */
    @Test
    public void NotContainsNodeTest() {
        Graph g = new Graph();
        g.insertNode("n1");
        g.insertNode("n2");
        g.insertEdge("n2","n1","e1");
        assertFalse(g.containsNode("n3"));

    }

    /** Tests whether containsNode is correct in empty graph
     *
     */
    @Test
    public void ContainsNodeForEmptyGraph() {
        Graph g = new Graph();
        assertFalse(g.containsNode("n2"));

    }

    /** Tests whether listPath() throws IllegalArgumentException when parent and child not exist
     *
     */
    @Test(expected=IllegalArgumentException.class)
    public void listPathNotChildNotParent() {
        Graph g = new Graph();
        List<String> result = new ArrayList<String>();
        result = g.listPath("n2","n1");

    }

    /**
     *  Tests whether listPath() throws IllegalArgumentException when parent not exist
     */
    @Test(expected=IllegalArgumentException.class)
    public void listPathNotParent() {
        Graph g = new Graph();
        g.insertNode("n1");
        g.insertNode("n2");
        List<String> result = new ArrayList<String>();
        result = g.listPath("n3","n1");

    }
    /**
     *  Tests whether listPath() throws IllegalArgumentException when child not exist
     */
    @Test(expected=IllegalArgumentException.class)
    public void listPathNotChild() {
        Graph g = new Graph();
        g.insertNode("n1");
        g.insertNode("n2");
        List<String> result = new ArrayList<String>();
        result = g.listPath("n1","n3");

    }
    /** Tests whether listPath() work correctly when there is no path between parent and child
     *
     */
    @Test
    public void listPathWithNoPath() {
        Graph g = new Graph();
        g.insertNode("n1");
        g.insertNode("n2");
        List<String> result = new ArrayList<String>();
        result = g.listPath("n1","n2");
        List<String> compareWith = new ArrayList<String>();
        assertEquals(compareWith,result);


    }

    /** Tests whether listPath() work correctly when there is ONE path between parent and child
     *
     */
    @Test
    public void listPathWithOnePath() {
        Graph g = new Graph();
        g.insertNode("n1");
        g.insertNode("n2");
        g.insertEdge("n1","n2","e1");
        List<String> result = new ArrayList<String>();
        result = g.listPath("n1","n2");
        List<String> compareWith = new ArrayList<String>();
        compareWith.add("n2(e1)");
        assertEquals(compareWith,result);


    }
    /** Tests whether listPath() work correctly when there is MULTIPLE paths between parent and child
     *
     */
    @Test
    public void listPathWithMultiplePath() {
        Graph g = new Graph();
        g.insertNode("n1");
        g.insertNode("n2");
        g.insertEdge("n1","n2","e12");
        g.insertNode("n3");
        g.insertEdge("n1","n3","n13");
        g.insertEdge("n3","n2","n32");
        List<String> result = new ArrayList<String>();
        result = g.listPath("n1","n2");
        List<String> compareWith = new ArrayList<String>();
        compareWith.add("n2(e12)");
        compareWith.add("n3(n13),n2(n32)");
        assertEquals(compareWith,result);


    }
    /**
     *  Tests whether listChildren() throws IllegalArgumentException when parent not exist
     */
    @Test(expected=IllegalArgumentException.class)
    public void listChildrenOfParentNotExist() {
        Graph g = new Graph();
        g.insertNode("n1");
        g.insertNode("n2");
        String result = g.listChildren("n3");

    }










}
