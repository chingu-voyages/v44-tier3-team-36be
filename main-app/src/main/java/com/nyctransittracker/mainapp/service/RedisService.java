package com.nyctransittracker.mainapp.service;

import com.nyctransittracker.mainapp.dto.ArrivalTimeResponse;
import com.nyctransittracker.mainapp.dto.MtaResponse;
import com.nyctransittracker.mainapp.dto.TrainPositionResponse;
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

    public void saveTrainPositions(TrainPositionResponse positions) {
        redisRepository.save(positions);
    }

    public void saveArrivalTimes(ArrivalTimeResponse times) {
        redisRepository.save(times);
    }

    public MtaResponse getMtaData() {
        return redisRepository.getMtaData();
    }

    public TrainPositionResponse getTrainPositions() {
        return redisRepository.getTrainPositions();
    }

    public ArrivalTimeResponse getArrivalTimes() {
        return redisRepository.getArrivalTimes();
    }

}
