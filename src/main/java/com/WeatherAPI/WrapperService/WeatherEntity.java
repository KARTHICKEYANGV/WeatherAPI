package com.WeatherAPI.WrapperService;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class WeatherEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String city;
    private String date;
    private Double tempmax;
    private Double tempmin;
    private Double temp;
    private Double humidity;
    private Double windspeed;
    private Double winddir;
    private Double cloudcover;
    private Double precip;
    private Double snow;

}
