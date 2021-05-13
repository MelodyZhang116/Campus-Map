package graph.junitTests;
import graph.Graph;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.Timeout;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


import static org.junit.Assert.assertEquals;

/**
 * This class contains a set of test cases that can be used to test the implementation of the Graph
 * class.
 */
public class GraphTest {
    @Rule public Timeout glocalTimeout = Timeout.seconds(10);//10 seconds max per method tested

    /**
     * test a more complicated graph and also test that if listNode and listChildren
     * return string that store nodes/edges in alphabetical order
     */
    @Test
    public void complicatedAlphabeticalTest(){
        Graph a = new Graph();
        a.insertNode("hijk");
        a.insertNode("abc");
        a.insertNode("def");
        a.insertEdge("abc","def","l1");
        a.insertEdge("hijk","abc","l2");
        a.insertEdge("def","hijk","l3");
        a.insertEdge("def","def","self");
        Graph b = new Graph();
        b.insertNode("xyz");
        b.insertNode("lmn");
        b.insertNode("opq");
        b.insertEdge("xyz","lmn","l1");
        b.insertEdge("opq","xyz","l2");
        b.insertEdge("lmn","xyz","l3");
        b.insertEdge("xyz","opq","haha");
        b.insertEdge("xyz","xyz","wow");
        b.insertEdge("xyz","lmn","zzz");
        assertEquals(" abc def hijk",a.listNodes());
        assertEquals(" def(self) hijk(l3)",a.listChildren("def"));
        assertEquals(" lmn opq xyz",b.listNodes());
        assertEquals(" lmn(l1) lmn(zzz) opq(haha) xyz(wow)",b.listChildren("xyz"));
        a.removeEdge("def","def","self");
        assertEquals(" abc def hijk",a.listNodes());
        assertEquals(" hijk(l3)",a.listChildren("def"));
        a.insertEdge("def","def","self");
        b.removeEdge("xyz","lmn","l1");
        assertEquals(" lmn(zzz) opq(haha) xyz(wow)",b.listChildren("xyz"));
        b.insertEdge("xyz","lmn","l1");
        a.removeNode("abc");
        assertEquals(" def hijk",a.listNodes());
        assertEquals("",a.listChildren("hijk"));
        b.removeNode("opq");
        assertEquals(" lmn xyz",b.listNodes());
        assertEquals(" lmn(l1) lmn(zzz) xyz(wow)",b.listChildren("xyz"));


    }
    /**
     * Tests that "add a node that already exist in graph" throws an IllegalArgumentException
     */
    @Test(expected=IllegalArgumentException.class)
    public void addNodeAlreadyExist() {
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
        assertEquals("remove node incorrectly because nodes are incorrect"," n2",g.listNodes());
        assertEquals("remove node incorrectly because edge is incorrect","",g.listChildren("n2"));
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

    /**
     *  Tests whether listChildren() throws IllegalArgumentException when parent not exist
     */
    @Test(expected=IllegalArgumentException.class)
    public void listChildrenOfParentNotExist() {
        Graph g = new Graph();
        g.insertNode("n1");
        g.insertNode("n2");
        List<String[]> result = g.listChildren("n3");

    }










}
