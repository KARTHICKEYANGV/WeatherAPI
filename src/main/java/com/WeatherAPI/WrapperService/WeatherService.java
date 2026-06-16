package com.WeatherAPI.WrapperService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import tools.jackson.core.ObjectReadContext;


@Service
@RequiredArgsConstructor
public class WeatherService {
    private final WeatherRepository weatherRepository;
    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;
    @Value("${weather.api.key}")
    private String apiKey;


    public WeatherDTO fetchWeatherFromAPI(String city, String date) {
        // Implement the logic to call the weather API and return the weather data for the given city
        String cacheKey = city + "_" + date;
        String cachedData = redisTemplate.opsForValue().get(cacheKey);
        if (cachedData != null) {
            WeatherDTO weather_city_date = objectMapper.readValue(cachedData, WeatherDTO.class);
            return weather_city_date;

        }


        String url = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/"
                + city + "/" + date
                + "?unitGroup=us&key="
                + apiKey
                + "&contentType=json";
        RestTemplate restTemplate = new RestTemplate();
        WeatherDTO response = restTemplate.getForObject(url, WeatherDTO.class);
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
