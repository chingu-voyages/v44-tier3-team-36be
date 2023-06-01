package com.nyctransittracker.mainapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nyctransittracker.mainapp.dto.AuthenticationRequest;
import com.nyctransittracker.mainapp.dto.AuthenticationResponse;
import com.nyctransittracker.mainapp.dto.RegisterRequest;
import com.nyctransittracker.mainapp.service.AuthenticationService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    
    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
        @RequestBody RegisterRequest request
    ) {
        String jwtToken = service.register(request);
        if (jwtToken.length() > 0) {
            return ResponseEntity
                    .ok(AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build());
        } else {
            return ResponseEntity
            .badRequest().body(AuthenticationResponse.builder()
            .message("Email already taken.")
            .build());
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
        @RequestBody AuthenticationRequest request
    ) {
        String jwtToken = service.authenticate(request);
        if (jwtToken.length() > 0) {
            return ResponseEntity
                    .ok(AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build());
        } else {
            return ResponseEntity
            .badRequest().body(AuthenticationResponse.builder()
            .message("Invalid login credentials.")
            .build());
        }
    }
}
