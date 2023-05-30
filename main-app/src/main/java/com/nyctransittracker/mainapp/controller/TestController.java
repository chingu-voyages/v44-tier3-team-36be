package com.nyctransittracker.mainapp.controller;

import com.nyctransittracker.mainapp.dto.TrainPositionResponse;
import com.nyctransittracker.mainapp.model.Point;
import com.nyctransittracker.mainapp.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/trains")
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;

    @GetMapping
    public TrainPositionResponse getTrainPositions() {
        Map<String, List<Point>> trainPositions = testService.getTrainPositions();
        return new TrainPositionResponse(trainPositions);
    }

}
