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
        Graph<String,String> a = new Graph<String,String>();
        a.insertNode("hijk");
        a.insertNode("abc");
        a.insertNode("def");
        a.insertEdge("abc","def","l1");
        a.insertEdge("hijk","abc","l2");
        a.insertEdge("def","hijk","l3");
        a.insertEdge("def","def","self");
        Graph<String,String> b = new Graph<String,String>();
        b.insertNode("xyz");
        b.insertNode("lmn");
        b.insertNode("opq");
        b.insertEdge("xyz","lmn","l1");
        b.insertEdge("opq","xyz","l2");
        b.insertEdge("lmn","xyz","l3");
        b.insertEdge("xyz","opq","haha");
        b.insertEdge("xyz","xyz","wow");
        b.insertEdge("xyz","lmn","zzz");
        String nodes = "";
        for(Graph.Node<String> node:a.listNodes()){
            nodes = nodes+" "+node.getName();
        }
        String children = "";
        for(String[] child: a.listChildren("def")){
            children = children + " "+ child[0]+"("+child[1]+")";
        }
        assertEquals(" abc def hijk",nodes);
        assertEquals(" def(self) hijk(l3)",children);
        String nodes1 = "";
        for(Graph.Node<String> node:b.listNodes()){
            nodes1 = nodes1+" "+node.getName();
        }
        String children1 = "";
        for(String[] child: b.listChildren("xyz")){
            children1 = children1 + " "+ child[0]+"("+child[1]+")";
        }
        assertEquals(" lmn opq xyz",nodes1);
        assertEquals(" lmn(l1) lmn(zzz) opq(haha) xyz(wow)",children1);
        a.removeEdge("def","def","self");
        String nodes2 = "";
        for(Graph.Node<String> node:a.listNodes()){
            nodes2 = nodes2+" "+node.getName();
        }
        String children2 = "";
        for(String[] child: a.listChildren("def")){
            children2 = children2 + " "+ child[0]+"("+child[1]+")";
        }
        assertEquals(" abc def hijk",nodes2);
        assertEquals(" hijk(l3)",children2);
        a.insertEdge("def","def","self");
        b.removeEdge("xyz","lmn","l1");

        String children3 = "";
        for(String[] child: b.listChildren("xyz")){
            children3 = children3 + " "+ child[0]+"("+child[1]+")";
        }
        assertEquals(" lmn(zzz) opq(haha) xyz(wow)",children3);
        b.insertEdge("xyz","lmn","l1");
        a.removeNode("abc");
        String nodes4 = "";
        for(Graph.Node<String> node:a.listNodes()){
            nodes4 = nodes4+" "+node.getName();
        }
        String children4 = "";
        for(String[] child: a.listChildren("hijk")){
            children4 = children4 + " "+ child[0]+"("+child[1]+")";
        }
        assertEquals(" def hijk",nodes4);
        assertEquals("",children4);
        b.removeNode("opq");
        String nodes5 = "";
        for(Graph.Node<String> node:b.listNodes()){
            nodes5 = nodes5+" "+node.getName();
        }
        String children5 = "";
        for(String[] child: b.listChildren("xyz")){
            children5 = children5 + " "+ child[0]+"("+child[1]+")";
        }
        assertEquals(" lmn xyz",nodes5);
        assertEquals(" lmn(l1) lmn(zzz) xyz(wow)",children5);


    }
    /**
     * Tests that "add a node that already exist in graph" throws an IllegalArgumentException
     */
    @Test(expected=IllegalArgumentException.class)
    public void addNodeAlreadyExist() {
        Graph<String,String> g = new Graph<String,String>();
        g.insertNode("n1");
        g.insertNode("n1");
    }

    /**
     * Tests that "add an edge whose parent does not exist" throws an IllegalArgumentException
     */
    @Test
    public void addEdgeWithParentNotExist() {
        Graph<String,String> g = new Graph<String,String>();
        g.insertNode("n1");
        g.insertEdge("n2","n1","e1");
    }

    /**
     * Tests that "add an edge whose child does not exist" throws an IllegalArgumentException
     */
    @Test
    public void addEdgeWithChildNotExist() {
        Graph<String,String> g = new Graph<String,String>();
        g.insertNode("n1");
        g.insertEdge("n1","n2","e1");
    }

    /**
     * Tests that "add an edge that already exist" throws an IllegalArgumentException
     */
    @Test
    public void addEdgeAlreadyExist() {
        Graph<String,String> g = new Graph<String,String>();
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
        Graph<String,String> g = new Graph<String,String>();
        g.insertNode("n1");
        g.insertNode("n2");
        g.insertEdge("n2","n1","e1");
        g.removeNode("n1");
        String nodes = "";
        for(Graph.Node<String> node:g.listNodes()){
            nodes = nodes+" "+node.getName();
        }
        String children = "";
        for(String[] child: g.listChildren("n2")){
            children = children + " "+ child[0]+"("+child[1]+")";
        }
        assertEquals("remove node incorrectly because nodes are incorrect"," n2",nodes);
        assertEquals("remove node incorrectly because edge is incorrect","",children);
    }
    /** Tests whether removing a node(not in the graph) throw IllegalArgumentException
     *
     */
    @Test(expected=IllegalArgumentException.class)
    public void removeNodeNotExist() {
        Graph<String,String> g = new Graph<String,String>();
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
        Graph<String,String> g = new Graph<String,String>();
        g.insertNode("n1");
        g.insertNode("n2");
        g.insertEdge("n2","n1","e1");
        g.removeEdge("n2","n1","e1");

        String children = "";
        for(String[] child: g.listChildren("n2")){
            children = children + " "+ child[0]+"("+child[1]+")";
        }
        assertEquals("remove edge incorrectly","",children);

    }

    /**
     * Tests that "remove an edge whose parent does not exist" throws an IllegalArgumentException
     */
    @Test(expected=IllegalArgumentException.class)
    public void removeEdgeWithParentNotExist() {
        Graph<String,String> g = new Graph<String,String>();
        g.insertNode("n1");
        g.removeEdge("n2","n1","e1");
    }

    /**
     * Tests that "remove an edge whose child does not exist" throws an IllegalArgumentException
     */
    @Test(expected=IllegalArgumentException.class)
    public void removeEdgeWithChildNotExist() {
        Graph<String,String> g = new Graph<String,String>();
        g.insertNode("n1");
        g.removeEdge("n1","n2","e1");
    }

    /**
     * Tests that "remove an edge that already exist" throws an IllegalArgumentException
     */
    @Test(expected=IllegalArgumentException.class)
    public void removeEdgeAlreadyExist() {
        Graph<String,String> g = new Graph<String,String>();
        g.insertNode("n1");
        g.insertNode("n2");
        g.removeEdge("n2","n1","e1");

    }

    /** Tests whether containsNode is correct when the node exist
     *
     */
    @Test
    public void ContainsNodeTest() {
        Graph<String,String> g = new Graph<String,String>();
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
        Graph<String,String> g = new Graph<String,String>();
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
        Graph<String,String> g = new Graph<String,String>();
        assertFalse(g.containsNode("n2"));

    }

    /**
     *  Tests whether listChildren() throws IllegalArgumentException when parent not exist
     */
    @Test(expected=IllegalArgumentException.class)
    public void listChildrenOfParentNotExist() {
        Graph<String,String> g = new Graph<String,String>();
        g.insertNode("n1");
        g.insertNode("n2");
        List<String[]> result = g.listChildren("n3");

    }










}
