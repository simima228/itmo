package model;

import java.io.Serializable;

public record Coordinates(Float x, long y) implements Serializable, Comparable<Coordinates> {

    @Override
    public String toString() {
        return "Кординаты" + ": " + "[x = " + x + ", y = " + y + "]";
    }

    @Override
    public int compareTo(Coordinates o) {
        if (this.x != o.x) {
            return Float.compare(this.x, o.x);
        }
        return Long.compare(this.y, o.y);
    }
}