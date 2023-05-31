package com.nyctransittracker.mainapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nyctransittracker.mainapp.entity.Stop;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StationResponse {
    private long timestamp;
    @JsonProperty("stops")
    private List<Stop> stops;
}