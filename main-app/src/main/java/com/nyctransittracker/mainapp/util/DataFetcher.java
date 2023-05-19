package com.nyctransittracker.mainapp.util;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class DataFetcher implements CommandLineRunner {

    private final WebClient webClient;
    private final String url;

    public DataFetcher(WebClient webClient) {
        this.webClient = webClient;
        this.url = "https://www.goodservice.io/api/routes/?detailed=1";
    }

    public DataResponse fetchData() {
        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(DataResponse.class)
                .block();
    }

    @Override
    public void run(String... args) throws Exception {
        DataResponse response = fetchData();
        System.out.println(response);
    }
}
