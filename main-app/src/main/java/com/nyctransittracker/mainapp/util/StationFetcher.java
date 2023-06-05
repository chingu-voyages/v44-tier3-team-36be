package com.nyctransittracker.mainapp.util;

import com.nyctransittracker.mainapp.model.Stop;
import com.nyctransittracker.mainapp.model.StationResponse;
import com.nyctransittracker.mainapp.service.StopService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;
import java.util.List;

@Component
@RequiredArgsConstructor
public class StationFetcher implements CommandLineRunner {
    private final static String url = "https://www.goodservice.io/api/stops/";
    private final StopService service;

    private static ObjectMapper mapper = new ObjectMapper();

    @Override
    public void run(String... args) throws IOException {
        fetchStations();
    }

    public void fetchStations() throws IOException {
        StationResponse stationResponse =
                mapper.readValue(new URL(url), StationResponse.class);
        List<Stop> stops = stationResponse.getStops();
        stops.forEach(stop ->
                stop.getRoutes().forEach((route, dList) ->
                            dList.forEach(d ->
                                    service.createStop(stop, d.toString(), route.toString())
                        )
                )
        );
    }
}