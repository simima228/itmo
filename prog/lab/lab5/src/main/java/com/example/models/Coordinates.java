package com.example.models;

public class Coordinates {
    private Float x; //Поле не может быть null
    private long y;

    public Coordinates(Float x, long y) {
        this.x = x;
        this.y = y;
    }

    public Float getX() {
        return x;
    }
    public long getY() {
        return y;
    }

    @Override
    public String toString(){
        return "Кординаты" + ": " + "[x = " + x + ", y = " + y + "]";
    }
}