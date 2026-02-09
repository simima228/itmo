package objects;

import enums.Time;

public class Place extends Obj {

    private Time time = Time.UNKNOWN;

    public Place(String name) {
        super(name);
    }

    public void setTime(Time time) {

        this.time = time;
        if (time == Time.NOW) {
            System.out.print("тут ");
        }

    }

}
