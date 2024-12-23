package com.kkrinitskiy.bot.models.weatherModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class WeatherItem {
    private long dt;
    private Main main;
    private List<Weather> weather = new ArrayList<>();
    private Clouds clouds;
    private Wind wind;
    private int visibility;
    private double pop;
    private Sys sys;
    @JsonProperty("dt_txt")
    private String dtTxt;
//    private Snow snow;
}
