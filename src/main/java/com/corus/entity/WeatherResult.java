package com.corus.entity;

import java.util.Collection;

/**
 * Created by dmitrigu on 23/09/2019.
 */
public class WeatherResult {

    private String callback;
    private Collection<Weather> weathers;
    private String message;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Weather weather = (Weather) o;
        return callback.equals(weather.getCallback());
    }


    public WeatherResult() {
    }

    public WeatherResult(String callback, Collection<Weather> weathers) {
        this.callback = callback;
        this.weathers = weathers;
    }

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }

    public Collection<Weather> getWeathers() {
        return weathers;
    }

    public void setWeathers(Collection<Weather> weathers) {
        this.weathers = weathers;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}