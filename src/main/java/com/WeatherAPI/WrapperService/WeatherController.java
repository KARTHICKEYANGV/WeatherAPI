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

    // Endpoint to get weather data for a given city and date
    @GetMapping("/getWeather/{city}/{date}")
    public WeatherDTO getWeather(@PathVariable String city, @PathVariable String date) {
        // Call the weather API and return the weather data for the given city
        return weatherService.fetchWeatherFromAPI(city,date);
    }

    //Save the weather data to the database and return the saved entity
    @GetMapping("/saveWeather/{city}/{date}")
    public WeatherEntity saveWeather(@PathVariable String city, @PathVariable String date) {
        return weatherService.saveToDB(city,date);
    }


    // Endpoint to get weather data as a string for a given city
    @GetMapping("/getWeatherString/{city}")
    public String getWeatherString(@PathVariable String city) {
        // Call the weather API and return the weather data for the given city
        return weatherService.fetchWeatherFromAPIString(city);
    }

    //Test Redis connection
    @GetMapping("/testRedis")
    public String testRedis() {
        return weatherService.testRedis();
        }

}
