package com.nyctransittracker.mainapp.dto;

import com.nyctransittracker.mainapp.model.CoordinateBearing;

import java.util.List;
import java.util.Map;

public record TrainPositionResponse(Map<String, List<CoordinateBearing>> positions) {
}
