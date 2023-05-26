package com.nyctransittracker.mainapp.model;

import java.util.List;

public record Path(String pathName, List<Coordinate> coordinates) {
}
