package com.nyctransittracker.mainapp.repository;

import com.nyctransittracker.mainapp.model.DataResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RedisRepository {

    private final RedisTemplate<String, DataResponse> redisTemplate;

    private static final String KEY = "data";

    public void save(DataResponse data) {
        try {
            redisTemplate.opsForValue().set(KEY, data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public DataResponse getData() {
        return redisTemplate.opsForValue().get(KEY);
    }
}
