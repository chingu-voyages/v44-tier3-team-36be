package com.nyctransittracker.mainapp.service;

import com.nyctransittracker.mainapp.model.Stop;
import com.nyctransittracker.mainapp.repository.StopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StopService {

    private final StopRepository repository;
    public void createStop(Stop stop, String direction, String route) {
        if (!repository.findById(route).isPresent()) {
            var newStop = new Stop();
            newStop.setUniqueId(stop.getGTFSStopID() + route + direction);
            newStop.setGTFSStopID(stop.getGTFSStopID());
            newStop.setName(stop.getName());
            newStop.setLatitude(stop.getLatitude());
            newStop.setLongitude(stop.getLongitude());
            newStop.setDirection(direction);
            newStop.setRoute(route);
            repository.save(newStop);
        }
    }
}