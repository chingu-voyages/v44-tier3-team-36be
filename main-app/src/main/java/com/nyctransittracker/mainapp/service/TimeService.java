package com.nyctransittracker.mainapp.service;

import com.nyctransittracker.mainapp.dto.ArrivalTimeResponse;
import com.nyctransittracker.mainapp.model.ArrivalTime;
import com.nyctransittracker.mainapp.dto.MtaResponse;
import com.nyctransittracker.mainapp.model.Route;
import com.nyctransittracker.mainapp.model.Trip;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class TimeService {

    private final RedisService redisService;

    public ArrivalTimeResponse getArrivalTimes() {
        return redisService.getArrivalTimes();
    }

    public void processTimeInfo() {
        log.info("Starting time info process: " + Instant.now().toString());
        MtaResponse mtaResponse = redisService.getMtaData();
        Map<String, Route> routes = mtaResponse.getRoutes();
        Map<String, Map<String, Map<String, List<ArrivalTime>>>> allTimes = new HashMap<>();
        routes.forEach((line, route) -> {
            if (!route.isScheduled() || route.getStatus().equals("No Service")) {
                return;
            }
            // create new map for each of the lines
            // used to store arrival time on per stop basis, since mta response comes on trip basis
            Map<String, Map<String, List<ArrivalTime>>> stopTimes = new HashMap<>();
            allTimes.put(line, stopTimes);
            Map<String, List<Trip>> trips = route.getTrips();
            trips.forEach((direction, tripList) -> {
                tripList.forEach((trip) -> {
                    String lastStopId = trip.getLastStopMade();
                    if (lastStopId == null) {
                        return;
                    }
                    Map<String, Long> stops = trip.getStops();
                    stops.forEach((stopId, time) -> {
                        long eta = time - Instant.now().getEpochSecond();
                        if (stopTimes.get(stopId) == null) {
                            // since there is no entry for this stop yet, create a new map with two empty maps (directions)
                            Map<String, List<ArrivalTime>> directionMap = new HashMap<>();
                            directionMap.put("north", new ArrayList<>());
                            directionMap.put("south", new ArrayList<>());
                            stopTimes.put(stopId, directionMap);
                        }
                        ArrivalTime arrivalTime = new ArrivalTime(eta, trip.isDelayed(), trip.isAssigned());
                        stopTimes.get(stopId).get(direction).add(arrivalTime);
                    });
                });
            });
        });
        redisService.saveArrivalTimes(new ArrivalTimeResponse(Instant.now().getEpochSecond(), allTimes));
        log.info("Done with time info process: " + Instant.now().toString());
    }

}
