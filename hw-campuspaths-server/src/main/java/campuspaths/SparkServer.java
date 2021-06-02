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

        Spark.get("/shortestPath", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                String startShortName = request.queryParams("startShortName");
                String endShortName = request.queryParams("endShortName");
                if(startShortName == null || endShortName == null) {
                    // You can also have a message in "halt" that is displayed in the page.
                    Spark.halt(400, "must have start and end");
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

        // TODO: Create all the Spark Java routes you need here.
    }

}
