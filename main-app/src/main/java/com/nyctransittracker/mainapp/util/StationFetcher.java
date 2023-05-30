package com.nyctransittracker.mainapp.util;

import com.nyctransittracker.mainapp.entity.Stop;
import com.nyctransittracker.mainapp.model.StationResponse;
import com.nyctransittracker.mainapp.service.StopService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;
import java.util.List;

@Component
@RequiredArgsConstructor
public class StationFetcher {
    private final static String url = "https://www.goodservice.io/api/stops/";
    private final StopService service;

    ObjectMapper mapper = new ObjectMapper();

    public void fetchStations() throws IOException {
        StationResponse stationResponse =
                mapper.readValue(new URL(url), StationResponse.class);
        List<Stop> stops = stationResponse.getStops();
        stops.forEach(stop ->
                stop.getRoutes().forEach((route, dList) ->
                            dList.forEach(d ->
                                    service.createStation(stop, d.toString(), route.toString())
                        )
                )
        );
    }
}