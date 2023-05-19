package com.nyctransittracker.mainapp.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
class Route {
    private String id;
    private String name;
    private String color;
    private String status;
    private boolean visible;
    private boolean scheduled;
    @JsonProperty("direction_statuses")
    private Map<String, String> directionStatuses;
    @JsonProperty("delay_summaries")
    private Map<String, String> delaySummaries;
    @JsonProperty("service_irregularity_summaries")
    private Map<String, String> serviceIrregularitySummaries;
    @JsonProperty("service_change_summaries")
    private Map<String, List<String>> serviceChangeSummaries;
    @JsonProperty("actual_routings")
    private Map<String, List<List<String>>> actualRoutings;
    @JsonProperty("scheduled_routings")
    private Map<String, List<List<String>>> scheduledRoutings;
    @JsonProperty("slow_sections")
    private Map<String, List<Section>> slowSections;
    @JsonProperty("long_headway_sections")
    private Map<String, List<Section>> longHeadwaySections;
    @JsonProperty("delayed_sections")
    private Map<String, List<Section>> delayedSections;
//    routesWithSharedTracks;
//    routesWithSharedTracksSummary;
    private Map<String, List<Trip>> trips;
    @JsonProperty("additional_trips_on_shared_tracks")
    private List<String> additionalTripsOnSharedTracks;

}
