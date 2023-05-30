package com.nyctransittracker.mainapp.service;

import com.nyctransittracker.mainapp.model.*;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.linearref.LengthIndexedLine;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Service
@RequiredArgsConstructor
public class TestService {

    private final PathService pathService;
    private final RedisService redisService;
    private final GeometryFactory geometryFactory;

    public Map<String, List<Point>> getTrainPositions() {
        MtaResponse mtaResponse = redisService.getData();
        Map<String, Route> routes = mtaResponse.getRoutes();
        Map<String, List<Point>> allPositions = new HashMap<>();
        routes.forEach((line, route) -> {
            if (!route.isScheduled() || route.getStatus().equals("No Service")) {
                return;
            }
            List<Point> trainPositions = new ArrayList<>();
            Map<String, List<Trip>> trips = route.getTrips();
            trips.forEach((direction, tripList) -> {
                tripList.forEach((trip) -> {
                    String lastStopId = trip.getLastStopMade();
                    if (lastStopId == null) {
                        return;
                    }
                    Map<String, Long> stops = trip.getStops();
                    String nextStopId = findNextStopId(stops, lastStopId);
                    String pathName = (direction.equals("north")) ?
                            (lastStopId + "-" + nextStopId) : (nextStopId + "-" + lastStopId);
                    Optional<Path> path = pathService.getPath(pathName);
                    if (path.isEmpty()) {
                        return;
                    }
                    Point point = calculateTrainPosition(stops, path.get(), lastStopId, nextStopId);
                    trainPositions.add(point);
                });
            });
            allPositions.put(line, trainPositions);
        });
        return allPositions;
    }

    private String findNextStopId(Map<String, Long> stops, String lastStopId) {
//        sorting stops by the timestamp then extracting out the keys (stop IDs)
        List<Map.Entry<String, Long>> entryList = new ArrayList<>(stops.entrySet());
        entryList.sort(Map.Entry.comparingByValue());
        List<String> stopIds = entryList.stream().map(Map.Entry::getKey).toList();
        return stopIds.get(stopIds.indexOf(lastStopId) + 1); // not too sure if I have to check bounds here
    }

    private Point calculateTrainPosition(Map<String, Long> stops, Path path, String lastStopId, String nextStopId) {
        List<Point> points = path.getPoints();
        Coordinate[] coordinates = points.stream()
                .map(point -> new Coordinate(point.longitude(), point.latitude()))
                .toArray(Coordinate[]::new);
        LineString lineString = geometryFactory.createLineString(coordinates);
        LengthIndexedLine indexedLine = new LengthIndexedLine(lineString);
        long lastTimestamp = stops.get(lastStopId);
        long nextTimeStamp = stops.get(nextStopId);
        long nowTimestamp = Instant.now().getEpochSecond();
        double progress = (double) (nowTimestamp - lastTimestamp) / (nextTimeStamp - lastTimestamp);
        Coordinate coordinate = indexedLine.extractPoint(progress * lineString.getLength());
        return new Point(coordinate.getX(), coordinate.getY());
    }

}
