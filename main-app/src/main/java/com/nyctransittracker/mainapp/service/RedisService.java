package com.nyctransittracker.mainapp.service;

import com.nyctransittracker.mainapp.model.MtaResponse;
import com.nyctransittracker.mainapp.repository.RedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisService {
    private final RedisRepository redisRepository;
    public void saveData(MtaResponse data) {
        redisRepository.save(data);
    }

    public MtaResponse getData() {
        return redisRepository.getData();
    }
}
