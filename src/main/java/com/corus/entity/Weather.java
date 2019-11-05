package com.corus.entity;

/**
 * Created by dmitrigu on 21/09/2019.
 */
public class Weather {

    private Number temp;

    private Number pressure;

    private Number humidity;

    private Number windSpeed;

    private String country;

    private String callback;


    public Weather() {
    }

    public Weather(Double temp, Integer pressure, Integer humidity,
                   Integer visibility,
                   Double windSpeed, String country, String callback) {
        this.temp = temp;
        this.pressure = pressure;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.country = country;
        this.callback = callback;
    }


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

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }


    @Override
    public String toString() {
        return "Weather{" +
                "temp=" + temp +
                ", pressure=" + pressure +
                ", humidity=" + humidity +
                ", windSpeed=" + windSpeed +
                ", country='" + country + '\'' +
                ", callback='" + callback + '\'' +
                '}';
    }
}