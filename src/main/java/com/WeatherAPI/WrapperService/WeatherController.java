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

    @GetMapping("/getWeather/{city}/{date}")
    public WeatherDTO getWeather(@PathVariable String city, @PathVariable String date) {
        // Call the weather API and return the weather data for the given city
        return weatherService.fetchWeatherFromAPI(city,date);
    }

    @GetMapping("/getWeatherString/{city}")
    public String getWeatherString(@PathVariable String city) {
        // Call the weather API and return the weather data for the given city
        return weatherService.fetchWeatherFromAPIString(city);
    }


}
