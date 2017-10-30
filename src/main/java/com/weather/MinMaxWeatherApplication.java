package com.weather;

import com.weather.collector.MinMaxValues;
import com.weather.collector.MultiThreadCollector;
import com.weather.collector.SingleThreadCollector;
import com.weather.httpserver.MinMaxHttpServer;
import com.weather.openweather.CityReaderFromJson;
import com.weather.openweather.json.city.City;

import java.util.List;

/**
 * Created by Szabolcs Filep in 22 October 2017.
 */
class MinMaxWeatherApplication {

    public final MinMaxValues minMaxValues = new MinMaxValues();

    public static void main(String[] args) {
        // Collect minmax values
        MinMaxWeatherApplication app = new MinMaxWeatherApplication();
        app.start();

        // Start data provider REST server
        MinMaxHttpServer server = new MinMaxHttpServer();
        server.start(app.minMaxValues);
    }

    private void start() {
        System.out.println("Start reading cities from file...");
        List<City> cities = new CityReaderFromJson().readCitiesForCountry("hu");

//        collectInSingleThread(cities);
        collectInMultiThread(cities);
    }

    private void collectInSingleThread(List<City> cities) {
        System.out.println("\n----------------------------------------------");
        System.out.println(" Start requesting temperatures one by one...");
        System.out.println("----------------------------------------------");
        long messaureTime = System.currentTimeMillis();

        new SingleThreadCollector().checkAndCollectTemperatures(cities, minMaxValues);

        System.out.println("Finished in " + (System.currentTimeMillis() - messaureTime) + "ms.");
    }

    private void collectInMultiThread(List<City> cities) {
        System.out.println("\n----------------------------------------------");
        System.out.println(" Start requesting temperatures in parallel...");
        System.out.println("----------------------------------------------");
        long messaureTime = System.currentTimeMillis();

        new MultiThreadCollector().checkAndCollectTemperatures(cities, minMaxValues);

        System.out.println("Finished in " + (System.currentTimeMillis() - messaureTime) + "ms.");
    }
}