package com.kkrinitskiy.bot.models.weatherModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherApiResponse {
    private String code;
    private String message;
    private String cnt;
    private List<WeatherItem> list = new ArrayList<WeatherItem>();
}
