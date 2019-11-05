package com.corus.service;

import com.corus.entity.Weather;
import com.corus.entity.WeatherRequest;
import com.corus.exceptions.WeatherException;

import java.util.Collection;

/**
 * Created by dmitrigu on 21/09/2019.
 */
public interface IWeatherService {

    public Collection<Weather> getWeather(WeatherRequest req) throws WeatherException;

}
