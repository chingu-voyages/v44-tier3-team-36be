package com.nyctransittracker.mainapp.repository;

import com.nyctransittracker.mainapp.model.MtaResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RedisRepository {

    private final RedisTemplate<String, MtaResponse> redisTemplate;

    private static final String KEY = "data";

    public void save(MtaResponse data) {
        try {
            redisTemplate.opsForValue().set(KEY, data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public MtaResponse getData() {
        return redisTemplate.opsForValue().get(KEY);
    }
}
