package com.nyctransittracker.mainapp.dto;

import com.nyctransittracker.mainapp.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionRequest {
    private String userEmail;
    private String stopId;
}