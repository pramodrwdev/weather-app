package com.weather.application.weatherapp.service;

import com.weather.application.weatherapp.errorhandler.CityNotFoundException;
import com.weather.application.weatherapp.model.ForecastDate;
import com.weather.application.weatherapp.model.MainDetails;
import com.weather.application.weatherapp.model.WeatherCondition;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class WeatherService {

    @Autowired
    RestTemplate restTemplate;

    public List<ForecastDate> getWeatherDetails(String city) {
        List<ForecastDate> forcastDates = new ArrayList<>();
        try {

            String url = "http://api.openweathermap.org/data/2.5/forecast?q=" + city + "&appid=0993e9857645cfbfd1e15e5ef2f00548";
            String result = restTemplate.getForObject(url, String.class);
            JSONObject root = new JSONObject(result);

            JSONArray weatherObject = root.getJSONArray("list");

            String description = null;

            double temp = 0;
            int pressure = 0;
            int humidity = 0;
            int temp_min = 0;
            int temp_max = 0;
            int temp_kf = 0;
            int sea_level = 0;
            int grnd_level = 0;

            String date = null;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            for (int i = 0; i < weatherObject.length(); i++) {
                StringBuffer suggession = new StringBuffer();
                JSONObject arrayElement = weatherObject.getJSONObject(i);

                JSONObject main = arrayElement.getJSONObject("main");

                temp = main.getInt("temp");
                pressure = main.getInt("pressure");
                humidity = main.getInt("humidity");
                temp_min = main.getInt("temp_min");
                temp_max = main.getInt("temp_max");
                temp_kf = main.getInt("temp_kf");
                sea_level = main.getInt("sea_level");
                grnd_level = main.getInt("grnd_level");

                MainDetails mainDetails = new MainDetails();
                mainDetails.setTemp(temp);
                mainDetails.setPressure((double) pressure);
                mainDetails.setHumidity(humidity);
                mainDetails.setGrndLevel((double) grnd_level);
                mainDetails.setSeaLevel((double) sea_level);
                mainDetails.setTempKf(temp_kf);

                mainDetails.setTempMax((double) temp_max);
                mainDetails.setTempMin((double) temp_min);

                WeatherCondition wc = new WeatherCondition();


                description = arrayElement.getJSONArray("weather").getJSONObject(0).getString("main");

                if (temp > 40) {
                    suggession.append("Use sunscreen lotion");
                }
                if (description.equals("Rain")) {
                    suggession.append("Carry Umbrella");
                }
                wc.setDescition(description);
                wc.setSuggession(suggession.toString());

                ForecastDate fD = new ForecastDate();
                fD.setMain(mainDetails);
                fD.setCondition(wc);

                date = arrayElement.getString("dt_txt");

                fD.setDate(date);
                forcastDates.add(fD);
            }


        } catch (Exception e) {
              throw new CityNotFoundException("City Not found");
        }
        return forcastDates;
    }
}
