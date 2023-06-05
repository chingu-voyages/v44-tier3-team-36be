package com.nyctransittracker.mainapp.service;

import com.nyctransittracker.mainapp.dto.SubscriptionRequest;
import com.nyctransittracker.mainapp.model.Stop;
import com.nyctransittracker.mainapp.model.User;
import com.nyctransittracker.mainapp.repository.StopRepository;
import com.nyctransittracker.mainapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final StopRepository stopRepository;
    private final UserRepository userRepository;

    public void subscribe(SubscriptionRequest request) {
        Optional<Stop> stop = stopRepository.findById(request.getStopId());
        Optional<User> user = userRepository.findByEmail(request.getUserEmail());
        if (user.isPresent() && stop.isPresent()){
            Stop existingStop = stop.get();
            User existingUser = user.get();
            if (!existingStop.getSubscribedUsers().contains(existingUser)) {
                existingStop.getSubscribedUsers().add(existingUser);
                stopRepository.save(existingStop);
            }
        }
    }

    public void unsubscribe(SubscriptionRequest request) {
        Optional<Stop> stop = stopRepository.findById(request.getStopId());
        Optional<User> user = userRepository.findByEmail(request.getUserEmail());
        if (user.isPresent() && stop.isPresent()){
            Stop existingStop = stop.get();
            User existingUser = user.get();
            if (!existingStop.getSubscribedUsers().contains(existingUser)) {
                existingStop.getSubscribedUsers().remove(existingUser);
                stopRepository.save(existingStop);
            }
        }
    }
}