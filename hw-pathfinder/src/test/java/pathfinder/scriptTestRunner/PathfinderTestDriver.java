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
import pathfinder.datastructures.Path;
import pathfinder.datastructures.PathFinder;

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
public class PathfinderTestDriver {
    private final PrintWriter output;
    private final BufferedReader input;
    private final Map<String, Graph<String,Double>> graph = new HashMap<String,Graph<String,Double>>();

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
                case "FindPath":
                    FindPath(arguments);
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
        if (arguments.size() != 2) {
            throw new CommandException("Bad arguments to ListChildren: " + arguments);
        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        listChildren(graphName, parentName);
    }
    private void listChildren(String graphName, String parentName) {

        Graph<String,Double> g = graph.get(graphName);
        output.print("the children of "+parentName+" in "+graphName+" are:");
        List<Graph.Edge<String,Double>> children = g.listChildren(parentName);
        List<String[]> sorted = convert(children);
        for(String[] str:sorted){
            output.println(" "+str[0]+"("+String.format("%.3f",Double.valueOf(str[1]))+")");
        }


    }
    private List<String[]> convert(List<Graph.Edge<String,Double>> list){
        Set<String> sorted = new TreeSet<>();
        for(Graph.Edge<String,Double> ed:list){
            sorted.add(ed.getChild().getName()+" "+ed.getName().toString());
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

    private void createGraph(List<String> arguments) {
        if(arguments.size() != 1) {
            throw new CommandException("Bad arguments to CreateGraph: " + arguments);
        }

        String graphName = arguments.get(0);
        createGraph(graphName);
    }

    private void createGraph(String graphName) {

        graph.put(graphName, new Graph<String,Double>());
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

        Graph<String,Double> g = graph.get(graphName);
        g.insertNode(nodeName);
        output.println("added node "+nodeName+" to "+graphName);
    }



    private void FindPath(List<String> arguments){
        if(arguments.size() != 3){
            throw new CommandException("Bad arguments to FindPath: "+ arguments);
        }
        String graphName = arguments.get(0);
        String start = arguments.get(1);
        String destination = arguments.get(2);
        FindPath(graphName,start,destination);
    }

    private void FindPath(String graphName, String start, String destination){
        Graph<String,Double> g= graph.get(graphName);
        if(!g.containsNode(start) || !g.containsNode(destination)){
            if(!g.containsNode(start)){
                output.println("unknown: "+start);
            }
            if(!g.containsNode(destination)){
                output.println("unknown: "+destination);
            }
        }else {
            output.println("path from " + start + " to " + destination + ":");
            try {
                PathFinder<String,Double> pathFinding = new PathFinder<String,Double>(g);

                Path<Graph.Node<String>> paths = pathFinding.findPath(start, destination);
                Iterator<Path<Graph.Node<String>>.Segment> iterator = paths.iterator();
                double sumCost = 0.0;
                while(iterator.hasNext()){
                    Path<Graph.Node<String>>.Segment seg= iterator.next();
                    output.println(seg.getStart().getName()+ " to "+seg.getEnd().getName()+
                            " with weight "+String.format("%.3f",seg.getCost()));
                    sumCost+=seg.getCost();
                }
                output.println("total cost: "+String.format("%.3f",sumCost));

            } catch (RuntimeException r) {
                output.println("no path found");
            }
        }

    }
    private void addEdge(List<String> arguments) {
        if(arguments.size() != 4) {
            throw new CommandException("Bad arguments to AddEdge: " + arguments);
        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        String childName = arguments.get(2);
        String edgeLabel = arguments.get(3);

        addEdge(graphName, parentName, childName, Double.valueOf(edgeLabel));
    }

    private void addEdge(String graphName, String parentName, String childName,
                         Double edgeLabel) {

        Graph<String,Double> g = graph.get(graphName);
        g.insertEdge(parentName,childName,Double.valueOf(edgeLabel));
        output.println("added edge "+String.format("%.3f",edgeLabel)+" from "+parentName+" to "+childName+" in "+graphName);
    }


}
