package com.nyctransittracker.mainapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
@RedisHash("data")
public class MtaResponse {

    private long timestamp;
    @JsonProperty("routes")
    private Map<String, Route> routes;

}
