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

package pathfinder.scriptTestRunner;

import graph.Graph;
import marvel.MarvelPaths;
import java.io.*;
import java.util.*;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * This class implements a test driver that uses a script file format
 * to test an implementation of Dijkstra's algorithm on a graph.
 */
public class PathfinderTestDriver<A> {
    private final PrintWriter output;
    private final BufferedReader input;
    private final Map<String, Graph<A,Double>> graph = new HashMap<String,Graph<A,Double>>();

    /**
     * @spec.requires r != null && w != null
     * @spec.effects Creates a new PathfinderTestDriver which reads command from
     * {@code r} and writes results to {@code w}
     **/
    public PathfinderTestDriver(Reader r, Writer w) {
        // TODO: Implement this, reading commands from `r` and writing output to `w`.
        // See GraphTestDriver as an example.
        input = new BufferedReader(r);
        output = new PrintWriter(w);
    }

    // Leave this method public
    public void runTests() throws IOException {
        // TODO: Implement this.
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

    private void listChildren(List<String> arguments) {
        if(arguments.size() != 2) {
            throw new CommandException("Bad arguments to ListChildren: " + arguments);
        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        listChildren(graphName, parentName);
    }
    public <A,Double> List<String[]> convert(List<Graph.Edge<A,Double>> list){
        Set<String> sorted = new TreeSet<>();
        for(Graph.Edge<A,Double> ed:list){
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

        Graph<A,Double> g = graph.get(graphName);
        output.print("the children of "+parentName+" in "+graphName+" are:");
        List<Graph.Edge<A,Double>> children1 = g.listChildrenWithString(parentName);
        List<String[]> children = convert(children1);

        if(!children.isEmpty()){
            for(String[] childEdge:children){
                output.print(" "+childEdge[0]+"("+childEdge[1]+")");
            }
        }
        output.println();
    }


}
