package com.kkrinitskiy.bot.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kkrinitskiy.bot.models.weatherModels.WeatherApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class WeatherService {
    private final WebClient webClient;
    private final String lat = "55.75";
    private final String lon = "37.61";
    private final String url = "https://api.openweathermap.org/data/2.5/forecast?lat=%s&lon=%s&lang=ru&units=metric&appid=%s";
    private WeatherApiResponse weatherApiResponse;

    @Autowired
    public WeatherService(WebClient.Builder webClientBuilder, @Value("${api.weather}") String token) {
        this.webClient = webClientBuilder.baseUrl(url.formatted(lat, lon, token)).build();
    }

    public void updateWeather() {
        log.info("Get weather from {}", url);
        Mono<String> stringMono = webClient.get().retrieve().bodyToMono(String.class);
        String block = stringMono.block();
        log.info("Get weather from block {}", block);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            weatherApiResponse = objectMapper.readValue(block, WeatherApiResponse.class);
        }catch (JsonProcessingException e){
            log.error("Map from json to WeatherApiResponse.class problem: \n" + e.getMessage());
        }
    }

    public WeatherApiResponse getWeather() {
        updateWeather();
        return weatherApiResponse;
    }
}
