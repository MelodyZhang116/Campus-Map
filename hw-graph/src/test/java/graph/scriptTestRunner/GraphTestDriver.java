/*
 * Copyright (C) 2021 Kevin Zatloukal.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Spring Quarter 2021 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

package graph.scriptTestRunner;

import graph.Graph;

import java.io.*;
import java.util.*;

/**
 * This class implements a testing driver which reads test scripts
 * from files for testing Graph.
 **/
public class GraphTestDriver {

    // ***************************
    // ***  JUnit Test Driver  ***
    // ***************************

    /**
     * String -> Graph: maps the names of graphs to the actual graph
     **/
    private final Map<String, Graph<String,String>> graphs = new HashMap<String,Graph<String,String>>();
    private final PrintWriter output;
    private final BufferedReader input;

    /**
     * @spec.requires r != null && w != null
     * @spec.effects Creates a new GraphTestDriver which reads command from
     * {@code r} and writes results to {@code w}
     **/
    // Leave this constructor public
    public GraphTestDriver(Reader r, Writer w) {
        input = new BufferedReader(r);
        output = new PrintWriter(w);
    }

    /**
     * @throws IOException if the input or output sources encounter an IOException
     * @spec.effects Executes the commands read from the input and writes results to the output
     **/
    // Leave this method public
    public void runTests() throws IOException {
        String inputLine;
        while((inputLine = input.readLine()) != null) {
            if((inputLine.trim().length() == 0) ||
               (inputLine.charAt(0) == '#')) {
                // echo blank and comment lines
                output.println(inputLine);
            } else {
                // separate the input line on white space
                StringTokenizer st = new StringTokenizer(inputLine);
                if(st.hasMoreTokens()) {
                    String command = st.nextToken();

                    List<String> arguments = new ArrayList<>();
                    while(st.hasMoreTokens()) {
                        arguments.add(st.nextToken());
                    }

                    executeCommand(command, arguments);
                }
            }
            output.flush();
        }
    }

    private void executeCommand(String command, List<String> arguments) {
        try {
            switch(command) {
                case "CreateGraph":
                    createGraph(arguments);
                    break;
                case "AddNode":
                    addNode(arguments);
                    break;
                case "AddEdge":
                    addEdge(arguments);
                    break;
                case "ListNodes":
                    listNodes(arguments);
                    break;
                case "ListChildren":
                    listChildren(arguments);
                    break;
                default:
                    output.println("Unrecognized command: " + command);
                    break;
            }
        } catch(Exception e) {
            String formattedCommand = command;
            formattedCommand += arguments.stream().reduce("", (a, b) -> a + " " + b);
            output.println("Exception while running command: " + formattedCommand);
            e.printStackTrace(output);
        }
    }

    private void createGraph(List<String> arguments) {
        if(arguments.size() != 1) {
            throw new CommandException("Bad arguments to CreateGraph: " + arguments);
        }

        String graphName = arguments.get(0);
        createGraph(graphName);
    }

    private void createGraph(String graphName) {

        graphs.put(graphName, new Graph<String,String>());
        output.println("created graph "+graphName);
    }

    private void addNode(List<String> arguments) {
        if(arguments.size() != 2) {
            throw new CommandException("Bad arguments to AddNode: " + arguments);
        }

        String graphName = arguments.get(0);
        String nodeName = arguments.get(1);

        addNode(graphName, nodeName);
    }

    private void addNode(String graphName, String nodeName) {

        Graph<String,String> g = graphs.get(graphName);
        g.insertNode(nodeName);
        output.println("added node "+nodeName+" to "+graphName);
    }

    private void addEdge(List<String> arguments) {
        if(arguments.size() != 4) {
            throw new CommandException("Bad arguments to AddEdge: " + arguments);
        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        String childName = arguments.get(2);
        String edgeLabel = arguments.get(3);

        addEdge(graphName, parentName, childName, edgeLabel);
    }

    private void addEdge(String graphName, String parentName, String childName,
                         String edgeLabel) {

        Graph<String,String> g = graphs.get(graphName);
        g.insertEdge(parentName,childName,edgeLabel);
        output.println("added edge "+edgeLabel+" from "+parentName+" to "+childName+" in "+graphName);
    }

    private void listNodes(List<String> arguments) {
        if(arguments.size() != 1) {
            throw new CommandException("Bad arguments to ListNodes: " + arguments);
        }

        String graphName = arguments.get(0);
        listNodes(graphName);
    }

    private void listNodes(String graphName) {

        Graph<String,String> g= graphs.get(graphName);
        output.print(graphName + " contains:");
        List<Graph.Node<String>> nodes = g.listNodes();
        for(Graph.Node<String> node:nodes){
            output.print(" "+node.getName());
        }
        output.println();

    }

    private void listChildren(List<String> arguments) {
        if(arguments.size() != 2) {
            throw new CommandException("Bad arguments to ListChildren: " + arguments);
        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        listChildren(graphName, parentName);
    }
    public <A,B> List<String[]> convert(List<Graph.Edge<A,B>> list){
        Set<String> sorted = new TreeSet<>();
        for(Graph.Edge<A,B> ed:list){
            sorted.add(ed.getChild().getName().toString()+" "+ed.getName().toString());
        }
        List<String[]> result = new ArrayList<String[]>();
        Iterator<String> itr = sorted.iterator();
        while(itr.hasNext()){ // add the names into string result
            String nextChild = itr.next();
            int index = nextChild.indexOf(" ");
            String[] childToAdd = new String[]{nextChild.substring(0,index),nextChild.substring(index+1)};
            result.add(childToAdd);

        }
        return result;


    }
    private void listChildren(String graphName, String parentName) {

        Graph<String,String> g = graphs.get(graphName);
        output.print("the children of "+parentName+" in "+graphName+" are:");
        List<Graph.Edge<String,String>> children1 = g.listChildren(parentName);
        List<String[]> children = convert(children1);

        if(!children.isEmpty()){
            for(String[] childEdge:children){
                output.print(" "+childEdge[0]+"("+childEdge[1]+")");
            }
        }
        output.println();
    }

    /**
     * This exception results when the input file cannot be parsed properly
     **/
    static class CommandException extends RuntimeException {

        public CommandException() {
            super();
        }

        public CommandException(String s) {
            super(s);
        }

        public static final long serialVersionUID = 3495;
    }
}
