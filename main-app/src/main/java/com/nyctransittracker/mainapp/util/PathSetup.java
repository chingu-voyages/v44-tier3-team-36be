package com.nyctransittracker.mainapp.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nyctransittracker.mainapp.model.*;
import com.nyctransittracker.mainapp.service.PathService;
import com.nyctransittracker.mainapp.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class PathSetup implements CommandLineRunner {

    private final PathService pathService;
    private final RedisService redisService;
    private final GeometryFactory geometryFactory;
    private final static String filePath = "classpath:station_details.json";

    @Override
    public void run(String... args) throws Exception {
//        readJson();
//        calculateTrainPositions();
    }

    /*
    * Create set of paths between stations (list of coordinates) that can be used to create a path/line between
    * two stations, which can be used to generate a shape and approximate a train's position
    * */
    public void readJson() {
        /*
        * currently, can't capture service changes where train goes from station A to station C,
        * when normally this train goes station A -> station B -> station C
        * */
        try {
            File file = ResourceUtils.getFile(filePath);
            ObjectMapper mapper = new ObjectMapper();
            Map<String, StationDetail> map = mapper.readValue(file,
                    new TypeReference<HashMap<String, StationDetail>>() {});
            List<Path> paths = new ArrayList<>();
            map.forEach((stopId, stationDetail) -> {
//                code to see if there are stations where it splits into multiple next stations
//                if (stationDetail.getNorth().size() > 1) {
//                    log.info(stationDetail.getName());
//                }
                stationDetail.getNorth().forEach((nextStopId, coordinateList) -> {
                    String pathName = stopId + "-" + nextStopId;
                    List<Point> points = new ArrayList<>(coordinateList.stream()
                            .map(coordinate -> new Point(coordinate.get(0), coordinate.get(1)))
                            .toList()); // coordinates not including the current stop's coordinate or next stop's coordinate
                    points.add(0, new Point(stationDetail.getLongitude(), stationDetail.getLatitude()));
                    StationDetail nextStationDetail = map.get(nextStopId);
                    points.add(new Point(nextStationDetail.getLongitude(), nextStationDetail.getLatitude()));
                    paths.add(Path.builder().pathName(pathName).points(points).build());
                });
            });
            pathService.saveAllPaths(paths);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void calculateTrainPositions() {
        MtaResponse mtaResponse = redisService.getData();
        Map<String, Route> routes = mtaResponse.getRoutes();
        Map.Entry<String, Route> routeEntry = routes.entrySet().iterator().next();
        Route route = routeEntry.getValue();
        Map<String, List<Trip>> trips = route.getTrips();
        trips.forEach((direction, tripList) -> {
            tripList.forEach((trip) -> {
                String lastStopId = trip.getLastStopMade();
                if (lastStopId == null) {
                    return;
                }
                String nextStopId = findNextStopId(trip.getStops(), lastStopId);
                String pathName = (direction.equals("north")) ?
                        (lastStopId + "-" + nextStopId) : (nextStopId + "-" + lastStopId);
                // simply getting from pathService will not work for express routes
                Path path = pathService.getPath(pathName);
                //TODO: convert list of Coordinates into a line using JTS and approximate current train position
            });
        });
    }

    public String findNextStopId(Map<String, Integer> stops, String lastStopId) {
        // sorting stops by the timestamp then extracting out the keys (stop IDs)
        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(stops.entrySet());
        entryList.sort(Map.Entry.comparingByValue());
        List<String> stopIds = entryList.stream().map(Map.Entry::getKey).toList();
        return stopIds.get(stopIds.indexOf(lastStopId) + 1); // not too sure if I have to check bounds here
    }
}
