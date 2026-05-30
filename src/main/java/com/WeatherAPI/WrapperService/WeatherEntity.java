package com.WeatherAPI.WrapperService;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class WeatherEntity {
    @Id
    private Long id;

}
