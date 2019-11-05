package com.corus.exceptions;
/**
 * Created by dmitrigu on 23/09/2019.
 */

public class WeatherException extends Exception {

    public WeatherException(String s){
        super(s);
    }

    public WeatherException(String s, Exception e){
        super(s, e);
    }
}
