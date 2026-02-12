package com.example.models;

public class Location {
    private final long x;
    private final int y;
    private final double z;

    public Location(long x, int y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public long getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public String toString() {
        return "Локация" + ": " + "[x = " + x + ", y = " + y + ", z = " + z + "]";
    }
}