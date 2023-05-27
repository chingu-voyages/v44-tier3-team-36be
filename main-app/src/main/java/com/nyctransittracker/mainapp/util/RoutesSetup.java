package com.nyctransittracker.mainapp.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nyctransittracker.mainapp.model.Coordinate;
import com.nyctransittracker.mainapp.model.Path;
import com.nyctransittracker.mainapp.model.StationDetail;
import com.nyctransittracker.mainapp.service.PathService;
import lombok.RequiredArgsConstructor;
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
public class RoutesSetup implements CommandLineRunner {

    private final PathService pathService;
    private final static String filePath = "classpath:station_details.json";

    @Override
    public void run(String... args) throws Exception {
//        readJson();
    }

    public void readJson() {
        try {
            File file = ResourceUtils.getFile(filePath);
            ObjectMapper mapper = new ObjectMapper();
            Map<String, StationDetail> map = mapper.readValue(file,
                    new TypeReference<HashMap<String, StationDetail>>() {});
            List<Path> paths = new ArrayList<>();
            map.forEach((stopId, stationDetail) -> {
                stationDetail.getNorth().forEach((nextStopId, coordinateList) -> {
                    String pathName = stopId + "-" + nextStopId;
                    List<Coordinate> coordinates = new ArrayList<>(coordinateList.stream()
                            .map(coordinate -> new Coordinate(coordinate.get(0), coordinate.get(1)))
                            .toList()); // coordinates not including the current stop's coordinate or next stop's coordinate
                    coordinates.add(0, new Coordinate(stationDetail.getLongitude(), stationDetail.getLatitude()));
                    StationDetail nextStationDetail = map.get(nextStopId);
                    coordinates.add(new Coordinate(nextStationDetail.getLongitude(), nextStationDetail.getLatitude()));
                    paths.add(Path.builder().pathName(pathName).coordinates(coordinates).build());
                });
            });
            pathService.saveAllPaths(paths);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
