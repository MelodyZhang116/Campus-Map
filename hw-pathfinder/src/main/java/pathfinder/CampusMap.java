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

package pathfinder;

import graph.Graph;
import pathfinder.datastructures.Path;
import pathfinder.datastructures.PathFinder;
import pathfinder.datastructures.Point;
import pathfinder.parser.CampusBuilding;
import pathfinder.parser.CampusPath;
import pathfinder.parser.CampusPathsParser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class implements the ModelAPI interface. It contains all the information of UW campus
 * including buildings and paths from building to building.
 */
public class CampusMap implements ModelAPI {
    private static final boolean DEBUG = false;

    //a graph that store the info of building and places on the road, and edge to connect them
    private Graph<Point,Double> graph;

    // a list of building on campus
    private List<CampusBuilding> building;
    // AF(this) = all buildings are store in this.building in form of CampusBuilding
    //            all edges are stored in this.graph that its nodes are locations
    //            and edge starting from one location to another, labeled with distance.
    // RepInv:
    // building != null &&
    // each CampusBuilding in building!= null  &&
    // graph != null && no edges in the map has negative edge weights
    public CampusMap(){
        graph = new Graph<Point,Double>();
        List<CampusPath> paths =  CampusPathsParser.parseCampusPaths("campus_paths.csv");
        for(CampusPath path:paths) {
            Point start = new Point(path.getX1(),path.getY1());
            Point ending = new Point(path.getX2(),path.getY2());
            graph.insertNode(start);
            graph.insertNode(ending);
            graph.insertEdge(start,ending,path.getDistance());
            graph.insertEdge(ending,start,path.getDistance());

        }
        building = CampusPathsParser.parseCampusBuildings("campus_buildings.csv");
    }

    @Override
    public boolean shortNameExists(String shortName) {
        for(CampusBuilding build:building){
            if(build.getShortName().equals(shortName)){
                return true;
            }
        }
        return false;
    }

    @Override
    public String longNameForShort(String shortName) {
        for(CampusBuilding build:building){
            if(build.getShortName().equals(shortName)){
                return build.getLongName();
            }
        }
        throw new IllegalArgumentException();
    }

    @Override
    public Map<String, String> buildingNames() {
        Map<String,String> result = new HashMap<String,String>();
        for(CampusBuilding build:building){
            result.put(build.getShortName(),build.getLongName());
        }
        return result;
    }

    @Override
    public  Path<Graph.Node<Point>> findShortestPath(String startShortName, String endShortName) {
        if(startShortName.isEmpty()||endShortName.isEmpty() ||startShortName ==null || endShortName==null){
            throw new IllegalArgumentException();
        }
        if(!this.shortNameExists(startShortName) ||!this.shortNameExists(endShortName)){
            throw new IllegalArgumentException();
        }
        Point starting = null ;
        Point ending = null;
        for(CampusBuilding build:building){
            if(build.getShortName().equals(startShortName)){
                starting = new Point(build.getX(),build.getY());
            }
            if(build.getShortName().equals(endShortName)){
                ending = new Point(build.getX(),build.getY());
            }
        }
        PathFinder<Point,Number> path = new PathFinder<Point,Number>(graph);
        return path.findPath(starting,ending);

    }

}
