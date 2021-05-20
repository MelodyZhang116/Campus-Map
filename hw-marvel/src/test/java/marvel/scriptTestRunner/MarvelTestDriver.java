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

package marvel.scriptTestRunner;

import graph.Graph;
import marvel.MarvelPaths;

import java.io.*;
import java.util.*;
import graph.Graph.Node;
import graph.Graph.Edge;
import graph.Graph;

/**
 * This class implements a testing driver which reads test scripts from
 * files for testing Graph, the Marvel parser, and your BFS algorithm.
 */
public class MarvelTestDriver {
    private final PrintWriter output;
    private final BufferedReader input;
    private final Map<String,MarvelPaths> marvelGraphs = new HashMap<String,MarvelPaths>();

    // Leave this constructor public
    public MarvelTestDriver(Reader r, Writer w) {
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
                case "FindPath":
                    FindPath(arguments);
                    break;
                case "LoadGraph":
                    LoadGraph(arguments);
                    break;
                case "CreateGraph":
                    createGraph(arguments);
                    break;
                case "AddNode":
                    addNode(arguments);
                    break;
                case "AddEdge":
                    addEdge(arguments);
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
    private void LoadGraph(List<String> arguments) throws IOException {
        if(arguments.size() != 2) {
            throw new CommandException("Bad arguments to LoadGraph: " + arguments);
        }
        String graphName = arguments.get(0);
        String fileName = arguments.get(1);
        LoadGraph(graphName,fileName);
    }
    private void LoadGraph(String graphName,String fileName) throws IOException {
        marvelGraphs.put(graphName,new MarvelPaths(fileName));
        output.println("loaded graph "+graphName);
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
        MarvelPaths marvel = marvelGraphs.get(graphName);
        if(!marvel.containsNode(start) || !marvel.containsNode(destination)){
            if(!marvel.containsNode(start)){
                output.println("unknown: "+start);
            }
            if(!marvel.containsNode(destination)){
                output.println("unknown: "+destination);
            }
        }else {
            output.println("path from " + start + " to " + destination + ":");
            try {
                List<Graph.Edge<String,String>> paths = marvel.findPaths(start, destination);

                for (Edge<String,String> ed : paths) {
                    output.println(ed.getParent().getName() + " to " + ed.getChild().getName() + " via " + ed.getName());

                }
            } catch (RuntimeException r) {
                output.println("no path found");
            }
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

        marvelGraphs.put(graphName, new MarvelPaths(new Graph<String,String>()));
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

        MarvelPaths g = marvelGraphs.get(graphName);
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

        MarvelPaths g = marvelGraphs.get(graphName);
        g.insertEdge(parentName,childName,edgeLabel);
        output.println("added edge "+edgeLabel+" from "+parentName+" to "+childName+" in "+graphName);
    }



    private void listChildren(List<String> arguments) {
        if(arguments.size() != 2) {
            throw new CommandException("Bad arguments to ListChildren: " + arguments);
        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        listChildren(graphName, parentName);
    }

    private void listChildren(String graphName, String parentName) {

        MarvelPaths g = marvelGraphs.get(graphName);
        output.print("the children of "+parentName+" in "+graphName+" are:");
        List<String[]> children = g.listChildren(parentName);
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
