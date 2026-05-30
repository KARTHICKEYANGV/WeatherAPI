package com.WeatherAPI.WrapperService;

import lombok.Data;

@Data
public class WeatherDaysDTO {
    private String datetime;
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
