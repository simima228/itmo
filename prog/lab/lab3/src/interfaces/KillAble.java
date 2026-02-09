package interfaces;

import exceptions.KillException;
import objects.Animal;

public interface KillAble {
    void kill(Animal animal) throws KillException;
}
