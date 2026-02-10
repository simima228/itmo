package com.example.models;

public enum Country {
    INDIA,
    ITALY,
    THAILAND,
    JAPAN;

    public static String getCountry() {
        StringBuilder country = new StringBuilder();
        for (Country c : Country.values()) {
            country.append(c.name()).append("\n");
        }
        return country.substring(0, country.length() - 1);
    }
}
