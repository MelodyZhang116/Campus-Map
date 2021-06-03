package campuspaths;/*
 * Copyright (C) 2021 Kevin Zatloukal.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Spring Quarter 2021 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */
import com.google.gson.Gson;
import graph.Graph;
import pathfinder.CampusMap;
import pathfinder.datastructures.Path;
import pathfinder.datastructures.Point;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import campuspaths.*;
import campuspaths.utils.CORSFilter;

public class SparkServer {

    public static void main(String[] args) {
        CORSFilter corsFilter = new CORSFilter();
        corsFilter.apply();
        CampusMap map = new CampusMap();
        Spark.get("/path", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                String startShortName = request.queryParams("start");
                String endShortName = request.queryParams("end");
                if(startShortName == null || endShortName == null) {
                    Spark.halt(400, "must have start and end short names");
                }
                if(!map.shortNameExists(startShortName) || !map.shortNameExists(endShortName)){
                    Spark.halt(400, "start and end must be valid short name on campus");
                }
                Path<Graph.Node<Point>> path = map.findShortestPath(startShortName,endShortName);
                ArrayList<ArrayList<Number>> pathString = new ArrayList<>();
                ArrayList<Number> coordinate1 = new ArrayList<Number>();
                coordinate1.add(path.getStart().name.getX());
                coordinate1.add(path.getStart().name.getY());
                pathString.add(coordinate1);
                List<Path<Graph.Node<Point>>.Segment> segment = path.getSegment();
                for(Path<Graph.Node<Point>>.Segment seg:segment){
                    ArrayList<Number> coordinate2 = new ArrayList<Number>();
                    coordinate2.add(seg.getEnd().getName().getX());
                    coordinate2.add(seg.getEnd().getName().getY());
                    pathString.add(coordinate2);
                }
                Gson gson = new Gson();
                return gson.toJson(pathString);
            }
        });
//        let result:[number,number][] = [];
//        let spaceWidth: number = this.props.width / (this.props.size+1);
//        let spaceHeight: number = this.props.height / (this.props.size+1);
//        for(let i = 1; i <= this.props.size; i++){
//            for(let j = 1; j <= this.props.size;j++){
//                result.push([spaceHeight*i,spaceWidth*j]);
//
//            }
//        }
        Spark.get("/buildings", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                Gson gson = new Gson();
                return gson.toJson(map.buildingNames());
            }
        });


    }

}
