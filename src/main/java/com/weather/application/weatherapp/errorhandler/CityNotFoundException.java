package com.weather.application.weatherapp.errorhandler;

public class CityNotFoundException  extends RuntimeException{

    public CityNotFoundException(String message){
        super(message);
    }
}
