package com.example.models;

import com.example.etc.CheckInterface;

public class Coordinates implements CheckInterface {
    private Float x; //Поле не может быть null
    private long y;

    public Coordinates(Float x, long y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean check() {
        return x != null;
    }
}