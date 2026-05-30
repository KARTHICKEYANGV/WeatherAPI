package com.WeatherAPI.WrapperService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WeatherController {
    private final WeatherService weatherService;

    @GetMapping("/getWeather/{city}")
    public String getWeather(@PathVariable String city) {
        System.out.println(city);
        // Call the weather API and return the weather data for the given city
        return "Weather data for " +city+ weatherService.fetchWeatherFromAPI(city);
    }
}
