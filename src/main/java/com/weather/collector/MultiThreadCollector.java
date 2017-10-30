package com.weather.collector;

import com.weather.openweather.json.city.City;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MultiThreadCollector implements TemperatureCollector {
    public static final int PARALLEL_THREADS = 5;

    private ExecutorService executor = Executors.newFixedThreadPool(PARALLEL_THREADS);

    @Override
    public void checkAndCollectTemperatures(List<City> cities, MinMaxValues minMaxToRefresh) {

        for (City city : cities) {
            TemperatureCollectorTask task = new TemperatureCollectorTask(city, minMaxToRefresh);
            executor.submit(task);
        }
        finishAndPrintResult(minMaxToRefresh);
    }

    private void finishAndPrintResult(MinMaxValues minMax) {
        try {
            executor.shutdown();

            // A lekerdezesek befejezesere maximum 1 percet varunk
            boolean finishedInTime = executor.awaitTermination(1, TimeUnit.MINUTES);

            if (finishedInTime) {
                System.out.println("RESULT: \n" + minMax);
            } else {
                System.out.println("There was not enough time to complete the requests. ");
            }

        } catch (InterruptedException e) {
            System.out.println("Could not finish all the tasks.");
            e.printStackTrace();
        }
    }
}
