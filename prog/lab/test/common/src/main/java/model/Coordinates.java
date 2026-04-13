package model;

import java.io.Serializable;

public class Coordinates implements Serializable, Comparable<Coordinates>{
    private final Float x; //Поле не может быть null
    private final long y;

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

    @Override
    public int compareTo(Coordinates o) {
        if (this.x != o.x){
            return Float.compare(this.x, o.x);
        }
        return Long.compare(this.y, o.y);
    }
}