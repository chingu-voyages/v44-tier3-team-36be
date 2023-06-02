package com.nyctransittracker.mainapp.controller;

import com.nyctransittracker.mainapp.dto.ArrivalTimeResponse;
import com.nyctransittracker.mainapp.service.TimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/times")
@RequiredArgsConstructor
public class ArrivalTimeController {

    private final TimeService timeService;

    @GetMapping
    public ArrivalTimeResponse getArrivalTimes() {
        return timeService.getArrivalTimes();
    }
}
