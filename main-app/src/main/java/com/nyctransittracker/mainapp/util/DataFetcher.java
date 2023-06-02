package com.nyctransittracker.mainapp.util;

import com.nyctransittracker.mainapp.model.MtaResponse;
import com.nyctransittracker.mainapp.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@EnableScheduling
@RequiredArgsConstructor
@Slf4j
public class DataFetcher {

    private final WebClient webClient;
    private final static String url = "https://www.goodservice.io/api/routes/?detailed=1";
    private final RedisService redisService;

    @Scheduled(fixedRate = 30000)
    public void fetchData() {
        MtaResponse mtaResponse = webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(MtaResponse.class)
                .block();
        redisService.saveData(mtaResponse);
        log.info("data fetched and saved");
    }

}
