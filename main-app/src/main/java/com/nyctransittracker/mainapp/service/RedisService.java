package com.nyctransittracker.mainapp.service;

import com.nyctransittracker.mainapp.model.DataResponse;
import com.nyctransittracker.mainapp.repository.RedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisService {
    private final RedisRepository redisRepository;
    public void saveData(DataResponse data) {
        redisRepository.save(data);
    }

    public DataResponse getData() {
        return redisRepository.getData();
    }
}
