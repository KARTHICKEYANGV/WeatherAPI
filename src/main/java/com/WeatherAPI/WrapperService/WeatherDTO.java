package com.WeatherAPI.WrapperService;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
public class WeatherDTO {
    private List<WeatherDaysDTO> days;
}
