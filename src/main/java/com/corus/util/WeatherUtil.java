package com.corus.util;


/**
 * Created by dmitrigu on 21/09/2019.
 */
public class WeatherUtil {

    public static Double kelvinToCelsius(Number temp){

        return temp == null ? null: Math.round((temp.doubleValue() - 273.15) * 10) / 10.0;
    }
}
