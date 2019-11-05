package com.corus;

import com.corus.dto.ResultDTO;
import com.corus.entity.Weather;
import com.corus.entity.Settings;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class WeatherTest extends AbstractTest {
    @Autowired
    Settings settings;

    private List<Weather> weathers = new ArrayList<>();


    @Override
    @Before
    public void setUp() {

        super.setUp();
    }


    @Test
    public void getWeather() throws Exception {
        String uri = "/api/weather";
        String callback = "corus";
        String city = "Toronto";


        uri += "?city="+city+"&callback="+callback ;
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        ResultDTO resultDTO = super.mapFromJson(content, ResultDTO.class);
        assertTrue(resultDTO.getData().size() > 0);
    }

    @Test
    public void getWeatherByCoords() throws Exception {
        String uri = "/api/weather";
        String callback = "corus";
        String lat = "60.0";
        String lon = "60.0";

        uri += "?latitude="+lat+ "&longitude=" + lon + "&callback="+callback ;
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        ResultDTO resultDTO = super.mapFromJson(content, ResultDTO.class);
        assertTrue(resultDTO.getData().size() > 0);
    }


}