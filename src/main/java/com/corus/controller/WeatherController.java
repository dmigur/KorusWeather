package com.corus.controller;

import com.corus.dto.ResultDTO;
import com.corus.dto.SettingsDTO;
import com.corus.dto.UserDTO;
import com.corus.dto.WeatherDTO;
import com.corus.entity.Weather;
import com.corus.entity.WeatherRequest;
import com.corus.enums.ErrorCode;
import com.corus.exceptions.WeatherException;
import com.corus.service.AuthenticationService;
import com.corus.service.OpenWeatherService;
import com.corus.entity.Settings;
import com.corus.util.WeatherUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collection;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by dmitrigu on 21/09/2019.
 */

@RestController
public class WeatherController {

    private static Logger weatherLogger = LoggerFactory.getLogger("WEATHER");

    private static Logger logger = LoggerFactory.getLogger(WeatherController.class);
    @Autowired
    OpenWeatherService weatherService;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    Settings settings;

    @ResponseStatus(OK)
    @RequestMapping(value = "/api/weather", method = GET)
    public ResponseEntity getWeather(
            @RequestParam(value = "callback") String callback,
            @RequestParam(value = "city", required = false) String city,
            @RequestParam(value = "longitude", required = false) Float longitude,
            @RequestParam(value = "latitude", required = false) Float latitude) {

            User user = authenticationService.getLoggedUser();

        WeatherRequest request = new WeatherRequest();
        request.setCity(city);
        request.setLatitude(latitude);
        request.setLongitude(longitude);
        request.setCallback(callback);

        String location = city;

        ResultDTO res = new ResultDTO();
        try {

            Collection<Weather> weathers = weatherService.getWeather(request);
                Collection<WeatherDTO> results = new ArrayList<WeatherDTO>();
            for (Weather weather : weathers) {

                WeatherDTO dto = new WeatherDTO();

                if (city != null){
                    dto.setTemp(WeatherUtil.kelvinToCelsius(weather.getTemp()));
                    dto.setCountry(weather.getCountry());
                }else{
                    dto.setTemp(weather.getTemp());
                    dto.setCountry("--");
                }

                dto.setHumidity(weather.getHumidity());
                dto.setPressure(weather.getPressure());
                dto.setWindSpeed(weather.getWindSpeed());

                results.add(dto);
            }


            res.setData(results);
            res.setStatus(CollectionUtils.isEmpty(weathers) ?
                    ErrorCode.Error : ErrorCode.Success);

            String logMessage = (user == null ? "" : user.getUsername()) +
                    " , location = " + location + ", result = " + res.toString();

            weatherLogger.info(logMessage);

            return new ResponseEntity<>(res, HttpStatus.OK);

        }
        catch (WeatherException e) {
            logger.error("Error getting weather", e);
            res.setStatus( ErrorCode.Error);
            res.setMessage(e.getMessage());
            return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);

        }
        catch (Exception e) {
            logger.error("Error getting weather", e);
            res.setMessage("Error processing request");

        }
        return null;
    }

    @RequestMapping("/api/settings")
    @ResponseStatus(OK)
    @ResponseBody
    public SettingsDTO getSettings() {
        SettingsDTO result = new SettingsDTO();
        result.setWeatherByCityUrlTemplate(settings.getWeatherByCityUrlTemplate());
        result.setWeatherByCoordsUrlTemplate(settings.getWeatherByCoordsUrlTemplate());
        result.setInputCapacity(settings.getInputCapacity());
        return result;
    }

    private void handleLogOutResponse(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            cookie.setMaxAge(0);
            cookie.setValue(null);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
    }


    @ResponseStatus(OK)
    @RequestMapping(value = "/api/auth", method = GET)
    public UserDTO getAuthUser() {
        User auth = authenticationService.getLoggedUser();

        UserDTO userDTO = new UserDTO();

        if (auth == null) {
            return userDTO;
        }
        userDTO.setName(auth.getUsername());
        return userDTO;
    }

}