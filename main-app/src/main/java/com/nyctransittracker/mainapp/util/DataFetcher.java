package com.nyctransittracker.mainapp.util;

import com.nyctransittracker.mainapp.model.DataResponse;
import com.nyctransittracker.mainapp.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class DataFetcher implements CommandLineRunner {

    private final WebClient webClient;
    private final static String url = "https://www.goodservice.io/api/routes/?detailed=1";
    private final RedisService redisService;

    public DataResponse fetchData() {
        DataResponse dataResponse = webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(DataResponse.class)
                .block();
        redisService.saveData(dataResponse);
        return dataResponse;
    }

    @Override
    public void run(String... args) throws Exception {
        DataResponse response = fetchData();
        System.out.println(response.getTimestamp());
    }
}
