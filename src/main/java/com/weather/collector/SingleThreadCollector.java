package com.weather.collector;

import com.weather.openweather.json.city.City;

import java.util.List;

public class SingleThreadCollector implements TemperatureCollector {

    @Override
    public void checkAndCollectTemperatures(List<City> cities, MinMaxValues minMax) {
        for (City city: cities) {
            TemperatureCollectorTask task = new TemperatureCollectorTask(city, minMax);
            task.run();
            System.out.println("RESULT: \n" + minMax);
        }
    }
}
