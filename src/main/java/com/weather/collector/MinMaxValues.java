package com.weather.collector;


import com.weather.openweather.json.temperature.Weather;

public class MinMaxValues {

    private volatile Weather minTempWeather;
    private volatile Weather maxTempWeather;

    public synchronized Weather getMinTempWeather() {
        return minTempWeather;
    }

    public synchronized void setMinTempWeather(Weather minTempWeather) {
        this.minTempWeather = minTempWeather;
    }

    public synchronized Weather getMaxTempWeather() {
        return maxTempWeather;
    }

    public synchronized void setMaxTempWeather(Weather maxTempWeather) {
        this.maxTempWeather = maxTempWeather;
    }

    @Override
    public String toString() {
        return "Minimum: " + minTempWeather + "\n" +
                "Maximum: " + maxTempWeather + "\n";
    }
}
