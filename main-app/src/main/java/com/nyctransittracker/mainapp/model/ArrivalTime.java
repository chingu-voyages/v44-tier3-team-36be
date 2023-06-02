package com.nyctransittracker.mainapp.model;

public record ArrivalTime(long time, boolean isDelayed, boolean isAssigned) {
}
