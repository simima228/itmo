package com.example.models;

public enum MovieGenre {
    ACTION,
    ADVENTURE,
    TRAGEDY,
    THRILLER,
    FANTASY;

    public static String getGenre() {
        StringBuilder sb = new StringBuilder();
        for (MovieGenre genre : MovieGenre.values()) {
            sb.append(genre.name()).append("\n");
        }
        return sb.substring(0, sb.length() - 1);
    }
}