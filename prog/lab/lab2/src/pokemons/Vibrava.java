package pokemons;

import attacks.BugBuzz;
import ru.ifmo.se.pokemon.Type;


public class Vibrava extends Trapinch{
    public Vibrava(String name, int lvl) {
        super(name, lvl);
        setStats(50.0, 70.0, 50.0, 50.0, 50.0, 70.0);
        addType(Type.DRAGON);
        addMove(new BugBuzz());
    }
}