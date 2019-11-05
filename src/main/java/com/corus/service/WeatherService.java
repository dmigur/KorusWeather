package com.corus.service;

import com.corus.entity.Weather;
import com.corus.entity.WeatherRequest;

import com.corus.entity.Settings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by dmitrigu on 21/09/2019.
 */
@Service
public class WeatherService {

    @Autowired
    private Settings settings;

    private static Logger logger = LoggerFactory.getLogger(WeatherService.class);

    public Collection<Weather> getWeather(WeatherRequest request) {

        Collection<Weather> result = new ArrayList();

        return result;
    }


}