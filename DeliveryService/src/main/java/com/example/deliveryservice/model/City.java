package com.example.deliveryservice.model;

import lombok.Getter;

@Getter
public enum City {
    CITY1("Kharkov"),
    CITY2("Odessa"),
    CITY3("Kiev"),
    CITY4("Lviv");

    private final String cityName;

    City(String cityName) {
        this.cityName = cityName;
    }

    public static City fromCityName(String cityName) {
        for (City city : City.values()) {
            if (city.getCityName().equalsIgnoreCase(cityName)) {
                return city;
            }
        }
        throw new IllegalArgumentException("Invalid city name: " + cityName);
    }
}
