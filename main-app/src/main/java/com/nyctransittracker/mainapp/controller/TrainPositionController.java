package com.nyctransittracker.mainapp.controller;

import com.nyctransittracker.mainapp.model.TrainPositionResponse;
import com.nyctransittracker.mainapp.service.TrainPositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/trains")
@RequiredArgsConstructor
public class TrainPositionController {

    private final TrainPositionService trainPositionService;

    @GetMapping
    public TrainPositionResponse getTrainPositions() {
        return trainPositionService.getTrainPositions();
    }
}
