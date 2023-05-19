package com.nyctransittracker.mainapp.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DataResponse {
    private int timestamp;
    @JsonProperty("routes")
    private Map<String, Route> routes;

}
