package com.nyctransittracker.mainapp.model;

import com.nyctransittracker.mainapp.model.Point;

import java.util.List;
import java.util.Map;

public record TrainPositionResponse(Map<String, List<Point>> positions) {
}
