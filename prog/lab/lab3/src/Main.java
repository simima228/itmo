import enums.DirectionOfView;
import enums.Time;
import exceptions.GiftException;
import exceptions.KillException;
import objects.Animal;
import objects.Human;
import objects.Place;
import objects.Thing;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws KillException {

        Place place1 = new Place("Место 1");
        Animal snake = new Animal("Змей", place1);
        Human anglerfish = new Human("Удильщик", place1);
        Thing head1 = new Thing("Голова");
        Thing head2 = new Thing("Голова");
        Thing head3 = new Thing("Голова");
        head1.setProperty("Первая ");
        head2.setProperty("Вторая ");
        head3.setProperty("Третья ");
        snake.setInventory(new ArrayList<Thing>(List.of(head1, head2, head3)));

        anglerfish.talk("Смотри, дом твой горит!");
        snake.look(DirectionOfView.BACK);
        place1.setTime(Time.NOW);
        anglerfish.chop(head3);
        anglerfish.kill(snake);

        Place place2 = new Place("Место 2");
        Human daughter = new Human("Дочь", place2);
        daughter.setProperty("Царская ");
        Human man = new Human("Молодец", place2);
        Thing ring = new Thing("Кольцо");
        ring.setProperty("именное ");
        daughter.addItem(ring);
        try {
            daughter.gift(man, ring);
        } catch (GiftException e) {
            throw new GiftException();
        }
        Place place3 = new Place("Дворец");
        place3.setProperty("Царский ");
        daughter.call(place3);
        man.order("идти домой");
        Place place4 = new Place("у старушки-вдовы");
        man.walk(place4);
        Human mate = new Human("Товарищи", place4);
        mate.wait(man, place4);
    }

}
