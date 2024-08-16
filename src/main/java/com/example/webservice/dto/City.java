package com.example.webservice.dto;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum City {
    KHARKOV("KHARKOV"),
    ODESSA("ODESSA"),
    KIEV("KIEV"),
    LVIV("LVIV"),
    DNIPRO("DNIPRO"),
    ZAPOROZHYE("ZAPOROZHYE"),
    MIKOLAIV("MIKOLAIV"),
    VINNYTSIA("VINNYTSIA"),
    POLTAVA("POLTAVA"),
    SUMY("SUMY"),
    CHERNIVTSI("CHERNIVTSI"),
    CHERKASY("CHERKASY"),
    TERNOPIL("TERNOPIL"),
    KHERSON("KHERSON"),
    RIVNE("RIVNE"),
    UZHGOROD("UZHGOROD");

    private final String cityName;

    City(String cityName) {
        this.cityName = cityName;
    }

    @JsonValue
    public String getCityName() {
        return cityName;
    }

    @JsonCreator
    public static City fromCityName(String cityName) {
        for (City city : City.values()) {
            if (city.getCityName().equalsIgnoreCase(cityName)) {
                return city;
            }
        }
        throw new IllegalArgumentException("Invalid city name: " + cityName);
    }
}
