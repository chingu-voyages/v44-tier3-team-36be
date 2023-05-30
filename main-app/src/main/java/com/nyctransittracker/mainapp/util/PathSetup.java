package com.nyctransittracker.mainapp.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nyctransittracker.mainapp.model.*;
import com.nyctransittracker.mainapp.service.PathService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

//@Component
@RequiredArgsConstructor
@Slf4j
public class PathSetup implements CommandLineRunner {

    private final PathService pathService;
    private final static String filePath = "classpath:station_details.json";

    @Override
    public void run(String... args) throws Exception {
//        readJson();
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
}
