package com.nyctransittracker.mainapp.service;

import com.nyctransittracker.mainapp.model.MtaResponse;
import com.nyctransittracker.mainapp.model.Route;
import com.nyctransittracker.mainapp.model.Trip;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@EnableScheduling
@RequiredArgsConstructor
public class TimeService {

    private final RedisService redisService;

    @Scheduled(fixedRate = 30000, initialDelay = 5000)
    public void processTimeInfo() {
        MtaResponse mtaResponse = redisService.getMtaData();
        Map<String, Route> routes = mtaResponse.getRoutes();
        routes.forEach((line, route) -> {
            if (!route.isScheduled() || route.getStatus().equals("No Service")) {
                return;
            }
            Map<String, List<Trip>> trips = route.getTrips();
            trips.forEach((direction, tripList) -> {
                tripList.forEach((trip) -> {
                    String lastStopId = trip.getLastStopMade();
                    if (lastStopId == null) {
                        return;
                    }

                });
            });
        });
    }


}
