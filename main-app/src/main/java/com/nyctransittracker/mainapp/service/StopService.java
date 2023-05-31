package com.nyctransittracker.mainapp.service;

import com.nyctransittracker.mainapp.entity.Stop;
import com.nyctransittracker.mainapp.repository.StopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class StopService {

    private final StopRepository repository;
    public void createStation(Stop stop, String direction, String route) {
        var newStop = new Stop();
        newStop.setUniqueId(new Random().nextLong());
        newStop.setGTFSStopID(stop.getGTFSStopID());
        newStop.setName(stop.getName());
        newStop.setLatitude(stop.getLatitude());
        newStop.setLongitude(stop.getLongitude());
        newStop.setDirection(direction);
        newStop.setRoute(route);
        repository.save(newStop);
    }
}