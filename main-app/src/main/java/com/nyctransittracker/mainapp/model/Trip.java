package com.nyctransittracker.mainapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Trip {
    private String id;
    private Map<String, Long> stops; // stopId to arrival time (in epoch time)
    @JsonProperty("delayed_time")
    private int delayedTime;
    @JsonProperty("schedule_discrepancy")
    private int scheduleDiscrepancy;
    @JsonProperty("is_delayed")
    private boolean isDelayed;
    @JsonProperty("is_assigned")
    private boolean isAssigned;
    @JsonProperty("last_stop_made")
    private String lastStopMade;
}
