package objects;

import enums.IsAlive;
import enums.IsWaiting;
import exceptions.KillException;
import interfaces.*;

public class Human extends Animal implements ChopAble, GiftAble, CallAble, OrderAble, WaitAble, KillAble {

    private IsWaiting isWaiting = IsWaiting.NOTWAITING;

    public Human(String name, Place place) {
        super(name, place);
    }

    @Override
    public void talk(String speech) {
        System.out.printf("%s сказал: %s\n", getNameWithProperty(), speech);
    }

    @Override
    public void chop(Obj obj) {
        obj.setChopped(true);
        System.out.println(getNameWithProperty() + " отрезал " + obj.getNameWithProperty());
    }

    @Override
    public void gift(Human who, Thing what) {
        this.removeItem(what);
        who.addItem(what);
        System.out.printf(getNameWithProperty() + " подарил %s " + what.getNameWithProperty() + "\n", who);
    }

    @Override
    public void call(Place where) {
        System.out.println(getNameWithProperty() + " стал звать с собой в " + where.getNameWithProperty());
    }

    @Override
    public void order(String order) {
        System.out.println(getNameWithProperty() + " велел " + order);
    }

    @Override
    public void wait(Human who, Place where) {
        this.isWaiting = IsWaiting.WAITING;
        System.out.println(getNameWithProperty() + " ждет " + who.getNameWithProperty()
                + " в " + where.getNameWithProperty());
    }

    @Override
    public void kill(Animal animal) throws KillException {
        if (animal.getIsAlive() == IsAlive.DEAD) {
            throw new KillException();
        }
        animal.setIsAlive(IsAlive.DEAD);
        System.out.println(getNameWithProperty() + " убил " + animal.getNameWithProperty());
    }

}
