package com.nyctransittracker.mainapp.repository;

import com.nyctransittracker.mainapp.dto.ArrivalTimeResponse;
import com.nyctransittracker.mainapp.dto.MtaResponse;
import com.nyctransittracker.mainapp.dto.TrainPositionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RedisRepository {

    private final RedisTemplate<String, MtaResponse> mtaResponseRedisTemplate;
    private final RedisTemplate<String, TrainPositionResponse> trainPositionResponseRedisTemplate;
    private final RedisTemplate<String, ArrivalTimeResponse> arrivalTimeResponseRedisTemplate;

    private static final String MTA_KEY = "data";
    private static final String TRAIN_KEY = "train";
    private static final String TIME_KEY = "time";

    public void save(MtaResponse data) {
        try {
            mtaResponseRedisTemplate.opsForValue().set(MTA_KEY, data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void save(TrainPositionResponse data) {
        try {
            trainPositionResponseRedisTemplate.opsForValue().set(TRAIN_KEY, data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void save(ArrivalTimeResponse data) {
        try {
            arrivalTimeResponseRedisTemplate.opsForValue().set(TIME_KEY, data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public MtaResponse getMtaData() {
        return mtaResponseRedisTemplate.opsForValue().get(MTA_KEY);
    }

    public TrainPositionResponse getTrainPositions() {
        return trainPositionResponseRedisTemplate.opsForValue().get(TRAIN_KEY);
    }

    public ArrivalTimeResponse getArrivalTimes() {
        return arrivalTimeResponseRedisTemplate.opsForValue().get(TIME_KEY);
    }
}
