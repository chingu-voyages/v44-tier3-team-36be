package com.nyctransittracker.mainapp.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nyctransittracker.mainapp.dto.MtaResponse;
import com.nyctransittracker.mainapp.dto.TrainPositionResponse;
import com.nyctransittracker.mainapp.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.algorithm.Angle;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.linearref.LengthIndexedLine;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.util.*;

import static com.nyctransittracker.mainapp.util.StopIdUtil.findNextStopId;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrainPositionService {

    private final RedisService redisService;
    private final PathService pathService;
    private final GeometryFactory geometryFactory;

    public TrainPositionResponse getTrainPositions() {
        return redisService.getTrainPositions();
    }

    public void processTrainPositions() {
        log.info("Starting train position calculation: " + Instant.now().toString());
        MtaResponse mtaResponse = redisService.getMtaData();
        Map<String, Route> routes = mtaResponse.getRoutes();
        Map<String, Map<String, List<CoordinateBearing>>> allPositions = new HashMap<>();
        routes.forEach((line, route) -> {
            if (!route.isScheduled() || route.getStatus().equals("No Service")) {
                return;
            }
            Map<String, List<CoordinateBearing>> directionMap = new HashMap<>();
            Map<String, List<Trip>> trips = route.getTrips();
            trips.forEach((direction, tripList) -> {
                List<CoordinateBearing> trainPositions = new ArrayList<>();
                tripList.forEach((trip) -> {
                    String lastStopId = trip.getLastStopMade();
                    if (lastStopId == null) {
                        return;
                    }
                    Map<String, Long> stops = trip.getStops();
                    String nextStopId = findNextStopId(stops, lastStopId);
                    String pathName = (direction.equals("north")) ?
                            (lastStopId + "-" + nextStopId) : (nextStopId + "-" + lastStopId);
                    Optional<Path> path = getPath(pathName);
                    if (path.isEmpty()) {
                        return;
                    }
                    CoordinateBearing coordinateBearing =
                            calculateTrainPosition(stops, path.get(), lastStopId, nextStopId);
                    trainPositions.add(coordinateBearing);
                });
                directionMap.put(direction, trainPositions);
            });
            allPositions.put(line, directionMap);
        });
        redisService.saveTrainPositions(new TrainPositionResponse(allPositions));
        log.info("Done with train position calculation: " + Instant.now().toString());
    }

    private CoordinateBearing calculateTrainPosition(Map<String, Long> stops, Path path,
                                                     String lastStopId, String nextStopId) {
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
        double length = lineString.getLength();
        Coordinate coordinate = indexedLine.extractPoint(progress * length);
        Coordinate heading = indexedLine.extractPoint((progress + 0.01) * length);
        // Returns the angle of the vector from p0 to p1, relative to the positive X-axis.
        // The angle is normalized to be in the range [ -Pi, Pi ].
        double bearing = Angle.angle(coordinate, heading);
        return new CoordinateBearing(coordinate.getX(), coordinate.getY(), bearing);
    }

    private Optional<Path> getPath(String pathName) {
        Optional<Path> pathOptional = pathService.getPath(pathName);
        if (pathOptional.isPresent()) {
            return pathOptional;
        }
        Map<String, StationDetail> stationDetailMap = getStationDetailMap();
        String[] nameSplit = pathName.split("-");
        List<Point> points = getPathRecursive(nameSplit[0], nameSplit[1], stationDetailMap, 0);
        if (points.isEmpty()) {
            return Optional.empty();
        }
        Path path = Path.builder().pathName(pathName).points(points).build();
        // save the new path so next time you can grab from DB rather than finding it again;
        return Optional.of(pathService.savePath(path));
    }

    /**
     * @param start - stopId of the starting stop
     * @param end - stopId of the destination stop
     * @param stationDetailMap - map representation of station details json
     * @param step - number steps into the recursive call for stops that split into multiple stops
     * @return list of Points from start to end, or empty list if path does not exist
     * <p>
     * finds path from Station x to Station x+n, going from x->x+n, then x+1->x+n, until a path exists,
     * most likely till x+n-1->x+n
     */
    private List<Point> getPathRecursive(String start, String end,
                                         Map<String, StationDetail> stationDetailMap, int step) {
        if (step > 10) {
            return new ArrayList<>();
        }
        Optional<Path> pathOptional = pathService.getPath(start + "-" + end);
        if (pathOptional.isPresent()) {
            return pathOptional.get().getPoints();
        }
        StationDetail curr = stationDetailMap.get(start);
        if (curr.getNorth().isEmpty()) {
            return new ArrayList<>();
        }
        for (var entry : curr.getNorth().entrySet()) {
            String next = entry.getKey();
            List<Point> nextPath = getPathRecursive(next, end, stationDetailMap, step + 1);
            if (nextPath.isEmpty()) {
                continue;
            }
            // Optional should not be empty since this is based on the json.
            Path pathToNext = pathService.getPath(start + "-" + next).get();
            List<Point> pointsToNext = pathToNext.getPoints();
            // excluding the first coordinate since each Path has the coordinates for the start and end stops
            pointsToNext.addAll(nextPath.subList(1, nextPath.size()));
            return pointsToNext;
        }
        // if the for loop terminates, then none of the next stops led to end station, return empty list.
        return new ArrayList<>();
    }


    /*
    * creates a map of stopId to StationDetail, containing information,
    * such as what stops are north/south of this stop
    * use this to recursively find paths
    * */
    private Map<String, StationDetail> getStationDetailMap() {
        final String fileName = "station_details.json";
        try {
            ClassPathResource resource = new ClassPathResource(fileName);
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(resource.getInputStream(),
                    new TypeReference<>() {});
        } catch (IOException e) {
            return new HashMap<>();
        }
    }
}
