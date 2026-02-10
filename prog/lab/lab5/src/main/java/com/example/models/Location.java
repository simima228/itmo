package com.example.models;

import com.example.etc.CheckInterface;

public class Location implements CheckInterface {
    private long x;
    private int y;
    private double z;

    public Location(long x, int y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }


    @Override
    public boolean check() {
        return true;
    }
}