package com.nyctransittracker.mainapp.controller;

import com.nyctransittracker.mainapp.model.DataResponse;
import com.nyctransittracker.mainapp.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TestController {

    private final RedisService redisService;

    @GetMapping
    public DataResponse getData() {
        return redisService.getData();
    }
}
