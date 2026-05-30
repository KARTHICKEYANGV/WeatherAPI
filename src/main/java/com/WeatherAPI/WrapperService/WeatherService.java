package com.WeatherAPI.WrapperService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {
    @Value("${weather.api.key}")
    private String apiKey;
    public String fetchWeatherFromAPI(String city) {
        // Implement the logic to call the weather API and return the weather data for the given city

        String url = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/"
                + city
                + "?unitGroup=us&key="
                + apiKey
                + "&contentType=json";
        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.getForObject(url, String.class);
    }
}
