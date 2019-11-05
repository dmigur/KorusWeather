package com.corus.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by dmitrigu on 22/09/2019.
 */

@Service("Settings")
public class Settings {

    @Value("${weather.url.template.city}")
    private String weatherByCityUrlTemplate;

    @Value("${weather.url.template.coords}")
    private String weatherByCoordsUrlTemplate;

    @Value("${weather.input.capacity}")
    private Integer inputCapacity;

    public String getWeatherByCityUrlTemplate() {
        return weatherByCityUrlTemplate;
    }

    public void setWeatherByCityUrlTemplate(String weatherByCityUrlTemplate) {
        this.weatherByCityUrlTemplate = weatherByCityUrlTemplate;
    }

    public String getWeatherByCoordsUrlTemplate() {
        return weatherByCoordsUrlTemplate;
    }

    public void setWeatherByCoordsUrlTemplate(String weatherByCoordsUrlTemplate) {
        this.weatherByCoordsUrlTemplate = weatherByCoordsUrlTemplate;
    }

    public Integer getInputCapacity() {
        return inputCapacity;
    }

    public void setInputCapacity(Integer inputCapacity) {
        this.inputCapacity = inputCapacity;
    }

}