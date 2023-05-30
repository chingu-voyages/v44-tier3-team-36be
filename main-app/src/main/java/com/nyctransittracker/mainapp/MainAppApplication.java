package com.nyctransittracker.mainapp;

import com.nyctransittracker.mainapp.config.RedisProperties;
import com.nyctransittracker.mainapp.util.StationFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.io.IOException;

@SpringBootApplication
@EnableConfigurationProperties(RedisProperties.class)
public class MainAppApplication {

	@Autowired
	private static StationFetcher stationFetcher;

	public MainAppApplication(StationFetcher stationFetcher) {
		this.stationFetcher = stationFetcher;
	}

	public static void main(String[] args) throws IOException {
		SpringApplication.run(MainAppApplication.class, args);
		stationFetcher.fetchStations();
	}

}

