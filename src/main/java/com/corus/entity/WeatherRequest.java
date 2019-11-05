package com.corus.entity;

import com.corus.dto.WeatherRequestDTO;

public class WeatherRequest {
    private String city;
    private Float longitude;
    private Float latitude;
    private String callback;

    public WeatherRequest() {
    }

    public WeatherRequest(WeatherRequestDTO dto) {

        this.callback = dto.getCallback();
        this.city = dto.getCity();
        this.longitude = dto.getLongitude();
        latitude = dto.getLatitude();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }
}