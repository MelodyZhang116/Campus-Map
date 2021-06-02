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

import java.util.ArrayList;
import java.util.List;
import campuspaths.*;
import campuspaths.utils.CORSFilter;

public class SparkServer {

    public static void main(String[] args) {
        CORSFilter corsFilter = new CORSFilter();
        corsFilter.apply();
        CampusMap map = new CampusMap();
        //"/range?start=NUMBER&end=NUMBER"
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

                Gson gson = new Gson();
                String jsonResponse = gson.toJson(path);
                return jsonResponse;
            }
        });
        Spark.get("/buildings", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                Gson gson = new Gson();
                String jsonResponse = gson.toJson(map.buildingNames());
                return jsonResponse;
            }
        });


    }

}
