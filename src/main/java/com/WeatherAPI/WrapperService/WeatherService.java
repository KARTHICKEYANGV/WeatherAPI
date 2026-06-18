package com.WeatherAPI.WrapperService;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Duration;


@Service
@RequiredArgsConstructor
public class WeatherService {
    private final WeatherRepository weatherRepository;
    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Value("${weather.api.key}")
    private String apiKey;


    public WeatherDTO fetchWeatherFromAPI(String city, String date) {
        // Implement the logic to call the weather API and return the weather data for the given city
        String cacheKey = city + "_" + date;
        String cachedData = redisTemplate.opsForValue().get(cacheKey);
        if (cachedData != null) {
            try {
                System.out.println("Cache hit for key: " + cacheKey);
                WeatherDTO weather_city_date = objectMapper.readValue(cachedData, WeatherDTO.class);
                return weather_city_date;
            } catch (JsonProcessingException e){
                throw new RuntimeException("Failed to parse cached data", e);
            }

        }
        System.out.println("Cache miss for key: " + cacheKey);


        String url = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/"
                + city + "/" + date
                + "?unitGroup=us&key="
                + apiKey
                + "&contentType=json";
        RestTemplate restTemplate = new RestTemplate();
        WeatherDTO response = restTemplate.getForObject(url, WeatherDTO.class);
        try{redisTemplate.opsForValue().set(cacheKey, objectMapper.writeValueAsString(response), Duration.ofHours(12));}
        catch (JsonProcessingException e){
            throw new RuntimeException("Failed to cache data", e);
        }
        return response;

    }

    public WeatherEntity saveToDB(String city, String date) {
        // Implement the logic to call the weather API and return the weather data for the given city

        String url = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/"
                + city + "/" + date
                + "?unitGroup=us&key="
                + apiKey
                + "&contentType=json";
        RestTemplate restTemplate = new RestTemplate();
        WeatherDTO response = restTemplate.getForObject(url, WeatherDTO.class);
        WeatherDaysDTO day = response.getDays().get(0);

        WeatherEntity weatherEntity = new WeatherEntity();
        weatherEntity.setCity(city);
        weatherEntity.setDate(date);
        weatherEntity.setTempmax(day.getTempmax());
        weatherEntity.setTempmin(day.getTempmin());
        weatherEntity.setTemp(day.getTemp());
        weatherEntity.setHumidity(day.getHumidity());
        weatherEntity.setWindspeed(day.getWindspeed());
        weatherEntity.setWinddir(day.getWinddir());
        weatherEntity.setCloudcover(day.getCloudcover());
        weatherEntity.setPrecip(day.getPrecip());
        weatherEntity.setSnow(day.getSnow());

        return weatherRepository.save(weatherEntity);

    }

    public String fetchWeatherFromAPIString(String city) {
        // Implement the logic to call the weather API and return the weather data for the given city

        String url = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/"
                + city
                + "?unitGroup=us&key="
                + apiKey
                + "&contentType=json";
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, String.class);
    }

    //test redis connection
    public String testRedis() {
         redisTemplate.opsForValue().set("testKey", "test");

         return redisTemplate.opsForValue().get("testKey");
    }
}
