package com.nyctransittracker.mainapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Section {
    private String begin;
    private String end;
    @JsonProperty("runtime_diff")
    private int runtimeDiff;
    @JsonProperty("max_actual_headway")
    private int maxActualHeadway;
    @JsonProperty("max_scheduled_headway")
    private int maxScheduledHeadway;
    @JsonProperty("delayed_time")
    private int delayedTime;

}
