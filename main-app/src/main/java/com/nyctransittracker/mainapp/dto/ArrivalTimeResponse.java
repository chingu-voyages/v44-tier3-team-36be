package com.nyctransittracker.mainapp.dto;

import com.nyctransittracker.mainapp.model.ArrivalTime;

import java.util.Map;
import java.util.List;

// arrivalTime: line to stopId to direction to times
public record ArrivalTimeResponse(long timestamp,
                                  Map<String, Map<String, Map<String, List<ArrivalTime>>>> arrivalTimes) {
}
