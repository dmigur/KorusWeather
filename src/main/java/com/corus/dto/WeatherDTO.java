package com.corus.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by dmitrigu on 21/09/2019.
 */

public class WeatherDTO implements Serializable {
    @JsonProperty("temp")
    private Number temp;

    @JsonProperty("pressure")
    private Number pressure;

    @JsonProperty("humidity")
    private Number humidity;

    @JsonProperty("windSpeed")
    private Number windSpeed;

    @JsonProperty("country")
    private String country;

    public Number getTemp() {
        return temp;
    }

    public void setTemp(Number temp) {
        this.temp = temp;
    }

    public Number getPressure() {
        return pressure;
    }

    public void setPressure(Number pressure) {
        this.pressure = pressure;
    }

    public Number getHumidity() {
        return humidity;
    }

    public void setHumidity(Number humidity) {
        this.humidity = humidity;
    }


    public Number getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Number windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }


    @Override
    public String toString() {
        return "WeatherDTO{" +
                "temp=" + temp +
                ", pressure=" + pressure +
                ", humidity=" + humidity +
                ", windSpeed=" + windSpeed +
                ", country='" + country + '\'' +
                '}';
    }
}