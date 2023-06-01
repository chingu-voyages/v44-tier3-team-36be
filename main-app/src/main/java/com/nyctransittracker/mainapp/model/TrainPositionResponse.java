package com.nyctransittracker.mainapp.model;

import java.util.List;
import java.util.Map;

public record TrainPositionResponse(Map<String, List<CoordinateBearing>> positions) {
}
