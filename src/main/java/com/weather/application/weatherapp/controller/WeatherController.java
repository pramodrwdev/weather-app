package com.weather.application.weatherapp.controller;


import com.weather.application.weatherapp.model.ForecastDate;
import com.weather.application.weatherapp.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WeatherController {

    @Autowired
    WeatherService weatherService;

    @RequestMapping("/")
    public String main() {
        return "Hello please enter city";

    }

    @RequestMapping("weatherforecast/{city}")
    public List<ForecastDate> getWeatherForThree(@PathVariable String city){

              return  weatherService.getWeatherDetails(city);

    }
}
