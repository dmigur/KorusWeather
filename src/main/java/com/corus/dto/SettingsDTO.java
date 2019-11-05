/**
 * Created by dmitrigu on 21/09/2019.
 */

package com.corus.dto;

/**
 * Created by dmitrigu on 22/09/2019.
 */

public class SettingsDTO {

    private String weatherByCityUrlTemplate;
    private String weatherByCoordsUrlTemplate;
    private Integer inputCapacity;

    public String getWeatherByCityUrlTemplate() {
        return weatherByCityUrlTemplate;
    }

    public void setWeatherByCityUrlTemplate(String weatherUrlTemplate) {
        this.weatherByCityUrlTemplate = weatherUrlTemplate;
    }

    public Integer getInputCapacity() {
        return inputCapacity;
    }

    public String getWeatherByCoordsUrlTemplate() {
        return weatherByCoordsUrlTemplate;
    }

    public void setWeatherByCoordsUrlTemplate(String weatherByCoordsUrlTemplate) {
        this.weatherByCoordsUrlTemplate = weatherByCoordsUrlTemplate;
    }

    public void setInputCapacity(Integer inputCapacity) {
        this.inputCapacity = inputCapacity;
    }
}