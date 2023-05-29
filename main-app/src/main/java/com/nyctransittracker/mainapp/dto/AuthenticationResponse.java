package com.nyctransittracker.mainapp.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private String token;
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private String message;
}
