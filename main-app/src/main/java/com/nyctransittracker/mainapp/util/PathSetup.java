package com.nyctransittracker.mainapp.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nyctransittracker.mainapp.model.*;
import com.nyctransittracker.mainapp.service.PathService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class PathSetup implements CommandLineRunner {

    private final PathService pathService;
    private final static String fileName = "station_details.json";

    @Override
    public void run(String... args) throws Exception {
        readJson();
    }

    /*
    * Create set of paths between stations (list of coordinates) that can be used to create a path/line between
    * two stations, which can be used to generate a shape and approximate a train's position
    * */
    public void readJson() {
        try {
            if (!pathService.isEmpty()) {
                ClassPathResource resource = new ClassPathResource(fileName);
                ObjectMapper mapper = new ObjectMapper();
                Map<String, StationDetail> map = mapper.readValue(resource.getInputStream(),
                        new TypeReference<HashMap<String, StationDetail>>() {});
                List<Path> paths = new ArrayList<>();
                map.forEach((stopId, stationDetail) -> {
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
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
