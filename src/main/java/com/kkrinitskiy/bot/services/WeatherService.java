package com.kkrinitskiy.bot.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kkrinitskiy.bot.models.weatherModels.WeatherApiResponse;
import com.kkrinitskiy.bot.models.weatherModels.WeatherItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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

    @Scheduled(cron = "0 0 0 * * *")
    private void updateWeather() {
        Mono<String> stringMono = webClient.get().retrieve().bodyToMono(String.class);
        String block = stringMono.block();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            weatherApiResponse = objectMapper.readValue(block, WeatherApiResponse.class);
        } catch (JsonProcessingException e) {
            log.error("Map from json to WeatherApiResponse.class problem: \n" + e.getMessage());
        }
    }

    private WeatherApiResponse getWeather() {
        if (weatherApiResponse == null) {
            updateWeather();
        }
        return weatherApiResponse;
    }

    public String getCurrentWeather() {
        List<WeatherItem> list = getWeather().getList().stream().filter(w -> {
            LocalDateTime parse = LocalDateTime.parse(w.getDtTxt(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            return parse.isBefore(LocalDateTime.now().plusHours(3));
        }).toList();
        WeatherItem weatherItem = list.get(list.size() - 1);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("погода на: ").append(LocalTime.parse(weatherItem.getDtTxt(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("\n");
                stringBuilder.append("температура: ").append(weatherItem.getMain().getTemp()).append("\n");
                stringBuilder.append("чувствуется как: ").append(weatherItem.getMain().getFeelsLike()).append("\n");
                stringBuilder.append("").append(weatherItem.getWeather().get(0).getDescription()).append("\n\n");
        return stringBuilder.toString();
    }
}
