package com.nyctransittracker.mainapp.controller;

import com.nyctransittracker.mainapp.dto.MtaResponse;
import com.nyctransittracker.mainapp.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/data")
@RequiredArgsConstructor
public class MtaController {

    private final RedisService redisService;

    @GetMapping
    public MtaResponse getMtaData() {
        return redisService.getMtaData();
    }
}
