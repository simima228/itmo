package pokemons;

import attacks.Blizzard;
import attacks.Scratch;
import attacks.TailWhip;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;


public class Psyduck extends Pokemon{
    public Psyduck(String name, int lvl) {
        super(name, lvl);
        setStats(50.0, 52.0, 48.0, 65.0, 50.0, 55.0);
        setType(Type.WATER);
        setMove(new Scratch(), new Blizzard(), new TailWhip());
    }
}
