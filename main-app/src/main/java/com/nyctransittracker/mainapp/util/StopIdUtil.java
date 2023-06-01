package com.nyctransittracker.mainapp.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StopIdUtil {

    public static String findNextStopId(Map<String, Long> stops, String lastStopId) {
        // sorting stops by the timestamp then extracting out the keys (stop IDs)
        List<Map.Entry<String, Long>> entryList = new ArrayList<>(stops.entrySet());
        entryList.sort(Map.Entry.comparingByValue());
        List<String> stopIds = entryList.stream().map(Map.Entry::getKey).toList();
        return stopIds.get(stopIds.indexOf(lastStopId) + 1); // not too sure if I have to check bounds here
    }

}
