package com.corus.service;

import com.corus.entity.Weather;
import com.corus.entity.WeatherRequest;
import com.corus.entity.WeatherResult;
import com.corus.exceptions.WeatherException;
import com.corus.util.BlockingMap;
import com.corus.util.Constants;
import com.corus.util.JsonUtil;
import com.corus.entity.Settings;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.*;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by dmitrigu on 21/09/2019.
 */

@Service
public class OpenWeatherService implements IWeatherService {
    @Autowired
    private Settings settings;

    private static Logger logger = LoggerFactory.getLogger(OpenWeatherService.class);

    private WeatherProcessor processor = new WeatherProcessor();

    public OpenWeatherService() {

    }

    @PostConstruct
    public void startProcessor() {
        new Thread(processor).start();
    }

    public Collection<Weather> getWeather(WeatherRequest request) throws WeatherException {

        try {

            WeatherResult result;
            processor.addRequest(request);

            long t1 = System.currentTimeMillis();
            logger.info(".....Getting response for " + request.getCallback());
            result = processor.getResult(request.getCallback());

            if (result == null) {
                throw new WeatherException("Error getting weather: no response");
            }

            if (CollectionUtils.isEmpty(result.getWeathers())) {
                String msg = result.getMessage() == null ? "No data found" : result.getMessage();
                throw new WeatherException(msg);
            }
            long t2 = System.currentTimeMillis();
            logger.info(".....GOT RESPONSE for " + request.getCallback() + " in " + (t2 - t1) + " ms");

            return result.getWeathers();

        } catch (Exception e) {

            logger.error("error getting weather", e);
            throw new WeatherException(e.getMessage());
        }
    }

    private List<Weather> parseWeather(JSONObject object) {

        return null;
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }


    class WeatherProcessor implements Runnable {

        private LinkedBlockingQueue<WeatherRequest> requests;
        private BlockingMap<String, WeatherResult> responses;

        public void run() {
            requests = new LinkedBlockingQueue<WeatherRequest>(settings.getInputCapacity());
            responses = new BlockingMap<String, WeatherResult>();

            do {
                WeatherRequest request = null;
                try {

                    request = requests.take();

                    Collection<Weather> weathers = processRequest(request);

                    WeatherResult result =
                            new WeatherResult(request.getCallback(), weathers);

                    responses.put(request.getCallback(), result);

                } catch (Exception e) {
                    logger.error("Error processing requests", e);
                    WeatherResult result =
                            new WeatherResult(request.getCallback(), null);
                    result.setMessage(e.getMessage());
                    responses.put(request.getCallback(), result);
                }

            } while (true);
        }

        public void addRequest(WeatherRequest request) {

            requests.add(request);
        }

        public WeatherResult getResult(String key) {

            try {
                WeatherResult result = responses.take(key);

                return result;
            } catch (Exception e) {
                return null;
            }

        }

        private Collection<Weather> processRequest(WeatherRequest request) throws Exception {
            JSONObject obj = readJsonWeather(request);
            if (request.getCity() != null)
                return parseJsonCityWeather(obj);
            else
                return parseJsonCoordsWeather(obj);
        }

        private JSONObject readJsonWeather(WeatherRequest request) throws Exception {
            String city = request.getCity();
            String url = null;
            if (StringUtils.isEmpty(city)) {
                url = settings.getWeatherByCoordsUrlTemplate();
                url = url.replace("####", request.getLatitude().toString());
                url = url.replace("$$$$", request.getLongitude().toString());
            } else {
                url = settings.getWeatherByCityUrlTemplate();
                url = url.replace("####", city);
            }

            InputStream is = new URL(url).openStream();
            try {
                BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
                String jsonText = readAll(rd);
                JSONObject json = new JSONObject(jsonText);
                return json;
            } catch (UnknownHostException e) {
                throw new WeatherException("Unknown host: " + url);
            } catch (Exception e) {
                throw new WeatherException("Error connecting to OpenWeather site: " + e.getMessage());
            } finally {
                is.close();
            }

        }

        private Collection<Weather> parseJsonCityWeather(JSONObject json) throws Exception {
            List<Weather> we = new ArrayList<Weather>();
            try {
                String code = (String) json.get(Constants.cod.name());
                if (!"200".equals(code)) {
                    throw new WeatherException("Cannot get weather");
                }
                JSONArray arr = (JSONArray) json.get("list");
                for (int i = 0; i < arr.length(); i++) {

                    JSONObject obj = (JSONObject) arr.get(i);
                    JSONObject main = (JSONObject) obj.get(Constants.main.name());
                    if (main == null) {
                        continue;
                    }
                    Weather weather = new Weather();
                    Number temp = (Number) main.get(Constants.temp.name());

                    Number humidity = (Number) main.get(Constants.humidity.name());
                    JSONObject wind = (JSONObject) obj.get(Constants.wind.name());

                    weather.setPressure((Number) main.get(Constants.pressure.name()));
                    weather.setHumidity(humidity);
                    weather.setTemp(temp);

                    if (wind != null) {
                        weather.setWindSpeed((Number) wind.get(Constants.speed.name()));
                    }

                    JSONObject sys = (JSONObject) obj.get(Constants.sys.name());
                    if (sys != null) {
                        weather.setCountry((String) sys.get(Constants.country.name()));
                    }

                    we.add(weather);
                }


            } catch (Exception e) {

                logger.error("error parsing weather", e);
                if (we.isEmpty()) throw new WeatherException("cannot parse weather result");

            }


            return we;
        }

        private Collection<Weather> parseJsonCoordsWeather(JSONObject json) throws Exception {
            List<Weather> we = new ArrayList<Weather>();
            try {
                JSONObject main = (JSONObject) JsonUtil.getValue(json, Constants.main.name());
                if (main == null) {
                    throw new WeatherException("no weather data");
                }
                Weather weather = new Weather();
                Number temp = (Number) JsonUtil.getValue(main, Constants.temp.name());

                Number humidity = (Number) JsonUtil.getValue(main, Constants.humidity.name());
                JSONObject wind = (JSONObject) JsonUtil.getValue(json, Constants.wind.name());

                weather.setPressure((Number) JsonUtil.getValue(main, Constants.pressure.name()));
                weather.setHumidity(humidity);
                weather.setTemp(temp);

                if (wind != null) {
                    weather.setWindSpeed((Number) JsonUtil.getValue(wind, Constants.speed.name()));
                }

                we.add(weather);

            } catch (WeatherException e) {

                logger.error("error parsing weather", e);

                if (we.isEmpty()) throw e;

            } catch (Exception e) {

                logger.error("error parsing weather", e);
                if (we.isEmpty()) {
                    throw new WeatherException("Error parsing weather ");
                }

            }

            return we;
        }
    }
}