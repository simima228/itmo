package model;

import java.io.Serializable;

public record Location(long x, int y, double z) implements Serializable {

    public String toString() {
        return "Локация" + ": " + "[x = " + x + ", y = " + y + ", z = " + z + "]";
    }
}