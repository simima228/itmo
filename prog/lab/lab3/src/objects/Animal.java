package objects;

import enums.DirectionOfView;
import enums.IsAlive;
import exceptions.GiftException;
import interfaces.LookAble;
import interfaces.WalkAble;

import java.util.ArrayList;
import java.util.List;

public class Animal extends Entity implements LookAble, WalkAble {

    private DirectionOfView direction = DirectionOfView.FORWARD;
    private Place place;
    private List<Thing> inventory = new ArrayList<Thing>();

    public Animal(String name, Place place) {
        super(name);
        this.place = place;
        this.setIsAlive(IsAlive.ALIVE);
    }

    @Override
    public void look(DirectionOfView direction) {
        this.direction = direction;
        System.out.println(getNameWithProperty() + direction.getText());
    }

    @Override
    public void walk(Place where) {
        this.place = where;
        System.out.println(getNameWithProperty() + " пошел к " + this.place);
    }

    public String getInventory() {
        StringBuilder sb = new StringBuilder();
        for (Thing thing : inventory) {
            sb.append(thing.toString());
            sb.append(", ");
        }
        return sb.toString();
    }

    public void setInventory(List<Thing> inventory) {
        this.inventory = inventory;
    }

    public void addItem(Thing item) {
        this.inventory.add(item);
    }

    public void removeItem(Thing item) {
        if (!inventory.contains(item)) {
            throw new GiftException();
        }
        this.inventory.remove(item);
    }

}
