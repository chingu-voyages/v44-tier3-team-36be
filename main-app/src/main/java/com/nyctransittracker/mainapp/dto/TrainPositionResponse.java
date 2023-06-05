package com.nyctransittracker.mainapp.dto;

import com.nyctransittracker.mainapp.model.CoordinateBearing;

import java.util.List;
import java.util.Map;


// positions: line to direction to positions
public record TrainPositionResponse(Map<String, Map<String, List<CoordinateBearing>>> positions) {
}
