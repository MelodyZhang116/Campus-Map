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

public class CampusMap implements ModelAPI {
    private Graph<Point,Double> graph;
    private List<CampusBuilding> building;
    public CampusMap(){
        graph = new Graph<Point,Double>();
        List<CampusPath> paths =  CampusPathsParser.parseCampusPaths("campus_paths.csv");
        for(CampusPath path:paths) {
            Point start = new Point(path.getX1(),path.getY1());
            Point ending = new Point(path.getX2(),path.getY2());
            graph.insertNode(start);
            graph.insertNode(ending);
            graph.insertEdge(start,ending,path.getDistance());
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
        // TODO: Implement this method exactly as it is specified in ModelAPI
        if(startShortName.isEmpty()||endShortName.isEmpty()){
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
